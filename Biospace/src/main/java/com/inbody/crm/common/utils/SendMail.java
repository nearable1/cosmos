/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.utils;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 发送电子邮件
 */
public class SendMail implements java.lang.Runnable {

	public static Logger log = Logger.getLogger(SendMail.class);
	
	private static ResourceBundle resource = ResourceBundle.getBundle("mail");
	
	private String subject = null;
	private String templatePath = null;
	private Object model = null;
	private String[] toMailAddr = null;

	static {
		resource = ResourceBundle.getBundle("mail");
	}

	public SendMail(String subject, String templatePath, Object model,
            String... toMailAddr) {
		this.subject = subject;
		this.templatePath = templatePath;
		this.model = model;
		this.toMailAddr = toMailAddr;
	}

	@Override
	public void run() {
		start(this);
	}

	public void start(final SendMail sendMail) {

		new Thread(new java.lang.Runnable() {

			@Override
			public void run() {
				try {
					sendMail.sendFtlMail(subject, templatePath, model, toMailAddr);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 发送模板邮件
	 * 
	 * @param subject
	 *            email主题
	 * @param templatePath
	 *            模板地址
	 * @param model
	 *            模板数据对象
     * @param toMailAddr
     *            收信人地址
	 */
    public void sendFtlMail(String subject, String templatePath, Object model,
            String... toMailAddr) {
		Template template = null;
		Configuration freeMarkerConfig = null;
		HtmlEmail hemail = new HtmlEmail();
		try {
            String connectType = StringUtils.lowerCase(resource.getString("connect"));
            if (StringUtils.equals(connectType, "ssl")) {
                hemail.setSSLOnConnect(true);
            } else if (StringUtils.equals(connectType, "tls")) {
                hemail.setStartTLSEnabled(true);
            }
			hemail.setHostName(resource.getString("host"));
			hemail.setSmtpPort(Integer.parseInt(resource.getString("port")));
			hemail.setCharset(resource.getString("charSet"));
			hemail.addTo(toMailAddr);
			hemail.setFrom(resource.getString("from"), resource.getString("fromName"));
			hemail.setAuthentication(resource.getString("username"), resource.getString("password"));
			hemail.setSubject(subject);
			freeMarkerConfig = new Configuration();
			freeMarkerConfig.setDirectoryForTemplateLoading(new File(
					getFilePath()));
			// 获取模板
			template = freeMarkerConfig.getTemplate(getFileName(templatePath),
					new Locale("Zh_cn"), "UTF-8");
			// 模板内容转换为string
			String htmlText = FreeMarkerTemplateUtils
					.processTemplateIntoString(template, model);
			hemail.setMsg(htmlText);
			hemail.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送普通邮件
	 * 
	 * @param toMailAddr
	 *            收信人地址
	 * @param subject
	 *            email主题
	 * @param message
	 *            发送email信息
	 */
//	public static void sendCommonMail(String subject,
//			String message,String... toMailAddr ) {
//		HtmlEmail hemail = new HtmlEmail();
//		try {
//            String connectType = StringUtils.lowerCase(resource.getString("connect"));
//            if (StringUtils.equals(connectType, "ssl")) {
//                hemail.setSSLOnConnect(true);
//            } else if (StringUtils.equals(connectType, "tls")) {
//                hemail.setStartTLSEnabled(true);
//            }
//			hemail.setHostName(resource.getString("host"));
//			hemail.setSmtpPort(Integer.parseInt(resource.getString("port")));
//			hemail.setCharset(resource.getString("charSet"));
//			hemail.addTo(toMailAddr);
//			hemail.setFrom(resource.getString("from"), resource.getString("fromName"));
//			hemail.setAuthentication(resource.getString("username"), resource.getString("password"));
//
//			hemail.setSubject(subject);
//			hemail.setMsg(message);
//			hemail.send();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	public static String getHtmlText(String templatePath,
			Map<String, Object> map) {
		Template template = null;
		String htmlText = "";
		try {
			Configuration freeMarkerConfig = null;
			freeMarkerConfig = new Configuration();
			freeMarkerConfig.setDirectoryForTemplateLoading(new File(
					getFilePath()));
			// 获取模板
			template = freeMarkerConfig.getTemplate(getFileName(templatePath),
					new Locale("Zh_cn"), "UTF-8");
			// 模板内容转换为string
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(
					template, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlText;
	}

	private static String getFilePath() {
		String path = getAppPath(SendMail.class);
		path = path + File.separator + "mailtemplate" + File.separator;
		path = path.replace("\\", "/");
		return path;
	}

	private static String getFileName(String path) {
		path = path.replace("\\", "/");
		return path.substring(path.lastIndexOf("/") + 1);
	}

//	@SuppressWarnings("unchecked")
	public static String getAppPath(Class<?> cls) {
		// 检查用户传入的参数是否为空
		if (cls == null)
			throw new java.lang.IllegalArgumentException("参数不能为空！");
		ClassLoader loader = cls.getClassLoader();
		// 获得类的全名，包括包名
		String clsName = cls.getName() + ".class";
		// 获得传入参数所在的包
		Package pack = cls.getPackage();
		String path = "";
		// 如果不是匿名包，将包名转化为路径
		if (pack != null) {
			String packName = pack.getName();
			// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (packName.startsWith("java.") || packName.startsWith("javax."))
				throw new java.lang.IllegalArgumentException("不要传送系统类！");
			// 在类的名称中，去掉包名的部分，获得类的文件名
			clsName = clsName.substring(packName.length() + 1);
			// 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
			if (packName.indexOf(".") < 0)
				path = packName + "/";
			else {// 否则按照包名的组成部分，将包名转换为路径
				int start = 0, end = 0;
				end = packName.indexOf(".");
				while (end != -1) {
					path = path + packName.substring(start, end) + "/";
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path = path + packName.substring(start) + "/";
			}
		}
		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		java.net.URL url = loader.getResource(path + clsName);
		// 从URL对象中获取路径信息
		String realPath = url.getPath();
		// 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1)
			realPath = realPath.substring(pos + 5);
		// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1);
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!"))
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		/*------------------------------------------------------------ 
		 ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径 
		  中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要 
		  的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的 
		  中文及空格路径 
		-------------------------------------------------------------*/
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return realPath;
	}

//	public static void main(String[] args) {
//        String subject = "申请审批通知";
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("workflowName", "test");
//		params.put("applyUserName", "test");
//		//params.put("applyNo", "applyNo");
//		params.put("applyDate", "test");
//		params.put("url", "test");
//		
//		sendCommonMail(subject, "ddkkdkdkdkdkdk", 
//				"103849215@qq.com");
//	}

}