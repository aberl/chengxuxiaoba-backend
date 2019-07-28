package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.mapper.MessageMapper;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.model.po.AccountMessageRelationShip;
import com.chengxuxiaoba.video.model.po.Message;
import com.chengxuxiaoba.video.model.query.MessageQuery;
import com.chengxuxiaoba.video.service.IBaseService;
import com.chengxuxiaoba.video.service.IMessageService;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.util.ListUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService extends IBaseService<Message> implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private IUserService userService;


    @Override
    public KeyValuePair<Boolean, String> createNewMessage(Message message, List<Integer> accountList) {
        Integer primaryKey = messageMapper.insertMessage(message);

        if (primaryKey <= 0)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.MessageCreateFail);

        if(!ListUtil.isNullOrEmpty(accountList)) {
            Integer messageId = message.getId();

            Boolean broadcastFlag = broadcastMessage(messageId, accountList);
        }

        return new KeyValuePair<Boolean, String>(true, ResultMessage.Success);
    }

    @Override
    public KeyValuePair<Boolean, String> createNewMessage(Message message) {
        if (message == null)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.DataIsNULL);

        Integer primaryKey = messageMapper.insertMessage(message);

        return createNewMessage(message, false);
    }

    @Override
    public KeyValuePair<Boolean, String> createNewMessage(Message message, Boolean isBroadcast) {
        Integer primaryKey = messageMapper.insertMessage(message);

        if (primaryKey <= 0)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.MessageCreateFail);

        if (isBroadcast == null || !isBroadcast)
            return new KeyValuePair<Boolean, String>(true, ResultMessage.Success);

        Integer messageId = message.getId();

        Boolean flag = broadcastMessage(messageId);
        if (!flag)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.MessageBroadCastFail);

        return new KeyValuePair<Boolean, String>(true, ResultMessage.Success);
    }

    @Override
    public Message getMessage(Integer messageId) {
        if (messageId == null || messageId == 0)
            return null;

        Message message = messageMapper.getMessage(messageId);

        return message;
    }

    @Override
    public Message getMessageByContent(String content) {
        if (StringUtil.isNullOrEmpty(content))
            return null;

        Message message = messageMapper.getMessageByContent(content);

        return message;
    }


    @Override
    public Boolean setRead(Integer accountId, List<Integer> messageIdList)
    {
        Integer primaryKey=messageMapper.setRead(accountId, messageIdList);

        return primaryKey>0;
    }


    @Override
    public Boolean broadcastMessage(Integer messageId) {
        if (messageId == null || messageId == 0)
            return false;

        List<Account> accountList = userService.getAllUserList();

        if (ListUtil.isNullOrEmpty(accountList))
            return false;

        List<Integer> accountIdList = accountList.stream().map(Account::getId).collect(Collectors.toList());

        broadcastMessage(messageId, accountIdList);

        return true;
    }

    @Override
    public Boolean broadcastMessage(Integer messageId, List<Integer> accountList) {
        if (messageId == null || messageId == 0)
            return false;

        if (ListUtil.isNullOrEmpty(accountList))
            return false;

        Message message = messageMapper.getMessage(messageId);

        if (message == null)
            return false;

        Integer messageCategory = message.getCategory();

        AccountMessageRelationShip accountMessageRelationShip = null;

        List<AccountMessageRelationShip> accountMessageRelationShipList = new ArrayList<>();

        for (Integer accountId : accountList) {
            accountMessageRelationShip = new AccountMessageRelationShip(accountId, messageCategory, messageId);
            accountMessageRelationShipList.add(accountMessageRelationShip);
        }

        messageMapper.insertAccountMessageRelationShip(accountMessageRelationShipList);

        return true;
    }

    @Override
    public Integer getUnReadCount(Integer accountId) {
        if (accountId == null || accountId == 0)
            return 0;

        Integer unreadCount = messageMapper.getUnReadCount(accountId);

        return unreadCount;
    }


    @Override
    public PageResult<Message> getMessageListByAccountId(Integer accountId, Boolean isRead, PageInfo pageInfo) {
        PageResult<AccountMessageRelationShip> accountMessageRelationShip = getAccountMessageRelationShipListByAccountId(accountId, isRead,pageInfo);

        if (accountMessageRelationShip == null)
            return new PageResult<Message>(pageInfo.getCurrentPageNum(),
                    new Long(0), null);

        List<AccountMessageRelationShip> accountMessageRelationShipList = accountMessageRelationShip.getData();

        if (ListUtil.isNullOrEmpty(accountMessageRelationShipList))
            return new PageResult<Message>(pageInfo.getCurrentPageNum(),
                    new Long(0), null);

        List<Message> messageList = new ArrayList<>();

        for (AccountMessageRelationShip item : accountMessageRelationShipList) {
            messageList.add(item.getMessage());
        }

        PageResult<Message> pageResult = new PageResult<Message>(accountMessageRelationShip.getCurrentNum(),
                accountMessageRelationShip.getTotalCount(), messageList);

        return pageResult;
    }

    @Override
    public PageResult<AccountMessageRelationShip> getAccountMessageRelationShipListByAccountId(Integer accountId, Boolean isRead, PageInfo pageInfo) {
        if (accountId == null || accountId == 0)
            return new PageResult<AccountMessageRelationShip>(pageInfo.getCurrentPageNum(),
                    new Long(0), null);

        MessageQuery query = new MessageQuery();
        query.setAccountId(accountId);
        query.setPageInfo(pageInfo);
        query.setRead(isRead);

        PageHelper.startPage(query.getPageInfo().getCurrentPageNum(), query.getPageInfo().getPageSize());

        PageHelper.orderBy(query.getPageInfo().getSort());

        Page<AccountMessageRelationShip> retList = messageMapper.getAccountMessageRelationShipList(query);

        PageResult<AccountMessageRelationShip> pageResult = new PageResult<AccountMessageRelationShip>(retList.getPageNum(), retList.getTotal(), retList.getResult());

        return pageResult;
    }

    @Override
    public  KeyValuePair<Boolean, String> deleteMessage(Integer accountId, List<Integer> messageIdList)
    {
        if(accountId == null || accountId==0)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.ACCOUNTIDCANNOTBENULL);

        if(ListUtil.isNullOrEmpty(messageIdList))
            return new KeyValuePair<Boolean, String>(false, ResultMessage.MESSAGEIDLISTCANNOTBENULL);

        Integer primaryKey = messageMapper.deleteMessage(accountId, messageIdList);

        return new KeyValuePair<Boolean, String>(true, ResultMessage.Success);
    }
}
