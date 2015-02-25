package dfzq.service.excel;

import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.OneOnOneMeetingRequest;
import dfzq.service.ArrangementService;
import dfzq.service.DataLoadService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-8-26
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ExcelService {
    private Map<String, Company> companyMap = new HashMap<String, Company>();
    private List<Fund> funds = new ArrayList<Fund>();

    @Autowired
    private DataLoadService dataLoad;

    private int[] t1a = new int[]{1, 2, 3};
    private int[] t1b = new int[]{4, 5, 6, 7};


    public void readExcel() throws IOException {


        FileInputStream is = new FileInputStream("data/input.xlsx");
        XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);


        XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if (hssfSheet == null) {
            return;
        }


        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            XSSFRow hssfRow = hssfSheet.getRow(rowNum);

            if (hssfRow != null) {
                Fund fund = new Fund();
                for (int i = 1; i < 4; i++) {
                    Cell cell = hssfRow.getCell(i);
                    switch (i) {
                        case 1:
                            fund.setFundName(cell.getStringCellValue());
                            break;
                        case 2:
                            fund.setContactor(cell.getStringCellValue());
                            break;
                        case 3:
                            createOneOnOneRequest(fund, cell.getStringCellValue());
                            break;
                        default:

                    }
                }
                funds.add(fund);
                dataLoad.saveFund(fund);

            }
        }


    }


    public void loadCompanyData() throws IOException {


        FileInputStream is = new FileInputStream("schedule.xlsx");
        XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);


        XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if (hssfSheet == null) {
            return;
        }


        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            XSSFRow hssfRow = hssfSheet.getRow(rowNum);

            if (hssfRow != null) {
                String companyName = getCellValue(4, hssfRow);
                String contact = getCellValue(6, hssfRow);
                List<Integer> availability = getAvailability(hssfRow);
                Company company = new Company(companyName, contact);
                dataLoad.saveCompany(company, availability);

            }
        }


    }

    private List<Integer> getAvailability(XSSFRow hssfRow) {
        List<Integer> result = new ArrayList<Integer>();
        if (isAvailable(hssfRow, 12)) {
            addToList(t1a, result);
        }

        if (isAvailable(hssfRow, 13)) {
            addToList(t1b, result);
        }
        return result;

    }

    private void addToList(int[] ints, List<Integer> result) {
        for (int i : ints) {
            result.add(i);

        }
    }

    private boolean isAvailable(XSSFRow hssfRow, int i) {
        return "Y".equalsIgnoreCase(getCellValue(i, hssfRow));
    }

    private String getCellValue(int i, XSSFRow hssfRow) {
        Cell cell = hssfRow.getCell(i);
        if (cell == null) {
            return "";
        } else {
            return cell.getStringCellValue();
        }
    }


    private void createOneOnOneRequest(Fund fund, String companysString) {
        String[] compnays = companysString.split(",");
        for (String companyName : compnays) {
            if (!"".equals(companyName)) {
                Company company = getCompanyFromMap(companyName);

                OneOnOneMeetingRequest oneOnOneMeetingRequest = new OneOnOneMeetingRequest(fund, company);
                fund.addOneOnOneMeetingRequest(oneOnOneMeetingRequest);
                company.addOneOnOneMeetingRequest(oneOnOneMeetingRequest);
            }


        }

    }

    private Company getCompanyFromMap(String companyName) {
        Company company = companyMap.get(companyName);
        if (company == null) {
            company = new Company();
            company.setName(companyName);
            companyMap.put(companyName, company);
        }
        return company;


    }

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"applicationContext.xml"});

        ArrangementService arrangementService = (ArrangementService)context.getBean("arrangementService");
//        arrangementService.fundCancel(74, 92, 2);
        arrangementService.fundReschedule(70, new int[]{1,2,3}, new int[]{4,5,6});
//        ExcelService excelService = (ExcelService) context.getBean("excelService");
//        excelService.readExcel();

//        for (Company company : excelService.companyMap.values()) {
//            System.out.println(company);
//
//
//        }

    }

    public Map<String, Company> getCompanyMap() {
        return companyMap;
    }

    public List<Fund> getFunds() {
        return funds;
    }

    //    public void generateExcel(OutputStream outputStream, String[] headers, List<? extends ExcelReportItem> reportItems) throws IOException {
