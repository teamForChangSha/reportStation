package com.jxxp.comms.util;


import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jxxp.pojo.Company;
import com.jxxp.pojo.ReportCase;

public class PDFUtil {
	private static final Logger log = LoggerFactory.getLogger(PDFUtil.class);
	
	public void toPDF() throws Exception {
		Document document = new Document(PageSize.A4, 80, 79, 20, 45); // A4纸大小 左、右、上、下                /* 使用中文字体 */                
//	    BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); // 中文处理   
		BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);	
		
		Font FontChinese = new Font(bfChinese, 14, Font.NORMAL); // 其他所有文字字体        
		Font BoldChinese = new Font(bfChinese, 14, Font.BOLD); // 粗体        
		Font titleChinese = new Font(bfChinese, 20, Font.BOLD); // 模板抬头的字体      
		Font moneyFontChinese = new Font(bfChinese, 8, Font.NORMAL);       
		Font subBoldFontChinese = new Font(bfChinese, 8, Font.BOLD); 
		PdfWriter.getInstance(document,new FileOutputStream("D:/ITEXT5.pdf"));
		document.open(); //打开文档   
		//------------开始写数据-------------------   
		Paragraph title = new Paragraph("案件说明书", titleChinese);// 抬头   
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置   
		title.setLeading(1f);//设置行间距//设置上面空白宽度   
		document.add(title);  
		  
		title = new Paragraph("致：平安银行股份有限公司", BoldChinese);// 抬头   
		title.setSpacingBefore(25f);//设置上面空白宽度   
		document.add(title);  
		
		title = new Paragraph("         现收到一封贵公司关于（内幕交易/违法证券交易,偷窃）的举报，举报内容如下：", FontChinese);  
		title.setLeading(22f);//设置行间距   
		document.add(title);  
		
		float[] widths = { 10f,55f,30f };// 设置表格的列宽和列数  默认是4列    
		  
		PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格   
		table.setSpacingBefore(20f);// 设置表格上面空白宽度   
		table.setTotalWidth(500);// 设置表格的宽度   
		table.setWidthPercentage(100);//设置表格宽度为%100   
		// table.getDefaultCell().setBorder(0);//设置表格默认为无边框   
		          
		int rowCount=1; //行计数器   
		PdfPCell cell = null;  
		//---表头   
		cell = new PdfPCell(new Paragraph("序号", subBoldFontChinese));//描述   
		cell.setFixedHeight(20);  
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
		table.addCell(cell);  
		cell = new PdfPCell(new Paragraph("问题", subBoldFontChinese));//描述   
		cell.setFixedHeight(20);  
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
		table.addCell(cell);  
		cell = new PdfPCell(new Paragraph("答案", subBoldFontChinese));//描述   
		cell.setFixedHeight(20);  
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
		table.addCell(cell);  
		  

		String[] tempValue = new String[]{"1","请详细阐述该事件或违规行为的完整细节","测试"};  //租金期次列表   
		for (int j = 1 ; j< tempValue.length; j++){  
			if(j==1){      //第一列 日期   
				cell = new PdfPCell(new Paragraph(rowCount+"", moneyFontChinese));//描述   
				cell.setFixedHeight(20);  
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
				table.addCell(cell);  
				rowCount++;  
			}	  
			cell = new PdfPCell(new Paragraph(tempValue[j], moneyFontChinese));//描述   
			cell.setFixedHeight(20);  
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
			table.addCell(cell);  
		}  
		document.add(table);  
		
		title = new Paragraph("案件编号为：31112，查看详情请登录：http://120.24.97.214:8080/reportStation/", FontChinese);  
		title.setLeading(22f);//设置行间距   
		document.add(title);  
		
