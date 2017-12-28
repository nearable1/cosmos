package com.nssol_sh.controller.wx.test.imitator;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.cache.RequestCacher;

@Controller
public class ImitatorController implements Serializable {

	/**
	 * 模拟器
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/imitator")
	public String sendHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/imitator";
	}

	/**
	 * 审批
	 * 
	 * @param
	 * @return 处理结果
	 */
	@RequestMapping(value = "/imitator/get_messages", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String messages() {
		String result = JSON.toJSONString(RequestCacher.getInstance().getMessages());

		// 获得所有消息
		return result;
	}
}
