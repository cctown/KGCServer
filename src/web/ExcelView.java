package web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import domain.Mb_showVo;

public class ExcelView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		@SuppressWarnings("rawtypes")
		List<Map> queryList = (List<Map>) request.getSession().getAttribute("queryList");
    	List<Mb_showVo> showList = (List<Mb_showVo>) request.getSession().getAttribute("mb_showListR");
    	HSSFSheet sheet;
    	HSSFRow r;
    	HSSFCell c;
    	int rownum = 0;
    	int cellnum = 0;
    	
        sheet = workbook.createSheet("结果报表");
    	r = sheet.createRow(rownum++);
    	for(Mb_showVo showColumn:showList){
    		c = r.createCell(cellnum++);
    		c.setCellValue(showColumn.getCdsc());
        }
        for(@SuppressWarnings("rawtypes") Map queryMap:queryList){
        	r = sheet.createRow(rownum++);
        	cellnum = 0;
        	for(Mb_showVo showColumn:showList){
        		c = r.createCell(cellnum++);
        		c.setCellValue(String.valueOf(queryMap.get(showColumn.getCname())));
            }
        }
	}

}
