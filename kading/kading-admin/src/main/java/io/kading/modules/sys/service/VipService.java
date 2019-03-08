package io.kading.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.club.common.utils.PageUtils;
import io.kading.modules.sys.entity.VipEntity;

import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-27
 */
public interface VipService extends IService<VipEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

