package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.from.VideoInfoForm;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhander.GuliException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Resource
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVoide")
    public R addVoide(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节并删除视频
    @DeleteMapping("remove/{id}")
    public R deleteVoide(@PathVariable String id){
        EduVideo video = eduVideoService.getById(id);
        String sourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(sourceId)) {
            R voide = vodClient.removeAlyVoide(sourceId);
            if(voide.getCode()==20001){
                throw new GuliException(20001,"熔断器，删除视频失败");
            }
        }
        eduVideoService.removeById(id);
        return R.ok();
    }

    //修改小节
    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("/videoinfo/{id}")
    public R getVideInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        VideoInfoForm videoInfoForm = eduVideoService.getVideoInfoFormById(id);
        return R.ok().data("item", videoInfoForm);
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("/updatevideo/{id}")
    public R updateCourseInfoById(
            @ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
            @RequestBody VideoInfoForm videoInfoForm,

            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        eduVideoService.updateVideoInfoById(videoInfoForm);
        return R.ok();
    }
}

