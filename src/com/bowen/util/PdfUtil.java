package com.bowen.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bowen.bean.Score;
import com.bowen.bean.Student;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil {
	// 生产pdf文件的路径
	private String path = "E:\\pdf\\2017_6_27\\";
	// 背景图片的路径
	public String imagePath = "E:\\image\\1.png";

	public void createPdf() {
		// 创建对象
		Student student = new Student();
		student.setId(1);
		student.setName("bowenchen");
		student.setAge(24);
		student.setSex("男");
		student.setAddress("上海市浦东新区浦东软件园");
		File file = new File(path);
		// 所在创建的文件路径 不存时，需要创建路径
		if (!file.exists()) {
			file.mkdirs();
		}
		// 获取生成pdf文件的地址
		String filePath = getSurveyInvoicePdf(path, student);
		System.out.println("获取的地址是："+filePath);
	}
	public Image getImageObject(String imagePath) {
		Image imageObject = null;
		// 通过图片路径获取图片对象
		try {
			imageObject = Image.getInstance(imagePath);

		} catch (BadElementException | IOException e) {
			e.printStackTrace();
		}
		return imageObject;

	}
	//根据图片的高度和宽度获取占据的百分比例
	private int getPercent(float h, float w) {
		int p = 0;
		float p2 = 0.0f;
		p2 = 595 / w * 100;
		p = Math.round(p2);
		return p;
	}
	//设置数据信息的文本样式
	public void setText(PdfWriter writer, String content,Student student, float x, float y, float z,boolean flag) throws DocumentException, IOException {
		//使用pdfContentByte，由一系列的类映射到每一个操作符的方法,操作数可以在Adobe的成像模型。这个类也有很多便利的方法来画弧线,圆形、矩形和文本在绝对位置。
		//获取层
		PdfContentByte canvas=writer.getDirectContent();
		//设置字体，使用windos自带的中文字体
		BaseFont bfChinese=BaseFont.createFont();
		//BaseFont bfChinese= BaseFont.createFont("C:/WINDOWS/Fonts/smalle.fon", BaseFont.COURIER_BOLD,BaseFont.EMBEDDED); 
		//生成整体中文字体类对象
		Font fontChinese = new Font(bfChinese, 8,Font.BOLD);
		//Phrase类的作用是添加一个短句,短语类知道如何添加行与行之间的间距
		Phrase phrase=new Phrase(content,fontChinese);
		//学生学号显示使用10号字体
		Font  id_Font=new Font(bfChinese,10,Font.BOLD);
		Phrase id_Phrase=new Phrase(student.getId().toString(),id_Font);
		//如果是学号设置成10号字体，否则其他学生信息的内容设置成8号字体
		if(flag){
			//给pdf添加文字水印 x:短语的x坐标 y:短语的y坐标,z:文本的旋转角度
			ColumnText.showTextAligned(canvas,Element.ALIGN_UNDEFINED,id_Phrase,x,y,z);
		}else{
			//给pdf添加文字水印 x:短语的x坐标 y:短语的y坐标,z:文本的旋转角度
			ColumnText.showTextAligned(canvas, Element.ALIGN_UNDEFINED, phrase, x, y, z);
		}
		
	}
	//表格样式的设置
	private void setTableText(PdfWriter writer,String text,Font font,float x, float y){
		//创建一个有一列的表格
		PdfPTable table=new PdfPTable(1);
		///设置表格的总宽度
		table.setTotalWidth(120);
		//将表格的宽度锁定
		table.setLockedWidth(true);
		//创建表格的一个单元格
		PdfPCell cell=new PdfPCell(new Phrase(text,font));
		//设置单元格的样式
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderWidth(0);
		cell.setPaddingRight(5);
		//将单元格添加到表格中
		table.addCell(cell);
		//public float writeSelectedRows(int rowStart, int rowEnd, float xPos, float yPos, PdfContentByte canvas); 
        //参数rowStart是你想开始的行的数目，参数rowEnd是你想显示的最后的行（如果你想显示所有的行，用-1），xPos和yPos是表格的坐标，canvas是一个PdfContentByte对象。
		//设置表格的行
		table.writeSelectedRows(0,+1,x,y,writer.getDirectContent());
	}

	// 获取生产后的pdf的文件路径
	/*
	 * ①建立com.lowagie.text.Document对象的实例。
	　　Document document = new Document(); 
	
	　　②建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
	　　PDFWriter.getInstance(document, new FileOutputStream("Helloworld.PDF")); 
	
	　　③打开文档。
	　　document.open(); 
	
	　　④向文档中添加内容。
	　　document.add(new Paragraph("Hello World")); 
	
	　　⑤关闭文档。
	　　document.close(); 
	 */
	public String getSurveyInvoicePdf(String path, Student student) {
		//存放生pdf文件的路径
		List<String> filePathList=new ArrayList<>();
		//pdf输出路径
		String filePath= path+"test.pdf";
		Image imageObject = null;
		if (null != getImageObject(imagePath)) {
			imageObject = getImageObject(imagePath);
		} else {
			System.out.println("找不到图片！");
		}
		//创建document对象
	   Document document=new Document(PageSize.A4, 0, 0, 0, 0);
	   try {
		   //创建一个书写器
		   PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(filePath));
		   //打开文档
		   document.open();
		   //向文档中添加内容
		   //分页
		   document.newPage();
		   //设置图片居住显示
		   imageObject.setAlignment(Image.MIDDLE);
		   imageObject.setAlignment(Image.TEXTWRAP);
		   //获取图片的高度
		   float heigth=imageObject.getHeight();
		   //获取图片的宽度
		   float width=imageObject.getWidth();
		   //根据图片的大小获取图片占据的比例
		   int percent=this. getPercent(heigth,width);
		   //设置图片的放缩比例
		   imageObject.scalePercent(percent);
		   //将设置后的背景图片添加到pdf文件中
		   document.add(imageObject);
		   //相对坐标偏移量
			int data_X=0;
			int data_Y=1;
		   //学生基本信息数据样式设置
			this.setText(writer, student.getName(),student, 498+data_X, 795+data_Y, 0,false);
			this.setText(writer, student.getAge().toString(),student, 498+data_X, 772+data_Y, 0,false);
			this.setText(writer, student.getSex(),student, 62+data_X, 749+data_Y, 0,false);
			this.setText(writer, student.getAddress(),student,115+data_X, 134+data_Y, 0,false);
			//右上角大标题学号
			this.setText(writer,student.getId().toString(), student, 500,820,0,true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse("2008-08-08 12:10:12");
			//学生成绩信息数据样式设置
			Score score=new Score(120,"数学",date,90.5);
			BaseFont bfChinese=BaseFont.createFont();
			//生成整体中文字体类对象
			Font fontChinese = new Font(bfChinese, 8,Font.BOLD);
			this.setTableText(writer, score.getId()==0?null:DataUtil.getNumKb(score.getId()),fontChinese,240+data_X, 645+data_Y);
			this.setTableText(writer, score.getScore()==0?null:DataUtil.getNumKb(score.getScore()),fontChinese,240+data_X, 630+data_Y);
		   //关闭文档
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(ParseException e){
			e.printStackTrace();
		}
	   File file=new File(filePath);
		if(file.exists()){
			return filePath;
		}else{
			return null;
		}
	}
//创建一个一行三列的表格
public void createTable(Document document) throws DocumentException{
	 PdfPTable table = new PdfPTable(3);
     //设置表格具体宽度
     table.setTotalWidth(90);
     //设置每一列所占的长度
     table.setWidths(new float[]{50f, 15f, 25f});


     PdfPCell cell1 = new PdfPCell();
     Paragraph para = new Paragraph("该单元居中");
     //设置该段落为居中显示
     para.setAlignment(1);
     cell1.setPhrase(para);
     table.addCell(cell1);

     table.addCell(new PdfPCell(new Phrase("無幽之路IText教程")));
     table.addCell(new PdfPCell(new Phrase("無幽之路IText教程")));

     document.add(table);
}
public static void main(String[] args) throws FileNotFoundException, DocumentException {
	PdfUtil pf=new PdfUtil();
	pf.createPdf();
}
}
