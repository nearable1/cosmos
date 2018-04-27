package com.inbody.crm.common.utils.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.cglib.beans.BeanMap;

import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.Encodes;
import com.inbody.crm.common.utils.StringUtils;

/**
 * 文件导出
 * 
 * @author jiang.lingling
 * 
 */
public class FileExportUtil {

    private static final String NUMBER_FORMAT1     = "#,##0.00_);[Red]\\(#,##0.00\\)";
    private static final String NUMBER_FORMAT2     = "#,##0.00_);\\(#,##0.00\\)";
    private static final String NUMBER_FORMAT3     = "#,##0.00;[Red]#,##0.00";
    private static final String NUMBER_FORMAT4     = "#,##0.00_";
    private static final String NUMBER_FORMAT5     = "#,##0.00_ ;[Red]\\-#,##0.00\\";
    private static final String NUMBER_FORMAT6     = "#,##0.0_);[Red]\\(#,##0.0\\)";
    private static final String NUMBER_FORMAT7     = "#,##0.0_);\\(#,##0.0\\)";
    private static final String NUMBER_FORMAT8     = "#,##0.0;[Red]#,##0.0";
    private static final String NUMBER_FORMAT9     = "#,##0.0_";
    private static final String NUMBER_FORMAT10    = "#,##0.0_ ;[Red]\\-#,##0.0\\";
    private static final String NUMBER_FORMAT11    = "#,##0_);[Red]\\(#,##0\\)";
    private static final String NUMBER_FORMAT12    = "#,##0_);\\(#,##0\\)";
    private static final String NUMBER_FORMAT13    = "#,##0;[Red]#,##0";
    private static final String NUMBER_FORMAT14    = "#,##0_";
    private static final String NUMBER_FORMAT15    = "#,##0_ ;[Red]\\-#,##0\\";
    private static final String CURRENCY_FORMAT1   = "#,##0.00_);[Red]\\(#,##0.00\\)";
    private static final String CURRENCY_FORMAT2   = "#,##0.00_);\\(#,##0.00\\)";
    private static final String CURRENCY_FORMAT3   = "#,##0.00;[Red]#,##0.00";
    private static final String CURRENCY_FORMAT4   = "#,##0.00;\\-#,##0.00";
    private static final String CURRENCY_FORMAT5   = "#,##0.00;[Red]\\-#,##0.00";
    private static final String CURRENCY_FORMAT6   = "#,##0.0_);[Red]\\(#,##0.0\\)";
    private static final String CURRENCY_FORMAT7   = "#,##0.0_);\\(#,##0.0\\)";
    private static final String CURRENCY_FORMAT8   = "#,##0.0;[Red]#,##0.0";
    private static final String CURRENCY_FORMAT9   = "#,##0.0;\\-#,##0.0";
    private static final String CURRENCY_FORMAT10  = "#,##0.0;[Red]\\-#,##0.0";
    private static final String CURRENCY_FORMAT11  = "#,##0_);[Red]\\(#,##0\\)";
    private static final String CURRENCY_FORMAT12  = "#,##0_);\\(#,##0\\)";
    private static final String CURRENCY_FORMAT13  = "#,##0;[Red]#,##0";
    private static final String CURRENCY_FORMAT14  = "#,##0";
    private static final String CURRENCY_FORMAT15  = "#,##0_);[Red](#,##0)";
    private static final String ACCOUNTING_FORMAT1 = "_ * #,##0.00_ ;_ * \\-#,##0.00_ ;_ * \"-\"??_ ;_ @_";
    private static final String ACCOUNTING_FORMAT2 = "_ * #,##0.0_ ;_ * \\-#,##0.0_ ;_ * \"-\"??_ ;_ @_";
    private static final String ACCOUNTING_FORMAT3 = "_ * #,##0_ ;_ * \\-#,##0_ ;_ * \"-\"??_ ;_ @_";
    private static final String DATE_FORMAT1       = "m/d/yy";
    private static final String DATE_FORMAT2       = "yyyy/m/d;@";
    private static final String PERCENT_FORMAT1    = "0.00%";
    private static final String PERCENT_FORMAT2    = "0.0%";
    private static final String PERCENT_FORMAT3    = "0%";
    private static final String TEXT_FORMAT        = "@";
	public static final String DEFAULT = "01"; // 默认
	public static final String TEXT = "01"; // 文本
	public static final String NUMBER = "02"; // 数字
	public static final String DATE = "03"; // 日期
	public static final String CURRENCY = "04"; // 货币
	public static final String ACCOUNTING = "05"; // 会计
	public static final String PERCENT = "06"; // 百分比

