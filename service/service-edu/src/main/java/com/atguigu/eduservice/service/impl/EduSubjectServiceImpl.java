package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectDate;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.eduservice.subject.OneSubject;
import com.atguigu.eduservice.subject.TwoSubject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-28
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //获取数据列表树形结构
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //一级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> list = baseMapper.selectList(wrapper);
        //二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> ListTwo = baseMapper.selectList(wrapperTwo);
        //一级分类封装
        List<OneSubject> finalList=new ArrayList<>();
        for (EduSubject subject : list) {
            OneSubject eduSubject = new OneSubject();
//            eduSubject.setId(subject.getId());
//            eduSubject.setTitle(subject.getTitle());
            BeanUtils.copyProperties(subject,eduSubject);

            List<TwoSubject> twoSubjects=new ArrayList<>();
            for (EduSubject eduSubject1 : ListTwo) {

                if(eduSubject1.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject1,twoSubject);
                    twoSubjects.add(twoSubject);
                }


            }
            eduSubject.setChildren(twoSubjects);
            finalList.add(eduSubject);
        }

        return finalList;
    }

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectDate.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