		//-------此处增加图片和日期，因为图片会遇到跨页的问题，图片跨页，图片下方的日期就会脱离图片下方会放到上一页。   
		//所以必须用表格加以固定的技巧来实现   
		float[] widthes = { 50f };// 设置表格的列宽和列数   
		PdfPTable hiddenTable = new PdfPTable(widthes);// 建立一个pdf表格   
		hiddenTable.setSpacingBefore(11f);  //设置表格上空间   
		hiddenTable.setTotalWidth(500);// 设置表格的宽度   
		hiddenTable.setWidthPercentage(100);//设置表格宽度为%100   
		hiddenTable.getDefaultCell().disableBorderSide(1);  
		hiddenTable.getDefaultCell().disableBorderSide(2);  
		hiddenTable.getDefaultCell().disableBorderSide(4);  
		hiddenTable.getDefaultCell().disableBorderSide(8);  
//		  
//	    Image upgif = Image.getInstance("D:/opt/yd_apps/rim/uploadfolder/stamp1.jpg");   
//	    upgif.scalePercent(7.5f);//设置缩放的百分比%7.5   
//	    upgif.setAlignment(Element.ALIGN_RIGHT);  
		  
		cell = new PdfPCell(new Paragraph("", FontChinese));//描述   
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居中显示   
		//	     cell.addElement(upgif);  
		cell.setPaddingTop(0f);             //设置内容靠上位置   
		cell.setPaddingBottom(0f);  
		cell.setPaddingRight(20f);  
		cell.setBorder(Rectangle.NO_BORDER);//设置单元格无边框   
		hiddenTable.addCell(cell);  
		   
