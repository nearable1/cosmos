package com.cosmos.utils;

import java.security.AlgorithmParameters;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.alibaba.fastjson.JSONObject;

public class SecretUtils {
	private SecretUtils(){}

	public static String AES128CBCdecrypt(String encryptedData,String iv,String appId,String sessionKey) throws Exception {
		// �����ܵ�����
	
		byte[] dataByte= Base64.decodeBase64(encryptedData);
	
		// ������Կ
	
		byte[] keyByte= Base64.decodeBase64(sessionKey);
	
		// ƫ����
	
		byte[] ivByte= Base64.decodeBase64(iv);
		
		// �����Կ����16λ����ô�Ͳ���.  ���if �е����ݺ���Ҫ
//        int base = 16;
//        if (keyByte.length % base != 0) {
//            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
//            byte[] temp = new byte[groups * base];
//            Arrays.fill(temp, (byte) 0);
//            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
//            keyByte = temp;
//        }
		
		Security.addProvider(new BouncyCastleProvider());

		Cipher cipher= Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
		//keyByte
		SecretKeySpec spec=new SecretKeySpec(keyByte,"AES");
	
		AlgorithmParameters parameters= AlgorithmParameters.getInstance("AES");
	
		parameters.init(new IvParameterSpec(ivByte));
	
		cipher.init(Cipher.DECRYPT_MODE,spec,parameters);// ��ʼ��
	
		byte[] resultByte=cipher.doFinal(dataByte);
	
		if(null!=resultByte&&resultByte.length> 0) {
	
			String result=new String(resultByte,"UTF-8");
		
			JSONObject obj= JSONObject.parseObject(result);
		
			JSONObject watermark=obj.getJSONObject("watermark");
		
			if(!watermark.getString("appid").equals(appId)){
		
				throw new Exception("��С����appid������");
		
			}
		
			if(System.currentTimeMillis()-watermark.getLong("timestamp")*1000>10000){
		
				throw new Exception("��֤ʱ�����");
		
			}
		
			return result;
		
		}else {

			return null;
		}
	
	}

}
