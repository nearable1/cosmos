package io.club.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import io.club.common.utils.Query;
import io.club.common.exception.RRException;
import io.club.common.utils.PageUtils;
import io.club.modules.sys.dao.ProductDao;
import io.club.modules.sys.entity.ProductEntity;
import io.club.modules.sys.service.ProductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {
//	@Autowired
//	private SysConfigRedis sysConfigRedis;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("name");

		Page<ProductEntity> page = this.selectPage(
				new Query<ProductEntity>(params).getPage(),
				new EntityWrapper<ProductEntity>()
					.like(StringUtils.isNotBlank(paramKey),"name", paramKey)
					.eq("status", 1)
		);

		return new PageUtils(page);
	}
	
	@Override
	public void save(ProductEntity config) {
		this.insert(config);
		//sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(ProductEntity config) {
		this.updateAllColumnById(config);
		//sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
		//sysConfigRedis.delete(key);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			ProductEntity config = this.selectById(id);
			//sysConfigRedis.delete(config.getParamKey());
		}

		this.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	public List<ProductEntity> selectList() {
		List<ProductEntity> list = this.selectList(new EntityWrapper<ProductEntity>()
				.eq("status", 1));
		return list;
	}
}
