package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.po.Course;
import com.chengxuxiaoba.video.model.po.CourseModule;

import java.util.List;

public interface ICourseService {
    List<Course> getEffectiveCourse();

    List<Course> getAllCourse();

    Integer createNewCourse(Course course);

    Course getCourse(Integer courseId);

    List<CourseModule> getAllCourseModuleList(Integer courseId);

    List<CourseModule> getAllEffectiveCourseModuleList(Integer courseId);
}
