package com.nssol_sh.util.tools.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.util.constants.HttpConstant;
import com.nssol_sh.util.log.LogUtils;
import com.nssol_sh.util.tools.http.ssl.trust.X509TrustManagerImpl;

/**
 * HTTP请求类（包括HTTPS）.
 */
@Service
public class HttpUtil {
	/**
	 * 构造方法.
	 */
	public HttpUtil() {
	}

	/**
	 * Get请求
	 * 
	 * @param url
	 *            请求路径
	 * @param params
	 *            请求参数
	 * @return 响应返回字符串
	 */
	public final String doGet(final String url, final List<NameValuePair> params) {
		if (url.indexOf("https://") == 0) {
			return doHttpsGetString(url, params);
		} else {
			return doHttpGetString(url, params);
		}
	}

	/**
	 * Post请求
	 * 
	 * @param url
	 *            请求路径
	 * @param params
	 *            请求参数
	 * @param output
	 *            POST体
	 * @return 响应返回字符串
	 */
	public final String doPost(final String url, final List<NameValuePair> params, final String output) {
		if (url.indexOf("https://") == 0) {
			return doHttpsPostString(url, params, output);
		} else {
			return doHttpPostString(url, params, output);
		}
	}

	/**
	 * 向指定URL发送HTTPS的GET请求.
	 * 
	 * @param url
	 *            请求URL
	 * @param params
	 *            请求参数
	 * @return String 批处理返回值
	 */
	public final String doHttpsGetString(final String url, final List<NameValuePair> params) {
		// LOG UUID
		UUID uuid = UUID.randomUUID();

		// 返回的字符串
		StringBuffer buffer = new StringBuffer();

		// 编辑参数
		String urlParams = URLEncodedUtils.format(params, HttpConstant.HTTP_DEFAULT_CHARSET);

		// X509证书对象
		X509TrustManager[] x509mgr = { new X509TrustManagerImpl() };

		// 连接对象
		HttpsURLConnection conn = null;

		// 读取输入流缓存对象
		BufferedReader bufferedReader = null;

		// 读取输入流对象
		InputStreamReader inputStreamReader = null;

		// 从输入流读取返回内容对象
		InputStream inputStream = null;

		try {
			// SSL上下文对象
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, x509mgr, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			// 初始化URL对象
			URL urlObject = new URL(url + HttpConstant.HTTP_QUERY_SYMBOL + urlParams);

			// LOG---------------------------------------------
			// 生成GET请求Log
			LogUtils.info(uuid.toString() + " [GET] " + urlObject.toString());
			// LOG---------------------------------------------

			// 生成URL连接对象
			conn = (HttpsURLConnection) urlObject.openConnection();

			// 设定SSLSocketFactory对象
			conn.setSSLSocketFactory(ssf);

			// 设置超时3秒
			conn.setConnectTimeout(HttpConstant.HTTP_TIME_OUT);

			// 设置可以使用InputStream
			conn.setDoInput(true);

			// 不做缓存
			conn.setUseCaches(false);

			// 设置请求方法
			conn.setRequestMethod(HttpConstant.HTTP_REQUEST_METHOD_GET);

			// 从输入流读取返回内容
			inputStream = conn.getInputStream();

			// 读取输入流
			inputStreamReader = new InputStreamReader(inputStream, HttpConstant.HTTP_DEFAULT_CHARSET);

			// 读取输入流缓存
			bufferedReader = new BufferedReader(inputStreamReader);

			// Buffer读取的字符串
			String bfString = null;

			while ((bfString = bufferedReader.readLine()) != null) {
				// Buffer拼接
				buffer.append(bfString);
			}
		} catch (NoSuchAlgorithmException e) {
			LogUtils.error("密钥产生发生异常..." + e.toString(), e);
		} catch (NoSuchProviderException e) {
			LogUtils.error("SSL上下文对象初始化发生异常..." + e.toString(), e);
		} catch (Exception ex) {
			LogUtils.error("向指定URL发送HTTPS的GET请求发生异常..." + ex.toString(), ex);
		} finally {
			// 释放资源
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭bufferedReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭inputStreamReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					LogUtils.error("关闭inputStream发生异常..." + e.toString(), e);
				}
			}

