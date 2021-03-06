package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.AccountMessageRelationShip;
import com.chengxuxiaoba.video.model.po.Message;

import java.util.List;

public interface IMessageService {
    KeyValuePair<Boolean, String> createNewMessage(Message message, List<Integer> accountList);

    KeyValuePair<Boolean, String> createNewMessage(Message message);

    KeyValuePair<Boolean, String> createNewMessage(Message message, Boolean isBroadcast);

    Message getMessage(Integer messageId);

    Message getMessageByContent(String content);

    Boolean setRead(Integer accountId, List<Integer> messageIdList);

    Boolean broadcastMessage(Integer messageId, List<Integer> accountList);

    Boolean broadcastMessage(Integer messageId);

    Integer getUnReadCount(Integer accountId);

    PageResult<Message> getMessageListByAccountId(Integer accountId, Boolean isRead, PageInfo pageInfo);

    PageResult<AccountMessageRelationShip> getAccountMessageRelationShipListByAccountId(Integer accountId, Boolean isRead, PageInfo pageInfo);

    KeyValuePair<Boolean, String> deleteMessage(Integer accountId, List<Integer> messageIdList);
}
