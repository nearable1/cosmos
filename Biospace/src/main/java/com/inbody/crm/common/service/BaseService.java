/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.BaseEntity;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.entity.Menu;
import com.inbody.crm.modules.sys.entity.Office;
import com.inbody.crm.modules.sys.entity.Role;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class BaseService {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @param officeAlias 机构表别名，多个用“,”逗号隔开。
	 * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
	 * @return 标准连接条件对象
	 */
	public static String dataScopeFilter(User user, String officeAlias, String userAlias) {

		StringBuilder sqlString = new StringBuilder();
		
		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScope = Lists.newArrayList();
		
		// 超级管理员，跳过权限过滤
		if (!user.isAdmin()){
			boolean isDataScopeAll = false;
			for (Role r : user.getRoleList()){
				for (String oa : StringUtils.split(officeAlias, ",")){
					if (!dataScope.contains(r.getDataScope()) && StringUtils.isNotBlank(oa)){
						if (Role.DATA_SCOPE_ALL.equals(r.getDataScope())){
							isDataScopeAll = true;
						}
						else if (Role.DATA_SCOPE_COMPANY_AND_CHILD.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getCompany().getId() + "'");
							sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getCompany().getParentIds() + user.getCompany().getId() + ",%'");
						}
						else if (Role.DATA_SCOPE_COMPANY.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getCompany().getId() + "'");
							// 包括本公司下的部门 （type=1:公司；type=2：部门）
							sqlString.append(" OR (" + oa + ".parent_id = '" + user.getCompany().getId() + "' AND " + oa + ".type = '2')");
						}
						else if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
							sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%'");
						}
						else if (Role.DATA_SCOPE_OFFICE.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
						}
						else if (Role.DATA_SCOPE_CUSTOM.equals(r.getDataScope())){
//							String officeIds =  StringUtils.join(r.getOfficeIdList(), "','");
//							if (StringUtils.isNotEmpty(officeIds)){
//								sqlString.append(" OR " + oa + ".id IN ('" + officeIds + "')");
//							}
							sqlString.append(" OR EXISTS (SELECT 1 FROM sys_role_office WHERE role_id = '" + r.getId() + "'");
							sqlString.append(" AND office_id = " + oa +".id)");
						}
						//else if (Role.DATA_SCOPE_SELF.equals(r.getDataScope())){
						dataScope.add(r.getDataScope());
					}
				}
			}
			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
			if (!isDataScopeAll){
				if (StringUtils.isNotBlank(userAlias)){
					for (String ua : StringUtils.split(userAlias, ",")){
						sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
					}
				}else {
					for (String oa : StringUtils.split(officeAlias, ",")){
						//sqlString.append(" OR " + oa + ".id  = " + user.getOffice().getId());
						sqlString.append(" OR " + oa + ".id IS NULL");
					}
				}
			}else{
				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
				sqlString = new StringBuilder();
			}
		}
		if (StringUtils.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}
	
	
	/**
	 * 数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @return 标准连接条件对象
	 */
	public static String dataScopeFilter(User user) {

		
		return dataScopeFilter(user, null);
	}

	public static String dataScopeFilter(User user, String permission) {

		// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
		StringBuilder sqlString = new StringBuilder();
		
		// 获取到最大的数据权限范围
		int dataScopeInteger = 8;
		String dataScopeString = null;
		List<String> officeIdList = null;

		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)UserUtils.getCache(UserUtils.CACHE_MENU_LIST);
		
