package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Course;
import com.chengxuxiaoba.video.model.po.CourseModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    Integer insert(Course course);

    Integer updateCourse(Course course);

    List<Course> getAllCourseList();

    List<Course> getEffectiveCourseList();

    Course getCourse(@Param("courseId") Integer courseId);

    Integer insertModule(CourseModule courseModule);

    List<CourseModule> getAllCourseModuleList(@Param("courseId") Integer courseId);

    CourseModule getCourseModule(@Param("courseModuleId") Integer courseModuleId);
}
