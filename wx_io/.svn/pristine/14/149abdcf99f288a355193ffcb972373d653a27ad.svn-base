package com.nssol_sh.util.tools.http.ssl.trust;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class X509TrustManagerImpl implements X509TrustManager {

	/**
	 * 给出同位体提供的部分或完整的证书链，构建到可信任的根的证书路径， 并且返回是否可以确认和信任将其用于基于验证类型的客户端 SSL 验证。
	 * 
	 * @param chain
	 *            - 同位体的证书链
	 * @param authType
	 *            - 基于客户端证书的验证类型
	 * @exception IllegalArgumentException
	 *                - 如果 null 或长度为零的 chain 传递给 chain 参数，或者 null 或长度为零的字符串传递给
	 *                authType
	 * @exception CertificateException
	 *                - 如果证书链不受此 TrustManager 信任。
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

	}

	/**
	 * 给出同位体提供的部分或完整的证书链，构建到可信任的根的证书路径， 并且返回是否可以确认和信任将其用于基于验证类型的服务器 SSL 验证。
	 * 
	 * @param ax509certificate
	 *            同位体的证书链
	 * @param s
	 *            使用的密钥交换算法
	 * @exception IllegalArgumentException
	 *                - 如果 null 或长度为零的 chain 传递给 chain 参数，或者 null 或长度为零的字符串传递给
	 *                authType
	 * @exception CertificateException
	 *                - 如果证书链不受此 TrustManager 信任。
	 */
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

	}

	/**
	 * @return 返回受验证同位体信任的认证中心的数组。 可接受的 CA 发行者证书的非 null（可能为空）的数组。
	 * 
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