//		String roleId = null;
		Map<String, String> roleId = new HashMap<String, String>();
		if (!StringUtils.isEmptyNull(permission)) {

			for (Menu m : menuList) {
				if (StringUtils.equals(m.getPermission(), permission)) {
//					roleId = m.getRoleId();
					List<Role> roleList = UserUtils.getRoleListByMenuId(m.getId());
					for (Role r : roleList) {
						roleId.put(r.getId(), r.getId());
					}
					break;
				}
			}
		}

		for (Role r : user.getRoleList()){
			int ds = Integer.valueOf(r.getDataScope());
//			if (!StringUtils.isEmptyNull(roleId)) {
			if (!roleId.isEmpty()) {
//				if (StringUtils.equals(roleId, r.getId())) {
				if (roleId.containsKey(r.getId())) {

//					dataScopeInteger = ds;
//					officeIdList = r.getOfficeIdList();
//					break;

					if (ds == 9){
						dataScopeInteger = ds;
						officeIdList = r.getOfficeIdList();
						break;
					}else if (ds < dataScopeInteger){
						officeIdList = r.getOfficeIdList();
						dataScopeInteger = ds;
					}
				}
			} else {

				if (ds == 9){
					dataScopeInteger = ds;
					officeIdList = r.getOfficeIdList();
					break;
				}else if (ds < dataScopeInteger){
					officeIdList = r.getOfficeIdList();
					dataScopeInteger = ds;
				}
			}
		}
		dataScopeString = String.valueOf(dataScopeInteger);

		if (officeIdList == null) {
			officeIdList = Lists.newArrayList();
		}
		if (officeIdList.size() == 0) {
			officeIdList.add(user.getOffice().getId());
		}
		
//		boolean zzFlag = false;
//		boolean zszFlag = false;
		boolean bzFlag = false;
		
