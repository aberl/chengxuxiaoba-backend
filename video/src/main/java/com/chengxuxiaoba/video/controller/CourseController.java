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
@RequestMapping(value = "/course")
public class CourseController {

    @Autowired
    private IVoService voService;

    @Autowired
    private ICourseService courseService;

    @GetMapping("/all")
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

    @GetMapping("/coursemodule/all/{courseid}")
    public Result<List<CourseModuleResponseVo>> getAllCourseModuleList(@PathVariable("courseid") String courseid) {
        List<CourseModule> courseModulelist = courseService.getAllCourseModuleList(Integer.valueOf(courseid));

        List<CourseModuleResponseVo> resultlist = voService.convertToCourseModuleResponseVo(courseModulelist);

        return new Result<List<CourseModuleResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }

    @GetMapping("/coursemodule/effective/{courseid}")
    public Result<List<CourseModuleResponseVo>> getEffectiveCourseModuleList(@PathVariable("courseid") String courseid) {
        List<CourseModule> courseModulelist = courseService.getAllEffectiveCourseModuleList(Integer.valueOf(courseid));

        List<CourseModuleResponseVo> resultlist = voService.convertToCourseModuleResponseVo(courseModulelist);

        return new Result<List<CourseModuleResponseVo>>(ResultCode.Success, resultlist, ResultMessage.Success);
    }
}
