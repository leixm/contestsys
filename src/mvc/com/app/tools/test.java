package com.app.tools;

import static org.hamcrest.CoreMatchers.startsWith;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import junit.framework.TestCase;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import com.sun.jna.platform.win32.WinBase.PROCESS_INFORMATION;
import com.sun.jna.platform.win32.WinBase.SECURITY_ATTRIBUTES;
import com.sun.jna.platform.win32.WinBase.STARTUPINFO;
import com.sun.jna.platform.win32.WinBase.SYSTEM_INFO.PI;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.MSG;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.ptr.IntByReference;
import com.sun.mail.util.MailSSLSocketFactory;

public class test extends TestCase {

	
	@Test
	public void test()
	{
    	double ans = 4750;
    	double ans1 = 2750;
    	double ans2 = ans - ans1;
    	double section[] = {3000,6000,999999};
    	double rate[] = {0.1,0.08,0.05};
    	double res = 0;
    
    	int i;
    	for(i=0;i<3;i++)
    	{
    		if(ans1<section[i])break;
    	}
    	
    	double last = ans1;
    	System.out.println("last:" + last + " i=" + i);
    	
    	for(;i<3;i++)
    	{
    		if(section[i] - last > ans2) 
    		{
    			res += ans2 * rate[i];
    			ans2 = 0;
    		}
    		else
    		{
    			res += (section[i] - last) * rate[i];
    			ans2 -= section[i] - last;
    		}
    		System.out.println("res:" + res + " i:" + i);
    		last = section[i];
    	}
    	System.out.print(res);
	}
	
	public static boolean sendMail(String receive, String subject, String msg, String filename)
            throws GeneralSecurityException {
        if (StringUtils.isEmpty(receive)) {
            return false;
        }

        // 发件人电子邮箱
        final String from = "123456789@163.com";
        // 发件人电子邮箱密码
        final String pass = "123456";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.163.com"; // 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() { // qq邮箱服务器账户、第三方登录授权码
                return new PasswordAuthentication(from, pass); // 发件人邮件用户名、密码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receive));

            // Set Subject: 主题文字
            message.setSubject(subject);

            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();

            // 消息
            messageBodyPart.setText(msg);

            // 创建多重消息
            Multipart multipart = new MimeMultipart();

            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            // 附件部分
            messageBodyPart = new MimeBodyPart();
            // 设置要发送附件的文件路径
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));

            // messageBodyPart.setFileName(filename);
            // 处理附件名称中文（附带文件路径）乱码问题
            messageBodyPart.setFileName(MimeUtility.encodeText(filename));
            multipart.addBodyPart(messageBodyPart);

            // 发送完整消息
            message.setContent(multipart);

            // 发送消息
            Transport.send(message);
            // System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

/*	@Autowired
	private com.app.dao.itemDao itemDao;
	*/
/*	@Test
	public void test()
	{
		HANDLE handle = null;
		handle = Kernel32.INSTANCE.OpenProcess(Kernel32.PROCESS_ALL_ACCESS, false, 24228);
		
		
	}*/
	
