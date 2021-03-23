package com.atguigu.educms.controller.Admin;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class AdminCrmBannerController {
    @Resource
    private CrmBannerService crmBannerService;

    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> pageBanner =new Page<>(page,limit);
        crmBannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "获取banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id){
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item",banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/update")
    public R updateById(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "删除banner")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id){
        crmBannerService.removeById(id);
        return R.ok();
    }
}

