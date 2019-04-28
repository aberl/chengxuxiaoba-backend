package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.model.Request.VO.CourseModuleRequestVo;
import com.chengxuxiaoba.video.model.Request.VO.CourseRequestVo;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private IVoService voService;

    @Autowired
    private ICourseService courseService;

    @PostMapping("/courses")
    public Result<Boolean> createCourse(@RequestBody CourseRequestVo courseRequestVo) {
        Course course = voService.convertToCourse(courseRequestVo);

        if (course == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        Integer primyKey = courseService.createNewCourse(course);
        if (primyKey <= 0)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.Fail);
        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @PutMapping("/courses")
    public Result<Boolean> updateCourse(@RequestBody CourseRequestVo courseRequestVo) {
        Course course = voService.convertToCourse(courseRequestVo);

        if (course == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        Boolean flag = courseService.updateCourse(course);

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @PostMapping("/courses/module")
    public Result<Boolean> createCourseModule(@RequestBody CourseModuleRequestVo courseModuleRequestVo) {
        CourseModule courseModule = voService.convertToCourseModule(courseModuleRequestVo);

        if (courseModule == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        Integer courseId = courseModule.getCourseId();
        if (courseId == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.CourseAndModuleRelationShipIsNotExist);

        Course course = courseService.getCourse(courseId);
        if (course == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.CourseAndModuleRelationShipIsNotExist);

        Integer primyKey = courseService.createNewCourseModule(courseModule);
        if (primyKey <= 0)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @GetMapping("/courses/{courseid}")
    public Result<CourseResponseVo> getCourseByCourseId(@PathVariable("courseid") Integer courseid)
    {
        Course course = courseService.getCourse(courseid);

        CourseResponseVo result = voService.convertToCourseResponseVo(course);

        return new Result<CourseResponseVo>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/courses")
    public Result<List<CourseResponseVo>> getAllCourse() {
        List<Course> courselist = courseService.getAllCourse();

        List<CourseResponseVo> resultlist = voService.convertToCourseResponseVo(courselist);

        return new Result<List<CourseResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }


    @GetMapping("/courses/effective")
    public Result<List<CourseResponseVo>> getEffectiveCourseList() {
        List<Course> courselist = courseService.getEffectiveCourse();

        List<CourseResponseVo> resultlist = voService.convertToCourseResponseVo(courselist);

        return new Result<List<CourseResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }

    @GetMapping("/courses/coursemodule/{coursemoduleid}")
    public Result<CourseModuleResponseVo> getAllCourseModuleList(@PathVariable("coursemoduleid") Integer coursemoduleid) {
        if (coursemoduleid == null || coursemoduleid == 0)
            return new Result<CourseModuleResponseVo>(ResultCode.Error, null, ResultMessage.ParameterError);

        CourseModule courseModule = courseService.getCourseModule(coursemoduleid);

        CourseModuleResponseVo result = voService.convertToCourseModuleResponseVo(courseModule);

        return new Result<CourseModuleResponseVo>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/courses/{courseid}/coursemodule/all")
    public Result<List<CourseModuleResponseVo>> getAllCourseModuleList(@PathVariable("courseid") String courseid) {
        List<CourseModule> courseModulelist = courseService.getAllCourseModuleList(Integer.valueOf(courseid));

        List<CourseModuleResponseVo> resultlist = voService.convertToCourseModuleResponseVo(courseModulelist);

        return new Result<List<CourseModuleResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }

    @GetMapping("/courses/{courseid}/coursemodule/effective")
    public Result<List<CourseModuleResponseVo>> getEffectiveCourseModuleList(@PathVariable("courseid") String courseid) {
        List<CourseModule> courseModulelist = courseService.getAllEffectiveCourseModuleList(Integer.valueOf(courseid));

        List<CourseModuleResponseVo> resultlist = voService.convertToCourseModuleResponseVo(courseModulelist);

        return new Result<List<CourseModuleResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }
}