/*	@Test
	 public void testfile() throws UnsupportedEncodingException, IOException
     {
   	  FileHelper fh = new FileHelper();
   	  String content = fh.getFileContent("F:\\JAVAWEB\\Workspaces\\MyEclipse 2017 CI\\rustshop\\src\\main\\webapp\\itemsExperimental.txt");
   	  String[] str = content.split("\n");
    //  itemDao dao = new itemDao();
   	  String newContent = "";
   	  String templateString = "insert into item(category,cname,shortname,game,id) values(''{0}'',''{1}'',''{2}'',''RUST'',''{3}'');\n";
   	  int id = 1;
   	  for(int i=0;i<str.length;i++)
   	  {
   		  int index = str[i].indexOf(" - ");
   		  
   		  int index1 = str[i].indexOf("|");
   		  String  s1;
   		  if(i==0)
   		  s1 = str[i].substring(1,index);
   		  else s1 = str[i].substring(0,index);
   		  String s2 = str[i].substring(index+3, index1);
   		  String s3 = str[i].substring(index1+1,str[i].length()-1);
   		 // System.out.println("s1=" + s1 +" s2="+s2 + " s3="+ s3);
   		  newContent += MessageFormat.format(templateString,"物品-"+ s1,s2,s3,String.valueOf(id));
   		  id++;
   		  //itemDao.Add(itemDao.GetTotalCounts()+1, s3, "", s2, s1, "RUST");
   	  }
   	  System.out.println(newContent);
   	  
   /*	  System.out.println(content);
   	  String replaceContent = "#region params \nString username=\"{0}\";\nString password=\"{1}\";\nString server = \"{2}\";\n ";
   	  content = content.replace("#region params", MessageFormat.format(replaceContent, "zz1006967725","771226822","k2"));
			fh.CreateFile("F:\\JAVAWEB\\Workspaces\\MyEclipse 2017 CI\\rustshop\\src\\main\\webapp\\template\\K2Shop.cs", content);
   	  
   
	      
   //  }

	
	public void test() throws IOException, InterruptedException
	{
		//ProcessBuilder pb = new ProcessBuilder("F:\\rustServer\\Run_DS.bat");
	//	pb.redirectOutput();

		//final Process proc =  Runtime.getRuntime().exec("F:\\rustServer\\Run_DS.bat");
	//	final Process proc =  pb.start();
		
		(new Thread(new Runnable(){

			@Override
			public void run() {
				BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				String content = null;
				try {
					while((content=br.readLine())!=null)
					{
						//System.out.println("isalive:" + proc.isAlive());
						System.out.println(content);
					}
						
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		})).start();
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String content = null;
		try {
			while((content=br.readLine())!=null)
			{
				//System.out.println("isalive:" + proc.isAlive());
				System.out.println(content);
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		proc.waitFor();
	} 
	
	
	
	
	
/*	@Test
	public void test1()
	{
		char[] buffer = new char[1024];
		HWND hwnd = User32.INSTANCE.GetForegroundWindow();
		System.out.println("pointer:" + hwnd.getPointer());
		User32.INSTANCE.GetWindowText(hwnd, buffer, 1024);
		System.out.println(buffer);
		//HWND hwnd = User32.INSTANCE.FindWindow(null, "Rust");
		//char[] arr = new char[1024];
		//User32.INSTANCE.GetWindowText(hwnd, arr, 1024);
		//System.out.println(arr);
	}*/
	/*
	public void test2() throws InterruptedException, UnsupportedEncodingException
	{
		System.out.println("test2");
		
		User32.INSTANCE.EnumWindows(new WNDENUMPROC(){
			public boolean callback(HWND hwnd, Pointer p) {
				// TODO Auto-generated method stub
				IntByReference re = new IntByReference();
			    User32.INSTANCE.GetWindowThreadProcessId(hwnd, re);
			    System.out.println(re.getValue());
			    char[] buffer = new char[1024];
			    
			    if(User32.INSTANCE.IsWindow(hwnd)&&User32.INSTANCE.IsWindowEnabled(hwnd)&&User32.INSTANCE.IsWindowVisible(hwnd)){
			    if(re.getValue() == 31256)
			    	{
			    	User32.INSTANCE.GetWindowText(hwnd, buffer, 1024);
			    //	Kernel32.INSTANCE.PeekNamedPipe(arg0, arg1, arg2, arg3, arg4, arg5)
			    	System.out.println(buffer);
			    	Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
			    	LPARAM lp = new LPARAM();
			    	lp.setValue(1);
			    	try{
			    	User32.INSTANCE.PostMessage(hwnd, 0x0104, new WPARAM(0x00000012), new LPARAM(0x20380001));//0x00000012可以写成0x12
			    	Thread.sleep(1000);
			    	User32.INSTANCE.PostMessage(hwnd, 0x0104, new WPARAM(0x00000041), new LPARAM(0x201E0001));//0x00000041可以写成0x41
			    	Thread.sleep(1000);
			    	User32.INSTANCE.PostMessage(hwnd, 0x0106, new WPARAM('a'), new LPARAM(0x20210001));// (把char转换为 int 或者API声明时传递参数wParam为char) 
			    	Thread.sleep(1000);
			    	User32.INSTANCE.PostMessage(hwnd, 0x0105, new WPARAM(0x00000041), new LPARAM(0xE01E0001));//0x00000041 == 'A'
			    	Thread.sleep(1000);
			    	User32.INSTANCE.PostMessage(hwnd, 0x0105, new WPARAM(0x00000012), new LPARAM(0xC0380001));//
			    	}
			    	catch(Exception e)
			    	{
			    		
			    	}
			    	//User32.INSTANCE.PostMessage(hwnd, User32.wm_, new WPARAM(User32.VK_LCONTROL), new LPARAM());
			    //	User32.INSTANCE.PostMessage(hwnd, User32.WM_KEYDOWN, new WPARAM(User32.VK_CONTROL), new LPARAM(1));
			    	//User32.INSTANCE.PostMessage(hwnd, User32.WM_SYSKEYDOWN, new WPARAM(0x41), new LPARAM(1<<29));
			    //	User32.INSTANCE.PostMessage(hwnd, User32.WM_KEYUP, new WPARAM('A'), new LPARAM(1));
			    //	User32.INSTANCE.PostMessage(hwnd, User32.WM_KEYUP, new WPARAM(User32.VK_CONTROL), new LPARAM(1)); 
			    	Transferable clipTf = sysClip.getContents(null);
			    	String content = ""; 
			    	if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor))
						try {
							content += (String)clipTf.getTransferData(DataFlavor.stringFlavor);
							System.out.println(content); 
						} catch (UnsupportedFlavorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	
			    	return false;
			    	}
			    }
				return true;
			} 
			
			
		}, null);*/
		
