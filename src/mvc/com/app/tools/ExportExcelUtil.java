package com.app.tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.poi.hssf.util.HSSFColor.BLACK;
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
public class ExportExcelUtil<T> {
	//获取文件分割符
	public static final String FILE_SEPARATOR = System.getProperties()
			.getProperty("file.separator");
	
	/**
	 * @param tableTitle 表格标题头
	 * @param dataset 表格内容
	 * @param out 指定服务器路径的输出流 如：OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + fileName);
	 */
	public void exportExcel(String tableTitle, Collection<T> dataset, OutputStream out) {
		exportExcel(tableTitle, null, dataset, out, "yyyy-MM-dd");
	}
	/**
	 * @param tableTitle 表格标题头
	 * @param headers 列头，每一列数据的列头
	 * @param dataset 表格内容
	 * @param out 指定服务器路径的输出流 
	 */
	public void exportExcel(String tableTitle,String[] headers, Collection<T> dataset,
			OutputStream out) {
		exportExcel(tableTitle, headers, dataset, out, "yyyy-MM-dd");
	}
	/**
	 * @param headers 列头，每一列数据的列头
	 * @param dataset 表格内容
	 * @param out 指定服务器路径的输出流 
	 * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"，可以自定义格式
	 */
	public void exportExcel(String[] headers, Collection<T> dataset,
			OutputStream out, String pattern) {
		exportExcel("未命名表格", headers, dataset, out, pattern);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd"
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(String tableTitle, String[] headers,
			Collection<T> dataset, OutputStream out, String pattern) {
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
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		//左右 上下居中 
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLUE.index);
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
		row.setHeight((short) 350);//目的是想把行高设置成25px
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 1;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			row.setHeight((short) 320);//目的是想把行高设置成22px
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					// if (value instanceof Integer) {
					// int intValue = (Integer) value;
					// cell.setCellValue(intValue);
					// } else if (value instanceof Float) {
					// float fValue = (Float) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(fValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Double) {
					// double dValue = (Double) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(dValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Long) {
					// long longValue = (Long) value;
					// cell.setCellValue(longValue);
					// }
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue) {
							textValue = "女";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							HSSFFont font3 = workbook.createFont();
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
