package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.UcenterMember;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("评论区")
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class CommentFrontController {
    @Resource
    private EduCommentService commentService;

    @Resource
    private UcenterClient ucenterClient;

    @ApiOperation("评论查询表")
    @GetMapping("/{page}/{limit}/{courseId}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit,
            @ApiParam(name = "courseId", value = "查询对象", required = false)
            @PathVariable String courseId
            ){

        Page<EduComment> pageIndex = new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(pageIndex,wrapper);
        List<EduComment> records = pageIndex.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", pageIndex.getCurrent());
        map.put("pages", pageIndex.getPages());
        map.put("size", pageIndex.getSize());
        map.put("total", pageIndex.getTotal());
        map.put("hasNext", pageIndex.hasNext());
        map.put("hasPrevious", pageIndex.hasPrevious());
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);

        UcenterMember ucenterInfo = ucenterClient.getInfo(memberId);

        comment.setNickname(ucenterInfo.getNickname());
        comment.setAvatar(ucenterInfo.getAvatar());

        commentService.save(comment);
        return R.ok();
    }
}
