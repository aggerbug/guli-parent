package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api("讲师列表查询")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Resource
    private EduTeacherService eduTeacherService;

    @Resource
    private EduCourseService courseService;

    @ApiOperation("讲师页的查询")
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable Long page,@PathVariable Long limit){
        Page<EduTeacher> teacherPage = new Page<EduTeacher>(page,limit);
       Map<String,Object> teacherMap=eduTeacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(teacherMap);
    }

    @ApiOperation("讲师详情查询")
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        EduTeacher teacher = eduTeacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",list);
    }


}
