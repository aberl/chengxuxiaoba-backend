package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.jwt.JWTTokenFactory;
import com.chengxuxiaoba.video.model.CurrentLoginUserModel;
import com.chengxuxiaoba.video.model.JWTToken;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.Response.VO.UserResponseVo;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.service.IAuthenticationService;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.CipherUtil;
import com.chengxuxiaoba.video.util.HttpServletRequestUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    private JWTTokenFactory jwtTokenFactory;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVoService voService;

    @Override
    public String generateCipherPassWord(String mobilePhone, String password) {
        try {
            String encryptStrategy = generateCipherTextByStrategy(mobilePhone, password);

            String cipherText = CipherUtil.generateMD5CipherText(encryptStrategy);

            return cipherText;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public KeyValuePair<Boolean, String> validatePassWord(String mobilePhone, String password) {
        Account account = userService.getUserByMobilePhone(mobilePhone);

        if (account == null)
            return new KeyValuePair<>(false, ResultMessage.UserIsNotExist);

        String encryptStrategy = generateCipherTextByStrategy(mobilePhone, password);

        String cipherText = account.getPassword();

        Boolean isValidate = CipherUtil.matchMD5CipherText(encryptStrategy, cipherText);

        return new KeyValuePair<>(isValidate, isValidate ? ResultMessage.Success : ResultMessage.PasswordConfirmWrong);
    }

    @Override
    public String generateToken(String mobilePhone) {
        Account account = userService.getUserByMobilePhone(mobilePhone);
        if (account == null)
            return null;

        UserResponseVo userResponseVo = voService.convertToUserResponseVo(account);

        JWTToken jwtUserInfo = new JWTToken();
        jwtUserInfo.setUserId(String.format("%s", userResponseVo.getId()));
        jwtUserInfo.setWechat_account(userResponseVo.getWechatAccount());
        jwtUserInfo.setStatus(String.format("%s", userResponseVo.getStatus()));
        jwtUserInfo.setName(userResponseVo.getName());
        jwtUserInfo.setRole(userResponseVo.getRole());
        jwtUserInfo.setMobilePhone(userResponseVo.getMobilePhoneNo());
        jwtUserInfo.setIsOverDue(userResponseVo.getIsOverDue());
        jwtUserInfo.setPermissions(userResponseVo.getPermissions());

        String tokenStr = jwtTokenFactory.generateTokenString(jwtUserInfo);

        return tokenStr;
    }

    @Override
    public CurrentLoginUserModel validateToken(String token) {
        try {
            if (StringUtil.isNullOrEmpty(token))
                return null;

            JWTToken jwtToken = jwtTokenFactory.refreshJWTToken(token);
            if (jwtToken == null)
                return null;
            CurrentLoginUserModel currentLoginUserModel = CurrentLoginUserModel.builder()
                    .userId(Integer.valueOf(jwtToken.getUserId()))
                    .isOverDue(jwtToken.getIsOverDue())
                    .mobilePhone(jwtToken.getMobilePhone())
                    .name(jwtToken.getName())
                    .role(Integer.valueOf(jwtToken.getRole()))
                    .status(Integer.valueOf(jwtToken.getStatus()))
                    .wechat_account(jwtToken.getWechat_account())
                    .permissions(jwtToken.getPermissions())
                    .build();

            return currentLoginUserModel;

        } catch (Exception ex) {
            //TODO log
            return null;
        }
    }

    @Override
    public KeyValuePair<Boolean, String> validateAuthorization(String token) {
        return null;
    }

    @Override
    public void setCurrentLoginUserModelInRequest(HttpServletRequest request) {
        request = request == null ? HttpServletRequestUtil.getHttpServletRequest() : request;

        String authorizationToken = request.getHeader("AuthorizationToken");

        if (!StringUtil.isNullOrEmpty(authorizationToken)) {
            CurrentLoginUserModel loginUserModel = validateToken(authorizationToken);

            request.setAttribute("CurrentLoginUserModel", loginUserModel);
        }
    }

    @Override
    public CurrentLoginUserModel getCurrentLoginUserModelFromRequest() {
        try {
            CurrentLoginUserModel currentLoginUserModel = HttpServletRequestUtil.getInstance("CurrentLoginUserModel", CurrentLoginUserModel.class);

            return currentLoginUserModel;
        } catch (Exception ex) {
            //TODO log
            return null;
        }
    }

    private String generateCipherTextByStrategy(String mobilePhone, String password) {
        String encryptStrategy = String.format("%s_%s", mobilePhone, password);
        return encryptStrategy;
    }

}
