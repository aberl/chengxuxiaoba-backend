package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.constant.RolePaymentTypeEnum;
import com.chengxuxiaoba.video.model.Request.VO.*;
import com.chengxuxiaoba.video.model.Response.VO.*;
import com.chengxuxiaoba.video.model.po.*;
import com.chengxuxiaoba.video.service.ICourseService;
import com.chengxuxiaoba.video.service.IIssueService;
import com.chengxuxiaoba.video.service.IRoleService;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoService implements IVoService {
    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private IIssueService issueService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ICourseService courseService;

    @Override
    public Account convertToUser(AccountRequestVo accountRequestVo) {
        if (accountRequestVo == null)
            return null;

        Account account = new Account();

        BeanUtils.copyProperties(accountRequestVo, account);

        return account;
    }

    @Override
    public UserResponseVo convertToUserResponseVo(Account account) {
        List<AccountRoleRelationShip> accountRoleRelationShipList = userService.getAccountRoleRelationShipList(new ArrayList<>(Arrays.asList(account.getId())));

        UserResponseVo userResponseVo = convertToUserResponseVo(account, accountRoleRelationShipList);

        if (userResponseVo != null) {
            AccountVipTimeRange accountVipTimeRange = userService.getVipTimeRange(userResponseVo.getId());
            if (accountVipTimeRange != null) {
                userResponseVo.setVipStartDate(accountVipTimeRange.getStartDate());
                userResponseVo.setVipEndDate(accountVipTimeRange.getEndDate());

                Integer diffDays = DateUtil.differentDays(new Date(), accountVipTimeRange.getEndDate());

                userResponseVo.setOverDue(diffDays > 0);
            }
        }
        return userResponseVo;
    }

    @Override
    public UserResponseVo convertToUserResponseVo(Account account, List<AccountRoleRelationShip> accountRoleRelationShipList) {
        if (account == null)
            return null;

        UserResponseVo userResponseVo = new UserResponseVo();

        BeanUtils.copyProperties(account, userResponseVo);

        userResponseVo.setStatusDesc(CommonStatus.getEnum(account.getStatus()).toString());

        accountRoleRelationShipList = accountRoleRelationShipList == null ? null : accountRoleRelationShipList.stream().filter(relationship -> {
            return relationship.getAccountId().equals(account.getId());
        }).collect(Collectors.toList());

        if (!ListUtil.isNullOrEmpty(accountRoleRelationShipList)) {
            userResponseVo.setRole(accountRoleRelationShipList.get(0).getRoleId().toString());
        }

        return userResponseVo;
    }

    @Override
    public List<UserResponseVo> convertToUserResponseVo(List<Account> accountList) {
        if (accountList == null)
            return null;
        List<UserResponseVo> userList = new ArrayList<UserResponseVo>();
        if (accountList.size() == 0)
            return userList;

        List<Integer> _accountIdList = accountList.stream().map(account -> account.getId()).collect(Collectors.toList());

        List<AccountRoleRelationShip> accountRoleRelationShipList = userService.getAccountRoleRelationShipList(_accountIdList);

        for (Account user : accountList) {
            userList.add(convertToUserResponseVo(user, accountRoleRelationShipList));
        }
        return userList;
    }

    @Override
    public Course convertToCourse(CourseRequestVo courseVo) {
        if (courseVo == null)
            return null;

        Course course = new Course();

        BeanUtils.copyProperties(courseVo, course);

        return course;
    }

    @Override
    public CourseModule convertToCourseModule(CourseModuleRequestVo courseModuleVo) {
        if (courseModuleVo == null)
            return null;

        CourseModule courseModule = new CourseModule();

        BeanUtils.copyProperties(courseModuleVo, courseModule);

        return courseModule;

    }

    @Override
    public CourseResponseVo convertToCourseResponseVo(Course course) {
        if (course == null)
            return null;
        CourseResponseVo courseResponseVo = new CourseResponseVo();

        BeanUtils.copyProperties(course, courseResponseVo);
        courseResponseVo.setStatusDesc(CommonStatus.getEnum(courseResponseVo.getStatus()).toString());

        List<String> imageNameList = JSONUtil.convertToList(course.getImages());
        if (ListUtil.isNullOrEmpty(imageNameList))
            return courseResponseVo;
        List<UploadFile> imageList = uploadFileService.getUploadFileByNameList(imageNameList);
        if (ListUtil.isNullOrEmpty(imageList))
            return courseResponseVo;
        courseResponseVo.setImageList(new ArrayList<>());
        for (UploadFile image : imageList) {
            courseResponseVo.getImageList().add(convertToUploadFileResponseVo(image));
        }


        return courseResponseVo;
    }

    @Override
    public List<CourseResponseVo> convertToCourseResponseVo(List<Course> courseList) {
        if (ListUtil.isNullOrEmpty(courseList))
            return null;

        List<CourseResponseVo> responseVoList = new ArrayList<>();
        CourseResponseVo courseResponseVo;
        for (Course course : courseList) {
            courseResponseVo = convertToCourseResponseVo(course);
            if (courseResponseVo != null)
                responseVoList.add(courseResponseVo);
        }
        return responseVoList;
    }

    @Override
    public CourseModuleResponseVo convertToCourseModuleResponseVo(CourseModule courseModule) {
        if (courseModule == null)
            return null;

        CourseModuleResponseVo courseModuleResponseVo = new CourseModuleResponseVo();
        BeanUtils.copyProperties(courseModule, courseModuleResponseVo);

        courseModuleResponseVo.setStatusDesc(CommonStatus.getEnum(courseModuleResponseVo.getStatus()).toString());

        VideoSummary videoSummary = videoService.getVideoSummary(courseModule.getId());
        if (videoSummary != null) {
            courseModuleResponseVo.setVideoCount(videoSummary.getVideoCount() == null ? 0 : videoSummary.getVideoCount());
            courseModuleResponseVo.setTotalViewCount(videoSummary.getTotalViewCount() == null ? 0 : videoSummary.getTotalViewCount());
            courseModuleResponseVo.setTotalPraiseCount(videoSummary.getTotalPraiseCount() == null ? 0 : videoSummary.getTotalPraiseCount());
        }

        List<String> imageNameList = JSONUtil.convertToList(courseModule.getImages());
        if (ListUtil.isNullOrEmpty(imageNameList))
            return courseModuleResponseVo;
        List<UploadFile> imageList = uploadFileService.getUploadFileByNameList(imageNameList);
        if (ListUtil.isNullOrEmpty(imageList))
            return courseModuleResponseVo;
        courseModuleResponseVo.setImageList(new ArrayList<>());
        for (UploadFile image : imageList) {
            courseModuleResponseVo.getImageList().add(convertToUploadFileResponseVo(image));
        }

        return courseModuleResponseVo;
    }

    @Override
    public List<CourseModuleResponseVo> convertToCourseModuleResponseVo(List<CourseModule> courseModuleList) {
        if (ListUtil.isNullOrEmpty(courseModuleList))
            return null;

        List<CourseModuleResponseVo> responseVoList = new ArrayList<>();
        CourseModuleResponseVo ourseModuleResponseVo;
        for (CourseModule courseModule : courseModuleList) {
            ourseModuleResponseVo = convertToCourseModuleResponseVo(courseModule);
            if (ourseModuleResponseVo != null)
                responseVoList.add(ourseModuleResponseVo);
        }
        return responseVoList;
    }

    @Override
    public Video convertToVideo(VideoRequestVo videoVo) {
        if (videoVo == null)
            return null;

        Video video = new Video();

        BeanUtils.copyProperties(videoVo, video);

        return video;
    }

    @Override
    public VideoResponseVo convertToVideoResponseVo(Video video)
    {
        return convertToVideoResponseVo(video, null);
    }

    @Override
    public VideoResponseVo convertToVideoResponseVo(Video video, Boolean isLoadExtentProperties) {
        if (video == null)
            return null;

        VideoResponseVo videoResponseVo = new VideoResponseVo();

        BeanUtils.copyProperties(video, videoResponseVo);

        if(isLoadExtentProperties != null && !isLoadExtentProperties)
            return videoResponseVo;

        videoResponseVo.setStatusDesc(CommonStatus.getEnum(videoResponseVo.getStatus()).toString());

        List<String> attachmentList = JSONUtil.convertToList(video.getAttachments());
        attachmentList.add(video.getFile());

        if (ListUtil.isNullOrEmpty(attachmentList))
            return videoResponseVo;

        List<UploadFile> uploadFileList = uploadFileService.getUploadFileByNameList(attachmentList);

        if (ListUtil.isNullOrEmpty(uploadFileList))
            return videoResponseVo;

        videoResponseVo.setAttachmentList(new ArrayList<UploadFileResponseVo>());
        for (UploadFile file : uploadFileList) {
            uploadFileService.setFileNameAsOriginName(file);
            if (file.getName().equalsIgnoreCase(video.getFile())) {
                videoResponseVo.setVideo(convertToUploadFileResponseVo(file));
                continue;
            }

            videoResponseVo.getAttachmentList().add(convertToUploadFileResponseVo(file));
        }

        return videoResponseVo;
    }

    @Override
    public List<VideoResponseVo> convertToVideoResponseVo(List<Video> videoList)
    {
        return convertToVideoResponseVo(videoList, null);
    }

    @Override
    public List<VideoResponseVo> convertToVideoResponseVo(List<Video> videoList, Boolean isLoadExtentProperties) {
        if (ListUtil.isNullOrEmpty(videoList))
            return null;
        List<VideoResponseVo> retList = new ArrayList<>();
        VideoResponseVo videoResponseVo;
        for (Video video : videoList) {
            videoResponseVo = convertToVideoResponseVo(video, isLoadExtentProperties);
            if (videoResponseVo != null)
                retList.add(videoResponseVo);
        }
        return retList;
    }

    @Override
    public Evaluate convertToEvalueate(EvaluateRequestVo evaluateRequestVo) {
        if (evaluateRequestVo == null)
            return null;

        Evaluate evaluate = new Evaluate();

        BeanUtils.copyProperties(evaluateRequestVo, evaluate);

        return evaluate;
    }

    @Override
    public EvaluateResponseVo convertToEvaluateResponseVo(Evaluate evaluate, String accountName) {
        if (evaluate == null)
            return null;

        EvaluateResponseVo evaluateResponseVo = new EvaluateResponseVo();

        BeanUtils.copyProperties(evaluate, evaluateResponseVo);
        evaluateResponseVo.setAccountName(accountName);
        evaluateResponseVo.setStatusDesc(CommonStatus.getEnum(evaluate.getStatus()).toString());

        return evaluateResponseVo;
    }

    @Override
    public EvaluateResponseVo convertToEvaluateResponseVo(Evaluate evaluate) {
        return convertToEvaluateResponseVo(evaluate, null);
    }

    @Override
    public List<EvaluateResponseVo> convertToEvaluateResponseVo(List<Evaluate> evaluateList) {
        if (ListUtil.isNullOrEmpty(evaluateList))
            return null;

        List<EvaluateResponseVo> retList = new ArrayList<>();
        EvaluateResponseVo evaluateResponseVo;

        List<Integer> _accountIdList = new ArrayList<>();
        evaluateList.forEach(evaluate -> {
            _accountIdList.add(evaluate.getAccountId());
        });

        List<Account> _accountList = userService.getUserList(_accountIdList);

        Map<Integer, String> _accountIdNameMap = new HashMap<Integer, String>();
        if (!ListUtil.isNullOrEmpty(_accountList)) {
            _accountList.forEach(account -> {
                _accountIdNameMap.put(account.getId(), account.getName());
            });
        }
        for (Evaluate evaluate : evaluateList) {
            evaluateResponseVo = convertToEvaluateResponseVo(evaluate, _accountIdNameMap.get(evaluate.getAccountId()));
            if (evaluateResponseVo != null)
                retList.add(evaluateResponseVo);
        }
        return retList;
    }

    @Override
    public Issue convertToIssue(IssueRequestVo issueRequestVo) {
        if (issueRequestVo == null)
            return null;

        Issue issue = new Issue();

        BeanUtils.copyProperties(issueRequestVo, issue);

        return issue;
    }

    public List<IssueResponseVo> convertIssueResponseVo(List<Issue> issueList) {
        if (ListUtil.isNullOrEmpty(issueList))
            return null;

        List<IssueResponseVo> issueResponseVoList = new ArrayList<>();
        List<Integer> accountIdList = new ArrayList<>();
        List<Integer> videoIdList = new ArrayList<>();

        List<Integer> issueIdList = new ArrayList<>();

        for (Issue issue : issueList) {
            accountIdList.add(issue.getQuestionerId());
            issueIdList.add(issue.getId());
            videoIdList.add(issue.getVideoId());
        }

        List<Account> accountList = userService.getUserList(accountIdList);
        List<Video> videoList=videoService.getVideoList(videoIdList);
        Map<Integer, Account> accountMap = new HashMap<>();
        Map<Integer, String> videoMap = new HashMap<>();
        for (Account account : accountList)
            accountMap.put(account.getId(), account);

        for (Video video : videoList)
            videoMap.put(video.getId(), video.getName());

        List<Answer> answerList = issueService.getAnswerListByIssueIdList(issueIdList);
        Map<Integer, List<AnswerResponseVo>> answerResponseVoMap = new HashMap<>();
        if (!ListUtil.isNullOrEmpty(answerList)) {
            AnswerResponseVo template;
            for (Answer answer : answerList) {
                template = convertAnswerResponseVo(answer, accountMap.get(answer.getAnswererId()));
                if (!answerResponseVoMap.containsKey(answer.getIssueId())) {
                    answerResponseVoMap.put(answer.getIssueId(), new ArrayList<>(Arrays.asList(template)));
                    continue;
                }
                answerResponseVoMap.get(answer.getIssueId()).add(template);
            }
        }

        IssueResponseVo issueResponseVo = null;
        for (Issue issue : issueList) {
            issueResponseVo = convertIssueResponseVo(issue, accountMap.get(issue.getQuestionerId()));
            issueResponseVo.setAnswerResponseVoList(answerResponseVoMap.get(issue.getId()));
            issueResponseVo.setVideoName(videoMap.get(issue.getVideoId()));
            issueResponseVoList.add(issueResponseVo);
        }
        return issueResponseVoList;
    }

    @Override
    public IssueResponseVo convertIssueResponseVo(Issue issue) {
        if (issue == null)
            return null;

        Integer questionerId = issue.getQuestionerId();

        Account account = userService.getUser(questionerId);

        return convertIssueResponseVo(issue, account);
    }

    @Override
    public IssueResponseVo convertIssueResponseVo(Issue issue, Account account) {
        if (issue == null)
            return null;

        IssueResponseVo issueResponseVo = new IssueResponseVo();

        BeanUtils.copyProperties(issue, issueResponseVo);
        issueResponseVo.setStatusDesc(CommonStatus.getEnum(issue.getStatus()).toString());

        if (account != null) {
            UserResponseVo userResponseVo = convertToUserResponseVo(account);
            issueResponseVo.setUserResponseVo(userResponseVo);
        }

        return issueResponseVo;
    }

    @Override
    public Answer convertToAnswer(AnswerRequestVo answerRequestVo) {
        if (answerRequestVo == null)
            return null;

        Answer answer = new Answer();

        BeanUtils.copyProperties(answerRequestVo, answer);

        return answer;
    }

    @Override
    public AnswerResponseVo convertAnswerResponseVo(Answer answer, Account account) {
        AnswerResponseVo answerResponseVo = new AnswerResponseVo();

        BeanUtils.copyProperties(answer, answerResponseVo);

        if (account != null) {
            UserResponseVo userResponseVo = convertToUserResponseVo(account);
            answerResponseVo.setUserResponseVo(userResponseVo);
        }

        return answerResponseVo;
    }

    @Override
    public List<AnswerResponseVo> convertAnswerResponseVo(List<Answer> answerList) {
        if (ListUtil.isNullOrEmpty(answerList))
            return null;

        List<AnswerResponseVo> answerResponseVoList = new ArrayList<>();
        List<Integer> accountIdList = new ArrayList<>();

        for (Answer answer : answerList) {
            accountIdList.add(answer.getAnswererId());
        }

        List<Account> accountList = userService.getUserList(accountIdList);

        Map<Integer, Account> accountMap = new HashMap<>();
        for (Account account : accountList)
            accountMap.put(account.getId(), account);

        AnswerResponseVo answerResponseVo = null;
        for (Answer answer : answerList) {
            answerResponseVo = convertAnswerResponseVo(answer, accountMap.get(answer.getAnswererId()));

            answerResponseVoList.add(answerResponseVo);
        }
        return answerResponseVoList;
    }

    @Override
    public Message convertToMessage(MessageRequestVo messageRequestVo) {
        if (messageRequestVo == null)
            return null;

        Message message = new Message();

        BeanUtils.copyProperties(messageRequestVo, message);

        return message;
    }

    @Override
    public MessageResponseVo convertToMessageResponseVo(Message message) {
        if (message == null)
            return null;

        MessageResponseVo messageResponseVo = new MessageResponseVo();

        BeanUtils.copyProperties(message, messageResponseVo);

        return messageResponseVo;
    }

    @Override
    public List<MessageResponseVo> convertToMessageResponseVo(List<Message> messageList) {
        if (ListUtil.isNullOrEmpty(messageList))
            return null;

        List<MessageResponseVo> retList = new ArrayList<>();
        MessageResponseVo messageResponseVo;
        for (Message message : messageList) {
            messageResponseVo = convertToMessageResponseVo(message);
            if (messageResponseVo != null)
                retList.add(messageResponseVo);
        }
        return retList;
    }

    @Override
    public UploadFile convertToUploadFile(UploadFileRequestVo uploadFileRequestVo) {
        if (uploadFileRequestVo == null)
            return null;

        UploadFile uploadFile = new UploadFile();

        BeanUtils.copyProperties(uploadFileRequestVo, uploadFile);

        return uploadFile;
    }

    @Override
    public UploadFileResponseVo convertToUploadFileResponseVo(UploadFile uploadFile) {
        if (uploadFile == null)
            return null;

        UploadFileResponseVo uploadFileResponseVo = new UploadFileResponseVo();

        BeanUtils.copyProperties(uploadFile, uploadFileResponseVo);

        String _accessURL = String.format("%s/%s/%s", uploadFileService.getAccessHostName(), uploadFile.getPurpose(), uploadFile.getName());
        uploadFileResponseVo.setUrl(_accessURL);

        return uploadFileResponseVo;
    }

    @Override
    public RoleResponseVo convertToRoleResponseVo(Role role) {
        if (role == null)
            return null;
        RoleResponseVo roleResponseVo = new RoleResponseVo();
        BeanUtils.copyProperties(role, roleResponseVo);
        Integer roleId = role.getId();

        if (roleId != null && roleId > 0) {
            List<Permission> permissionList = roleService.getPermissionListByRoleId(roleId);
            roleResponseVo.setPermissionList(permissionList);
        }

        List<Role> morePriorityRoleList = roleService.getMorePriorityRoleList(role);

        List<RoleResponseVo> morePriorityRoleResponseVoList = null;
        if (!ListUtil.isNullOrEmpty(morePriorityRoleList)) {
            morePriorityRoleResponseVoList = new ArrayList<>();
            for (Role roleItem : morePriorityRoleList) {
                morePriorityRoleResponseVoList.add(convertToRoleResponseVo(roleItem));
            }
        }

        roleResponseVo.setNeedUpgrade(false);
        if (!ListUtil.isNullOrEmpty(morePriorityRoleResponseVoList)) {
            roleResponseVo.setUpgradeRoleList(morePriorityRoleResponseVoList);
            roleResponseVo.setNeedUpgrade(true);
        }
        return roleResponseVo;
    }

    @Override
    public RolePaymentResponseVo convertToRolePaymentResponseVo(RolePayment rolePayment) {
        if (rolePayment == null)
            return null;

        RolePaymentResponseVo rolePaymentResponseVo = new RolePaymentResponseVo();
        BeanUtils.copyProperties(rolePayment, rolePaymentResponseVo);

        RolePaymentTypeEnum rolePaymentTypeEnum = RolePaymentTypeEnum.getEnum(rolePayment.getName());
        Date startDate = new Date();
        Date endDate = roleService.generateEndDateForRolePayment(rolePaymentTypeEnum);
        rolePaymentResponseVo.setStartDate(startDate);
        rolePaymentResponseVo.setEndDate(endDate);

        return rolePaymentResponseVo;
    }

    @Override
    public List<RolePaymentResponseVo> convertToRolePaymentResponseVo(List<RolePayment> rolePaymentList) {
        if (ListUtil.isNullOrEmpty(rolePaymentList))
            return null;

        List<RolePaymentResponseVo> _rolePaymentResponseVoList = new ArrayList<>();
        rolePaymentList.forEach(rolePayment -> {
            if (rolePayment != null) {
                _rolePaymentResponseVoList.add(convertToRolePaymentResponseVo(rolePayment));
            }
        });
        return _rolePaymentResponseVoList;
    }

    @Override
    public VideoWatchRecordCourseModuleStatisticResponseVo convertToVideoWatchRecordCourseModuleStatisticResponseVo(VideoWatchRecordCourseModuleStatistic videoWatchRecordCourseModuleStatistic, String courseModuleName, Integer totalCourseModuleVideoCount) {
        VideoWatchRecordCourseModuleStatisticResponseVo responseVo=new VideoWatchRecordCourseModuleStatisticResponseVo();
        BeanUtils.copyProperties(videoWatchRecordCourseModuleStatistic, responseVo);

        responseVo.setCourseModuleName(courseModuleName);
        responseVo.setTotalcourseModuleVideoCount(totalCourseModuleVideoCount);

        return responseVo;
    }

    @Override
    public List<VideoWatchRecordCourseModuleStatisticResponseVo> convertToVideoWatchRecordCourseModuleStatisticResponseVo(List<VideoWatchRecordCourseModuleStatistic> videoWatchRecordCourseModuleStatisticList) {
        if(ListUtil.isNullOrEmpty(videoWatchRecordCourseModuleStatisticList))
            return null;

        List<Integer> courseModuleIdList=new ArrayList<>();
        List<Integer> videoIdList=new ArrayList<>();

        videoWatchRecordCourseModuleStatisticList.forEach(statistic ->{
            if(statistic.getCourseModuleId() != null)
                courseModuleIdList.add(statistic.getCourseModuleId());
        });

        List<CourseModule> courseModuleList=  courseService.getCourseModuleList(courseModuleIdList);

        List<VideoSummary> videoSummaryList = videoService.getVideoSummary(courseModuleIdList);

        Map<Integer, String> courseModuleNameMap=new HashMap<>();

        Map<Integer, Integer> courseModuleVideoCountMap=new HashMap<>();

        if(!ListUtil.isNullOrEmpty(courseModuleList))
        {
            courseModuleList.forEach(module->{
                courseModuleNameMap.put(module.getId(),module.getName());
            });
        }

        if(!ListUtil.isNullOrEmpty(videoSummaryList))
        {
            videoSummaryList.forEach(summary->{
                courseModuleVideoCountMap.put(summary.getCourseModuleId(),summary.getVideoCount());
            });
        }

        List<VideoWatchRecordCourseModuleStatisticResponseVo> responseVoList=new ArrayList<>();

        videoWatchRecordCourseModuleStatisticList.forEach(statistic->{
            VideoWatchRecordCourseModuleStatisticResponseVo    responseVo=new VideoWatchRecordCourseModuleStatisticResponseVo();
            String courseModuleName=courseModuleNameMap.get(statistic.getCourseModuleId());
            Integer totalVideoCount=courseModuleVideoCountMap.get(statistic.getCourseModuleId());

            responseVo= convertToVideoWatchRecordCourseModuleStatisticResponseVo(statistic,courseModuleName,totalVideoCount);

            responseVoList.add(responseVo);
        });

        return responseVoList;
    }
}
