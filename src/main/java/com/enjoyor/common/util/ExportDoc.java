package com.enjoyor.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument; 
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

public class ExportDoc {

	public static XWPFDocument searchAndReplace(String srcPath, Map<String, String> map, Map<String, BufferedImage> mapImg,List<Map<String,String>> listTab){
		try {
			return searchAndReplace(new XWPFDocument(POIXMLDocument.openPackage(srcPath)),map,mapImg,listTab);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static XWPFDocument searchAndReplace(XWPFDocument document, Map<String, String> map, Map<String, BufferedImage> mapImg,List<Map<String,String>> listTab){
		try {
			if(map!=null&&map.size()>0){
				for(IBodyElement e:document.getBodyElements()){
					searchAndReplaceTxt(e,map);
				}
			}
			if(mapImg!=null&&mapImg.size()>0){
				for(IBodyElement e:document.getBodyElements()){
					searchAndReplaceImg(e,mapImg);
				}
			}
			if(listTab!=null&&listTab.size()>0){
				searchAndReplaceTable(document,listTab);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	
	private static void searchAndReplaceTxt(IBodyElement el, Map<String, String> map) throws InvalidFormatException, IOException{
		if(el.getElementType().name()=="TABLE"){
			XWPFTable table = (XWPFTable)el;
			int rcount = table.getNumberOfRows();
			for (int i = 0; i < rcount; i++) {
				XWPFTableRow row = table.getRow(i);
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					for(IBodyElement e:cell.getBodyElements()){
						searchAndReplaceTxt(e,map);
					}
				}
			}
		}else if(el.getElementType().name()=="PARAGRAPH"){
			XWPFParagraph paragraph = (XWPFParagraph) el;
			List<XWPFRun> runs = paragraph.getRuns();
			for (XWPFRun r : runs) {
				if (r.getText(0) != null && !r.getText(0).isEmpty()) {
					for (Entry<String, String> e : map.entrySet()) {
						if (r.getText(0).contains(e.getKey())) {
							r.setText(r.getText(0).replace(e.getKey(), e.getValue()), 0);
						}
					}
				}
			}
		}
	}
	private static void searchAndReplaceImg(IBodyElement el, Map<String, BufferedImage> mapImg) throws InvalidFormatException, IOException{
		if(el.getElementType().name()=="TABLE"){
			XWPFTable table = (XWPFTable)el;
			int rcount = table.getNumberOfRows();
			for (int i = 0; i < rcount; i++) {
				XWPFTableRow row = table.getRow(i);
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					for (Entry<String, BufferedImage> e : mapImg.entrySet()) {
						for(XWPFParagraph p:cell.getParagraphs()){
							searchAndReplaceImg(p,mapImg);
						}
					}
				}
			}
		}else if(el.getElementType().name()=="PARAGRAPH"){
			XWPFParagraph paragraph = (XWPFParagraph) el;
			for (Entry<String, BufferedImage> e : mapImg.entrySet()) {
				if(e.getKey().equals(paragraph.getText())){
					createPicture(paragraph,e.getValue());
				}
			}
		}
	}
	private static void createPicture(XWPFParagraph paragraph,BufferedImage img) throws IOException, InvalidFormatException {
		int type =img.getType();
		int width=img.getWidth();
		int height=img.getHeight();
		
		if(width>500){
			height=height*500/width;
			width=500;
		}
		
		createPicture(paragraph,img,type,width,height);
	}
	private static void createPicture(XWPFParagraph paragraph,BufferedImage img,int type, int width, int height) throws InvalidFormatException, IOException {
		paragraph.removeRun(0);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", out);
		paragraph.getDocument().addPictureData(out.toByteArray(), type);
		
		final int EMU = 9525;
		final int id = paragraph.getDocument().getAllPictures().size() - 1;
		width *= EMU;
		height *= EMU;
		String blipId = paragraph.getDocument().getAllPictures().get(id)
				.getPackageRelationship().getId();

		CTInline inline = paragraph.createRun().getCTR().addNewDrawing()
				.addNewInline();
		String picXml = ""
				+ "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
				+ "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
				+ id
				+ "\" name=\"Generated\"/>"
				+ "            <pic:cNvPicPr/>"
				+ "         </pic:nvPicPr>"
				+ "         <pic:blipFill>"
				+ "            <a:blip r:embed=\""
				+ blipId
				+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
				+ "            <a:stretch>"
				+ "               <a:fillRect/>"
				+ "            </a:stretch>"
				+ "         </pic:blipFill>"
				+ "         <pic:spPr>"
				+ "            <a:xfrm>"
				+ "               <a:off x=\"0\" y=\"0\"/>"
				+ "               <a:ext cx=\""
				+ width
				+ "\" cy=\""
				+ height
				+ "\"/>"
				+ "            </a:xfrm>"
				+ "            <a:prstGeom prst=\"rect\">"
				+ "               <a:avLst/>"
				+ "            </a:prstGeom>"
				+ "         </pic:spPr>"
				+ "      </pic:pic>"
				+ "   </a:graphicData>" + "</a:graphic>";

		// CTGraphicalObjectData graphicData =
		inline.addNewGraphic().addNewGraphicData();
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(picXml);
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);
		// graphicData.set(xmlToken);

		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);

		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);

		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("图片" + id);
		docPr.setDescr("");
	}
	
