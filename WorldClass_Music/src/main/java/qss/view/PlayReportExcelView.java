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

import qss.vo.PlayReportVo;
import qss.vo.ScheduleReportVo;

public class PlayReportExcelView extends AbstractExcelView {

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
		
		List<PlayReportVo> list = (List<PlayReportVo>) modelMap.get("List");
		
		if(list != null) {
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
			String str = dayTime.format(new Date(time));

			excelName = "광고재생보고서_" + str + excelName;
			
			worksheet = workbook.createSheet("PlayReport");
			
			worksheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));

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
			cell.setCellValue("스크린명");

			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("파일명");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("시작(플레이어)");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("종료(플레이어)");
			
			cell = row.createCell(cellIndex++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("재생시간(플레이어)");
			
			for (PlayReportVo playReportVo : list) {
				// 3열
				row = worksheet.createRow(rowIndex++);
				cellIndex = 0;

				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(dataCnt++);

				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(playReportVo.getServiceName());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(playReportVo.getSiteName());

				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(playReportVo.getPlayerName());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(playReportVo.getPlPlaydate());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(playReportVo.getScreenName());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(playReportVo.getFileName());
				
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(playReportVo.getPlStarttime());
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				cell.setCellValue(playReportVo.getPlEndtime());
				
				
				cell = row.createCell(cellIndex++);
				cell.setCellStyle(bodyCellStyle);
				int sp = playReportVo.getPlPlaytime();
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
			}
			
			for (int i = 0; i <  10; i++){ 
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