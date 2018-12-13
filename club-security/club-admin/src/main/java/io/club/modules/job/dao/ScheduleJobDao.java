package io.club.modules.job.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.club.modules.job.entity.ScheduleJobEntity;

import java.util.Map;

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
public interface ScheduleJobDao extends BaseMapper<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
