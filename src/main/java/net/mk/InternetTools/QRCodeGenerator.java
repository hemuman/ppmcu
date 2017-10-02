///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.mk.InternetTools;
//
///**
// *
// * @author Manoj
// */
//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.EnumMap;
//import java.util.Map;
// 
//import javax.imageio.ImageIO;
// 
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.BinaryBitmap;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatReader;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.NotFoundException;
//import com.google.zxing.Result;
//import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.common.HybridBinarizer;
//import com.google.zxing.qrcode.QRCodeWriter;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
// 
///**
// * @author Crunchify.com
// * Updated: 03/20/2016 - added code to narrow border size 
// */
// 
//public class QRCodeGenerator {
// 
//	// Tutorial: http://zxing.github.io/zxing/apidocs/index.html
// 
//	public static void main(String[] args) {
//		String myCodeText = "PPMCU App is WIP";
//		String filePath = "c:/CrunchifyQR.png";
//		int size = 250;
//		String fileType = "png";
//		File myFile = new File(filePath);
//		try {
//			
//			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
//			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//			
//			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
//			hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
//			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
// 
//			QRCodeWriter qrCodeWriter = new QRCodeWriter();
//			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
//					size, hintMap);
//			int CrunchifyWidth = byteMatrix.getWidth();
//			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
//					BufferedImage.TYPE_INT_RGB);
//			image.createGraphics();
// 
//			Graphics2D graphics = (Graphics2D) image.getGraphics();
//			graphics.setColor(Color.WHITE);
//			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
//			graphics.setColor(Color.BLACK);
// 
//			for (int i = 0; i < CrunchifyWidth; i++) {
//				for (int j = 0; j < CrunchifyWidth; j++) {
//					if (byteMatrix.get(i, j)) {
//						graphics.fillRect(i, j, 1, 1);
//					}
//				}
//			}
//			ImageIO.write(image, fileType, myFile);
//		} catch (WriterException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("\n\nYou have successfully created QR Code.");
//	}
//        
//        public static void createQRCode(String qrCodeData, String filePath,
//			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
//			throws WriterException, IOException {
//		BitMatrix matrix = new MultiFormatWriter().encode(
//				new String(qrCodeData.getBytes(charset), charset),
//				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
//		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
//				.lastIndexOf('.') + 1), new File(filePath));
//	}
//
//	public static String readQRCode(String filePath, String charset, Map hintMap)
//			throws FileNotFoundException, IOException, NotFoundException {
//		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
//				new BufferedImageLuminanceSource(
//						ImageIO.read(new FileInputStream(filePath)))));
//		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
//				hintMap);
//		return qrCodeResult.getText();
//	}
//}
