package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.mapper.CourseMapper;
import com.chengxuxiaoba.video.model.po.Course;
import com.chengxuxiaoba.video.model.po.CourseModule;
import com.chengxuxiaoba.video.service.ICourseService;
import com.chengxuxiaoba.video.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> getEffectiveCourse() {
        List<Course> allCourse= courseMapper.getAllCourseList();

        if(ListUtil.isNullOrEmpty(allCourse))
            return null;

        List<Course> effectiveCourseList= allCourse.stream().filter(course -> {return course.getStatus() == CommonStatus.ACTIVE.getValue();}).collect(Collectors.toList());

        return effectiveCourseList;
    }

    @Override
    public List<Course> getAllCourse() {
        return courseMapper.getAllCourseList();
    }

    @Override
    public Integer createNewCourse(Course course) {
        if(course == null)
            return  -1;

        return courseMapper.insert(course);
    }

    @Override
    public Boolean updateCourse(Course course) {
        Integer primaryKey= courseMapper.updateCourse(course);

        return primaryKey>0;
    }

    @Override
    public Boolean updateCourseModule(CourseModule courseModule) {
        Integer primaryKey= courseMapper.updateCourseModule(courseModule);

        return primaryKey>0;
    }

    @Override
    public Course getCourse(Integer courseId) {
        return courseMapper.getCourse(courseId);
    }

    @Override
    public Integer createNewCourseModule(CourseModule courseModule) {
        if(courseModule == null)
            return  -1;

        return courseMapper.insertModule(courseModule);
    }

    @Override
    public CourseModule getCourseModule(Integer courseModuleId) {
        return courseMapper.getCourseModule(courseModuleId);
    }

    @Override
    public List<CourseModule> getAllCourseModuleList(Integer courseId) {
        return courseMapper.getAllCourseModuleList(courseId);
    }

    @Override
    public List<CourseModule> getAllEffectiveCourseModuleList(Integer courseId) {
        List<CourseModule> courseModuleList= courseMapper.getAllCourseModuleList(courseId);

        if(ListUtil.isNullOrEmpty(courseModuleList))
            return null;

        List<CourseModule> resultList= courseModuleList.stream().filter(courseModule -> {return courseModule.getStatus() != CommonStatus.INACTIVE.getValue();}).collect(Collectors.toList());

        return resultList;
    }
}
