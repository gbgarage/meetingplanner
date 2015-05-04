package dfzq.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import dfzq.model.MeetingRequestExcelItem;

public class MeetingRequestExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Meeting Request");
		setExcelHeader(excelSheet);
		
		List<MeetingRequestExcelItem> mrxItemList = (List) model.get("mrxItemList");
		setExcelRows(excelSheet,mrxItemList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("基金公司");
		excelHeader.createCell(1).setCellValue("上市公司");
		excelHeader.createCell(2).setCellValue("会议时间");
		excelHeader.createCell(3).setCellValue("会议类型");
		excelHeader.createCell(4).setCellValue("会议状态");
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<MeetingRequestExcelItem> mrxItemList){
		int record = 1;
		for (MeetingRequestExcelItem mrxItem : mrxItemList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(mrxItem.getFundName());
			excelRow.createCell(1).setCellValue(mrxItem.getCompanyName());
			excelRow.createCell(2).setCellValue(mrxItem.getTimeSlot());
			excelRow.createCell(3).setCellValue(mrxItem.getMeetingType());
			excelRow.createCell(4).setCellValue(mrxItem.getMeetingStatus());
		}
	}
}

