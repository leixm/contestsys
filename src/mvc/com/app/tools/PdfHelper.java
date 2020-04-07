/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description pdf生成工具类
 * */
package com.app.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.MessagingException;

import com.code.model.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.sf.json.JSONObject;

public class PdfHelper {

	public static String pdf_dir  = "F:/pdf/";

	private  Font headfont ;
    private  Font keyfont;
    private  Font textfont;
	private Font redkeyfont;
	private Font optionfont;
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
	    cell.setRowspan(5);
	    cell.setPhrase(new Phrase(value,font)); 
	   return cell; 
	}

	public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align,int colspan,boolean boderFlag){
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setRowspan(5);
		cell.setPhrase(new Phrase(value,font));
		cell.setPadding(3.0f);
		if(!boderFlag){
			cell.setBorder(0);
			cell.setPaddingTop(15.0f);
			cell.setPaddingBottom(8.0f);
		}
		return cell;
	}

	public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align,int colspan,boolean boderFlag,float paddingTop, float paddingBottom){
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setRowspan(5);
		cell.setPhrase(new Phrase(value,font));
		cell.setPadding(3.0f);
		if(!boderFlag){
			cell.setBorder(0);
			cell.setPaddingTop(paddingTop);
			cell.setPaddingBottom(paddingBottom);
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
	 
	public String GeneratePDFForContestStatus(OneContest contest, int contestStatusId, double score) throws Exception{

		PdfPTable table = createTable(3);
		OnePaper paper = contest.getPaper();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BaseFont bfChinese = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		headfont = new Font(bfChinese, 14, Font.BOLD);
        keyfont = new Font(bfChinese, 10, Font.BOLD);
		optionfont = new Font(bfChinese, 9, Font.BOLD);
        textfont = new Font(bfChinese, 10, Font.NORMAL);
		redkeyfont = new Font(bfChinese, 10, Font.NORMAL, new BaseColor(255, 0, 0));

		table.addCell(createCell(paper.getContestpaper().getTitle(),headfont,Element.ALIGN_CENTER,3,false));

		table.addCell(createCell("批改日期：" + sdf.format(new Date())+ "                                        "
				+String.format("姓名: %s", contest.getStudent().getRealname())+ "                                        "
				+String.format("成绩: %.1f", score),
				keyfont,Element.ALIGN_CENTER,3,false,30,10));

		/*table.addCell(createCell(String.format("成绩: %.1f", score),headfont,Element.ALIGN_CENTER,3,false));
		table.addCell(createCell("批改日期：" + sdf.format(new Date()),headfont,Element.ALIGN_CENTER,3,false));*/
		
		int problemSize = paper.getProb() == null ? 0 : paper.getProb().size();
		int simproblemSize = paper.getSimp() == null ?0 : paper.getSimp().size();
		for(int k=1;k<=problemSize + simproblemSize;k++)
		{

			String simsolutionContent = "学生未作答";
			String answerContent = "未找到答案/无标准答案";

			if(paper.getSimp()!=null)
			for(OneSimproblem oneSimproblem : paper.getSimp())  //选择题{
			{
				if(oneSimproblem.getSimproblem().getPos().intValue() != k) continue;

					String content = String.valueOf(k) + "、 " + oneSimproblem.getSimproblem().getContent().replaceAll("<.*?>", "");
					table.addCell(createCell(content,keyfont,Element.ALIGN_LEFT,3,false,30,6));

					Map<Integer,String> optionsMap = new TreeMap<Integer, String>();

				    if(oneSimproblem.getSimproblem().getType().intValue()<=2)  //单选 多选题
					{
						for(Options options : oneSimproblem.getOption())
							optionsMap.put(options.getPos(), options.getContent());

						for(Map.Entry<Integer,String> entry : optionsMap.entrySet()){
							table.addCell(createCell( ContestHelper.indexToCharIndex(entry.getKey()) + "." + entry.getValue(),optionfont,Element.ALIGN_LEFT,3,false,8,8));
						}

						Map<String,Integer> optionsMapReserved = new HashMap<String,Integer>();
						for(Map.Entry<Integer,String> entry : optionsMap.entrySet()){
							optionsMapReserved.put(entry.getValue(),entry.getKey());
						}

						StringBuilder answerSB = new StringBuilder();
						for(Answer answer : oneSimproblem.getAnswer()){
							try {
								answerSB.append(ContestHelper.indexToCharIndex((int)optionsMapReserved.get(answer.getContent())));
								answerSB.append("、");
							}
							catch (NullPointerException e){
								e.printStackTrace();
							}
						}
						answerContent = answerSB.substring(0,answerSB.length()-1);

						String answer = oneSimproblem.getSimsolution().getAnswer();
						//System.out.println("answer---------"+answer);
						if(answer!=null && !"".equals(answer)) {
							StringBuilder simsolutionSB = new StringBuilder();
							String[] simsolutionArr = answer.split("§§§");
							for(String arr : simsolutionArr){
								try{
									//System.out.println("arr---------"+arr);
									simsolutionSB.append(ContestHelper.indexToCharIndex((int)optionsMapReserved.get(arr)));
									simsolutionSB.append("、");
								}
								catch (NullPointerException e){
									e.printStackTrace();
								}
							}
							simsolutionContent = simsolutionSB.substring(0,simsolutionSB.length()-1);
						}

					} else if(oneSimproblem.getSimproblem().getType().intValue() == 5 ) {  // 简答题
							StringBuilder answerContentSB = new StringBuilder();
							simsolutionContent = oneSimproblem.getSimsolution().getAnswer();

					} else{ //其他题目的正确答案和作答内容生成
				    	StringBuilder answerContentSB = new StringBuilder();
				    	Map<Integer,String> answerMap = new TreeMap<>();
				    	for(Answer answer : oneSimproblem.getAnswer()){
				    		answerMap.put(answer.getPos(),answer.getContent());
						}

				    	for(Map.Entry<Integer,String> entry: answerMap.entrySet()){
							answerContentSB.append(entry.getValue());
							answerContentSB.append("、");
						}
				    	answerContent = answerContentSB.substring(0,answerContentSB.length()-1);

				    	// 作答内容
						String answer = oneSimproblem.getSimsolution().getAnswer();
						//System.out.println("answer---------"+answer);
						//分割作答内容，美观
						if(answer!=null && !"".equals(answer)) {
							StringBuilder simsolutionSB = new StringBuilder();
							String[] simsolutionArr = answer.split("§§§");
							if(simsolutionArr.length > 0) {
								for(String arr : simsolutionArr){
									try{
										//System.out.println("arr---------"+arr);
										simsolutionSB.append(arr);
										simsolutionSB.append(" 、");
									}
									catch (NullPointerException e){
										e.printStackTrace();
									}
								}
								simsolutionContent = simsolutionSB.substring(0,simsolutionSB.length()-1);
							}
						}
					}

					table.addCell(createCell("正确答案: " + answerContent,textfont,Element.ALIGN_LEFT,3,false,25,6));
					table.addCell(createCell("作答: " + simsolutionContent,redkeyfont,Element.ALIGN_LEFT,3,false,6,6));
				    table.addCell(createCell(String.format("题目分值：%.1f" , oneSimproblem.getSimproblem().getScore().setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue()),textfont,Element.ALIGN_RIGHT,3,false,6,6));
				    table.addCell(createCell(String.format("得分：%.1f" , oneSimproblem.getSimsolution().getScore().setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue()) ,redkeyfont,Element.ALIGN_RIGHT,3,false,6,6));
			}

			if (paper.getProb()!=null)
			for(OneProblem oneProblem : paper.getProb()){
				if(oneProblem.getProblem().getPos().intValue() != k) continue;

				table.addCell(createCell(String.valueOf(k) + "、 " + oneProblem.getProblem().getTitle().replaceAll("<.*?>", ""),keyfont,Element.ALIGN_CENTER,3,false,30,6));
				table.addCell(createCell(oneProblem.getProblem().getDescription().replaceAll("<.*?>", ""),keyfont,Element.ALIGN_CENTER,3,false,6,6));
				table.addCell(createCell(String.format("题目分值：%.1f" ,oneProblem.getProblem().getScore().doubleValue()),keyfont,Element.ALIGN_CENTER,3,false,6,6));

				double solutionScore = oneProblem.getProblem().getScore().doubleValue() * oneProblem.getSolution().getPassRate().doubleValue();
				table.addCell(createCell("得分：" + String.format("%.1f",(new BigDecimal(solutionScore).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue()) ),keyfont,Element.ALIGN_CENTER,3,false,6,6));
			}
			
			
		}

		 //下载到服务器特定文件夹里
		 String classPath = this.getClass().getResource("/").getPath();
		 String webappRoot = classPath.replaceAll("WEB-INF/classes/", ""); 	//根目录
		 webappRoot  = webappRoot.replace("%20"," ");	//替换空格

		 String filePath = webappRoot + "solutionPdfDownload" + File.separator;		//模板文件绝对路径

		 String path = filePath + String.valueOf(contestStatusId) + ".pdf";
		 File file = new File(path);
         if(!file.getParentFile().exists()) {
         	file.getParentFile().mkdirs();
		 }
         file.createNewFile(); 
         PdfWriter.getInstance(document,new FileOutputStream(file)); 
         document.open();
		 document.add(table);
		 document.close();
		
		return path;
		
	}


}
