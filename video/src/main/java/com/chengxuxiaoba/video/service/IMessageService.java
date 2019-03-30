package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.AccountMessageRelationShip;
import com.chengxuxiaoba.video.model.po.Message;

import java.util.List;

public interface IMessageService {
    KeyValuePair<Boolean, String> createNewMessage(Message message);

    KeyValuePair<Boolean, String> createNewMessage(Message message, Boolean isBroadcast);

    Message getMessage(Integer messageId);

    Boolean setRead(Integer accountId, Integer messageId);

    Boolean broadcastMessage(Integer messageId, List<Integer> accountList);

    Boolean broadcastMessage(Integer messageId);

    Integer getUnReadCount(Integer accountId);

    PageResult<Message> getMessageListByAccountId(Integer accountId, PageInfo pageInfo);

    PageResult<AccountMessageRelationShip> getAccountMessageRelationShipListByAccountId(Integer accountId, PageInfo pageInfo);
}
