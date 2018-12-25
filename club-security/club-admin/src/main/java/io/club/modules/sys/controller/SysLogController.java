package io.club.modules.sys.controller;

import io.club.common.validator.ValidatorUtils;
import io.club.modules.sys.entity.ParamEntity;
import io.club.modules.sys.entity.ProductEntity;
import io.club.modules.sys.service.SysLogService;
import io.club.common.utils.PageUtils;
import io.club.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * 系统日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysLogService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 获取总和
	 */
	@ResponseBody
	@RequestMapping("/sum")
	public Double sum(@RequestBody Map<String, Integer> params){
		Double sum = sysLogService.sum(params);
		if(sum == null) {
			sum = 0.0;
		}
		return Math.abs(sum);
	}
	
}
