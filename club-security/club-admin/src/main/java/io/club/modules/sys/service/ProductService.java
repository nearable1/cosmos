package io.club.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import io.club.common.utils.PageUtils;
import io.club.modules.sys.entity.ProductEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:49:01
 */
public interface ProductService extends IService<ProductEntity>  {

	PageUtils queryPage(Map<String, Object> params);
	
	/**
	 * 保存配置信息
	 */
	public void save(ProductEntity config);
	
	/**
	 * 更新配置信息
	 */
	public void update(ProductEntity config);
	
	/**
	 * 根据key，更新value
	 */
	public void updateValueByKey(String key, String value);
	
	/**
	 * 删除配置信息
	 */
	public void deleteBatch(Long[] ids);

	public List<ProductEntity> selectList();
	
}
