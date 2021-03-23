package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectDate;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhander.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SubjectExcelListener extends AnalysisEventListener<SubjectDate> {
    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectDate subjectDate, AnalysisContext analysisContext) {

        if(subjectDate==null){
            throw new GuliException(20000,"文件数据为空");
        }

        EduSubject subject=this.excit(subjectService,subjectDate.getOneSubjectName());
        if (subject==null){
            subject = new EduSubject();
            subject.setParentId("0");
            subject.setTitle(subjectDate.getOneSubjectName());
            subjectService.save(subject);
        }
        String pid=subject.getId();
        EduSubject twoEdusubject = this.excitTwo(subjectService, subjectDate.getTwoSubjectName(), pid);

        if (twoEdusubject==null){
            twoEdusubject = new EduSubject();
            twoEdusubject.setParentId(pid);
            twoEdusubject.setTitle(subjectDate.getOneSubjectName());
            subjectService.save(twoEdusubject);
        }
    }

    //判断重复值
    public EduSubject excit(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }

    public EduSubject excitTwo(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
