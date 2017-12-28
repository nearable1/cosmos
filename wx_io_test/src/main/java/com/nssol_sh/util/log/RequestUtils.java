package com.nssol_sh.util.log;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求操作类
 * @author he.jiaqi
 *
 */
public class RequestUtils {
	/**
	 * 获得请求的POST体
	 * @param request 请求对象
	 * @return
	 * @throws IOException 
	 */
	public static String getPostBodyString(HttpServletRequest request) throws IOException {
		byte[] bytes = new byte[1024 * 1024]; 

        InputStream is = request.getInputStream();  
        
        int nRead = 1;  
        int nTotalRead = 0;  
        while (nRead > 0) {  
            nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);  
            if (nRead > 0)  
                nTotalRead = nTotalRead + nRead;  
        }
        return new String(bytes, 0, nTotalRead, "UTF-8");
	}
}
