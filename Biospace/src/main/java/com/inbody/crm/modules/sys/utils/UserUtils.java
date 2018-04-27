/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.sys.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.inbody.crm.cm.dao.CustomerAddManagementDao;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.utils.CacheUtils;
import com.inbody.crm.common.utils.SpringContextHolder;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.dao.AreaDao;
import com.inbody.crm.modules.sys.dao.MenuDao;
import com.inbody.crm.modules.sys.dao.OfficeDao;
import com.inbody.crm.modules.sys.dao.RoleDao;
import com.inbody.crm.modules.sys.dao.UserDao;
import com.inbody.crm.modules.sys.entity.Area;
import com.inbody.crm.modules.sys.entity.Menu;
import com.inbody.crm.modules.sys.entity.Office;
import com.inbody.crm.modules.sys.entity.Role;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserUtils {
	
	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	private static CustomerAddManagementDao customerAddManagementDao = SpringContextHolder.getBean(CustomerAddManagementDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_USER_LIST = "userList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_MENU_WEB_LIST = "menuWebList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
	
	public static final String USERTYPE_MANAGER = "1";
	public static final String USERTYPE_NORMAL = "2";
	
//	public static final String OFFICETYPE_COMPANY = "0";
//	public static final String OFFICETYPE_DEPARTMENT = "1";
//	public static final String OFFICETYPE_GROUP = "2";
//	public static final String OFFICETYPE_OTHER = "3";
	public static final String OFFICETYPE_DEPARTMENT = "2";
	public static final String OFFICETYPE_GROUP = "3";
	public static final String OFFICETYPE_GROUPS = "5";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User getDefault(String id){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user ==  null){
			user = userDao.getDefault(id);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		return get(id,UserUtils.getUser().getCurrentLocaleId());
	}
	
	/**
	 * 根据ID和区域获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id, String locale){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id + locale);
		if (user ==  null){
			user = userDao.get(id, locale);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId() + locale, user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取已离职用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getDeleteByLoginName(String loginName){
		User user = userDao.getDeleteByLoginName(new User(null, loginName));
		if (user == null){
			return null;
		}
		
		return user;
	}
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId() + user.getCurrentLocaleId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId() + user.getLocaleId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
		if (user.getOffice() != null && user.getOffice().getId() != null){
			CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
		}
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			User user = getDefault(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}

	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<Role> getRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			User user = getUser();
			if (user.isAdmin()){
				roleList = roleDao.findAllList(new Role());
			}else{
				Role role = new Role();
				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
				roleList = roleDao.findList(role);
			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<User> getUserList(){
		User user = UserUtils.getUser();
		List<User> userList = userDao.findUserByOfficeId(user);
		return userList;
	}
	
	/**
	 * 获取当前客户列表
	 * @return
	 */
	public static  List<Map<String,String>> getCustomerList(){
		return customerAddManagementDao.getList();
	}
	
	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<User> getUserList(User user){
		List<User> userList = userDao.findUserByOfficeId(user);
		return userList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
		if (menuList == null){
			User user = getUser();
			if (user.isAdmin()){
				menuList = menuDao.findAllList(new Menu());
			}else{
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuWebList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuWebList = (List<Menu>)getCache(CACHE_MENU_WEB_LIST);
		if (menuWebList == null){
			menuWebList = new ArrayList<Menu>();
			List<String> menuNameList = new ArrayList<String>();
			User user = getUser();
			List<Menu> menuList = new ArrayList<Menu>();
			if (user.isAdmin()){
				menuList = menuDao.findAllList(new Menu());
			}else{
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
			for(Menu menu : menuList)
				if(!menuNameList.contains(menu.getId()))
				{
					menuNameList.add(menu.getId());
					menuWebList.add(menu);
				}
			putCache(CACHE_MENU_WEB_LIST, menuWebList);
		}
		return menuWebList;
	}
	
	/**
	 * 获取当前用户授权的区域
	 * @return
	 */
	public static List<Area> getAreaList(){
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>)getCache(CACHE_AREA_LIST);
		if (areaList == null){
			areaList = areaDao.findAllList(new Area());
			putCache(CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_LIST);
		if (officeList == null){
			User user = getUser();
			if (user.isAdmin()){
				officeList = officeDao.findAllList(new Office());
			}else{
				Office office = new Office();
				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
				officeList = officeDao.findList(office);
			}
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeAllList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_ALL_LIST);
		if (officeList == null){
			officeList = officeDao.findAllList(new Office());
		}
		return officeList;
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}
	
//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}
	
	/**
	 * 根据用户登陆名称获取所在组组长（如果用户所属组织不在小组中则返回null）,组长用户是当前小组所在组织的主负责人
	 * @param username 用户登录名称
	 * @return
	 */
	public static User getUserGroupCharger(String username){
		User user = getByLoginName(username);
		if(user == null)	return null;
		
		Office office = user.getOffice();
		if(office == null)	return null;

		office = officeDao.getDefault(office.getId());
		if (office != null) {

			if (office.getType().equals(OFFICETYPE_GROUP)) {
				User pUser = office.getPrimaryPerson();
				if(pUser != null)
					return UserUtils.get(pUser.getId());
			}
		}

//		
//		if(office.getType().equals(OFFICETYPE_GROUP)){
//			User pUser = office.getPrimaryPerson();
//			if(pUser != null)
//				return UserUtils.get(pUser.getId());
//		}
		
		return null;
	}
	
	/**
	 * 根据用户登陆名称获取所在GROUP长（如果用户所属组织不在GROUP下则返回null）
	 * @param username 用户登录名称
	 * @return
	 */
	public static User getUserGroupsCharger(String username){
		User user = getByLoginName(username);
		if(user == null)	return null;
		
		Office office = user.getOffice();
		if(office == null)	return null;
		
		String parentIds = office.getParentIds();
		
		if (StringUtils.isEmptyNull(parentIds)) {
			return null;
		}
		
		String[] parentIdsList = parentIds.split(",");
		
		for (String str : parentIdsList) {
			
			if (!StringUtils.isEmptyNull(str)) {

				Office officeParent = officeDao.getDefault(str);
				if (officeParent != null) {

					if (officeParent.getType().equals(OFFICETYPE_GROUPS)) {
						User pUser = officeParent.getPrimaryPerson();
						if(pUser != null)
							return UserUtils.get(pUser.getId());
					}
				}
			}
		}
//		
//		//用户所属组织在小组中
//		if(office.getType().equals(OFFICETYPE_GROUP)){
//			//如果组织管理混乱，比如小组下面挂小组，小组上面直接是公司，下面语句需要改进
//			Office parent = office.getParent();
//			if(parent != null)	
//			{
//				parent = officeDao.get(parent.getId(),user.getCurrentLocaleId());
//				User pUser = parent.getPrimaryPerson();
//				if(pUser != null)
//					return UserUtils.get(pUser.getId());
//			}
//		}
//		//用户所属组织在部门中
//		if(office.getType().equals(OFFICETYPE_GROUPS)){
//			User pUser = office.getPrimaryPerson();
//			if(pUser != null)
//				return UserUtils.get(pUser.getId());
//		}
		
		return null;
	}
	
	/**
	 * 根据用户登陆名称获取所在部门部长（如果用户所属组织不在部门下则返回null）
	 * @param username 用户登录名称
	 * @return
	 */
	public static User getUserManager(String username){
		User user = getByLoginName(username);
		if(user == null)	return null;
		
		Office office = user.getOffice();
		if(office == null)	return null;
		
		String parentIds = office.getParentIds();
		
		if (StringUtils.isEmptyNull(parentIds)) {
			return null;
		}
		
		String[] parentIdsList = parentIds.split(",");
		
		for (String str : parentIdsList) {
			
			if (!StringUtils.isEmptyNull(str)) {

				Office officeParent = officeDao.getDefault(str);
				if (officeParent != null) {

					if (officeParent.getType().equals(OFFICETYPE_DEPARTMENT)) {
						User pUser = officeParent.getPrimaryPerson();
						if(pUser != null)
							return UserUtils.get(pUser.getId());
					}
				}
			}
		}
		
//		Office office = user.getOffice();
//		if(office == null)	return null;
//		
//		//用户所属组织在小组中
//		if(office.getType().equals(OFFICETYPE_GROUP)){
//			//如果组织管理混乱，比如小组下面挂小组，小组上面直接是公司，下面语句需要改进
//			Office parent = office.getParent();
//			if(parent != null)	
//			{
//				if (parent.getType().equals(OFFICETYPE_GROUPS)) {
//					parent = parent.getParent();
//				}
//				
//				if(parent == null)	return null;
//
//				if(parent.getType().equals(OFFICETYPE_DEPARTMENT)){
//
//					parent = officeDao.get(parent.getId(),user.getCurrentLocaleId());
//					User pUser = parent.getPrimaryPerson();
//					if(pUser != null)
//						return UserUtils.get(pUser.getId());
//				}
//			}
//		}
//		
//		//用户所属组织在Group中
//		if(office.getType().equals(OFFICETYPE_GROUPS)){
//			//如果组织管理混乱，比如小组下面挂小组，小组上面直接是公司，下面语句需要改进
//			Office parent = office.getParent();
//			if(parent != null)	
//			{
//
//				parent = officeDao.get(parent.getId(),user.getCurrentLocaleId());
//				User pUser = parent.getPrimaryPerson();
//				if(pUser != null)
//					return UserUtils.get(pUser.getId());
//			}
//		}
//		//用户所属组织在部门中
//		if(office.getType().equals(OFFICETYPE_DEPARTMENT)){
//			User pUser = office.getPrimaryPerson();
//			if(pUser != null)
//				return UserUtils.get(pUser.getId());
//		}
		
		return null;
	}
	
	/**
	 * 根据用户登陆名称获取所有负责的部门
	 * @param username 用户登录名称
	 * @return
	 */
	public static List<Office> getUserManagerList(String username){
		User user = getByLoginName(username);
		if(user == null)	return null;
		
		Office office = user.getOffice();
		if(office == null)	return null;
		
		List<Office> offices = officeDao.getManagerListByPrimaryPerson(user.getId());
		
		return offices;
	}
	
	/**
	 * 根据用户登陆名称获取所有负责的组
	 * @param username 用户登录名称
	 * @return
	 */
	public static List<Office> getUserGroupList(String username){
		User user = getByLoginName(username);
		if(user == null)	return null;
		
		Office office = user.getOffice();
		if(office == null)	return null;
		
		List<Office> offices = officeDao.getGroupListByPrimaryPerson(user.getId());
		
		return offices;
	}
	
	/**
	 * 根据用户登陆名称获取所有负责的组群
	 * @param username 用户登录名称
	 * @return
	 */
	public static List<Office> getUserGroupsList(String username){
		User user = getByLoginName(username);
		if(user == null)	return null;
		
		Office office = user.getOffice();
		if(office == null)	return null;
		
		List<Office> offices = officeDao.getGroupsListByPrimaryPerson(user.getId());
		
		return offices;
	}
	
	/**
	 * 根据目录ID获取所有角色
	 * @param menuId 目录ID
	 * @return
	 */
	public static List<Role> getRoleListByMenuId(String menuId){
		
		List<Role> roleList = roleDao.getRoleListByMenuId(menuId);
		
		return roleList;
	}

}
