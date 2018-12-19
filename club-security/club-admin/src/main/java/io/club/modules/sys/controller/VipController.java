package io.club.modules.sys.controller;

import io.club.common.annotation.SysLog;
import io.club.common.utils.PageUtils;
import io.club.common.utils.R;
import io.club.common.validator.ValidatorUtils;
import io.club.modules.sys.entity.VipEntity;
import io.club.modules.sys.service.VipService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-27
 */
@RestController
@RequestMapping("sys/dict")
public class VipController {
    @Autowired
    private VipService vipService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = vipService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") Long id){
        VipEntity dict = vipService.selectById(id);

        return R.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @SysLog
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestBody VipEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        vipService.insert(dict);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public R update(@RequestBody VipEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        vipService.updateById(dict);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@RequestBody Long[] ids){
        vipService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
