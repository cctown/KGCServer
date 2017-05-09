package web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.*;

import service.ListService;
import service.MobanService;

@Controller
public class MobanAction {
	
	@Autowired
	private MobanService mobanService;
	@Autowired
	private ListService listService;
	
	@RequestMapping("/output.pdf")
    public String outputPDF(){
        return "outputPDF";
    }
	
	//导出Excel
	@RequestMapping("/output.xls")
	public String outputExcel(){
		return "outputExcel";
	}
	
	@RequestMapping(value = "/showMoban.htm")
	public ModelAndView showMoban(HttpServletRequest request) {
		MobanVo moban = mobanService.findMobanByName(request.getParameter("mobanName"),request.getParameter("mobanId"));
		request.getSession().setAttribute("moban", moban);
		List<Mb_tableVo> mb_tableList= mobanService.findMb_table(moban.getName(),moban.getId());
		request.getSession().setAttribute("mb_tableList", mb_tableList);
		List<Mb_showVo> mb_showList= mobanService.findMb_show(moban.getName(),moban.getId());
		request.getSession().setAttribute("mb_showList", mb_showList);
		request.getSession().setAttribute("mb_showListR", mb_showList);
		List<Mb_cdtVo> mb_cdtList= mobanService.findMb_cdt(moban.getName(),moban.getId());
		request.getSession().setAttribute("mb_cdtList", mb_cdtList);
		return new ModelAndView("main");
	}
	
	@RequestMapping(value = "/delMoban.htm")
	public ModelAndView delMoban(HttpServletRequest request) {
		String mobanName = request.getParameter("mobanName");
		mobanService.delMoban(mobanName,request.getParameter("mobanId"));
		List<MobanVo> list= mobanService.findMoban();
		request.getSession().setAttribute("mobanList",list);
		request.getSession().setAttribute("moban", null);
		return new ModelAndView("main");
	}
	
	@RequestMapping(value = "/checkName.htm", method = RequestMethod.POST)
	public @ResponseBody String checkName(String mbName, String id) {  
        String msg = String.valueOf(mobanService.getMatchCount(mbName,id));
        return msg;
    }  
	
	@RequestMapping(value = "/editNewMoban.htm")
	public ModelAndView addNewMoban(HttpServletRequest request) {
		List<Ms_tableVo> ms_tableList=mobanService.findMs_table();
		List<Ms_columnVo> ms_columnList=mobanService.findMs_column();
		request.getSession().setAttribute("ms_tableList", ms_tableList);
		request.getSession().setAttribute("ms_columnList", ms_columnList);
		return new ModelAndView("editMoban");
	}
	
	@RequestMapping(value = "/addMoban.htm")
	public ModelAndView addMoban(HttpServletRequest request) {
		String name = request.getParameter("name");
		String dsc = request.getParameter("dsc");
		String table = request.getParameter("table");
		String cdt = request.getParameter("cdt");
		String show = request.getParameter("show");
		String id = request.getParameter("id");
		
		MobanVo moban = new MobanVo();
		moban.setName(name);
		moban.setDsc(dsc);
		moban.setId(id);
		mobanService.addMoban(moban);//添加模板
		
		String[] tableArray = table.split(",");
		mobanService.addMbTable(moban.getName(),moban.getId(),tableArray);//添加模板相关表
		
		String[] cdtArray = cdt.split(",");
		mobanService.addMbCdt(moban.getName(),moban.getId(),cdtArray);//添加条件相关项
		
		String[] showArray = show.split(",");
		mobanService.addMbShow(moban.getName(),moban.getId(),showArray);//添加显示相关项
		
		request.getSession().setAttribute("moban", null);
		List<MobanVo> list= mobanService.findMoban();
		request.getSession().setAttribute("mobanList",list);
		return new ModelAndView("main");
	}
	
	@RequestMapping(value = "/query.htm")
	public ModelAndView query(HttpServletRequest request) {
		String table = request.getParameter("table");
		String cdt = request.getParameter("cdt");
		String show = request.getParameter("show");
		
		List<Mb_showVo> mb_showList= listService.listShow(show);
		List<Mb_cdtVo> mb_cdtList= listService.listCdt(cdt);
		List<Mb_tableVo> mb_tableList= listService.listTable(table);
		
		@SuppressWarnings("rawtypes")
		List queryList = listService.listQuery(mb_showList,mb_tableList,mb_cdtList);
		request.getSession().setAttribute("queryList", queryList);
		request.getSession().setAttribute("mb_cdtList", mb_cdtList);
		request.getSession().setAttribute("mb_showListR", mb_showList);
		request.setAttribute("right","show");
		return new ModelAndView("main");
	}
}