//		HANDLEByReference readPipe = new HANDLEByReference();
//		HANDLEByReference writePipe = new HANDLEByReference();
//		SECURITY_ATTRIBUTES sa = new SECURITY_ATTRIBUTES();
//		sa.bInheritHandle = true;
//		sa.lpSecurityDescriptor = null;
//		Kernel32.INSTANCE.CreatePipe(readPipe, writePipe, sa, 0);
//		STARTUPINFO si = new STARTUPINFO();
//		si.dwFlags = 0x00000100;
//		si.hStdInput = readPipe.getValue();
//		si.hStdOutput = writePipe.getValue();
//		si.hStdError = Kernel32.INSTANCE.GetStdHandle(Kernel32.STD_ERROR_HANDLE);
//		
//		PROCESS_INFORMATION info = new PROCESS_INFORMATION();
//		
//		Kernel32.INSTANCE.CreateProcess("F:\\rustServer\\Run_DS.bat", "", null, null, true, new DWORD(), null, null, si, info);
//		
//		byte[] arr = new byte[1024];
//		IntByReference int1 = new IntByReference();
//		IntByReference int2 = new IntByReference();
//		IntByReference int3 = new IntByReference();
//		
//		while(true)
//		{
//			if(Kernel32.INSTANCE.PeekNamedPipe(readPipe.getValue(), arr, 1024, int1, int2, int3))
//			{
//				System.out.println(new String(arr,"UTF-8"));
//			}
//			Thread.sleep(1000);
//		}
		
		 

	//}

	/*@Test
	public void test() throws Exception
	{

		//String cmd= "wmic process where processid=\"" + 29324 + "\"";
		//sendMessage();
   // System.out.println("s");
   String cmd = "start F:\\rustServer\\Run_DS.bat";
			long pid = this.runCommand(cmd);
	System.out.println("pid=" + pid);
	cmd = "wmic process where parentprocessid=\"" + pid + "\" get processid";
	List<String> list = Runcmd(cmd);
	final String cmdID = list.get(2).trim();
		System.out.println("cmdID:" + cmdID);

       //Runtime.getRuntime().exec("F:\\rustServer\\Run_DS.bat");
		(new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("进入线程");
				boolean flag = false;
				while(!flag)
				{
				String cmd = "tasklist /v /fi \"pid eq " + cmdID + "\"";
				List<String> list = Runcmd(cmd);
				System.out.println("Runcmd");
				String title = list.get(3).substring(157,list.get(3).length()).trim();
					if(title.contains("cmd.exe"))System.out.println("启动中");
					else {
						flag=true;
						System.out.println("启动完成!");
						System.out.println("title: " + title);
						//HWND hwnd = User32.INSTANCE.GetForegroundWindow(); 
						HWND hwnd = User32.INSTANCE.FindWindow(null,title);
						char[] arr= new char[1024];	
						 
						//hwnd.setPointer(new Pointer(0x000C0652));
						System.out.println("pointer:" + hwnd.getPointer());
						User32.INSTANCE.GetWindowText(hwnd, arr, 1024);
						System.out.println(arr);
						
						String command = "save";
						for(int i=0;i<command.length();i++)
						User32.INSTANCE.PostMessage(hwnd, User32.WM_CHAR, new WinDef.WPARAM(command.charAt(i)), new LPARAM());
						User32.INSTANCE.PostMessage(hwnd, User32.WM_KEYUP, new WinDef.WPARAM(13), new LPARAM());
						MSG msg = new MSG();
						msg.hWnd = hwnd;
						
						User32.INSTANCE.GetMessage(msg, hwnd, 0, 0);
						System.out.println(msg.lParam);
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("监听线程结束！");
				//Runcmd("taskkill /pid " + cmdID);
			}
		})).start();
	
		while(true)
		{
			Thread.sleep(2000);
		}
	}*/

	  public long runCommand(String cmdExp) throws InterruptedException{
	   	     InputStream[] tmpstm=new InputStream[2]; 
	   	     String[] Info=new String[2];
	   	     InputStreamReader isr=null;
	   	     BufferedReader br=null;
	   	     String okInfo="";
	   	     String errInfo="";
	   	     long pid= 0;
	   	     try {
	   	         String osName =System.getProperty("os.name");
	   	         System.out.println("osName:"+osName);
	   	         String[] cmd=new String[3];
	   	                cmd[0]="cmd.exe";
	   	                cmd[1]="/c";
	   	                cmd[2]=cmdExp;
	   	         System.out.println(cmd[2]);
	   	         Runtime rt=Runtime.getRuntime();
	            
	   	         Process proc=rt.exec(cmd);
	   	         
	   	         pid = this.getProcessPID(proc);
	   	         System.out.println(this.getProcessPID(proc));
	   	       //  int id = rt.
	   	         InputStream in = proc.getInputStream();
	   	        
	   	         int c;
	   	      System.out.println("flag1");
	   	  /*    while ((c = in.read()) != -1) {
	              System.out.print((char)c);
	      } */
	   	         
	   	      System.out.println("flag2");
	   	         // any error message
	   	       //  gobbler errgobbler=new gobbler(proc,proc.getErrorStream(),"error");
	   	         tmpstm[0]=proc.getInputStream();
	   	         tmpstm[1]=proc.getErrorStream();
	   	          // any output
	   	       //  gobbler outgobbler=new gobbler(proc,proc.getInputStream(),"output");
	   	       // errgobbler.start();
	   	       // outgobbler.start();
	   	          isr=new InputStreamReader(tmpstm[0]);
	   	          br=new BufferedReader(isr);
	   	          String line=null;
	   	     /*     while((line=br.readLine())!=null){
	   	            System.out.println("*******"+line);
	   	            okInfo+=line+"\n";
	   	            }*/
	   	            
	   	          line=null;
	   	          isr=new InputStreamReader(tmpstm[1]);
	   	          br=new BufferedReader(isr);
	   	          
	   	      /*    while((line=br.readLine())!=null){
	   	            System.out.println("*******"+line);
	   	            errInfo+=line+"\n";
	   	            }*/
	   	            
	   	         //Info[0]=okInfo;
	   	         //Info[1]=errInfo; 
	   	      //   int exitVal=proc.waitFor();
	   	         // System.out.println("exitVal "+exitVal);
	   	     } catch (IOException e) {
	   	             e.printStackTrace();  //To change body of catch statement use Options | File Templates.
	   	     }
	   	     String mstmp="";
	   	     if (okInfo!=null) mstmp=okInfo;
	   	     if (errInfo!=null) mstmp+=errInfo;
	   	   
	   	     
	   	     return pid;
	   	  }
	  
	/*  @Test
		public void sendMessage() throws Exception{
		//  User32.GetWindowText();
		 
			HWND hWnd = User32.FindWindow("RustDedicated.exe", "RustDedicated");
	
			if(hWnd.getValue()>0){
				System.out.println("window exists");
				User32.SendMessage(hWnd, new UINT(0x10), new WPARAM(0), new LPARAM(0));
			}else{
				System.out.println("window doesn't exists");
			}
		}*/

