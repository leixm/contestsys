/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 高清二维码生成工具类
 * */
package com.app.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
 
import javax.imageio.ImageIO;
 

 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.app.tools.RandomString;
 

public class QrCodeUtil {
 
	private String fileContextPath;
 
	/**
	 * ���ɶ�ά��
	 * <p>
	 * TODO(�������������������C ��ѡ)
	 *
	 * @param content ��ά�������
	 * @param filePath��ʱ�ļ���·��
	 */
	public void encodeQrCode(String content, String filePath) {
		try {
			//��Ҫ����һ����ʱ�ļ���Ϊ�˷�ֹ���ֲ������⣬�ְѶ�ά���ļ�����16Ϊ��UUID������
			RandomString ran = new RandomString();
			String fileName = ran.getRandomString(16) + ".png";
			int width = 300; //��ά��ͼ����  
			int height = 300; // ��ά��ͼ��߶�  
			String format = "png";// ͼ������  
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// ���ɾ���  
			Path path = FileSystems.getDefault().getPath(filePath, fileName);
			//�������ɶ�ά��ķ���û�з���ֵ���ֽ���ά����ʱ·�����б���
			this.setFileContextPath(path.toString());
			MatrixToImageWriter.writeToPath(bitMatrix, format, path);// ���ͼ��  
			//System.out.println("����ɹ�.");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/** 
	 * ������ά�� 
	 */
	public void decodeQrCode(String filePath) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// ��ͼ����н���  
			System.out.println("ͼƬ�����ݣ�  ");
			System.out.println("author�� " + result.getText());
			System.out.println("ͼƬ�и�ʽ��  ");
			System.out.println("encode�� " + result.getBarcodeFormat());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getFileContextPath() {
		return fileContextPath;
	}
	public void setFileContextPath(String fileContextPath) {
		this.fileContextPath = fileContextPath;
	}
}
