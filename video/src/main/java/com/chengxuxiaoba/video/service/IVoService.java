package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.Request.VO.AnswerRequestVo;
import com.chengxuxiaoba.video.model.Request.VO.EvaluateRequestVo;
import com.chengxuxiaoba.video.model.Request.VO.IssueRequestVo;
import com.chengxuxiaoba.video.model.Request.VO.VideoRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.*;
import com.chengxuxiaoba.video.model.po.*;

import java.util.List;

public interface IVoService {
    UserResponseVo convertToUserResponseVo(Account account);

    CourseResponseVo convertToCourseResponseVo(Course course);

    List<CourseResponseVo> convertToCourseResponseVo(List<Course> course);

    CourseModuleResponseVo convertToCourseModuleResponseVo(CourseModule courseModule);

    List<CourseModuleResponseVo> convertToCourseModuleResponseVo(List<CourseModule> courseModuleList);

    Video convertToVideo(VideoRequestVo videoVo);

    VideoResponseVo convertToVideoResponseVo(Video video);

    List<VideoResponseVo> convertToVideoResponseVo(List<Video> videoList);

    Evaluate convertToEvalueate(EvaluateRequestVo evaluateRequestVo);

    EvaluateResponseVo convertToEvaluateResponseVo(Evaluate evaluate);

    List<EvaluateResponseVo> convertToEvaluateResponseVo(List<Evaluate> evaluateList);

    Issue convertToIssue(IssueRequestVo issueRequestVo);

    IssueResponseVo convertIssueResponseVo(Issue issue, Account account);

    List<IssueResponseVo> convertIssueResponseVo(List<Issue> issueList);

    Answer convertToAnswer(AnswerRequestVo answerRequestVo);

    AnswerResponseVo convertAnswerResponseVo(Answer answer, Account account);

    List<AnswerResponseVo> convertAnswerResponseVo(List<Answer> answerList);
}
