package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.Request.VO.IssueRequestVo;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.Issue;
import com.chengxuxiaoba.video.service.IIssueService;
import com.chengxuxiaoba.video.service.IVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IssueController {

    @Autowired
    private IIssueService issueService;
    @Autowired
    private IVoService voService;

    @PostMapping("/issues")
    public Result<Boolean> createIssue(@RequestBody IssueRequestVo requestBody) throws IOException {
        Issue issue = voService.convertToIssue(requestBody);

        KeyValuePair<Boolean, String> result = issueService.createNewIssue(issue);

        return new Result<Boolean>(ResultCode.Success, result.getKey(), result.getValue());
    }
}
