package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.AccountMessageRelationShip;
import com.chengxuxiaoba.video.model.po.Message;
import com.chengxuxiaoba.video.model.query.MessageQuery;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    Integer insertMessage(Message message);

    void insertAccountMessageRelationShip(@Param("accountMessageRelationShipList") List<AccountMessageRelationShip> accountMessageRelationShipList);

    Message getMessage(Integer id);

    Message getMessageByContent(String content);

    Integer setRead(@Param("accountId")Integer accountId, @Param("messageId")Integer messageId);

    Integer updateMessage(Message message);

    Page<AccountMessageRelationShip> getAccountMessageRelationShipList(MessageQuery query);

    Integer getUnReadCount(Integer accountId);
}
