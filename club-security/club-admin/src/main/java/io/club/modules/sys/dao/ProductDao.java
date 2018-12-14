package io.club.modules.sys.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.club.modules.sys.entity.ProductEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:46:16
 */
public interface ProductDao extends BaseMapper<ProductEntity> {

	/**
	 * 根据key，查询value
	 */
	ProductEntity queryByKey(String paramKey);

	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("paramKey") String paramKey, @Param("paramValue") String paramValue);
	
}