	/**
	 * 复制行
	 * 
	 * @param sheet
	 * @param srcRowNo
	 *            原始行数
	 * @param destRowNo
	 *            目标行号
	 */
	public static void copyRow(Sheet sheet, int srcRowNo) {

		sheet.shiftRows(srcRowNo, sheet.getLastRowNum(), 1, true, false);
		Row srcRow = sheet.getRow(srcRowNo + 1);
		if (srcRow == null) {
			srcRow = sheet.createRow(srcRowNo);
		}

		copyRow(sheet, srcRow, srcRowNo);

	}

	/**
	 * 复制行
	 * 
	 * @param sheet
	 * @param srcRow
	 *            原始行
	 * @param destRowNo
	 *            目标行号
	 */
	public static void copyRow(Sheet sheet, Row srcRow, int destRowNo) {

		if (srcRow == null) {
			return;
		}

		Row destRow = sheet.getRow(destRowNo);
		if (destRow == null) {
			destRow = sheet.createRow(destRowNo);
		}

		int sheetmergerCount = sheet.getNumMergedRegions();
		CellRangeAddress cellRange = null;
		CellRangeAddress cellRangeNew = null;
		for (int sheetmergerIndex = 0; sheetmergerIndex < sheetmergerCount; sheetmergerIndex++) {
			cellRange = sheet.getMergedRegion(sheetmergerIndex);
			if (cellRange.getFirstRow() == srcRow.getRowNum() && cellRange.getLastRow() == srcRow.getRowNum()) {
				cellRangeNew = new CellRangeAddress(destRowNo, destRowNo, cellRange.getFirstColumn(), cellRange.getLastColumn());
				sheet.addMergedRegion(cellRangeNew);
			}
		}

		for (int i = 0; i < srcRow.getLastCellNum() + 1; i++) {
			Cell srcCell = srcRow.getCell(i);
			if (srcCell != null) {
				Cell destCell = destRow.createCell(i);
				destCell.setCellStyle(srcCell.getCellStyle());
				destCell.setCellType(srcCell.getCellType());
				setCellValue(destCell, getCellValue(srcCell), srcCell.getCellType());
			}
		}
	}

