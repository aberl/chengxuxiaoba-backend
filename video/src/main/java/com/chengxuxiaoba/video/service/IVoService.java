package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.Request.VO.*;
import com.chengxuxiaoba.video.model.Response.VO.*;
import com.chengxuxiaoba.video.model.po.*;

import java.util.List;

public interface IVoService {
    Account convertToUser(AccountRequestVo account);

    UserResponseVo convertToUserResponseVo(Account account);

    UserResponseVo convertToUserResponseVo(Account account, List<AccountRoleRelationShip> accountRoleRelationShipList);

    List<UserResponseVo> convertToUserResponseVo(List<Account> accountList);

    Course convertToCourse(CourseRequestVo course);

    CourseModule convertToCourseModule(CourseModuleRequestVo course);

    CourseResponseVo convertToCourseResponseVo(Course course);

    List<CourseResponseVo> convertToCourseResponseVo(List<Course> course);

    CourseModuleResponseVo convertToCourseModuleResponseVo(CourseModule courseModule);

    List<CourseModuleResponseVo> convertToCourseModuleResponseVo(List<CourseModule> courseModuleList);

    Video convertToVideo(VideoRequestVo videoVo);

    VideoResponseVo convertToVideoResponseVo(Video video);

    VideoResponseVo convertToVideoResponseVo(Video video, Boolean isLoadExtentProperties);

    List<VideoResponseVo> convertToVideoResponseVo(List<Video> videoList);

    List<VideoResponseVo> convertToVideoResponseVo(List<Video> videoList, Boolean isLoadExtentProperties);

    Evaluate convertToEvalueate(EvaluateRequestVo evaluateRequestVo);

    EvaluateResponseVo convertToEvaluateResponseVo(Evaluate evaluate);

    EvaluateResponseVo convertToEvaluateResponseVo(Evaluate evaluate,String accountName);

    List<EvaluateResponseVo> convertToEvaluateResponseVo(List<Evaluate> evaluateList);

    Issue convertToIssue(IssueRequestVo issueRequestVo);

    IssueResponseVo convertIssueResponseVo(Issue issue, Account account);

    IssueResponseVo convertIssueResponseVo(Issue issue);

    List<IssueResponseVo> convertIssueResponseVo(List<Issue> issueList);

    Answer convertToAnswer(AnswerRequestVo answerRequestVo);

    AnswerResponseVo convertAnswerResponseVo(Answer answer, Account account);

    List<AnswerResponseVo> convertAnswerResponseVo(List<Answer> answerList);

    Message convertToMessage(MessageRequestVo messageRequestVo);

    MessageResponseVo convertToMessageResponseVo(Message message);

    List<MessageResponseVo> convertToMessageResponseVo(List<Message> messageList);

    UploadFile convertToUploadFile(UploadFileRequestVo uploadFileRequestVo);

    UploadFileResponseVo convertToUploadFileResponseVo(UploadFile uploadFile);

    RoleResponseVo convertToRoleResponseVo(Role role);

    RolePaymentResponseVo convertToRolePaymentResponseVo(RolePayment rolePayment);

    List<RolePaymentResponseVo> convertToRolePaymentResponseVo(List<RolePayment> rolePaymentList);

    VideoWatchRecordCourseModuleStatisticResponseVo convertToVideoWatchRecordCourseModuleStatisticResponseVo(VideoWatchRecordCourseModuleStatistic videoWatchRecordCourseModuleStatistic
    ,String courseModuleName, Integer totalCourseModuleVideoCount);

    List<VideoWatchRecordCourseModuleStatisticResponseVo> convertToVideoWatchRecordCourseModuleStatisticResponseVo(List<VideoWatchRecordCourseModuleStatistic> videoWatchRecordCourseModuleStatisticList);
}