			if (conn != null) {
				conn.disconnect();
			}
		}

		// LOG---------------------------------------------
		// 请求返回值
		LogUtils.info(uuid.toString() + " [GET_RESUT] " + buffer.toString());
		// LOG---------------------------------------------

		// 返回值
		return buffer.toString();
	}

	/**
	 * 向指定URL发送HTTP的GET请求.
	 * 
	 * @param url
	 *            请求URL
	 * @param params
	 *            请求参数
	 * @return String 批处理返回值
	 */
	public final String doHttpGetString(final String url, final List<NameValuePair> params) {
		// LOG UUID
		UUID uuid = UUID.randomUUID();

		// 返回的字符串
		StringBuffer buffer = new StringBuffer();

		// 编辑参数
		String urlParams = URLEncodedUtils.format(params, HttpConstant.HTTP_DEFAULT_CHARSET);

		// 连接对象
		HttpURLConnection conn = null;

		// 读取输入流缓存对象
		BufferedReader bufferedReader = null;

		// 读取输入流对象
		InputStreamReader inputStreamReader = null;

		// 从输入流读取返回内容对象
		InputStream inputStream = null;

		try {

			// 初始化URL对象
			URL urlObject = new URL(url + HttpConstant.HTTP_QUERY_SYMBOL + urlParams);

			// LOG---------------------------------------------
			// 生成GET请求Log
			LogUtils.info(uuid.toString() + " [GET] " + urlObject.toString());
			// LOG---------------------------------------------

			// 生成URL连接对象
			conn = (HttpURLConnection) urlObject.openConnection();

			// 设置超时3秒
			conn.setConnectTimeout(HttpConstant.HTTP_TIME_OUT);

			// 设置可以使用InputStream
			conn.setDoInput(true);

			// 不做缓存
			conn.setUseCaches(false);

			// 设置请求方法
			conn.setRequestMethod(HttpConstant.HTTP_REQUEST_METHOD_GET);

			// 从输入流读取返回内容
			inputStream = conn.getInputStream();

			// 读取输入流
			inputStreamReader = new InputStreamReader(inputStream, HttpConstant.HTTP_DEFAULT_CHARSET);

			// 读取输入流缓存
			bufferedReader = new BufferedReader(inputStreamReader);

			// Buffer读取的字符串
			String bfString = null;

			while ((bfString = bufferedReader.readLine()) != null) {
				// Buffer拼接
				buffer.append(bfString);
			}
		} catch (Exception ex) {
			LogUtils.error("向指定URL发送HTTP的GET请求发生异常..." + ex.toString(), ex);
		} finally {
			// 释放资源
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭bufferedReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭inputStreamReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					LogUtils.error("关闭inputStream发生异常..." + e.toString(), e);
				}
			}

			if (conn != null) {
				conn.disconnect();
			}
		}

		// LOG---------------------------------------------
		// 请求返回值
		LogUtils.info(uuid.toString() + " [GET_RESUT] " + buffer.toString());
		// LOG---------------------------------------------

		// 返回值
		return buffer.toString();
	}

	/**
	 * 向指定URL发送HTTPS的POST请求.
	 * 
	 * @param url
	 *            请求URL
	 * @param params
	 *            请求参数
	 * @param output
	 *            POST体
	 * @return String API返回值
	 */
	public final String doHttpsPostString(final String url, final List<NameValuePair> params, final String output) {
		// LOG UUID
		UUID uuid = UUID.randomUUID();

		// 返回的字符串
		StringBuffer buffer = new StringBuffer();

		// 编辑参数
		String urlParams = URLEncodedUtils.format(params, HttpConstant.HTTP_DEFAULT_CHARSET);

		// X509证书对象
		X509TrustManager[] x509mgr = { new X509TrustManagerImpl() };

		// 连接对象
		HttpsURLConnection conn = null;

		// 读取输入流缓存对象
		BufferedReader bufferedReader = null;

		// 读取输入流对象
		InputStreamReader inputStreamReader = null;

		// 从输入流读取返回内容对象
		InputStream inputStream = null;

		try {
			// SSL上下文对象
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, x509mgr, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			// 初始化URL对象
			URL urlObject = new URL(url + HttpConstant.HTTP_QUERY_SYMBOL + urlParams);

			// LOG---------------------------------------------
			// 生成POST请求URLLog
			LogUtils.info(uuid.toString() + " [POST] " + urlObject.toString());

			// 生成POST请求BodyLog
			LogUtils.info(uuid.toString() + " [POST_BODY] " + output);
			// LOG---------------------------------------------

			// 生成URL连接对象
			conn = (HttpsURLConnection) urlObject.openConnection();

			// 设定SSLSocketFactory对象
			conn.setSSLSocketFactory(ssf);

			// 设置超时3秒
			conn.setConnectTimeout(HttpConstant.HTTP_TIME_OUT);

			// 设置可以使用输出流
			conn.setDoOutput(true);

			// 设置可以使用InputStream
			conn.setDoInput(true);

			// 不做缓存
			conn.setUseCaches(false);

			// 设置请求方法
			conn.setRequestMethod(HttpConstant.HTTP_REQUEST_METHOD_POST);

			// 设置输出流
			if (!StringUtils.isEmpty(output)) {
				// 获得链接的输出流
				OutputStream outputStream = conn.getOutputStream();

				// 向链接写入输出流
				outputStream.write(output.getBytes(HttpConstant.HTTP_DEFAULT_CHARSET));
			}

			// 从输入流读取返回内容
			inputStream = conn.getInputStream();

			// 读取输入流
			inputStreamReader = new InputStreamReader(inputStream, HttpConstant.HTTP_DEFAULT_CHARSET);

			// 读取输入流缓存
			bufferedReader = new BufferedReader(inputStreamReader);

			// Buffer读取的字符串
			String bfString = null;

			while ((bfString = bufferedReader.readLine()) != null) {
				// Buffer拼接
				buffer.append(bfString);
			}
		} catch (NoSuchAlgorithmException e) {
			LogUtils.error("密钥产生发生异常..." + e.toString(), e);
		} catch (NoSuchProviderException e) {
			LogUtils.error("SSL上下文对象初始化发生异常..." + e.toString(), e);
		} catch (Exception ex) {
			LogUtils.error("向指定URL发送HTTPS的POST请求发生异常..." + ex.toString(), ex);
		} finally {
			// 释放资源
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭bufferedReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭inputStreamReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					LogUtils.error("关闭inputStream发生异常..." + e.toString(), e);
				}
			}

			if (conn != null) {
				conn.disconnect();
			}
		}

		// LOG---------------------------------------------
		// 请求返回值
		LogUtils.info(uuid.toString() + " [POST_RESUT] " + buffer.toString());
		// LOG---------------------------------------------

		// 返回值
		return buffer.toString();
	}

	/**
	 * 向指定URL发送HTTP的POST请求.
	 * 
	 * @param url
	 *            请求URL
	 * @param params
	 *            请求参数
	 * @param output
	 *            POST体
	 * @return String API返回值
	 */
	public final String doHttpPostString(final String url, final List<NameValuePair> params, final String output) {
		// LOG UUID
		UUID uuid = UUID.randomUUID();

		// 返回的字符串
		StringBuffer buffer = new StringBuffer();

		// 编辑参数
		String urlParams = URLEncodedUtils.format(params, HttpConstant.HTTP_DEFAULT_CHARSET);

		// 连接对象
		HttpURLConnection conn = null;

		// 读取输入流缓存对象
		BufferedReader bufferedReader = null;

		// 读取输入流对象
		InputStreamReader inputStreamReader = null;

		// 从输入流读取返回内容对象
		InputStream inputStream = null;

		try {
			// 初始化URL对象
			URL urlObject = new URL(url + HttpConstant.HTTP_QUERY_SYMBOL + urlParams);

			// LOG---------------------------------------------
			// 生成POST请求URLLog
			LogUtils.info(uuid.toString() + " [POST] " + urlObject.toString());

			// 生成POST请求BodyLog
			LogUtils.info(uuid.toString() + " [POST_BODY] " + output);
			// LOG---------------------------------------------

			// 生成URL连接对象
			conn = (HttpURLConnection) urlObject.openConnection();

			// 设置超时3秒
			conn.setConnectTimeout(HttpConstant.HTTP_TIME_OUT);

			// 设置可以使用输出流
			conn.setDoOutput(true);

			// 设置可以使用InputStream
			conn.setDoInput(true);

			// 不做缓存
			conn.setUseCaches(false);

			// 设置请求方法
			conn.setRequestMethod(HttpConstant.HTTP_REQUEST_METHOD_POST);

			// 设置输出流
			if (!StringUtils.isEmpty(output)) {
				// 获得链接的输出流
				OutputStream outputStream = conn.getOutputStream();

				// 向链接写入输出流
				outputStream.write(output.getBytes(HttpConstant.HTTP_DEFAULT_CHARSET));
			}

			// 从输入流读取返回内容
			inputStream = conn.getInputStream();

			// 读取输入流
			inputStreamReader = new InputStreamReader(inputStream, HttpConstant.HTTP_DEFAULT_CHARSET);

			// 读取输入流缓存
			bufferedReader = new BufferedReader(inputStreamReader);

			// Buffer读取的字符串
			String bfString = null;

			while ((bfString = bufferedReader.readLine()) != null) {
				// Buffer拼接
				buffer.append(bfString);
			}
		} catch (Exception ex) {
			LogUtils.error("向指定URL发送HTTP的POST请求发生异常..." + ex.toString(), ex);
		} finally {
			// 释放资源
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭bufferedReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭inputStreamReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					LogUtils.error("关闭inputStream发生异常..." + e.toString(), e);
				}
			}

			if (conn != null) {
				conn.disconnect();
			}
		}

		// LOG---------------------------------------------
		// 请求返回值
		LogUtils.info(uuid.toString() + " [POST_RESUT] " + buffer.toString());
		// LOG---------------------------------------------

		// 返回值
		return buffer.toString();
	}

	public final String doHttpGetString(final String url, final List<NameValuePair> params, String output) {
		// LOG UUID
		UUID uuid = UUID.randomUUID();

		// 返回的字符串
		StringBuffer buffer = new StringBuffer();

		// 编辑参数
		String urlParams = URLEncodedUtils.format(params, HttpConstant.HTTP_DEFAULT_CHARSET);

		// 连接对象
		HttpURLConnection conn = null;

		// 读取输入流缓存对象
		BufferedReader bufferedReader = null;

		// 读取输入流对象
		InputStreamReader inputStreamReader = null;

		// 从输入流读取返回内容对象
		InputStream inputStream = null;

		try {

			// 初始化URL对象
			URL urlObject = new URL(url + HttpConstant.HTTP_QUERY_SYMBOL + urlParams);

			// LOG---------------------------------------------
			// 生成GET请求Log
			LogUtils.info(uuid.toString() + " [GET] " + urlObject.toString());
			// LOG---------------------------------------------
			// 生成URL连接对象
			conn = (HttpURLConnection) urlObject.openConnection();

			// 设置超时3秒
			conn.setConnectTimeout(HttpConstant.HTTP_TIME_OUT);

			// 设置可以使用输出流
			conn.setDoOutput(true);

			// 设置可以使用InputStream
			conn.setDoInput(true);

			// 不做缓存
			conn.setUseCaches(false);

			// 设置请求方法
			conn.setRequestMethod(HttpConstant.HTTP_REQUEST_METHOD_POST);

			// 设置输出流
			if (!StringUtils.isEmpty(output)) {
				// 获得链接的输出流
				OutputStream outputStream = conn.getOutputStream();

				// 向链接写入输出流
				outputStream.write(output.getBytes(HttpConstant.HTTP_DEFAULT_CHARSET));
			}

			// 从输入流读取返回内容
			inputStream = conn.getInputStream();

			// 读取输入流
			inputStreamReader = new InputStreamReader(inputStream, HttpConstant.HTTP_DEFAULT_CHARSET);

			// 读取输入流缓存
			bufferedReader = new BufferedReader(inputStreamReader);

			// Buffer读取的字符串
			String bfString = null;

			while ((bfString = bufferedReader.readLine()) != null) {
				// Buffer拼接
				buffer.append(bfString);
			}
		} catch (Exception ex) {
			LogUtils.error("向指定URL发送HTTP的GET请求发生异常..." + ex.toString(), ex);
		} finally {
			// 释放资源
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭bufferedReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					LogUtils.error("关闭inputStreamReader发生异常..." + e.toString(), e);
				}
			}

			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					LogUtils.error("关闭inputStream发生异常..." + e.toString(), e);
				}
			}

			if (conn != null) {
				conn.disconnect();
			}
		}

		// LOG---------------------------------------------
		// 请求返回值
		LogUtils.info(uuid.toString() + " [GET_RESUT] " + buffer.toString());
		// LOG---------------------------------------------

		// 返回值
		return buffer.toString();
	}

	/**
	 * 向指定URL发送HTTP的GET请求下载文件.
	 * 
	 * @param url
	 *            请求URL
	 * @param params
	 *            请求参数
	 * @return InputStream 下载输入流
	 */
	public final HttpURLConnection getHttpConn(final String url, final List<NameValuePair> params) {

		// 链接
		HttpURLConnection conn = null;

		// 编辑参数
		String urlParams = URLEncodedUtils.format(params, HttpConstant.HTTP_DEFAULT_CHARSET);

		try {
			// 初始化URL对象
			URL urlObject = new URL(url + HttpConstant.HTTP_QUERY_SYMBOL + urlParams);

			// HTTP连接对象
			conn = (HttpURLConnection) urlObject.openConnection();

			// 得到输入流
			// inputStream = conn.getInputStream();

		} catch (IOException e) {
			// 如果出错链接置空
			conn = null;
			LogUtils.error("向指定URL发送HTTP的GET请求下载文件发生异常..." + e.toString(), e);
		}

		// 返回链接
		return conn;
	}
}