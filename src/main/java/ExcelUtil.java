
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Map;

public class ExcelUtil {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    private static final String finalXlsxPath = "E:\\data\\test.xls";
    private static final int cloumnCount = 8;



    public static void writeExcel(Map dataMap) {
        FileOutputStream out = null;

        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);

            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);

            int rowNumber1 = sheet.getLastRowNum();
            int rowNumber2 = sheet.getPhysicalNumberOfRows();
            //int rowNumber = sheet.getRow(0).getLastCellNum()+1;

            out =  new FileOutputStream(finalXlsxFile);
            workBook.write(out);

            /**
             * 往Excel中写新数据
             */

            Row row = sheet.createRow(rowNumber2);
            String Company_Name = dataMap.get("Company_Name").toString();
            String Exhibitor_Address = dataMap.get("Exhibitor_Address").toString();
            String Phone_Number = dataMap.get("Phone_Number").toString();
            String WebSite = dataMap.get("WebSite").toString();
            String FloorPlan_location = dataMap.get("FloorPlan_location").toString();
            String Company_Contacts = dataMap.get("Company_Contacts").toString();
            //String Company_Brief_Description =  dataMap.get("Company_Brief_Description ").toString();
            //String Email_Address = dataMap.get("Email_Address ").toString();

            Cell first = row.createCell(0);
            first.setCellValue(Company_Name);

            Cell second = row.createCell(1);
            second.setCellValue(Exhibitor_Address);

            Cell third = row.createCell(2);
            third.setCellValue(Phone_Number);

            Cell fourth = row.createCell(3);
            fourth.setCellValue(WebSite);

            Cell fifth = row.createCell(4);
            fifth.setCellValue(FloorPlan_location);

            Cell sixth = row.createCell(5);
            sixth.setCellValue(Company_Contacts);

            //Cell seventh = row.createCell(6);
            //seventh.setCellValue(Company_Brief_Description);

            //Cell eighth = row.createCell(7);
            //eighth.setCellValue(Email_Address);



            System.out.println("数据导出成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    /**
     * 判断Excel的版本,获取Workbook
     * @param file dd
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

}
