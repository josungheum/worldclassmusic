package qss.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import qss.vo.ScheduleReportVo;

public class ScheduleReportExcelView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> modelMap, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String excelName = ".xls";
		Sheet worksheet = null;
		Row row = null;
		Cell cell = null;
		int rowIndex = 1;
		int cellIndex = 0;
		int dataCnt = 1;
		CellStyle headerCellStyle = workbook.createCellStyle();
		CellStyle bodyCellStyle = workbook.createCellStyle();
		
		Font headerFont = workbook.createFont();
		Font bodyFont = workbook.createFont();
		
		headerFont.setFontHeight((short)(12*20));
		headerFont.setColor(HSSFColor.BLACK.index);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		bodyFont.setFontHeight((short)(11*20));
		bodyFont.setColor(HSSFColor.BLACK.index);
		bodyFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		headerCellStyle.setFont(headerFont);
		
		bodyCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		bodyCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		bodyCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		bodyCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		bodyCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		bodyCellStyle.setFont(bodyFont);
		
		List<ScheduleReportVo> list = (List<ScheduleReportVo>) modelMap.get("List");
		
		if(list != null) {
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
			String str = dayTime.format(new Date(time));

			excelName = "스케줄운영현황보고서_" + str + excelName;
			
			worksheet = workbook.createSheet("ScheduleReport");
			
			worksheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11));

			// 1열
			row = worksheet.createRow(rowIndex++);
			cellIndex = 0;
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			SimpleDateFormat dayTime2 = new SimpleDateFormat("yyyy-MM-dd");
			String str2 = dayTime2.format(new Date(time));
			cell.setCellValue(str2);
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);

			// 2열
			row = worksheet.createRow(rowIndex++);
			cellIndex = 0;

			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("NO");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("서비스");

			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("사이트");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("플레이어");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("일자");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("시작(스케줄)");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("종료(스케줄)");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("재생시간(스케줄)");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("시작(플레이어)");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("종료(플레이어)");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("재생시간(플레이어)");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("결과");
			
			for (ScheduleReportVo scheduleReportVo : list) {
				// 3열
				row = worksheet.createRow(rowIndex++);
				cellIndex = 0;

				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(dataCnt++);

				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(scheduleReportVo.getServiceName());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(scheduleReportVo.getSiteName());

				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(scheduleReportVo.getPlayerName());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(scheduleReportVo.getSpDate());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(scheduleReportVo.getSpSchedstarttime());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(scheduleReportVo.getSpSchedendtime());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				int sp = scheduleReportVo.getSpSchedplaytime();
				int hour = sp/3600;
				int minute = (sp-(hour*3600))/60;
				int second = sp-(hour*3600)-(minute*60);
				String hours = String.valueOf(hour);
				String minutes = String.valueOf(minute);
				String seconds = String.valueOf(second);
				if(hour < 10) hours = "0"+hour;
				if(minute < 10) minutes = "0"+minute;
				if(second < 10) seconds = "0"+second;
				cell.setCellValue(hours+":"+minutes+":"+seconds);
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(scheduleReportVo.getSpRealstarttime());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(scheduleReportVo.getSpRealendtime());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				
				int rp = scheduleReportVo.getSpRealplaytime();
				hour = rp/3600;
				minute = (rp-(hour*3600))/60;
				second = rp-(hour*3600)-(minute*60);
				hours = String.valueOf(hour);
				minutes = String.valueOf(minute);
				seconds = String.valueOf(second);
				if(hour < 10) hours = "0"+hour;
				if(minute < 10) minutes = "0"+minute;
				if(second < 10) seconds = "0"+second;
				cell.setCellValue("-"+hours+":"+minutes+":"+seconds);

				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				int diff = scheduleReportVo.getSpDiff();
				boolean abs = true;
				if (diff < 0) {
					diff = Math.abs(diff);
					abs = false;
				}
				hour = diff/3600;
				minute = (diff-(hour*3600))/60;
				second = diff-(hour*3600)-(minute*60);
				hours = String.valueOf(hour);
				minutes = String.valueOf(minute);
				seconds = String.valueOf(second);
				if(hour < 10) hours = "0"+hour;
				if(minute < 10) minutes = "0"+minute;
				if(second < 10) seconds = "0"+second;
				if (abs) {
					cell.setCellValue("+"+hours+":"+minutes+":"+seconds);
				}
				else {
					cell.setCellValue("-"+hours+":"+minutes+":"+seconds);
				}
			}
			
			for (int i = 0; i <  12; i++){ 
				worksheet.autoSizeColumn(i); 
				worksheet.setColumnWidth(i, (worksheet.getColumnWidth(i)) + 1000); 
			}
		}
		
		try {
			response.setHeader("Content-Disposition", "attachement; filename=\"" + java.net.URLEncoder.encode(excelName, "UTF-8") + "\";charset=\"UTF-8\"");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	
}