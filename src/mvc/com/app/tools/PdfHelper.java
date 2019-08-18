/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description pdf生成工具类
 * */
package com.app.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.code.model.OneContest;
import com.code.model.OnePaper;
import com.code.model.OneProblem;
import com.code.model.OneSimproblem;
import com.code.model.Options;
import com.code.model.Simproblem;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.sf.json.JSONObject;

public class PdfHelper {


	private  Font headfont ;// ���������С 
    private  Font keyfont;// ���������С 
    private  Font textfont;// ���������С 
    private int maxWidth = 520; 
    private  String fontPath = "/com/app/text/msyh.ttf";
    Document document = new Document();   
    
/*	public String Generate(userStatisticsModel model) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException
	{
		        BaseFont bfChinese; 
		        try { 

		            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); 

		        	
		            bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED); 
		            headfont = new Font(bfChinese, 10, Font.BOLD);// ���������С 
		            keyfont = new Font(bfChinese, 8, Font.BOLD);// ���������С  
		            textfont = new Font(bfChinese, 8, Font.NORMAL);// ���������С
		            document.setPageSize(PageSize.A4);// ����ҳ���С
		            
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            String dateString = sdf.format(new Date());
		            RandomString randomString = new RandomString();
		            String ran = randomString.getRandomString(8);
		            String path = "D:\\�����\\" + dateString + "\\" + model.getUserName() + "_" + ran + ".pdf";
		            File file = new File(path);
		            if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
		            file.createNewFile(); 
		            PdfWriter.getInstance(document,new FileOutputStream(file)); 
		            document.open(); 
		            HeaderFooter footer = new HeaderFooter(new Phrase("ҳ�룺",keyfont), true);
		            footer.setBorder(Rectangle.NO_BORDER); 
		            document.setHeader(footer);
		            generatePDF(model);     
		            return path;
		        } catch (Exception e) {          
		            e.printStackTrace(); 
		        }
		        return null; 
		     
	}*/

	/*public String GenerateForAdmin(List<userStatisticsModel> models,String basePath) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException
	{
		        BaseFont bfChinese; 
		        try { 

		            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); 

		        	
		            bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED); 
		            headfont = new Font(bfChinese, 10, Font.BOLD);// ���������С 
		            keyfont = new Font(bfChinese, 8, Font.BOLD);// ���������С  
		            textfont = new Font(bfChinese, 8, Font.NORMAL);// ���������С
		            document.setPageSize(PageSize.A4);// ����ҳ���С
		            
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            String dateString = sdf.format(new Date());
		            RandomString randomString = new RandomString();
		            String ran = randomString.getRandomString(8);
		            String path = "D:\\�����\\" + dateString + "\\����.pdf";
		            File file = new File(path);
		            if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
		            file.createNewFile();  
		            PdfWriter.getInstance(document,new FileOutputStream(file)); 
		            document.open(); 
		            HeaderFooter footer = new HeaderFooter(new Phrase("ҳ�룺",keyfont), true);
		            footer.setBorder(Rectangle.NO_BORDER); 
		            document.setHeader(footer);
		            generatePDFForAdmin(models,basePath);    
		            document.close();
		            return path;
		        } catch (Exception e) {          
		            e.printStackTrace(); 
		        }
		        return null; 
		     
	}*/


