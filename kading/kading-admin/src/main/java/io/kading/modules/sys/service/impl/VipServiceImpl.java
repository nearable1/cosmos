package io.kading.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.club.common.utils.PageUtils;
import io.kading.common.utils.Query;
import io.kading.modules.sys.dao.VipDao;
import io.kading.modules.sys.entity.VipEntity;
import io.kading.modules.sys.service.VipService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysDictService")
public class VipServiceImpl extends ServiceImpl<VipDao, VipEntity> implements VipService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        Page<VipEntity> page = this.selectPage(
                new Query<VipEntity>(params).getPage(),
                new EntityWrapper<VipEntity>()
                    .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }

}