	private static void searchAndReplaceTable(XWPFDocument document, List<Map<String,String>> list){
		List<String> head=new ArrayList<String>();
		 Iterator it = document.getTablesIterator();
         while (it.hasNext()) {
             XWPFTable table = (XWPFTable) it.next();
             boolean need=false;
             int start=0;
             int rcount = table.getNumberOfRows();
             for (start = 0; start < rcount; start++) {
                 XWPFTableRow row = table.getRow(start);
            	 head.clear();
                 for (XWPFTableCell cell : row.getTableCells()) {
                	 if(contains(list,cell.getText())){
                		 need=true;
                		 head.add(cell.getText());
                	 }else{
                		 head.add("");
                	 }
                 }
                 if(need)break;
             }
             if(need){
            	 for(int i=1;i<list.size();i++)table.createRow();
            	 for(int j=0;j<head.size();j++)table.getRow(start).getCell(j).removeParagraph(0);
            	 for(int i=0;i<list.size();i++){
            		 for(int j=0;j<head.size();j++){
            			 table.getRow(start+i).getCell(j).setText(list.get(i).get(head.get(j)));
            		 }
            	 }
             }

         }
	}
	private static boolean contains(List<Map<String,String>> list,String val){
		if(val==null || val.isEmpty())return false;
		for(Map<String,String> m:list){
			if(m.containsKey(val))return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		 Map<String, String> map = new HashMap<String, String>();
		 map.put("${title}", "cccccc");

		 Map<String, BufferedImage> mapImg = new HashMap<String, BufferedImage>();
		 mapImg.put("${img}",  ImageIO.read(new FileInputStream("E:\\DownLoad\\photos\\22.jpg")));

		 List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		 Map<String,String> r1= new HashMap<String,String>();
		 r1.put("#{id}","1");
		 r1.put("#{name}","张三");
		 r1.put("#{value}","1600");
		 list.add(r1);
		 Map<String,String> r2= new HashMap<String,String>();
		 r2.put("#{id}","2");
		 r2.put("#{name}","李四");
		 r2.put("#{value}","3950");
		 list.add(r2);

		 Map<String,String> r3= new HashMap<String,String>();
		 r3.put("#{id}","3");
		 r3.put("#{name}","王五");
		 r3.put("#{value}","4-8");
		 list.add(r3);
		 
		 //XWPFDocument doc = searchAndReplaceTxt("F:\\马荣\\新建文件夹\\djb.docx",map);
		 XWPFDocument doc = searchAndReplace("D:\\Documents\\Tencent Files\\362666840\\FileRecv\\djb.docx",map,mapImg,null);
		 
		 FileOutputStream outStream = new FileOutputStream("D:\\Documents\\Tencent Files\\362666840\\FileRecv\\"+System.currentTimeMillis()+".docx");
		 doc.write(outStream);
         outStream.close();

	}
}
