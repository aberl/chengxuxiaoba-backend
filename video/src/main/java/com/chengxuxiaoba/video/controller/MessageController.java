package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.model.Request.VO.MessageRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.EvaluateResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.MessageResponseVo;
import com.chengxuxiaoba.video.model.po.Evaluate;
import com.chengxuxiaoba.video.model.po.Message;
import com.chengxuxiaoba.video.service.IAuthenticationService;
import com.chengxuxiaoba.video.service.IMessageService;
import com.chengxuxiaoba.video.service.IVoService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class MessageController extends BaseController{
    @Autowired
    private IVoService voService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IAuthenticationService authenticationService;


    @PostMapping("/messages")
    public Result<Boolean> createMessage(@RequestBody MessageRequestVo requestBody) {
        Message message=voService.convertToMessage(requestBody);

        if(message == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        KeyValuePair<Boolean, String> result= messageService.createNewMessage(message, requestBody.getBroadcast());

        Boolean flag=result.getKey();

        if(!flag)
            return new Result<Boolean>(ResultCode.Error, result.getKey(),result.getValue());

        return new Result<Boolean>(ResultCode.Success, result.getKey(), result.getValue());
    }

    @GetMapping("/messages")
    public Result<PageResult<MessageResponseVo>> getMessageListByAccountId(@RequestParam(name = "isread", required = false) Boolean isRead,
                                                                        @RequestParam("pagenum") Integer pageNum,
                                                                        @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                                        @RequestParam(name = "sort", required = false) String sort)
    {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        Integer accountId = currentLoginUserModel.getUserId();

        if (accountId == null || accountId == 0)
            return new Result<PageResult<MessageResponseVo>>(ResultCode.Error, null, ResultMessage.ParameterError);

        PageInfo pageInfo = super.generatePageInfo(pageNum, pageSize, sort);

        PageResult<Message> pageData = messageService.getMessageListByAccountId(accountId, isRead, pageInfo);

        List<MessageResponseVo> resultList = voService.convertToMessageResponseVo(pageData.getData());

        PageResult<MessageResponseVo> result = new PageResult<MessageResponseVo>(pageData.getCurrentNum(), pageData.getTotalCount(), resultList);

        return new Result<PageResult<MessageResponseVo>>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/messages/{id}")
    public Result<MessageResponseVo> getMessage(@PathVariable("id") Integer id) {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        Integer accountId = currentLoginUserModel.getUserId();

        if((accountId ==null || accountId==0)|| (id == null || id == 0))
            return new Result<MessageResponseVo>(ResultCode.Error, null, ResultMessage.ParameterError);

        Message message = messageService.getMessage(id);

        if(message == null)
            return new Result<MessageResponseVo>(ResultCode.Error, null, ResultMessage.MessageIsNotExist);

        messageService.setRead(accountId, Arrays.asList(id));

        MessageResponseVo messageResponseVo = voService.convertToMessageResponseVo(message);

        return new Result<MessageResponseVo>(ResultCode.Success, messageResponseVo, ResultMessage.Success);
    }

    @GetMapping("/messages/unread/count")
    public Result<Integer> getMessageUnReadCountByAccountId()
    {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        Integer accountId = currentLoginUserModel.getUserId();

        if (accountId == null || accountId == 0)
            return new Result<Integer>(ResultCode.Error, null, ResultMessage.ParameterError);

        Integer unreadCount = messageService.getUnReadCount(accountId);

        return new Result<Integer>(ResultCode.Success, unreadCount, ResultMessage.Success);
    }

    @DeleteMapping("/messages")
    public Result<Boolean> deleteMessage(@RequestBody MessageRequestVo requestBody) {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        Integer accountId = currentLoginUserModel.getUserId();

        KeyValuePair<Boolean, String> result= messageService.deleteMessage(accountId, requestBody.getMessageIdList());

        if(!result.getKey())
            return new Result<Boolean>(ResultCode.Error, result.getKey(),result.getValue());

        return new Result<Boolean>(ResultCode.Success, result.getKey(), result.getValue());
    }

    @PutMapping("/messages")
    public Result<Boolean> readMessage(@RequestBody MessageRequestVo requestBody) {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        Integer accountId = currentLoginUserModel.getUserId();

        Boolean flag= messageService.setRead(accountId, requestBody.getMessageIdList());

        if(!flag)
            return new Result<Boolean>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);
    }
}
