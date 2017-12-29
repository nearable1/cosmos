package testPoi;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;

public class TestPoi {
public static void main(String[] args) {
		
		Workbook wb = new HSSFWorkbook();  
	    Sheet sheet1 = wb.createSheet("new sheet");
	    
	    Header header = sheet1.getHeader();
	    header.setCenter("Center Header");
	    
	    //创建表格样式
	    CellStyle style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	    //表格合并
		sheet1.addMergedRegion(new CellRangeAddress(
	            1, //first row (0-based)
	            2, //last row  (0-based)
	            1, //first column (0-based)
	            2  //last column  (0-based)
	    ));
    	
	    for(int i=0; i<10; i++) {
	    	//创建row
		    Row row = sheet1.createRow(i);
		    for(int j=0; j<10; j++) {
		    	//创建cell
			    Cell cell = row.createCell(j);
			    //设置cell的value
			    cell.setCellValue("hello"+i+j);
			    //设置cell的style
			    cell.setCellStyle(style);
		    }
	    }
	    
	    
	    Sheet sheet2 = wb.createSheet("second sheet");

	    // returns " O'Brien's sales   "
	    String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); 
	    Sheet sheet3 = wb.createSheet(safeName);

	    FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("E:\\workbook.xls");
			wb.write(fileOut);
		    fileOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	 
}