//		User zzUser = UserUtils.getUserGroupCharger(user.getLoginName());
//		User bzUser = UserUtils.getUserManager(user.getLoginName());
//		
//		if (bzUser != null && StringUtils.equals(user.getLoginName(), bzUser.getLoginName())) {
//			bzFlag = true;
//		} else {
//			if (zzUser != null && StringUtils.equals(user.getLoginName(), zzUser.getLoginName())) {
//				zzFlag = true;
//			}
//		}
		List<Office> zzOffices = UserUtils.getUserGroupList(user.getLoginName());
		List<Office> zszOffices = UserUtils.getUserGroupsList(user.getLoginName());
		List<Office> bzOffices = UserUtils.getUserManagerList(user.getLoginName());
		
		List<Office> userOffices = null;
		if (bzOffices != null && bzOffices.size() > 0) {
			bzFlag = true;
			userOffices = bzOffices;
		} else if (zszOffices != null && zszOffices.size() > 0) {
//			zszFlag = true;
			userOffices = zszOffices;
		} else if (zzOffices != null && zzOffices.size() > 0) {
//			zzFlag = true;
			userOffices = zzOffices;
		}
		
		String offices = listToSting(officeIdList, ",");
		
		sqlString.append("(SELECT DISTINCT suSql.id FROM SYS_USER suSql");

		if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(dataScopeString)
				|| Role.DATA_SCOPE_OFFICE.equals(dataScopeString)){
			sqlString.append(" INNER JOIN SYS_OFFICE soSql ON suSql.office_id = soSql.id");
//			sqlString.append(" AND (soSql.id = '" + user.getOffice().getId() + "'");
//			if (zzFlag) {
//				sqlString.append(" OR soSql.parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%')");
//			} 
//			
//			if (bzFlag) {
//				sqlString.append(" OR soSql.parent_ids LIKE '" + user.getOffice().getParentIds() + "%')");
//			} 
//			
//			if (!zzFlag && !bzFlag) {
//				sqlString.append(" OR soSql.parent_ids LIKE '" + user.getOffice().getParentIds() + "%')");
//			}
			
			if (userOffices != null && userOffices.size() > 0) {
				int index = 0;
				sqlString.append(" AND (");
				for (Office office : userOffices) {
					if (index == 0) {
						sqlString.append(" soSql.id = '" + office.getId() + "'");
					} else {
						sqlString.append(" OR soSql.id = '" + office.getId() + "'");
					}
					if (bzFlag) {

						sqlString.append(" OR soSql.parent_ids LIKE '" + office.getParentIds() + "%'");
					} else {

						sqlString.append(" OR soSql.parent_ids LIKE '" + office.getParentIds() + office.getId() + ",%'");
					}
					
					index++;
				}
				sqlString.append(")");
			} else {
				sqlString.append(" AND (soSql.id = '" + user.getOffice().getId() + "'");
				sqlString.append(" OR soSql.parent_ids LIKE '" + user.getOffice().getParentIds() + "%')");
			}
		}
		else if (Role.DATA_SCOPE_CUSTOM.equals(dataScopeString)){
			sqlString.append(" INNER JOIN SYS_OFFICE soSql ON suSql.office_id = soSql.id");
			sqlString.append(" AND soSql.id IN (" + offices + ")");
		}
		else if (Role.DATA_SCOPE_SELF.equals(dataScopeString)){
			sqlString.append(" WHERE suSql.id = '" + user.getId() + "'");
		}
		sqlString.append(")");
		
		return sqlString.toString();
	}

	/**
	 * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
	 * @param entity 当前过滤的实体类
	 * @param sqlMapKey sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
	 * @param officeWheres office表条件，组成：部门表字段=业务表的部门字段
	 * @param userWheres user表条件，组成：用户表字段=业务表的用户字段
	 * @example
	 * 		dataScopeFilter(user, "dsf", "id=a.office_id", "id=a.create_by");
	 * 		dataScopeFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。
	 */
	public static void dataScopeFilter(BaseEntity<?> entity, String sqlMapKey, String officeWheres, String userWheres) {

		User user = entity.getCurrentUser();
		
		// 如果是超级管理员，则不过滤数据
		if (user.isAdmin()) {
			return;
		}

		// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
		StringBuilder sqlString = new StringBuilder();
		
		// 获取到最大的数据权限范围
		String roleId = "";
		int dataScopeInteger = 8;
		for (Role r : user.getRoleList()){
			int ds = Integer.valueOf(r.getDataScope());
			if (ds == 9){
				roleId = r.getId();
				dataScopeInteger = ds;
				break;
			}else if (ds < dataScopeInteger){
				roleId = r.getId();
				dataScopeInteger = ds;
			}
		}
		String dataScopeString = String.valueOf(dataScopeInteger);
		
		// 生成部门权限SQL语句
		for (String where : StringUtils.split(officeWheres, ",")){
			if (Role.DATA_SCOPE_COMPANY_AND_CHILD.equals(dataScopeString)){
				// 包括本公司下的部门 （type=1:公司；type=2：部门）
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
				sqlString.append(" WHERE type='2'");
				sqlString.append(" AND (id = '" + user.getCompany().getId() + "'");
				sqlString.append(" OR parent_ids LIKE '" + user.getCompany().getParentIds() + user.getCompany().getId() + ",%')");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_COMPANY.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
				sqlString.append(" WHERE type='2'");
				sqlString.append(" AND id = '" + user.getCompany().getId() + "'");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
				sqlString.append(" WHERE (id = '" + user.getOffice().getId() + "'");
				sqlString.append(" OR parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%')");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_OFFICE.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
				sqlString.append(" WHERE id = '" + user.getOffice().getId() + "'");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_CUSTOM.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM sys_role_office ro123456, sys_office o123456");
				sqlString.append(" WHERE ro123456.office_id = o123456.id");
				sqlString.append(" AND ro123456.role_id = '" + roleId + "'");
				sqlString.append(" AND o123456." + where +")");
			}
		}
		// 生成个人权限SQL语句
		for (String where : StringUtils.split(userWheres, ",")){
			if (Role.DATA_SCOPE_SELF.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM sys_user");
				sqlString.append(" WHERE id='" + user.getId() + "'");
				sqlString.append(" AND " + where + ")");
			}
		}

//		System.out.println("dataScopeFilter: " + sqlString.toString());

		// 设置到自定义SQL对象
		entity.getSqlMap().put(sqlMapKey, sqlString.toString());
		
	}

    /**
     * 字符数组转换
     * @param List<String>
     * @return
     */
    public static String listToSting(List<String> strList, String separator){
    	StringBuilder strs = new StringBuilder();
		if (strList.size() > 0) {
			int index = 1;
			for (String str : strList) {
				
				if (index == strList.size()) {
					strs.append("'").append(str).append("'");
				} else {
					strs.append("'").append(str).append("'").append(separator);
				}
				
				index++;
			}
		} else {
			return null;
		}
		
		return strs.toString();
    }
}
