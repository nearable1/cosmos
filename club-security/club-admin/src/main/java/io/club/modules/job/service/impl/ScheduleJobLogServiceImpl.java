package io.club.modules.job.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.club.modules.job.service.ScheduleJobLogService;
import io.club.common.utils.PageUtils;
import io.club.common.utils.Query;
import io.club.modules.job.dao.ScheduleJobLogDao;
import io.club.modules.job.entity.ScheduleJobLogEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String jobId = (String)params.get("jobId");

		Page<ScheduleJobLogEntity> page = this.selectPage(
				new Query<ScheduleJobLogEntity>(params).getPage(),
				new EntityWrapper<ScheduleJobLogEntity>().like(StringUtils.isNotBlank(jobId),"job_id", jobId)
		);

		return new PageUtils(page);
	}

}
