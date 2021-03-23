package com.atguigu.eduorder.client.impl;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduorder.client.NacosServiceEdu;
import org.springframework.stereotype.Component;

@Component
public class NacosServiceEduImpl implements NacosServiceEdu {
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String courseId) {
        return null;
    }
}