		cell = new PdfPCell(new Paragraph("2016 年03月31日                    ", FontChinese));//金额   
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居中显示   
		cell.setPaddingTop(0f);  
		cell.setPaddingRight(20f);  
		cell.setBorder(Rectangle.NO_BORDER);  
		hiddenTable.addCell(cell);  
		document.add(hiddenTable);  
		System.out.println("拼装起租通知书结束...");  
		document.close();
	}
	
	public void createReportPDF(ReportCase reportCase,List<Map<String, String>> questAnswerList,String filePath) throws Exception {
		Document document = new Document(PageSize.A4, 80, 79, 20, 45); // A4纸大小 左、右、上、下                /* 使用中文字体 */                
		BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);	
		
		Font FontChinese = new Font(bfChinese, 14, Font.NORMAL); // 其他所有文字字体        
		Font BoldChinese = new Font(bfChinese, 14, Font.BOLD); // 粗体        
		Font titleChinese = new Font(bfChinese, 20, Font.BOLD); // 模板抬头的字体      
		Font moneyFontChinese = new Font(bfChinese, 8, Font.NORMAL);       
		Font subBoldFontChinese = new Font(bfChinese, 8, Font.BOLD); 
		PdfWriter.getInstance(document,new FileOutputStream(filePath + reportCase.getTrackingNo() + ".pdf"));
		document.open(); //打开文档   
		//------------开始写数据-------------------   
		Paragraph title = new Paragraph("案件说明书", titleChinese);// 抬头   
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置   
		title.setLeading(1f);//设置行间距//设置上面空白宽度   
		document.add(title);  
		
		title = new Paragraph("致：" + reportCase.getCompany().getCompanyName(), BoldChinese);// 抬头   
		title.setSpacingBefore(25f);//设置上面空白宽度   
		document.add(title);  
		
		title = new Paragraph("         现收到一封贵公司关于（" + reportCase.getRtList() + "）的举报，举报内容如下：", FontChinese);  
		title.setLeading(22f);//设置行间距   
		document.add(title);  
		
		float[] widths = { 10f,55f,30f };// 设置表格的列宽和列数  默认是4列    
		  
		PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格   
		table.setSpacingBefore(20f);// 设置表格上面空白宽度   
		table.setTotalWidth(500);// 设置表格的宽度   
		table.setWidthPercentage(100);//设置表格宽度为%100   
		          
		
		
		int rowCount=1; //行计数器   
		PdfPCell cell = null;  
		//---表头   
		cell = new PdfPCell(new Paragraph("序号", subBoldFontChinese));//描述   
		cell.setFixedHeight(20);  
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
		table.addCell(cell);  
		cell = new PdfPCell(new Paragraph("问题", subBoldFontChinese));//描述   
		cell.setFixedHeight(20);  
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
		table.addCell(cell);  
		cell = new PdfPCell(new Paragraph("答案", subBoldFontChinese));//描述   
		cell.setFixedHeight(20);  
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
		table.addCell(cell);  
		    
		for (int i = 0; i < questAnswerList.size(); i++) {
			Map<String, String> map = questAnswerList.get(i);
			cell = new PdfPCell(new Paragraph(rowCount+"", moneyFontChinese));//描述   
			cell.setFixedHeight(20);  
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
			table.addCell(cell);  
			rowCount++;  

			cell = new PdfPCell(new Paragraph(map.get("question"), moneyFontChinese));//描述   
			cell.setFixedHeight(20);  
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
			table.addCell(cell); 
			
			cell = new PdfPCell(new Paragraph(map.get("questValue"), moneyFontChinese));//描述   
			cell.setFixedHeight(20);  
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示   
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中   
			table.addCell(cell); 
		}
		document.add(table);  
		
		title = new Paragraph("案件编号为：" + reportCase.getTrackingNo() + "，查看详情请登录：http://120.24.97.214:8080/reportStation/", FontChinese);  
		title.setLeading(22f);//设置行间距   
		document.add(title);  
		
		//-------此处增加图片和日期，因为图片会遇到跨页的问题，图片跨页，图片下方的日期就会脱离图片下方会放到上一页。   
		//所以必须用表格加以固定的技巧来实现   
		float[] widthes = { 50f };// 设置表格的列宽和列数   
		PdfPTable hiddenTable = new PdfPTable(widthes);// 建立一个pdf表格   
		hiddenTable.setSpacingBefore(11f);  //设置表格上空间   
		hiddenTable.setTotalWidth(500);// 设置表格的宽度   
		hiddenTable.setWidthPercentage(100);//设置表格宽度为%100   
		hiddenTable.getDefaultCell().disableBorderSide(1);  
		hiddenTable.getDefaultCell().disableBorderSide(2);  
		hiddenTable.getDefaultCell().disableBorderSide(4);  
		hiddenTable.getDefaultCell().disableBorderSide(8);  
		  
		cell = new PdfPCell(new Paragraph("", FontChinese));//描述   
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居中显示   
 
		cell.setPaddingTop(0f);             //设置内容靠上位置   
		cell.setPaddingBottom(0f);  
		cell.setPaddingRight(20f);  
		cell.setBorder(Rectangle.NO_BORDER);//设置单元格无边框   
		hiddenTable.addCell(cell);  
		   
		cell = new PdfPCell(new Paragraph(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()), FontChinese));//金额   
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居中显示   
		cell.setPaddingTop(0f);  
		cell.setPaddingRight(20f);  
		cell.setBorder(Rectangle.NO_BORDER);  
		hiddenTable.addCell(cell);  
		document.add(hiddenTable);  
		log.debug("拼装起租通知书结束...");  
		document.close();
	}
	
	public void test() {
		ReportCase reportCase = new ReportCase();
		reportCase.setRtList("受贿、违规");
		reportCase.setTrackingNo("1111111");
		Company company = new Company();
		company.setCompanyName("中联重科");
		reportCase.setCompany(company);

		List<Map<String,String>> questAnswerList = new ArrayList<Map<String,String>>();
		for (int i = 1; i <= 5; i++) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("question", "问题" + i);
			map.put("questValue", "回答" + i);
			questAnswerList.add(map);
		}
		
		try {
			createReportPDF(reportCase, questAnswerList, "E:\\");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new PDFUtil().test();
	}
}
