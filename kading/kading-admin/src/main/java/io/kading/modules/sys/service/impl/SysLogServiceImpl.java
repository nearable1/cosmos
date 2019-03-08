package io.kading.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.club.common.utils.PageUtils;
import io.kading.common.utils.Query;
import io.kading.modules.sys.dao.SysLogDao;
import io.kading.modules.sys.entity.SysLogEntity;
import io.kading.modules.sys.service.ProductService;
import io.kading.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        //all consume消费 register注册
        String name = (String)params.get("name");
        String operation = (String)params.get("type");
        Date start = string2Date((String)params.get("start"));
        Date end = string2Date((String)params.get("end"));

        Page<SysLogEntity> page = null;

        if(operation==null || operation.equals("")) {
            page = this.selectPage(
                    new Query<SysLogEntity>(params).getPage(),
                    new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(name),"username", name)
                            .ge(start!=null,"create_date",start)
                            .le(end!=null,"create_date",end)
                            .orderBy("create_date", false)
            );
        }else if(operation.equals("consume")) {
            page = this.selectPage(
                    new Query<SysLogEntity>(params).getPage(),
                    new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(name),"username", name)
                            .notIn("operation","注册","充值")
                            .ge(start!=null,"create_date",start)
                            .le(end!=null,"create_date",end)
                            .orderBy("create_date", false)
            );
        }else {
            page = this.selectPage(
                    new Query<SysLogEntity>(params).getPage(),
                    new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(name),"username", name)
                            .like(StringUtils.isNotBlank(operation),"operation", operation)
                            .ge(start!=null,"create_date",start)
                            .le(end!=null,"create_date",end)
                            .orderBy("create_date", false)
            );
        }


        return new PageUtils(page);
    }

    @Override
    public Double sum(Map<String, Object> params) {

        String name = (String)params.get("name");
        String operation = (String)params.get("type");
        Date start = string2Date((String)params.get("start"));
        Date end = string2Date((String)params.get("end"));

        List<SysLogEntity> list = null;

        if(operation==null || operation.equals("")) {
            list = new ArrayList<SysLogEntity>();
        }else if(operation.equals("consume")) {
            list = this.selectList(new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(name),"username", name)
                    .ge(start!=null,"create_date",start)
                    .le(end!=null,"create_date",end)
                    .notIn("operation","注册","充值"));
        }else {
            list = this.selectList(new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(name),"username", name)
                    .like(StringUtils.isNotBlank(operation),"operation", operation)
                    .ge(start!=null,"create_date",start)
                    .le(end!=null,"create_date",end));
        }



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
