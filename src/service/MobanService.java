package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.MobanDao;
import domain.Mb_cdtVo;
import domain.Mb_showVo;
import domain.Mb_tableVo;
import domain.MobanVo;
import domain.Ms_columnVo;
import domain.Ms_tableVo;

@Service
public class MobanService {
	@Autowired
	private MobanDao mobanDao;
	public List<MobanVo> findMoban(){
		return mobanDao.findMoban();
	}
	public int getMatchCount(String name,String id){
		return mobanDao.getMatchCount(name, id);
	}
	public MobanVo findMobanByName(String mobanName,String mobanId){
		return mobanDao.findMobanByName(mobanName,mobanId);
	}
	public int addMoban(MobanVo mobanVo){
		return mobanDao.addMoban(mobanVo);
	}
	public void addMbTable(String name, String id, String[] tableArray) {
		// TODO Auto-generated method stub
		for(int i=0;i<tableArray.length;i++){
			String dsc = mobanDao.findTdsc(tableArray[i]);
			mobanDao.addMbTable(name,tableArray[i],dsc,id);
		}
	}
	public void addMbCdt(String name, String id, String[] cdtArray) {
		// TODO Auto-generated method stub
		for(int i=0;i<cdtArray.length;i++){
			String[] array = cdtArray[i].split(" ");
			String[] tc = array[0].split("-");
			String cdsc = mobanDao.findCdsc(tc[0],tc[1]);
			String type = mobanDao.findType(tc[0],tc[1]);
			mobanDao.addMbCdt(name,tc[0],tc[1],cdsc,array[1],array[2],type,id);
		}
	}
	public void addMbShow(String name, String id, String[] showArray) {
		// TODO Auto-generated method stub
		for(int i=0;i<showArray.length;i++){
			String[] tc = showArray[i].split("-");
			String cdsc = mobanDao.findCdsc(tc[0],tc[1]);
			mobanDao.addMbShow(name,tc[0],tc[1],cdsc,id);
		}
	}
	
	public List<Mb_tableVo> findMb_table(String name,String id) {
		// TODO Auto-generated method stub
		return mobanDao.findMb_table(name,id);
	}
	public List<Mb_showVo> findMb_show(String name,String id) {
		// TODO Auto-generated method stub
		return mobanDao.findMb_show(name,id);
	}
	public List<Mb_cdtVo> findMb_cdt(String name,String id) {
		// TODO Auto-generated method stub
		return mobanDao.findMb_cdt(name,id);
	}
	public List<Ms_tableVo> findMs_table() {
		// TODO Auto-generated method stub
		return mobanDao.findMs_table();
	}
	public List<Ms_columnVo> findMs_column() {
		// TODO Auto-generated method stub
		return mobanDao.findMs_column();
	}
	public void delMoban(String mobanName,String mobanId) {
		// TODO Auto-generated method stub
		mobanDao.delMoban(mobanName,mobanId);
	}
}
