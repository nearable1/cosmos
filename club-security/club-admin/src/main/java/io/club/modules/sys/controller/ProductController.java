package io.club.modules.sys.controller;


import io.club.common.annotation.SysLog;
import io.club.common.utils.PageUtils;
import io.club.common.utils.R;
import io.club.common.validator.ValidatorUtils;
import io.club.modules.sys.entity.ProductEntity;
import io.club.modules.sys.entity.SysDeptEntity;
import io.club.modules.sys.service.ProductService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/product/product")
public class ProductController extends AbstractController {
	@Autowired
	private ProductService productService;
	
	/**
	 * 所有商品列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:product:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = productService.queryPage(params);

		return R.ok().put("page", page);
	}

	@RequestMapping("/list2")
	@RequiresPermissions("sys:product:list")
	public List<ProductEntity> list2(){
		List<ProductEntity> list = productService.selectList(null);

		return list;
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:product:info")
	public R info(@PathVariable("id") Long id){
		ProductEntity config = productService.selectById(id);
		
		return R.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:product:save")
	public R save(@RequestBody ProductEntity config){
		ValidatorUtils.validateEntity(config);

		productService.save(config);
		
		return R.ok();
	}
	
	/**
	 * 修改配置
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:product:update")
	public R update(@RequestBody ProductEntity config){
		ValidatorUtils.validateEntity(config);

		productService.update(config);
		
		return R.ok();
	}
	
	/**
	 * 删除配置
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:product:delete")
	public R delete(@RequestBody Long[] ids){
		productService.deleteBatch(ids);
		
		return R.ok();
	}

	/**
	 * 删除配置
	 */
	@RequestMapping("/kind")
	public List<ProductEntity> kind(){
		List<ProductEntity> list = productService.selectList();

		return list;
	}

}
