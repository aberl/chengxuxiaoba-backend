package com.chengxuxiaoba.video.service.imp.lsm;

import com.chengxuxiaoba.video.model.lsm.VerifyResult;
import com.chengxuxiaoba.video.util.HttpUtils;
import com.chengxuxiaoba.video.util.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LuoSiMaoService {

    @Value("${captcha.luosimao.apikey}")
    private String api_key;

    @Value("${captcha.luosimao.verifyurl}")
    private String verifyurl;

    /**
     * verify captcha about luosimao
     * @param captcha
     * @return
     */
    public VerifyResult verifyCaptcha(String captcha) {
        try {
            Map<String, String> postData = new HashMap<>();

            postData.put("api_key", api_key);
            postData.put("response", captcha);

            String verifyResultStr = HttpUtils.postForm(verifyurl, postData);

            VerifyResult verifyResult = JSONUtil.deserializeJSONStr(verifyResultStr, VerifyResult.class);

            return verifyResult;
        } catch (Exception ex) {
            return VerifyResult.builder().error(-1).res(String.format("%s", ex)).build();
        }
    }
}
