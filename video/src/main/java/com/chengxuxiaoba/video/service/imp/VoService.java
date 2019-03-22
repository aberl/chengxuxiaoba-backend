package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.model.Response.VO.CourseModuleResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.CourseResponseVo;
import com.chengxuxiaoba.video.model.po.Course;
import com.chengxuxiaoba.video.model.po.CourseModule;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoService implements IVoService {
    @Override
    public CourseResponseVo convertToCourseResponseVo(Course course) {
        if (course == null)
            return null;

        CourseResponseVo courseResponseVo = new CourseResponseVo();
        courseResponseVo.setId(course.getId());
        courseResponseVo.setName(course.getName());
        courseResponseVo.setDescription(course.getDescription());
        courseResponseVo.setImages(course.getImages());
        courseResponseVo.setStatus(course.getStatus());
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
        courseModuleResponseVo.setId(courseModule.getId());
        courseModuleResponseVo.setCourseId(courseModule.getCourseId());
        courseModuleResponseVo.setName(courseModule.getName());
        courseModuleResponseVo.setDescription(courseModule.getDescription());
        courseModuleResponseVo.setImages(courseModule.getImages());
        courseModuleResponseVo.setStatus(courseModule.getStatus());
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
}
