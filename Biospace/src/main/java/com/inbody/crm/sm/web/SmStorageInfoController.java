package com.inbody.crm.sm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.service.SystemService;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;
import com.inbody.crm.sm.entity.SmStorageInfoManagementExcel;
import com.inbody.crm.sm.entity.SmStorageInfoManagementInput;
import com.inbody.crm.sm.service.SmStorageInfoService;



@Controller
@RequestMapping(value = "${adminPath}/sd/sm/sm011")
public class SmStorageInfoController extends BaseController {

	
			@Autowired
			private SmStorageInfoService smStorageInfoService;
			
			@Autowired
			private SystemService systemService;
			
			@Autowired
			private ActTaskService actTaskService;
			
			@Autowired
			private AttachmentsService attachmentsService;
			
			@ModelAttribute
			public User get(@RequestParam(required=false) String id) {
				if (StringUtils.isNotBlank(id)){
					return systemService.getUser(id);
				}else{
					return new User();
				}
			}
			
			@RequestMapping(value = {"list", ""})
			public String listData(SmStorageInfoManagement smStorageInfo,HttpServletRequest request,HttpServletResponse response,Model model){
			
				Page<SmStorageInfoManagement> page = smStorageInfoService.findPage(new Page<SmStorageInfoManagement>(request, response), smStorageInfo); 
				model.addAttribute("page", page);
			
				return "sd/sm/sm011";
			}
			
			
		    @RequestMapping(value = "export")
		    public String exportFile(SmStorageInfoManagement smStorageInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
				try {
		            String fileName = "出入库记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
		            List<SmStorageInfoManagement> smStorageInfoList = smStorageInfoService.findAllParentList(smStorageInfo);
		    		new ExportExcel("出入库记录", SmStorageInfoManagementExcel.class).setDataList(smStorageInfoList).write(response, fileName).dispose();
		    		return null;
				} catch (Exception e) {
					addMessage(redirectAttributes, "导出拜访信息失败！失败信息："+e.getMessage());
					e.printStackTrace();
				}
				return "redirect:" + adminPath + "/sd/sm/sm011?repage";
		    }
			
		    
		    /*
		     * 修改SN相关数据
		     * actualSN 实际SN
			 * snNo 虚拟SN
		     */
		    @RequiresPermissions("sm:sm011:edit")
		    @RequestMapping(value = "editSn", method=RequestMethod.POST)
		    @ResponseBody
		    public  Map<String, Object>  editSn(String snNo,String actualSN,String updateDate,String actualProductionDate){
		    	 return smStorageInfoService.updateSn(snNo, actualSN,updateDate,actualProductionDate);
		    }
		    
		    @RequestMapping(value = "getAttachments")
		    @ResponseBody
		    public  Map<String, Object>  getAttachments(String assid,String snNo){
		    	List<CoAttachments> list = attachmentsService.getAttachmentList(assid);
		    	SmStorageInfoManagement sm = smStorageInfoService.storageData(assid);
		    	Map<String, Object> map = new HashMap<String, Object>();
		    	map.put("list", list);
		    	map.put("installperson", sm.getInstallPersonId());
		    	map.put("date", sm.getActualInstallDateStr());
		    	return map;
		    }
		    
		    @RequestMapping(value = "getUpdteDate", method=RequestMethod.GET)
		    @ResponseBody
		    public  Map<String, String>  getUpdteDate(String snNo){
		    	SmStorageInfoManagement date = smStorageInfoService.contrastDate(snNo);
		    	Map<String, String> map = new HashMap<String, String>();
		    	map.put("date", date.getSNUpdateDate());
		    	return map;
		    }
		    
		    @RequiresPermissions("sm:sm011:edit")
		    @RequestMapping(value = "save")
		    @ResponseBody
		    public Map<String, Object> preSave(SmStorageInfoManagementInput input,Model model) {
		    	Map<String, Object> map = new HashMap<String, Object>();
		    	try {
		    		smStorageInfoService.save(input);
		    		map.put("message","安装日期完善成功");
				} catch (Exception e) {
					e.printStackTrace();
					map.put("message","修改失败:"+e.getMessage());
				}
		    	
		    	return map;
		    }
		    
		    @RequiresPermissions("sm:sm011:edit")
		    @RequestMapping(value = "delete/file/{fileId}")
		    @ResponseBody
		    public Map<String, Object> deleteFile(@PathVariable String fileId) {
		        String delFileName = attachmentsService.deleteFile(fileId);
		        Map<String, Object> resultMap = new HashMap<String, Object>();
		        resultMap.put("message", "文件：" + delFileName + "删除成功！");
		        resultMap.put("success", new Boolean(true));
		        return resultMap;
		    }
		    
		    @RequestMapping(value = "download/file/{fileId}")
		    public void downLoadFile(@PathVariable String fileId, HttpServletRequest request,
		            HttpServletResponse response) {
		        attachmentsService.downloadFile(fileId, request, response);
		    }
}
