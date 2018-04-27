/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.demo.test.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.service.SystemService;

/**
 * 用户Controller
 * 
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testUpload")
public class TestUploadController extends BaseController {

	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}

	@RequiresPermissions("test:testUpload:view")
	@RequestMapping(value = { "list", "" })
	public String list(User user, Model model) {
		return "modules/test/testUpload";
	}
	
	//通过Spring的autowired注解获取spring默认配置的request  
    @Autowired  
    private HttpServletRequest request;  
  
    /*** 
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile 
     *  
     * @param file 
     * @return 
     */  
    @RequestMapping("fileUpload")  
    public String fileUpload(@RequestParam("file") MultipartFile file) {  
        // 判断文件是否为空  
        if (!file.isEmpty()) {  
            try {  
                // 文件保存路径  
                String filePath = request.getSession().getServletContext().getRealPath("/") 
                        + file.getOriginalFilename();  
                // 转存文件  
                file.transferTo(new File(filePath));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        // 重定向  
        //return "redirect:/lists";  
        return "redirect:" + adminPath + "/test/testUpload/?repage";
    }  
}