//        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
//        XSSFSheet sheet = xssfWorkbook.createSheet();
//
//        setupColumnWith(sheet);
//
//        XSSFCellStyle moduleTileStyle = createModuleTitleStyle(xssfWorkbook);
//        XSSFCellStyle tableheaderStyle = createTableHeaderStyle(xssfWorkbook);
//        XSSFCellStyle tableValueStyle = createTableVauleStyle(xssfWorkbook);
//
//        int index = createModuleTitle(sheet, 0, "航变统计报告", moduleTileStyle, headers.length);
//        index = createTable(sheet, index,
//                headers,
//                reportItems,
//                tableheaderStyle, tableValueStyle);
//
//        xssfWorkbook.write(outputStream);
//
//    }
//
//    private void setupColumnWith(XSSFSheet sheet) {
//        for (int i = 0; i < columnWidthArray.length; i++) {
//            sheet.setColumnWidth(i, columnWidthArray[i]);
//        }
//    }
//
//    private XSSFCellStyle createModuleTitleStyle(XSSFWorkbook xssfWorkbook) {
//        XSSFFont font = xssfWorkbook.createFont();
//        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
//        font.setFontName("微软雅黑");
//        font.setFontHeight((short) 200);
//
//        XSSFCellStyle moduleTileStyle = xssfWorkbook.createCellStyle();
//        moduleTileStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//        moduleTileStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
//        moduleTileStyle.setFont(font);
//        moduleTileStyle.setAlignment(HorizontalAlignment.CENTER);
//        return moduleTileStyle;
//    }
//
//
//    private XSSFCellStyle createTableHeaderStyle(XSSFWorkbook xssfWorkbook) {
//        XSSFFont font = xssfWorkbook.createFont();
////        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
//        font.setFontName("微软雅黑");
//        font.setFontHeight((short) 200);
//
//
//        XSSFCellStyle style = xssfWorkbook.createCellStyle();
//
//        style.setFont(font);
//
//        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//
//        style.setBorderBottom((short) 1);
//        style.setBorderLeft((short) 1);
//        style.setBorderRight((short) 1);
//        style.setBorderTop((short) 1);
//        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//
//        style.setAlignment(HorizontalAlignment.CENTER);
//
//        return style;
//    }
//
//    private XSSFCellStyle createTableVauleStyle(XSSFWorkbook xssfWorkbook) {
//        XSSFFont font = xssfWorkbook.createFont();
////        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
//        font.setFontName("微软雅黑");
//        font.setFontHeight((short) 200);
//
//        XSSFCellStyle style = xssfWorkbook.createCellStyle();
//
//        style.setFont(font);
////        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
////        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//
//        style.setBorderBottom((short) 1);
//        style.setBorderLeft((short) 1);
//        style.setBorderRight((short) 1);
//        style.setBorderTop((short) 1);
//        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//        style.setWrapText(true);
//
//
//        style.setAlignment(HorizontalAlignment.CENTER);
//        return style;
//    }
//
//
//    private int createModuleTitle(XSSFSheet sheet, int startIndex, String title, XSSFCellStyle moduleTileStyle,int length) {
//        XSSFRow row = sheet.createRow(startIndex++);
//        Cell cell = row.createCell(0);
//        cell.setCellValue(title);
//
//        cell.setCellStyle(moduleTileStyle);
//        sheet.addMergedRegion(new CellRangeAddress(startIndex - 1, startIndex - 1, 0, length));
//
//
//        return startIndex;
//    }
//
//    private int createTable(XSSFSheet sheet, int index, String[] headers, List<? extends ExcelReportItem> reportItems, XSSFCellStyle tableheaderStyle, XSSFCellStyle tableValueStyle) {
//
//        index = createTableHeader(sheet, index, headers, tableheaderStyle);
//        index = createTableValue(sheet, index, reportItems, tableValueStyle);
//        return index;
//
//    }
//
//    private int createTableValue(XSSFSheet sheet, int index, List<? extends ExcelReportItem> reportItems, XSSFCellStyle tableValueStyle) {
//        for (ExcelReportItem excelReportItem : reportItems) {
//            Row row = sheet.createRow(index++);
//            String[] values = excelReportItem.generateTableItem();
//            int j = 1;
//            for (String value : values) {
//                Cell cell = row.createCell(j++);
//                cell.setCellValue(value);
//                cell.setCellStyle(tableValueStyle);
//            }
//
//
//        }
//        return index;
//    }
//
//    private int createTableHeader(XSSFSheet sheet, int index, String[] headers, XSSFCellStyle tableheaderStyle) {
//        Row row = sheet.createRow(index++);
//        int j = 1;
//        for (String header : headers) {
//            Cell cell = row.createCell(j++);
//            cell.setCellValue(header);
//            cell.setCellStyle(tableheaderStyle);
//        }
//        return index;
//    }
//
//    private int createTitle(XSSFSheet sheet, int index, String title) {
//        Row row = sheet.createRow(index++);
//        Cell cell = row.createCell(0);
//        cell.setCellValue(title);
//        sheet.addMergedRegion(new CellRangeAddress(index - 1, index - 1, 0, 2));
//
//
//        return index;
//
//
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        ExcelService excelService = new ExcelService();
//        excelService.readExcel();
//    }
//
//
//
//    private int createMultipleHeaderTable(XSSFSheet sheet, int startIndex, String title, String[] header1, String[] header2, List<? extends ExcelReportItem> reportItems, XSSFCellStyle tableheaderStyle, XSSFCellStyle tableValueStyle) {
//        int index = createTitle(sheet, startIndex, title);
//        index = createTableTopHeader(sheet, index, header1, new int[]{1, 1, 2, 7, 8, 13}, tableheaderStyle);
//        index = createTableHeader(sheet, index, header2, tableheaderStyle);
//        sheet.addMergedRegion(new CellRangeAddress(index - 2, index - 1, 1, 1));
//        index = createTableValue(sheet, index, reportItems, tableValueStyle);
//        return index;
//
//
//    }
//
//    private int createTableTopHeader(XSSFSheet sheet, int index, String[] headers, int[] ints, XSSFCellStyle tableheaderStyle) {
//        Row row = sheet.createRow(index++);
//
//        for (int i = 0, j = 0; i < headers.length; i++) {
//            Cell cell = row.createCell(ints[j]);
//            cell.setCellValue(headers[i]);
//            cell.setCellStyle(tableheaderStyle);
//            for (int k = ints[j] + 1; k <= ints[j + 1]; k++) {
//                cell = row.createCell(k);
//                cell.setCellValue("");
//                cell.setCellStyle(tableheaderStyle);
//            }
//            if (ints[j + 1] > ints[j]) {
//                sheet.addMergedRegion(new CellRangeAddress(index - 1, index - 1, ints[j], ints[j + 1]));
//            }
//            j = j + 2;
//        }
//        return index;
//    }


}
