/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 文件工具类
 * */
package com.app.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FileHelper {

	public File CreateFile(String path,String Content) throws UnsupportedEncodingException, IOException
	{
		//int index = path.lastIndexOf("/");
		//String dic = path.substring(0, index);
	    //new File(path).mkdirs();
	    File file = new File(path);
	    if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
	    FileOutputStream out = new FileOutputStream(file);
	    out.write(Content.getBytes("utf-8"));
	    out.close();
	    return file;
	}
	
	public String getFileContent(String path) throws IOException
	{
	    File file = new File(path);
	    FileInputStream in = new FileInputStream(file);
		  StringBuffer sb = new StringBuffer();
		  byte[] buffer = new byte[1024];
		  int hasRead = 0;
 		  while((hasRead=in.read(buffer))!=-1) 
 		  { 
 			  sb.append(new String(buffer,0,hasRead,"utf-8"));
 		  }
 		  in.close();
 		  return sb.toString();
	}
	
    public void deleteFile(File file) {  
        if (file.exists()) {//�ж��ļ��Ƿ����  
         if (file.isFile()) {//�ж��Ƿ����ļ�  
          file.delete();//ɾ���ļ�   
         } else if (file.isDirectory()) {//�����������һ��Ŀ¼  
          File[] files = file.listFiles();//����Ŀ¼�����е��ļ� files[];  
          for (int i = 0;i < files.length;i ++) {//����Ŀ¼�����е��ļ�  
           this.deleteFile(files[i]);//��ÿ���ļ�������������е���  
          }  
          file.delete();//ɾ���ļ���  
         }  
        } else {  
         System.out.println("��ɾ�����ļ�������");  
        }  
       }
	
     
}
