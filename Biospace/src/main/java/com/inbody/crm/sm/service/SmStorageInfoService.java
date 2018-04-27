package com.inbody.crm.sm.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.pm.dao.SmStorageInfoDao;
import com.inbody.crm.sm.dao.SmStorageInfoManagementDao;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;
import com.inbody.crm.sm.entity.SmStorageInfoManagementInput;



@Service
@Transactional(readOnly = true)
public class SmStorageInfoService extends CrudService<SmStorageInfoManagementDao, SmStorageInfoManagement> {
		
	
		@Autowired
	    private AttachmentsService attachmentsService;
		
		@Autowired
		private SmStorageInfoDao smStorageInfoDao;
		
		@Autowired
		private SmStorageInfoManagementDao smStorageInfoManagementDao;
		
		public Page<SmStorageInfoManagement> findPage(Page<SmStorageInfoManagement> page, SmStorageInfoManagement smStorageInfo) {
			return super.findPage(page, smStorageInfo);
		}
		
		public List<SmStorageInfoManagement> findAllParentList(SmStorageInfoManagement smStorageInfo){
				return dao.findAllList(smStorageInfo);
		}
		
		
		/*
		 * 修改SN相关数据
		 * actualSN 实际SN
		 * snNo 虚拟SN
		 */
		
		@Transactional(readOnly = false)
		public Map<String, Object> updateSn(String snNo,String actualSN,String updateDate,String actualProductionDate){
			Map<String, Object> map = new HashMap<String, Object>();
			SmStorageInfoManagement date = null;
			try {
				date = smStorageInfoManagementDao.selectSnUpdateDate(snNo);
				if(DateUtils.compareDate(date.getSNUpdateDate(),updateDate,"yyyy-MM-dd HH:mm:ss.SSS") == 0){
					SmStorageInfoManagement sm = new SmStorageInfoManagement();
					List<SmStorageInfoManagement> list = smStorageInfoManagementDao.selectStorageMat(snNo);
					if(null != list && list.size()>0){
						sm.setSnNo(actualSN);
						date = smStorageInfoManagementDao.selectSnUpdateDate(actualSN);
						if(null == date){
							sm.setUserId(UserUtils.getUser().getId());
							sm.setSnNo(snNo);
							sm.setActualSN(actualSN);
							sm.setProductionDateStr(actualProductionDate);
							sm.setUpdateDate(new Date());
							smStorageInfoManagementDao.updateSn(sm);
							smStorageInfoManagementDao.updateStorageSn(sm);
							smStorageInfoManagementDao.updateTestingSn(sm);
							smStorageInfoManagementDao.updateWarehouseSn(sm);
							map.put("message", "SN:"+snNo +"修改成功");
						}else{
							map.put("message", "保存失败：该SN:"+actualSN+"已经存在，请确认后重新输入");
						}
					}else{
						map.put("message", "保存失败：只允许更新UTown类物料的SN号码");
					}
				}else{
					map.put("message","保存失败：数据已经被更改，请取得最新数据后再操作。");
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message", e.getMessage());
				return map;
			}
			return map;
		}
		
		public SmStorageInfoManagement  contrastDate(String snNo){
			return smStorageInfoManagementDao.selectSnUpdateDate(snNo);
		}
		
		public SmStorageInfoManagement  storageData(String id){
			return smStorageInfoManagementDao.selectStorageUpdateDate(id);
		}
		
		@Transactional(readOnly = false)
	    public void save(SmStorageInfoManagementInput input) {
			try {
				Map<String,Integer> map = smStorageInfoManagementDao.getPeriod(input.getId());
				UserUtils.getUser().getId();
				Date actualDate = DateUtils.parse(input.getActualInstallDateStr(), "yyyy-MM-dd");
				Calendar cal = Calendar .getInstance();
				cal.setTime(actualDate);
				if(null != map.get("warrantyPeriod")){
					cal.add(Calendar.YEAR, map.get("warrantyPeriod"));
				}
				if(null != map.get("extendedWarrPeriod")){
					cal.add(Calendar.YEAR, map.get("extendedWarrPeriod"));
				}
				if(actualDate.getTime() == cal.getTime().getTime()){
					input.setWarrantyDateTo(input.getActualInstallDateStr());
				}else{
					cal.add(Calendar.DAY_OF_MONTH, -1);
					input.setWarrantyDateTo(DateUtils.formatDate(cal.getTime(), "yyyy-MM-dd"));
				}
				input.setUserId(UserUtils.getUser().getId());
				input.setUpdateDate(new Date());
				//更新SN表中的安装人安装时间
				//input.setWarrantyDateTo();
				smStorageInfoManagementDao.updateInstall(input);
				//更新storage表中的安装人安装时间
				smStorageInfoManagementDao.updateStorageInstall(input);
				this.saveAttachmentsFile(input.getId(),input.getFiles());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		 @Transactional(readOnly = false)
	     public void saveAttachmentsFile(String id, MultipartFile[] files) {
			 //保存附件信息
	        if (files != null && files.length > 0) {
	            attachmentsService.uploadFileList(id,
	                    CommonConstants.ATTACHMENT_TYPE_STORAGE, files);
	        }
	     }
}
