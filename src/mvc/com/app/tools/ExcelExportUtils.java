package com.app.tools;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 * 
 * @version v1.0
 * @param <T>
 * 			T传bean
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExcelExportUtils<T> {
	//获取文件分割符
	public static final String FILE_SEPARATOR = System.getProperties()
			.getProperty("file.separator");
	
	/**
	 * @param paramList 字段名，用来从dataset拿取值
	 * @param tableTitle 表格标题头
	 * @param dataset 表格内容
	 */
	public HSSFWorkbook exportExcel(String tableTitle,List<String> paramList, List<Map<String,Object>>  dataset) {
		return exportExcel(tableTitle, null, paramList, dataset, "yyyy-MM-dd");
	}
	/**
	 * @param tableTitle 表格标题头
	 * @param headers 列头，每一列数据的列头
	 * @param paramList 字段名，用来从dataset拿取值
	 * @param dataset 表格内容
	 */
	public HSSFWorkbook exportExcel(String tableTitle,String[] headers,List<String> paramList, List<Map<String,Object>> dataset)
			 {
		return exportExcel(tableTitle, headers, paramList, dataset, "yyyy-MM-dd");
	}
	/**
	 * @param paramList 字段名，用来从dataset拿取值
	 * @param headers 列头，每一列数据的列头
	 * @param dataset 表格内容
	 * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"，可以自定义格式
	 */
	public HSSFWorkbook exportExcel(String[] headers,List<String> paramList, List<Map<String,Object>> dataset,
			String pattern) {
		return exportExcel("未命名表格", headers, paramList, dataset,pattern);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * @param paramList 
	 * 			字段名，用来从dataset拿取值
	 * @param title
	 *            表格sheet名字
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd"
	 */
	@SuppressWarnings("unchecked")
	public HSSFWorkbook exportExcel(String tableTitle, String[] headers,
			List<String> paramList, List<Map<String,Object>>  dataset, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(tableTitle);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//标题头样式
		HSSFCellStyle styleHeadTitle = workbook.createCellStyle();
		// 设置这些样式
		styleHeadTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHeadTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeadTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeadTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHeadTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeadTitle.setFillForegroundColor(HSSFColor.WHITE.index);
		//左右 上下居中 
		styleHeadTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHeadTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		HSSFFont fontHeadTitle = workbook.createFont();
		fontHeadTitle.setColor(HSSFColor.VIOLET.index);
		fontHeadTitle.setFontHeightInPoints((short) 12);
		fontHeadTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontHeadTitle.setColor(HSSFColor.BLUE.index);
		// 把字体应用到当前的样式
		styleHeadTitle.setFont(fontHeadTitle);
		
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		//左右 上下居中 
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//框线
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//背景色
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font2.setColor(HSSFColor.BLACK.index);
		
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("数据由学生在线考试系统提供"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");
		//产生表格标题
		HSSFRow rowHeadTitle = sheet.createRow(0);
		rowHeadTitle.setHeight((short) 450);//目的是想把行高设置成30px
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = rowHeadTitle.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(tableTitle);
			cell.setCellValue(text);
		}
		//提供合并的起始列标,索引从0开始
		int start = 0;
		int end = headers.length-1;
		CellRangeAddress region = new CellRangeAddress(0, 0, start, end); //合并单元格(给表头去用)
		sheet.addMergedRegion(region);
		// 产生表头行
		HSSFRow row = sheet.createRow(1);
		row.setHeight((short) 400);//目的是想把行高设置成25px
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		//遍历集合获取数据
		if(!dataset.isEmpty()) {
			for(int i=0; i<dataset.size(); i++) {
				row = sheet.createRow(i + 2); //从第三行开始写数据
				row.setHeight((short) 350);//目的是想把行高设置成22px
				Map<String,Object> map = dataset.get(i);
				//遍历字段集合，用字段来寻找map中的value
				int j = 0;
				for(String str : paramList) {
					HSSFCell dataCell = row.createCell(j);	//给每个单元格设置样式   
					dataCell.setCellStyle(style2);
					dataCell.setCellValue(map.get(str).toString());
					j++;
				}
			}
			
		}
		
		
		return workbook;
	}
}
