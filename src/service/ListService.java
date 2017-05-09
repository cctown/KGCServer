package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.MobanDao;
import domain.Mb_cdtVo;
import domain.Mb_showVo;
import domain.Mb_tableVo;

@Service
public class ListService {
	@Autowired
	private MobanDao mobanDao;
	
	public List<Mb_showVo> listShow(String show){
		List<Mb_showVo> list = new ArrayList<Mb_showVo>();
		String[] showArray = show.split(",");
		for(int i=0;i<showArray.length;i++){
			Mb_showVo mb_show = new Mb_showVo();
			String[] tc = showArray[i].split("-");
			String cdsc = mobanDao.findCdsc(tc[0],tc[1]);
			mb_show.setTname(tc[0]);
			mb_show.setCname(tc[1]);
			mb_show.setCdsc(cdsc);
			list.add(mb_show);
		}
		return list;
	}
	public List<Mb_tableVo> listTable(String table){
		List<Mb_tableVo> list = new ArrayList<Mb_tableVo>();
		String[] tableArray = table.split(",");
		for(int i=0;i<tableArray.length;i++){
			Mb_tableVo mb_table = new Mb_tableVo();
			String dsc = mobanDao.findTdsc(tableArray[i]);
			mb_table.setTname(tableArray[i]);
			mb_table.setTdsc(dsc);
			list.add(mb_table);
		}
		return list;
	}
	public List<Mb_cdtVo> listCdt(String cdt){
		List<Mb_cdtVo> list = new ArrayList<Mb_cdtVo>();
		if(cdt.equals("")) return null;
		String[] cdtArray = cdt.split(",");
		for(int i=0;i<cdtArray.length;i++){
			Mb_cdtVo mb_cdt = new Mb_cdtVo();
			String[] array = cdtArray[i].split(" ");
			String[] tc = array[0].split("-");
			String cdsc = mobanDao.findCdsc(tc[0],tc[1]);
			String type = mobanDao.findType(tc[0],tc[1]);
			mb_cdt.setTname(tc[0]);
			mb_cdt.setCname(tc[1]);
			mb_cdt.setCdsc(cdsc);
			mb_cdt.setCdt(array[1]);
			mb_cdt.setValue(array[2]);
			mb_cdt.setType(type);
			list.add(mb_cdt);
		}
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List listQuery(List<Mb_showVo> mb_showList,
			List<Mb_tableVo> mb_tableList, List<Mb_cdtVo> mb_cdtList) {
		// TODO Auto-generated method stub
		return mobanDao.listQuery(mb_showList,mb_tableList,mb_cdtList);
	}
}