	/**
	 * 下载excel模板，
	 * 
	 * @param templatePath 模板地址（相对storage的地址）
	 * @return Workbook
	 */
	public static Workbook wrokbookWithTemplate(String templatePath, BeanMap beanMapHead, List<BeanMap> listBeanMap) {

		Workbook workbook = null;
		ByteArrayInputStream inputStream = null;
		POIFSFileSystem fileSystem = null;
		byte[] templateByte = null;

		try {
			templateByte = File2byte(templatePath);
			inputStream = new ByteArrayInputStream(templateByte);
			fileSystem = new POIFSFileSystem(inputStream);
			workbook = new HSSFWorkbook(fileSystem);
			if (null != workbook) {
				Sheet sheetTemp = workbook.getSheetAt(0);
				for (int i = 0; i <= sheetTemp.getLastRowNum(); i++) {
					Row row = sheetTemp.getRow(i);
					if (row == null) {
						continue;
					}
					for (int j = 0; j <= row.getLastCellNum(); j++) {
						String cellValue = getCellValue(row.getCell(j));
						String cellValueTemp = null;
						if (cellValue.startsWith("##")) {
							cellValue = cellValue.substring(2);
							cellValueTemp = StringUtils.nullToSp(beanMapHead.get(cellValue));
							setCellValue(row.getCell(j), cellValueTemp, Cell.CELL_TYPE_STRING);

						} else {
							if (cellValue.contains("##")) {
								String[] cellValues = cellValue.split("##");
								cellValue = cellValues[1];
								cellValueTemp = cellValues[0] + StringUtils.nullToSp(beanMapHead.get(cellValue));
								setCellValue(row.getCell(j), cellValueTemp, Cell.CELL_TYPE_STRING);
							}
						}
					}
				}
				// 申请单明细数据插入
				if (listBeanMap != null && listBeanMap.size() > 0) {
					//查找标记
					int rowNum = -1;
					for (int i = 0; i <= sheetTemp.getLastRowNum(); i++) {
						Row row = sheetTemp.getRow(i);
						if (row == null) {
							continue;
						}
						for (int j = 0; j <= row.getLastCellNum(); j++) {
							String cellValue = getCellValue(row.getCell(j));
							if (cellValue.startsWith("$$")) {
								rowNum = i;
							}
						}

					}
					//创建MAp，记录下一行列表记录；key为字段名称，value为列号
					Map<String, Integer> nextRowMap = new HashMap<String, Integer>();
					//插入数据库第一条记录
					BeanMap beanMapDetail1 = listBeanMap.get(0);
					if (rowNum != -1) {
						Row row = sheetTemp.getRow(rowNum);
						for (int j = 0; j <= row.getLastCellNum(); j++) {
							String cellValue = getCellValue(row.getCell(j));
							String cellValueTemp = null;
							if (cellValue.startsWith("$$")) {
								cellValue = cellValue.substring(2);
								try {
									cellValueTemp = StringUtils.nullToSp(beanMapDetail1.get(cellValue));
									nextRowMap.put(cellValue, j);

								} catch (IllegalArgumentException e) {
									cellValueTemp = getCellValue(row.getCell(j));
								}
								setCellValue(row.getCell(j), cellValueTemp, Cell.CELL_TYPE_STRING);

							}
						}
						if (listBeanMap.size() > 1) {

							//后续记录插入	
							int listSize = listBeanMap.size();
							Set<Entry<String, Integer>> entrySet = nextRowMap.entrySet();
							for (int i = 1; i < listSize; i++) {
								copyRow(sheetTemp, rowNum);
								rowNum++;
								for (Entry<String, Integer> entry : entrySet) {
									BeanMap beanMapDetail = listBeanMap.get(i);
									Row row1 = sheetTemp.getRow(rowNum);
									String value = StringUtils.nullToSp(beanMapDetail.get(entry.getKey()));
									setCellValue(row1.getCell(entry.getValue()), value, Cell.CELL_TYPE_STRING);
								}

							}
						}
					}
				}
			}
		} catch (IOException e) {
			try {
				inputStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return workbook;
	}


    /**
     * 根据单元格的类型来设置单元格的内容
     * 
     * @param cell 单元格
     * @param value 要设置的内容
     * @param cellType 单元格的类型
     * @throws ParseException
     */
    public static void setCellValue(Cell cell, String value, int cellType) {
         switch (cellType) {
         case Cell.CELL_TYPE_STRING:
        	 cell.setCellValue(value);
        	 break;
         case Cell.CELL_TYPE_NUMERIC:
        	 if (StringUtils.isNumeirc(value)) {
        		 // 导出时防止整数位大于7位数值自动转换成科学计数法的处理
        		 String valueBit = value;
        		 if (value.indexOf(".") != -1) {
        			 valueBit = value.substring(0, value.indexOf("."));
        		 }
        		 // 整数位大于7,或已‘0’开头的不做数字转换处理
        		 if (valueBit.length() > 7 || 
        				 (value.startsWith("0") && !"0".equals(valueBit))) {
        			 cell.setCellValue(value);
        		 } else {
        			 try {
        				 cell.setCellValue(Double.valueOf(value));
        				 cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        			 } catch (Exception e) {
        				 cell.setCellValue(value);
        			 }
        		 }
             } else if (isDate(cell)) {
                 Date cellDate = DateUtils.parse(value, "yyyy/MM/dd");
                 cell.setCellValue(cellDate);
             } else {
                 cell.setCellValue(value);
             }
         break;
         case Cell.CELL_TYPE_BOOLEAN:
        	 if (!StringUtils.isEmpty(value)) {
        		 cell.setCellValue(Boolean.getBoolean(value));
        		 cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
        	 } else {
        		 cell.setCellValue(value);
        	 }
        	 
         break;
         case Cell.CELL_TYPE_FORMULA:
        	 if (!StringUtils.isEmpty(value)) {
        		 cell.setCellFormula(value);
        		 cell.setCellType(Cell.CELL_TYPE_FORMULA);
        	 } else {
        		 cell.setCellValue(value);
        	 }
         break;
         default:
	        if (cell.getCellStyle() == null) { // 无格式设定时
	            if (!StringUtils.isEmpty(value) 
	            		&& StringUtils.isNumeirc(value)) {
	                cell.setCellValue(Double.valueOf(value));
	            } else {
	                cell.setCellValue(value);
	            }
	        } else {
	            String dataFormatString = cell.getCellStyle().getDataFormatString();
	            if (null == dataFormatString) {
	                cell.setCellValue(value);
	            } else {
	                if (isNumber(cell) || isCurrency(cell) || isAccounting(cell) || isPercent(cell)) {
	                    cell.setCellValue(Double.valueOf(value));
	                } else if (isDate(cell)) {
                        Date cellDate = DateUtils.parse(value, "yyyy/MM/dd");
                        cell.setCellValue(cellDate);
	                } else {
	                    cell.setCellValue(value);
	                }
	            }
	         }
        }
    }
    
    /**
     * 根据单元格的类型来设置单元格的内容
     * 
     * @param cell 单元格
     * @param value 要设置的内容
     * @param cellType 单元格的类型
     * @throws ParseException
     */
    public static void setCellValue(XSSFCell cell, String value, int cellType) {
    	switch (cellType) {
        case Cell.CELL_TYPE_STRING:
       	 cell.setCellValue(value);
       	 break;
        case Cell.CELL_TYPE_NUMERIC:
       	 if (StringUtils.isNumeirc(value)) {
                cell.setCellValue(Double.valueOf(value));
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            } else if (isDate(cell)) {
                Date cellDate = DateUtils.parse(value, "yyyy/MM/dd");
                cell.setCellValue(cellDate);
            } else {
                cell.setCellValue(value);
            }
        break;
        case Cell.CELL_TYPE_BOOLEAN:
       	 if (!StringUtils.isEmpty(value)) {
       		 cell.setCellValue(Boolean.getBoolean(value));
       		 cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
       	 } else {
       		 cell.setCellValue(value);
       	 }
       	 
        break;
        case Cell.CELL_TYPE_FORMULA:
       	 if (!StringUtils.isEmpty(value)) {
       		 cell.setCellFormula(value);
       		 cell.setCellType(Cell.CELL_TYPE_FORMULA);
       	 } else {
       		 cell.setCellValue(value);
       	 }
        break;
        default:
	        if (cell.getCellStyle() == null) { // 无格式设定时
	            if (!StringUtils.isEmpty(value) 
	            		&& StringUtils.isNumeirc(value)) {
	                cell.setCellValue(Double.valueOf(value));
	            } else {
	                cell.setCellValue(value);
	            }
	        } else {
	            String dataFormatString = cell.getCellStyle().getDataFormatString();
	            if (null == dataFormatString) {
	                cell.setCellValue(value);
	            } else {
	                if (isNumber(cell) || isCurrency(cell) || isAccounting(cell) || isPercent(cell)) {
	                    cell.setCellValue(Double.valueOf(value));
	                } else if (isDate(cell)) {
                        Date cellDate = DateUtils.parse(value, "yyyy/MM/dd");
                        cell.setCellValue(cellDate);
	                } else {
	                    cell.setCellValue(value);
	                }
	            }
	         }
       }
    }


    /**
     * 获取单元格类型
     * 
     * @param cell
     * @return
     */
    public static String getCellType(Cell cell) {

        if (isText(cell)) {
            return TEXT;
        } else if (isNumber(cell)) {
            return NUMBER;
        } else if (isDate(cell)) {
            return DATE;
        } else if (isCurrency(cell)) {
            return CURRENCY;
        } else if (isAccounting(cell)) {
            return ACCOUNTING;
        } else if (isPercent(cell)) {
            return PERCENT;
        }

        return DEFAULT;
    }

    /**
     * 获取单元格的内容
     * 
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (isDate(cell)) {
                    return DateUtils.formatDate(cell.getDateCellValue(), "yyyy/MM/dd");
                } else {
                    return getNumericValue(cell);
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                try {

                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    switch (evaluator.evaluateFormulaCell(cell)) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            return String.valueOf(cell.getBooleanCellValue());
                        case Cell.CELL_TYPE_NUMERIC:
                            if (isDate(cell)) {
                                return DateUtils.formatDate(cell.getDateCellValue(), "yyyy/MM/dd");
                            } else {
                                return getNumericValue(cell);
                            }
                        case Cell.CELL_TYPE_STRING:
                            return cell.getRichStringCellValue().getString();
                        default:
                            return "";
                    }
                } catch (Exception e) {
                    return cell.getCellFormula();
                }
            default:
                return "";
        }
    }

    private static String getNumericValue(Cell cell) {

        double cellValueD = cell.getNumericCellValue();
        NumberFormat nf = new DecimalFormat("#,##0.00");

        return nf.format(cellValueD);
    }

    /**
     * 单元格是否为百分比类型
     * 
     * @param cell
     * @return
     */
    public static boolean isPercent(Cell cell) {

        if (cell == null || cell.getCellStyle() == null || cell.getCellStyle().getDataFormatString() == null) {
            return false;
        }
        String dataFormatString = cell.getCellStyle().getDataFormatString().trim();
        if (PERCENT_FORMAT1.equals(dataFormatString) || PERCENT_FORMAT2.equals(dataFormatString) || PERCENT_FORMAT3.equals(dataFormatString)) {
            return true;
        }
        return false;

    }

    /**
     * 单元格是否为文本类型
     * 
     * @param cell
     * @return
     */
    public static boolean isText(Cell cell) {

        if (cell == null || cell.getCellStyle() == null || cell.getCellStyle().getDataFormatString() == null) {
            return false;
        }
        String dataFormatString = cell.getCellStyle().getDataFormatString().trim();
        if (TEXT_FORMAT.equals(dataFormatString)) {
            return true;
        }
        return false;

    }

    /**
     * 单元格是否为会计类型
     * 
     * @param cell
     * @return
     */
    public static boolean isAccounting(Cell cell) {

        if (cell == null || cell.getCellStyle() == null || cell.getCellStyle().getDataFormatString() == null) {
            return false;
        }
        String dataFormatString = cell.getCellStyle().getDataFormatString().trim();
        if (ACCOUNTING_FORMAT1.equals(dataFormatString) || ACCOUNTING_FORMAT2.equals(dataFormatString) || ACCOUNTING_FORMAT3.equals(dataFormatString)) {
            return true;
        }
        return false;

    }

    /**
     * 单元格是否为货币类型
     * 
     * @param cell
     * @return
     */
    public static boolean isCurrency(Cell cell) {

        if (cell == null || cell.getCellStyle() == null || cell.getCellStyle().getDataFormatString() == null) {
            return false;
        }
        String dataFormatString = cell.getCellStyle().getDataFormatString().trim();
        if (CURRENCY_FORMAT1.equals(dataFormatString) || CURRENCY_FORMAT2.equals(dataFormatString)
                || CURRENCY_FORMAT3.equals(dataFormatString)
                || CURRENCY_FORMAT4.equals(dataFormatString)
                || CURRENCY_FORMAT5.equals(dataFormatString)
                || CURRENCY_FORMAT6.equals(dataFormatString)
                || CURRENCY_FORMAT7.equals(dataFormatString)
                || CURRENCY_FORMAT8.equals(dataFormatString)
                || CURRENCY_FORMAT9.equals(dataFormatString)
                || CURRENCY_FORMAT10.equals(dataFormatString)
                || CURRENCY_FORMAT11.equals(dataFormatString)
                || CURRENCY_FORMAT12.equals(dataFormatString)
                || CURRENCY_FORMAT13.equals(dataFormatString)
                || CURRENCY_FORMAT14.equals(dataFormatString)
                || CURRENCY_FORMAT15.equals(dataFormatString)) {
            return true;
        }
        return false;

    }

    /**
     * 检查单元格是否为日期类型
     * 
     * @param cell
     * @return
     */
    public static boolean isDate(Cell cell) {
        if (cell == null || cell.getCellStyle() == null || cell.getCellStyle().getDataFormatString() == null) {
            return false;
        }
        String dataFormatString = cell.getCellStyle().getDataFormatString().trim();
        if (DATE_FORMAT1.equals(dataFormatString) || DATE_FORMAT2.equals(dataFormatString)) {
            return true;
        }
        return false;

    }

    /**
     * 检查单元格是否为数字类型
     * 
     * @param cell
     * @return
     */
    public static boolean isNumber(Cell cell) {

        if (cell == null || cell.getCellStyle() == null || cell.getCellStyle().getDataFormatString() == null) {
            return false;
        }
        String dataFormatString = cell.getCellStyle().getDataFormatString().trim();
        if (NUMBER_FORMAT1.equals(dataFormatString) || NUMBER_FORMAT2.equals(dataFormatString)
                || NUMBER_FORMAT3.equals(dataFormatString)
                || NUMBER_FORMAT4.equals(dataFormatString)
                || NUMBER_FORMAT5.equals(dataFormatString)
                || NUMBER_FORMAT6.equals(dataFormatString)
                || NUMBER_FORMAT7.equals(dataFormatString)
                || NUMBER_FORMAT8.equals(dataFormatString)
                || NUMBER_FORMAT9.equals(dataFormatString)
                || NUMBER_FORMAT10.equals(dataFormatString)
                || NUMBER_FORMAT11.equals(dataFormatString)
                || NUMBER_FORMAT12.equals(dataFormatString)
                || NUMBER_FORMAT13.equals(dataFormatString)
                || NUMBER_FORMAT14.equals(dataFormatString)
                || NUMBER_FORMAT15.equals(dataFormatString)) {
            return true;
        }
        return false;

    }
    public static byte[] File2byte(String filePath)  
    {  
        byte[] buffer = null;  
        try  
        {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            byte[] b = new byte[1024];  
            int n;  
            while ((n = fis.read(b)) != -1)  
            {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        }  
        catch (FileNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
    
    public static void write(HttpServletResponse response, Workbook workbook, String fileName) {

		try {
			response.reset();
	        response.setContentType("application/octet-stream; charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));

			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