/*	@Test
	public void test()
	{
		comfirmSingleProcess("RustDedicated");
	}
	*/
	public List<String> Runcmd(String cmd) {


        // 声明文件读取流
        BufferedReader out = null;
        BufferedReader br = null;
        List<String> list = new ArrayList<>();
         try {

              // 创建系统进程
        	  Runtime rt=Runtime.getRuntime();
        	  Process p = rt.exec(cmd);
              // 读取进程信息
              //out = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream()), Charset.forName("GB2312")));
        	 // out = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream()), Charset.forName("utf-8")));
        	  out = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
              br = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getErrorStream())));

              // 创建集合 存放 进程+pid
              // 读取
              String ostr;
              while ((ostr = out.readLine()) != null){
                   // 将读取的进程信息存入集合
                   list.add(ostr);
              }
              
              //for (int i = 0; i < list.size(); i++)
            	//  System.out.println(list.get(i));
              // 遍历所有进程
         /*     for (int i = 3; i < list.size(); i++) {
            	  System.out.println("info: " + list.get(i));
                  // 必须写死,截取长度,因为是固定的
                  String process = list.get(i).substring(0, 25).trim(); // 进程名
                  String pid = list.get(i).substring(25, 35).trim();    // 进程号
                  String title = list.get(i).substring(157,list.get(i).length()).trim();
                  System.out.println("process=" + process + " pid=" +pid + "title=" + title);
                  // 匹配指定的进程名,若匹配到,则立即杀死
                  
              }*/

              // 若有错误信息 即打印日志
              String estr = br.readLine();
              if (estr != null) {
                 
              }
              return list;
        } catch (IOException e) {
            e.printStackTrace();
            
        }finally {
            // 关流
            try {
                if(out != null) { out.close(); }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(br != null) { br.close(); }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }
         
	}
	
	/*public interface Kernel32 extends Library {
		public static Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
	 
		public long GetProcessId(Long hProcess);
	}
	
	public interface user32 extends Library {
		public static user32 INSTANCE = (user32) Native.loadLibrary("user32", user32.class);
	 
		int GetWindowText(HWND hWnd,char[] charr,int length);
	}*/
	
	// 获取pid
		public long getProcessPID(Process child) {
			long pid = -1;
			Field field = null;
			try {
				field = child.getClass().getDeclaredField("handle");
				field.setAccessible(true);
				WinNT.HANDLE handle = new WinNT.HANDLE();
				handle.setPointer(new Pointer((long)field.get(child)));
				
			    System.out.println("handle=" + (long)field.get(child));
			     
			    
			    
				pid = Kernel32.INSTANCE.GetProcessId(handle);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return pid;
		}
		
		public void getWindowText(Process child){
			Field field = null;
			try {
				field = child.getClass().getDeclaredField("handle");
				field.setAccessible(true);
				char[] buffer = new char[1024];
				//user32.INSTANCE.GetWindowText(field.get(child), buffer, 1024);
				
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		
	
}
