/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 加密工具
 * */
package com.app.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

/**
 * ����һ��MD5��
 * 
 * @author Administrator
 *
 */
public class MD5Util {

    public static String encodePassword(String password) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(password)) {
            return password;
        }
        return getMD5(password.getBytes("utf-8"));
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = { // �������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest(); // MD5 �ļ�������һ�� 128 λ�ĳ�������
            // ���ֽڱ�ʾ���� 16 ���ֽ�
            char str[] = new char[16 * 2]; // ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ���
            // ���Ա�ʾ�� 16 ������Ҫ 32 ���ַ�
            int k = 0; // ��ʾת������ж�Ӧ���ַ�λ��
            for (int i = 0; i < 16; i++) { // �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�
                // ת���� 16 �����ַ���ת��
                byte byte0 = tmp[i]; // ȡ�� i ���ֽ�
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // ȡ�ֽ��и� 4 λ������ת��,
                // >>> Ϊ�߼����ƣ�������λһ������
                str[k++] = hexDigits[byte0 & 0xf]; // ȡ�ֽ��е� 4 λ������ת��
            }
            s = new String(str); // ����Ľ��ת��Ϊ�ַ���

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public final static String MD5(String inputStr) {
        // ���ڼ��ܵ��ַ�
        char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            // ʹ��utf-8�ַ������� String ����Ϊ byte���У���������洢��һ���µ� byte������
            byte[] btInput = inputStr.getBytes("utf-8");

            // ��ϢժҪ�ǰ�ȫ�ĵ����ϣ�����������������С�����ݣ�������̶����ȵĹ�ϣֵ��
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            // MessageDigest����ͨ��ʹ�� update�����������ݣ� ʹ��ָ����byte�������ժҪ
            mdInst.update(btInput);

            // ժҪ����֮��ͨ������digest����ִ�й�ϣ���㣬�������
            byte[] md = mdInst.digest();

            // ������ת����ʮ�����Ƶ��ַ�����ʽ
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) { // i = 0
                byte byte0 = md[i]; // 95
                str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
                str[k++] = md5String[byte0 & 0xf]; // F
            }

            // ���ؾ������ܺ���ַ���
            return new String(str);

        } catch (Exception e) {
            return null;
        }
    }
    
	public static String encryption(String plain) throws UnsupportedEncodingException {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plain.getBytes("utf-8"));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}
	
	public static byte[] encode2bytes(String source) {
		byte[] result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(source.getBytes("UTF-8"));
			result = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * ��Դ�ַ���ʹ��MD5����Ϊ32λ16������
	 * @param source
	 * @return
	 */
	public static String encode2hex(String source) {
		byte[] data = encode2bytes(source);

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0xff & data[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			
			hexString.append(hex);
		}
		
		return hexString.toString();
	}
	
	/**
	 * ��֤�ַ����Ƿ�ƥ��
	 * @param unknown ����֤���ַ���
	 * @param okHex	ʹ��MD5���ܹ���16�����ַ���
	 * @return	ƥ�䷵��true����ƥ�䷵��false
	 */
	public static boolean validate(String unknown , String okHex) {
		return okHex.equals(encode2hex(unknown));
	}
	
	public static String GetSign(Map<String,String> paramMap) throws UnsupportedEncodingException
	{
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String,String>>(paramMap.entrySet());
	    Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
	    	
	    	public int compare(Entry<String,String> o1,Entry<String,String> o2)
	    	{
	    		return o1.getKey().compareToIgnoreCase(o2.getKey());
	    	}
	    });
	    
	    String signStr = "";
	    
	    boolean first = true;
	    for(Map.Entry<String, String> ent : list)
	    if(first)
	    {
	    signStr += ent.getKey() + "=" + ent.getValue();
	    first = false;
	    }
	    else
	    signStr += "&" +ent.getKey() + "=" + ent.getValue();
	    
	    signStr += "&5304d8ebe705a2cb06cb1cceb9c3d63052204986";
	    System.out.println("����:" + signStr);
	    signStr = signStr.toLowerCase();
	    System.out.println("toLowerCase:" + signStr);
	    
		signStr = encodePassword(signStr).toUpperCase();
		
		System.out.println("MD5:" + signStr);
	    return signStr;
	}
}
