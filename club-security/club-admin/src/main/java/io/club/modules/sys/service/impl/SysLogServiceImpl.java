package io.club.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.club.common.utils.Query;
import io.club.common.utils.PageUtils;
import io.club.modules.sys.dao.SysLogDao;
import io.club.modules.sys.entity.SysLogEntity;
import io.club.modules.sys.service.ProductService;
import io.club.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Autowired
    private SysLogDao sysLog;

    @Autowired
    private ProductService productService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        Page<SysLogEntity> page = this.selectPage(
            new Query<SysLogEntity>(params).getPage(),
            new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key).orderBy("create_date", false)
        );

        return new PageUtils(page);
    }

    @Override
    public Double sum(Map<String, Integer> params) {

        String typedata = null;
        if(params.get("typedata")!=0) {
            typedata = productService.selectById(params.get("typedata")).getName();
        }

        int time = params.get("interdata");
        Double sum = 0.0;
        switch(time) {
            case 1:
                sum = sysLog.today(typedata);
                break;
            case 2:
                sum = sysLog.recentMonth(typedata);
                break;
            case 3:
                sum = sysLog.recentWeek(typedata);
                break;
            case 4:
                sum = sysLog.thisMonth(typedata);
                break;
            default:
                sum = 0.0;
                break;
        }
        return sum;
    }


}
