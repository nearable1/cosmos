package io.club.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.club.common.utils.Query;
import io.club.common.utils.PageUtils;
import io.club.modules.sys.dao.SysLogDao;
import io.club.modules.sys.entity.ProductEntity;
import io.club.modules.sys.entity.SysLogEntity;
import io.club.modules.sys.service.ProductService;
import io.club.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Autowired
    private SysLogDao sysLog;

    @Autowired
    private ProductService productService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String name = (String)params.get("name");
        String operation = (String)params.get("type");
        Date start = string2Date((String)params.get("start"));
        Date end = string2Date((String)params.get("end"));

        Page<SysLogEntity> page = this.selectPage(
            new Query<SysLogEntity>(params).getPage(),
            new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(name),"username", name)
                    .like(StringUtils.isNotBlank(operation),"operation", operation)
                    .ge(start!=null,"create_date",start)
                    .le(end!=null,"create_date",end)
                    .orderBy("create_date", false)
        );

        return new PageUtils(page);
    }

    @Override
    public Double sum(Map<String, Object> params) {

        String name = (String)params.get("name");
        String operation = (String)params.get("type");
        Date start = string2Date((String)params.get("start"));
        Date end = string2Date((String)params.get("end"));
        List<SysLogEntity> list = this.selectList(new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(name),"username", name)
                .like(StringUtils.isNotBlank(operation),"operation", operation)
                .ge(start!=null,"create_date",start)
                .le(end!=null,"create_date",end)
                .notIn("operation","注册","充值"));

        Double sum = 0.0;
        //求和
        for(int i=0; i<list.size(); i++) {
            sum += list.get(i).getMoney();
        }

        return sum;
    }

    public Date string2Date(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            if(str==null || str.equals("")) {
                return date;
            }
            date = sdf.parse(str);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


}
