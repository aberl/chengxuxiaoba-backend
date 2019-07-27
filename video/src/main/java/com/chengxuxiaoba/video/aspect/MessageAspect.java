package com.chengxuxiaoba.video.aspect;

import com.chengxuxiaoba.video.constant.MessageCategory;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.model.po.CourseModule;
import com.chengxuxiaoba.video.model.po.Message;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.service.ICourseService;
import com.chengxuxiaoba.video.service.IMessageService;
import com.chengxuxiaoba.video.service.imp.CourseService;
import com.chengxuxiaoba.video.util.ArrayUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    private Account currentAccount=null;

    static {

    }

    @Pointcut("execution(* com.chengxuxiaoba.video.service.IVideoService.createNewVideo(..))")
    public void videoPointCut() {
    }

    @Pointcut("execution(* com.chengxuxiaoba.video.service.ICourseService.createNewCourse(..))")
    public void coursePointCut() {
    }

    @Pointcut("execution(* com.chengxuxiaoba.video.service.ICourseService.createNewCourseModule(..))")
    public void courseModulePointCut() {
    }

    @Pointcut("execution(* com.chengxuxiaoba.video.service.IIssueService.answerIssue(..))")
    public void answerIssuePointCut() {
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

        System.out.println(":::" + joinPoint);
    }

    //     @AfterReturning(value ="courseModulePointCut()", returning = "result")
    public void addcourseModuleNotificationMessage(JoinPoint joinPoint, Integer result) {
        System.out.println(":::" + joinPoint);
    }

    @AfterReturning(value = "answerIssuePointCut()", returning = "result")
    public void addAnswerIssueNotificationMessage(JoinPoint joinPoint, KeyValuePair<Boolean, String> result) {
        System.out.println(":::" + joinPoint);
    }
}
