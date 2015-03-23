package com.foal.yonder.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.foal.yonder.config.Constant;

public class ExcelUtil {
	
	public static String exportRepositoryModule() throws Exception{
		String fileName = UUID.randomUUID().toString()+ ".xls";
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中设置
		HSSFSheet sheet = wb.createSheet("题库列表"); 
		String[] title = new String[]{"题型(1-单选,2-多选,3-判断,4-填空,5-简答)","科目(100000-制作,200000-服务,300000-运营)多个用逗号隔开",
				"类型(1-模拟,2-正式)","分类(1-证书,2-年检)多个用逗号隔开","状态(1-启用,2-禁用)","问题","答案","答案选项(只有选择题才有此项,选项描述不能含有#号,多个选项用#号隔开)"};
		short[] width = new short[]{10000, 13000, 5000, 8000, 5000, 15000, 15000, 20000};
		insertTitleForRepository(sheet, style, title, width);
		int currentRow = 0;
		currentRow++;
		HSSFRow row = sheet.createRow(currentRow);
		insertData(row, "示例1(请删除)1","100000,200000","1","1,2","1", "1加1等于多少？", "B", "#A、1\n#B、2\n#C、3\n#D、4");
		currentRow++;
		row = sheet.createRow(currentRow);
		insertData(row, "示例2(请删除)2","100000,200000","1","1,2","1", "1加1不等于多少？", "ACD", "#A、1\n#B、2\n#C、3\n#D、4");
		currentRow++;
		row = sheet.createRow(currentRow);
		insertData(row, "示例3(请删除)3","100000,200000","1","1,2","1", "1加1等于2？", "正确");
		currentRow++;
		row = sheet.createRow(currentRow);
		insertData(row, "示例4(请删除)3","100000,200000","1","1,2","1", "1加1等于3？", "错误");
		currentRow++;
		row = sheet.createRow(currentRow);
		insertData(row, "示例5(请删除)4","100000,200000","1","1,2","1", "1加1等于多少？", "2");
		currentRow++;
		row = sheet.createRow(currentRow);
		insertData(row, "示例6(请删除)5","100000,200000","1","1,2","1", "请简述GIF图片制作过程.", "...");
		String filePath = Constant.TOMCAT_SERVICE_ADDRESS + "/" + fileName;
		writeToDisk(wb, filePath);
		return filePath;
	}
	
	private static void insertTitleForRepository(HSSFSheet sheet, HSSFCellStyle style, String[] title, short[] width) {
		HSSFRow headRow = sheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = headRow.createCell((short)i, HSSFCell.CELL_TYPE_STRING);
			HSSFRichTextString ts = new HSSFRichTextString(title[i]);
			cell.setCellValue(ts);
			cell.setCellStyle(style);
			sheet.setColumnWidth((short)i, width[i]);
		}
	}
	
	private static void insertData(HSSFRow row, Object... data) {
		for (int i = 0; i < data.length; i++) {
			HSSFCell cell = row.createCell((short)i, HSSFCell.CELL_TYPE_STRING);
			HSSFRichTextString ts = new HSSFRichTextString((String)data[i]);
			cell.setCellValue(ts);
		}
	}
	
	@SuppressWarnings("unused")
	private static void insertTitle(HSSFSheet sheet, HSSFCellStyle style, String[] title) {
		HSSFRow headRow = sheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = headRow.createCell((short)i, HSSFCell.CELL_TYPE_STRING);
			HSSFRichTextString ts = new HSSFRichTextString(title[i]);
			cell.setCellValue(ts);
			cell.setCellStyle(style);
			sheet.setColumnWidth((short)i, (short)(title[i].length() * 1000));
		}
	}
	
	private static void writeToDisk(HSSFWorkbook wb, String filePath) throws Exception{
		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		fos.close();
	}
	
	public static String getCellValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		String value = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			double numTxt = cell.getNumericCellValue();
			value = String.valueOf((int)numTxt);
			break;
		}
		return value.trim();
	}
	
}
