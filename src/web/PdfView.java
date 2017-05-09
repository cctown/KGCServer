package web;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import domain.Mb_showVo;

public class PdfView extends AbstractIText5PdfView {
    @SuppressWarnings("unchecked")
	@Override
    protected void buildPdfDocument(Map<String, Object> model,
            Document document, PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	@SuppressWarnings("rawtypes")
		List<Map> queryList = (List<Map>) request.getSession().getAttribute("queryList");
    	List<Mb_showVo> showList = (List<Mb_showVo>) request.getSession().getAttribute("mb_showListR");
//        Paragraph header = new Paragraph(new Chunk("PDF 输出测试",
//                getChineseFont(12)));
//        document.add(header);
        PdfPTable table = new PdfPTable(showList.size());
        for(Mb_showVo showColumn:showList){
        	Phrase ph = new Phrase(showColumn.getCdsc(), getChineseFont(12));
        	table.addCell(ph);
        }
        for(@SuppressWarnings("rawtypes") Map queryMap:queryList){
        	for(Mb_showVo showColumn:showList){
        		Phrase ph = new Phrase(String.valueOf(queryMap.get(showColumn.getCname())), getChineseFont(12));
        		table.addCell(ph);
            }
        }
        document.add(table);
    }

    private static final Font getChineseFont(float size) {
        Font FontChinese = null;
        try {
            //BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        	BaseFont bfChinese = BaseFont.createFont("C:/windows/fonts/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);     
        	FontChinese = new Font(bfChinese, size, Font.NORMAL);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return FontChinese;
    }
}