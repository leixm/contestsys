/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 随机字符串生成工具类
 * */
package com.app.tools;
import org.springframework.stereotype.Repository;
import java.util.Random;

@Repository
public class RandomString {
	public static String getRandomString(int len)
	{
		Random rd = new Random();
		String s = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
		String str="";
		for(int i=0;i<len;i++)
		{
			int index = rd.nextInt(s.length());
			str+=s.charAt(index);
		}
		return str;
	}
	
	public static String getRandomNum(int len)
	{
		Random rd = new Random();
		String s = "1234567890";
		String str="";
		for(int i=0;i<len;i++)
		{
			int index = rd.nextInt(s.length());
			str += s.charAt(index);
		}
		return str;
	}
}
