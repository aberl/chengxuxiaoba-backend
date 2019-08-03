package com.chengxuxiaoba.video.aspect;

import com.chengxuxiaoba.video.constant.MessageCategory;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.po.*;
import com.chengxuxiaoba.video.service.*;
import com.chengxuxiaoba.video.util.ArrayUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MessageAspect {
    @Value("${message.template.newvideo}")
    private String newvideoTemplate;
    @Value("${message.template.newcourse}")
    private String newcourseTemplate;
    @Value("${message.template.register}")
    private String registerTemplate;
    @Value("${message.template.upgradememberlevel}")
    private String upgradememberlevelTemplate;
    @Value("${message.template.answerissue}")
    private String answerissueTemplate;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IIssueService issueService;

    @Autowired
    private IVideoService videoService;

    @Autowired
    private IUserService userService;

    private Account currentAccount=null;

    static {

    }

    @Pointcut("execution(* com.chengxuxiaoba.video.service.IVideoService.createNewVideo(..))")
    public void videoPointCut() {
    }

    @Pointcut("execution(* com.chengxuxiaoba.video.service.ICourseService.createNewCourse(..))")
    public void coursePointCut() {
    }

    @Pointcut("execution(* com.chengxuxiaoba.video.service.IIssueService.answerIssue(..))")
    public void answerIssuePointCut() {
    }
    @Pointcut("execution(* com.chengxuxiaoba.video.service.IUserService.regier(..))")
    public void userRegisterPointCut() {
    }

    @AfterReturning(value = "videoPointCut()", returning = "result")
    public void addVideoNotificationMessage(JoinPoint joinPoint, Boolean result) {
        Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isNullOrEmpty(args))
            return;

        Object object = args[0];

        if (!(object instanceof Video))
            return;

        Video video = (Video) object;

        Integer courseModuleId=video.getCourseModuleId();
        CourseModule courseModule=courseService.getCourseModule(courseModuleId);

        String videoName=video.getName();
        String courseName=courseModule.getCourseName();
        String courseModuleName=courseModule.getName();

       String content=String.format(newvideoTemplate,courseName,courseModuleName,videoName);

       Message message=Message.build(MessageCategory.System.getValue(),"新增视频", content);

        messageService.createNewMessage(message, true);
    }

    @AfterReturning(value = "coursePointCut()", returning = "result")
    public void addCoursePointCutNotificationMessage(JoinPoint joinPoint, Integer result) {
        Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isNullOrEmpty(args))
            return;

        Object object = args[0];

        if (!(object instanceof Course))
            return;

        Course course=(Course)object;
        String courseName=course.getName();

        String content=String.format(newcourseTemplate,courseName);

        Message message=Message.build(MessageCategory.System.getValue(),"新增课程", content);

        messageService.createNewMessage(message, true);

    }

    @AfterReturning(value = "answerIssuePointCut()", returning = "result")
    public void addAnswerIssueNotificationMessage(JoinPoint joinPoint, KeyValuePair<Boolean, String> result) {
        Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isNullOrEmpty(args))
            return;

        Object object = args[0];

        if (!(object instanceof Answer))
            return;

        Answer answer = (Answer)object;

        Issue issue=issueService.getSingle(answer.getIssueId());

        Video video = videoService.getSingle(issue.getVideoId());

        String content=String.format(answerissueTemplate,video.getName());

        Message message=Message.build(MessageCategory.User.getValue(),"问题答复", content);

        messageService.createNewMessage(message, Arrays.asList(issue.getQuestionerId()));
    }
    @AfterReturning(value = "userRegisterPointCut()", returning = "result")
    public void accountRegisterNotificationMessage(JoinPoint joinPoint, Boolean result) {
        if(!result)
            return;

        Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isNullOrEmpty(args))
            return;

        Object object = args[0];

        String mobilePhone=object.toString();

        Account account=userService.getUserByMobilePhone(mobilePhone);

        Message message=Message.build(MessageCategory.User.getValue(),"新用户注册", registerTemplate);

        messageService.createNewMessage(message, Arrays.asList(account.getId()));
    }
}
