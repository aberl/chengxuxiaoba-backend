package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.model.Request.VO.*;
import com.chengxuxiaoba.video.model.Response.VO.*;
import com.chengxuxiaoba.video.model.po.*;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.BeanUtils;
import com.chengxuxiaoba.video.util.JSONUtil;
import com.chengxuxiaoba.video.util.ListUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Override
    public Account convertToUser(AccountRequestVo accountRequestVo) {
        if (accountRequestVo == null)
            return null;

        Account  account = new Account();

        BeanUtils.copyProperties(accountRequestVo, account);

        return account;
    }

    @Override
    public UserResponseVo convertToUserResponseVo(Account account) {
        List<AccountRoleRelationShip> accountRoleRelationShipList=  userService.getAccountRoleRelationShipList(new ArrayList<>(Arrays.asList(account.getId())));

        UserResponseVo userResponseVo= convertToUserResponseVo(account, accountRoleRelationShipList);

        if(userResponseVo != null) {
            AccountVipTimeRange accountVipTimeRange = userService.getVipTimeRange(userResponseVo.getId());
            if (accountVipTimeRange != null) {
                userResponseVo.setVipStartDate(accountVipTimeRange.getStartDate());
                userResponseVo.setVipEndDate(accountVipTimeRange.getEndDate());
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

        accountRoleRelationShipList=accountRoleRelationShipList == null? null: accountRoleRelationShipList.stream().filter(relationship->{
            return relationship.getAccountId().equals(account.getId());
        }).collect(Collectors.toList());

        userResponseVo.setRoles(new ArrayList<>());

        if(ListUtil.isNullOrEmpty(accountRoleRelationShipList))
            return userResponseVo;

        for(AccountRoleRelationShip relationship: accountRoleRelationShipList)
        {
            userResponseVo.getRoles().add(relationship.getRoleId().toString());
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

        List<UploadFile> uploadFileList = uploadFileService.getUploadFileByNames(course.getImages());

        if (ListUtil.isNullOrEmpty(uploadFileList))
            return courseResponseVo;

        for (UploadFile file : uploadFileList) {
            uploadFileService.setFileNameAsOriginName(file);
        }

        List<String> imagesFilePath = uploadFileList.stream().map(file -> file.getPath()).collect(Collectors.toList());

        try {
            ObjectMapper mapper = new ObjectMapper();
            String _images = mapper.writeValueAsString(imagesFilePath);
            courseResponseVo.setImages(_images);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        courseResponseVo.setStatusDesc(CommonStatus.getEnum(courseResponseVo.getStatus()).toString());

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

        List<UploadFile> uploadFileList = uploadFileService.getUploadFileByNames(courseModule.getImages());

        if (ListUtil.isNullOrEmpty(uploadFileList))
            return courseModuleResponseVo;
        for (UploadFile file : uploadFileList) {
            uploadFileService.setFileNameAsOriginName(file);
        }
        List<String> imagesFilePath = uploadFileList.stream().map(file -> file.getPath()).collect(Collectors.toList());

        try {
            ObjectMapper mapper = new ObjectMapper();
            String _images = mapper.writeValueAsString(imagesFilePath);
            courseModuleResponseVo.setImages(_images);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
    public VideoResponseVo convertToVideoResponseVo(Video video) {
        if (video == null)
            return null;

        VideoResponseVo videoResponseVo = new VideoResponseVo();

        BeanUtils.copyProperties(video, videoResponseVo);

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
    public List<VideoResponseVo> convertToVideoResponseVo(List<Video> videoList) {
        if (ListUtil.isNullOrEmpty(videoList))
            return null;
        List<VideoResponseVo> retList = new ArrayList<>();
        VideoResponseVo videoResponseVo;
        for (Video video : videoList) {
            videoResponseVo = convertToVideoResponseVo(video);
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
    public EvaluateResponseVo convertToEvaluateResponseVo(Evaluate evaluate) {
        if (evaluate == null)
            return null;

        EvaluateResponseVo evaluateResponseVo = new EvaluateResponseVo();

        BeanUtils.copyProperties(evaluate, evaluateResponseVo);

        return evaluateResponseVo;
    }

    @Override
    public List<EvaluateResponseVo> convertToEvaluateResponseVo(List<Evaluate> evaluateList) {
        if (ListUtil.isNullOrEmpty(evaluateList))
            return null;

        List<EvaluateResponseVo> retList = new ArrayList<>();
        EvaluateResponseVo evaluateResponseVo;
        for (Evaluate evaluate : evaluateList) {
            evaluateResponseVo = convertToEvaluateResponseVo(evaluate);
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

        for (Issue issue : issueList) {
            accountIdList.add(issue.getQuestionerId());
        }

        List<Account> accountList = userService.getUserList(accountIdList);

        Map<Integer, Account> accountMap = new HashMap<>();
        for (Account account : accountList)
            accountMap.put(account.getId(), account);

        IssueResponseVo issueResponseVo = null;
        for (Issue issue : issueList) {
            issueResponseVo = convertIssueResponseVo(issue, accountMap.get(issue.getQuestionerId()));

            issueResponseVoList.add(issueResponseVo);
        }
        return issueResponseVoList;
    }

    @Override
    public IssueResponseVo convertIssueResponseVo(Issue issue, Account account) {
        if (issue == null)
            return null;

        IssueResponseVo issueResponseVo = new IssueResponseVo();

        BeanUtils.copyProperties(issue, issueResponseVo);

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
        if (answer == null)
            return null;

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

        return uploadFileResponseVo;
    }
}
