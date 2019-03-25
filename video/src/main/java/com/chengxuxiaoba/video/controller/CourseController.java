package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.model.Response.VO.CourseModuleResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.CourseResponseVo;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.Course;
import com.chengxuxiaoba.video.model.po.CourseModule;
import com.chengxuxiaoba.video.service.ICourseService;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {

    @Autowired
    private IVoService voService;

    @Autowired
    private ICourseService courseService;

    @GetMapping("/")
    public Result<List<CourseResponseVo>> getAllCourse() {
        List<Course> courselist = courseService.getAllCourse();

        List<CourseResponseVo> resultlist = voService.convertToCourseResponseVo(courselist);

        return new Result<List<CourseResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }


    @GetMapping("/effective")
    public Result<List<CourseResponseVo>> getEffectiveCourseList() {
        List<Course> courselist = courseService.getEffectiveCourse();

        List<CourseResponseVo> resultlist = voService.convertToCourseResponseVo(courselist);

        return new Result<List<CourseResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }

    @GetMapping("/coursemodule/{coursemoduleid}")
    public Result<CourseModuleResponseVo> getAllCourseModuleList(@PathVariable("coursemoduleid") Integer coursemoduleid) {
        if(coursemoduleid == null || coursemoduleid==0)
            return new Result<CourseModuleResponseVo>(ResultCode.Error, null, ResultMessage.ParameterError);

        CourseModule courseModule = courseService.getCourseModule(coursemoduleid);

        CourseModuleResponseVo result = voService.convertToCourseModuleResponseVo(courseModule);

        return new Result<CourseModuleResponseVo>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/{courseid}/coursemodule/all")
    public Result<List<CourseModuleResponseVo>> getAllCourseModuleList(@PathVariable("courseid") String courseid) {
        List<CourseModule> courseModulelist = courseService.getAllCourseModuleList(Integer.valueOf(courseid));

        List<CourseModuleResponseVo> resultlist = voService.convertToCourseModuleResponseVo(courseModulelist);

        return new Result<List<CourseModuleResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }

    @GetMapping("/{courseid}/coursemodule/effective")
    public Result<List<CourseModuleResponseVo>> getEffectiveCourseModuleList(@PathVariable("courseid") String courseid) {
        List<CourseModule> courseModulelist = courseService.getAllEffectiveCourseModuleList(Integer.valueOf(courseid));

        List<CourseModuleResponseVo> resultlist = voService.convertToCourseModuleResponseVo(courseModulelist);

        return new Result<List<CourseModuleResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }
}