	public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align){ 
	    PdfPCell cell = new PdfPCell(); 
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);         
	    cell.setHorizontalAlignment(align);     
	    cell.setPhrase(new Phrase(value,font)); 
	   return cell; 
	} 
	  
	public PdfPCell createCell(String value,com.itextpdf.text.Font font){ 
	    PdfPCell cell = new PdfPCell(); 
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);  
	    cell.setPhrase(new Phrase(value,font)); 
	   return cell; 
	} 

	public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align,int colspan){ 
	    PdfPCell cell = new PdfPCell(); 
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
	    cell.setHorizontalAlignment(align);     
	    cell.setColspan(colspan); 
	    cell.setPhrase(new Phrase(value,font)); 
	   return cell; 
	} 
	public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align,int colspan,boolean boderFlag){ 
	    PdfPCell cell = new PdfPCell(); 
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
	    cell.setHorizontalAlignment(align);     
	    cell.setColspan(colspan); 
	    cell.setPhrase(new Phrase(value,font)); 
	    cell.setPadding(3.0f); 
	    if(!boderFlag){ 
	        cell.setBorder(0); 
	        cell.setPaddingTop(15.0f); 
	        cell.setPaddingBottom(8.0f); 
	    } 
	   return cell; 
	} 
	public PdfPTable createTable(int colNumber){ 
	   PdfPTable table = new PdfPTable(colNumber); 
	   try{ 
	       table.setTotalWidth(maxWidth); 
	       table.setLockedWidth(true); 
	       table.setHorizontalAlignment(Element.ALIGN_CENTER);      
	       table.getDefaultCell().setBorder(1); 
	   }catch(Exception e){ 
	       e.printStackTrace(); 
	   } 
	   return table; 
	} 
	public PdfPTable createTable(float[] widths){ 
	       PdfPTable table = new PdfPTable(widths); 
	       try{ 
	           table.setTotalWidth(maxWidth); 
	           table.setLockedWidth(true); 
	           table.setHorizontalAlignment(Element.ALIGN_CENTER);      
	           table.getDefaultCell().setBorder(1); 
	       }catch(Exception e){ 
	           e.printStackTrace(); 
	       } 
	       return table; 
	   } 
	  
	public PdfPTable createBlankTable(){ 
	    PdfPTable table = new PdfPTable(1); 
	    table.getDefaultCell().setBorder(0); 
	    table.addCell(createCell("", keyfont));          
	    table.setSpacingAfter(20.0f); 
	    table.setSpacingBefore(20.0f); 
	    return table; 
	} 
	 
	public String GeneratePDFForContestStatus(OneContest contest,String path,int score) throws Exception{ 

		PdfPTable table = createTable(3);
		OnePaper paper = contest.getPaper();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		headfont = new Font(bfChinese, 14, Font.BOLD);
        keyfont = new Font(bfChinese, 10, Font.BOLD); 
        textfont = new Font(bfChinese, 8, Font.NORMAL);
        
		table.addCell(createCell(paper.getContestpaper().getTitle(),headfont,Element.ALIGN_CENTER,3,false));
		table.addCell(createCell(String.format("姓名: %s", contest.getStudent().getRealname()),headfont,Element.ALIGN_CENTER,3,false));
		table.addCell(createCell(String.format("成绩: %d", score),headfont,Element.ALIGN_CENTER,3,false));
		table.addCell(createCell("批改日期：" + sdf.format(new Date()),headfont,Element.ALIGN_CENTER,3,false));
		
		
		for(int k=1;k<=paper.getProb().size() + paper.getSimp().size();k++)
		{
			
			for(OneSimproblem oneSimproblem : paper.getSimp())  //选择题{
			{
				if(oneSimproblem.getSimproblem().getPos().intValue() != k) continue;
				
					String content = k + "." + oneSimproblem.getSimproblem().getContent();
					table.addCell(createCell(content,keyfont,Element.ALIGN_CENTER,3,false));
				    
				    if(oneSimproblem.getSimproblem().getType().intValue()<4)  //选择题
					for(int j=1;j<=oneSimproblem.getOption().size();j++)
					for(int i=0;i<=oneSimproblem.getOption().size();i++)
					{
						if(oneSimproblem.getOption().get(i).getPos().intValue() == j)
						table.addCell(createCell(j + "." + oneSimproblem.getOption().get(i).getContent(),keyfont,Element.ALIGN_CENTER,3,false));
						
					}
					
				    table.addCell(createCell("题目分值：" + oneSimproblem.getSimproblem().getScore().doubleValue(),keyfont,Element.ALIGN_CENTER,3,false));
				    table.addCell(createCell("得分：" + oneSimproblem.getSimsolution().getScore().doubleValue() ,keyfont,Element.ALIGN_CENTER,3,false));

				
			}
			
			for(OneProblem oneProblem : paper.getProb()){
				if(oneProblem.getProblem().getPos().intValue() != k) continue;
				
				table.addCell(createCell(k + "." + oneProblem.getProblem().getTitle(),keyfont,Element.ALIGN_CENTER,3,false));
				table.addCell(createCell(oneProblem.getProblem().getDescription(),keyfont,Element.ALIGN_CENTER,3,false));
				table.addCell(createCell("题目分值：" + oneProblem.getProblem().getScore().doubleValue(),keyfont,Element.ALIGN_CENTER,3,false));
				table.addCell(createCell("得分：" + String.format("%.1f", oneProblem.getProblem().getScore().doubleValue() * oneProblem.getSolution().getPassRate().doubleValue()),keyfont,Element.ALIGN_CENTER,3,false));
			}
			
			
		}
		
			
/*		for(OneSimproblem oneSimproblem : paper.getSimp())  //选择题
		{
			if(oneSimproblem.getSimproblem().getType().intValue() != 1)
				continue;
			String problemContent = String.valueOf(num++) + "." + oneSimproblem.getSimproblem().getContent();
			table.addCell(createCell(problemContent,headfont,Element.ALIGN_CENTER,3,false));
			List<Options> options = oneSimproblem.getOption();
			Map<Integer,Options> map = new HashMap<Integer,Options>();
			String answer = oneSimproblem.getSimsolution().getAnswer();
			char c = 'A';
			for(Options option : options)
			{ 
				map.put(new Integer(c++), option);
			}
			c = 'A';
			for(int i=0;i<4;i++)
			{
				Options option = map.get(new Integer(c+i));
				table.addCell(createCell(c + "." + option.getContent(),headfont,Element.ALIGN_CENTER,3,false));
			}
			char ansC = ' ';
			for(Integer key : map.keySet())
			{ 
				if(map.get(key).equals(answer))
				{
					ansC = (char)key.intValue();
					break;
				}
			}
			table.addCell(createCell("作答：" + ansC,headfont,Element.ALIGN_CENTER,3,false));
			table.addCell(createCell("得分：" + String.format("%.1f", oneSimproblem.getSimsolution().getScore().doubleValue()),headfont,Element.ALIGN_CENTER,3,false));
		}
		
		for(OneSimproblem oneSimproblem : paper.getSimp()) //填空题
		{
			if(oneSimproblem.getSimproblem().getType().intValue() != 2)
				continue;
			String problemContent = String.valueOf(num++) + "." + oneSimproblem.getSimproblem().getContent();
			table.addCell(createCell(problemContent,headfont,Element.ALIGN_CENTER,3,false));
			String answer = oneSimproblem.getSimsolution().getAnswer();
			table.addCell(createCell("作答：" + answer,headfont,Element.ALIGN_CENTER,3,false));
			table.addCell(createCell("得分：" + String.format("%.1f", oneSimproblem.getSimsolution().getScore().doubleValue()),headfont,Element.ALIGN_CENTER,3,false));
		}
		
		
		
		
		for(OneProblem oneProblem : paper.getProb()) //编程题
		{
			String problemContent = String.valueOf(num++) + "." + oneProblem.getProblem().getTitle();
			table.addCell(createCell(problemContent,headfont,Element.ALIGN_CENTER,3,false));
			String answer = String.format("得分: %。1f", oneProblem.getSolution().getPassRate().doubleValue() * oneProblem.getProblem().getScore().doubleValue());
			table.addCell(createCell(answer,headfont,Element.ALIGN_CENTER,3,false));
		}*/
	
		 File file = new File(path);
         if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
         file.createNewFile(); 
         PdfWriter.getInstance(document,new FileOutputStream(file)); 
         document.open();
		document.add(table);
		document.close();
		
		return path;
		
	}

	    
	/*public void generatePDF(userStatisticsModel model) throws Exception{ 
		System.out.println("generate: " + JSONObject.fromObject(model).toString());
		
	   PdfPTable table = createTable(7); 
	   table.addCell(createCell("���ν������룺" + model.getIncomeNow() + " Ԫ", headfont,Element.ALIGN_LEFT,3,false)); 
	   table.addCell(createCell("���ν�����" + model.getNotCleanMoney()+ " Ԫ", headfont,Element.ALIGN_LEFT,4,false)); 
	   table.addCell(createCell("���ν��������ѣ�  "+ String.valueOf(model.getIncomeNow() - model.getNotCleanMoney())+ " Ԫ", headfont,Element.ALIGN_LEFT,3,false)); 
	   table.addCell(createCell("�����룺  "+ model.getIncome()+ " Ԫ", headfont,Element.ALIGN_LEFT,4,false)); 
	   table.addCell(createCell("��ˮ�˵���ϸ�� ", headfont,Element.ALIGN_LEFT,7,false)); 
	   
	   table.addCell(createCell("��ˮ��", keyfont, Element.ALIGN_CENTER)); 
	   table.addCell(createCell("�����û�", keyfont, Element.ALIGN_CENTER)); 
	   table.addCell(createCell("��Ʒ����", keyfont, Element.ALIGN_CENTER)); 
	   table.addCell(createCell("֧�����", keyfont, Element.ALIGN_CENTER)); 
	   table.addCell(createCell("����֧��ʱ��", keyfont, Element.ALIGN_CENTER));
	   table.addCell(createCell("����������", keyfont, Element.ALIGN_CENTER));
	   table.addCell(createCell("������", keyfont, Element.ALIGN_CENTER));
	   
	   List<orderInfoModel> list = model.getDetail(); 
	   for(int i=0;i<list.size();i++){ 
		   orderInfoModel order = list.get(i);
	       table.addCell(createCell(order.getId(), textfont)); 
	       table.addCell(createCell(order.getUserID(), textfont)); 
	       table.addCell(createCell(order.getGoodName(), textfont)); 
	       table.addCell(createCell(String.valueOf(order.getPrice()), textfont)); 
	       table.addCell(createCell(order.getApplyTime(), textfont)); 
	       table.addCell(createCell(order.getServerName(), textfont)); 
	       table.addCell(createCell(String.valueOf(order.getCleanPrice()), textfont)); 
	   } 
	   document.add(table); 
	      
	   document.close(); 
	}*/
}
