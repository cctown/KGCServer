package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import domain.Mb_cdtVo;
import domain.Mb_showVo;
import domain.Mb_tableVo;
import domain.MobanVo;
import domain.Ms_columnVo;
import domain.Ms_tableVo;
import domain.RelationVo;

@Repository
public class MobanDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public RelationVo findRelation(String t1, String t2){
		String sqlStr = "SELECT * FROM relation WHERE t1 =? AND t2=?";
		final RelationVo relation = new RelationVo();
		jdbcTemplate.query(sqlStr, new Object[]{t1,t2},
				new RowCallbackHandler() {
            		public void processRow(ResultSet rs) throws SQLException {
            			relation.setT1(rs.getString("t1"));
            			relation.setC1(rs.getString("c1"));
            			relation.setT2(rs.getString("t2"));
            			relation.setC2(rs.getString("c2"));
            }
        });
		if(relation.getT1()==null){
			jdbcTemplate.query(sqlStr, new Object[]{t2,t1},
					new RowCallbackHandler() {
	            		public void processRow(ResultSet rs) throws SQLException {
	            			relation.setT1(rs.getString("t1"));
	            			relation.setC1(rs.getString("c1"));
	            			relation.setT2(rs.getString("t2"));
	            			relation.setC2(rs.getString("c2"));
	            }
	        });
		}
		if(relation.getT1()==null) return null;
		return relation;
	}
	
	public List<MobanVo> findMoban(){
		String sqlStr = "SELECT * FROM moban";
		final List<MobanVo> list = new ArrayList<MobanVo>();
		jdbcTemplate.query(sqlStr,
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                    	MobanVo moban = new MobanVo();
                        moban.setName(rs.getString("name"));
                        moban.setDsc(rs.getString("dsc"));
                        moban.setId(rs.getString("id"));
                        list.add(moban);
                    }
                });
		return list;
	}
	public int getMatchCount(String name, String id) {
        String sqlStr = " SELECT count(*) FROM moban WHERE name =? and id =?";
        return jdbcTemplate.queryForInt(sqlStr, new Object[]{name, id});
    }
	public MobanVo findMobanByName(String mobanName,String mobanId){
		String sqlStr = "SELECT * FROM moban WHERE name =? and id =?";
		final MobanVo moban = new MobanVo();
		jdbcTemplate.query(sqlStr, new Object[]{mobanName,mobanId},
				new RowCallbackHandler() {
            		public void processRow(ResultSet rs) throws SQLException {
            			moban.setName(rs.getString("name"));
            			moban.setDsc(rs.getString("dsc"));
            			moban.setId(rs.getString("id"));
            }
        });
		return moban;
	}
	public String findTdsc(String name) {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT dsc FROM ms_table WHERE name = ?";
		String dsc = (String)jdbcTemplate.queryForObject(sqlStr, new Object[]{name}, java.lang.String.class);
		return dsc;
	}
	public String findCdsc(String tname,String cname) {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT dsc FROM ms_column WHERE tname = ? and cname = ?";
		String dsc = (String)jdbcTemplate.queryForObject(sqlStr, new Object[]{tname,cname}, java.lang.String.class);
		return dsc;
	}
	public String findType(String tname, String cname) {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT type FROM ms_column WHERE tname = ? and cname = ?";
		String type = (String)jdbcTemplate.queryForObject(sqlStr, new Object[]{tname,cname}, java.lang.String.class);
		return type;
	}
	public int addMoban(MobanVo mobanVo){
		String sqlStr = "INSERT INTO moban VALUES(?,?,?)";
		return jdbcTemplate.update(sqlStr, new Object[]{mobanVo.getName(),mobanVo.getDsc(),mobanVo.getId()});
	}
	public int addMbTable(String name, String tname, String dsc, String id) {
		// TODO Auto-generated method stub
		String sqlStr = "INSERT INTO mb_table VALUES(?,?,?,?)";
		return jdbcTemplate.update(sqlStr, new Object[]{name,tname,dsc,id});
	}
	public int addMbCdt(String name, String tname, String cname,
			String cdsc, String cdt, String value, String type,String id) {
		// TODO Auto-generated method stub
		String sqlStr = "INSERT INTO mb_cdt VALUES(?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sqlStr, new Object[]{name,tname,cname,cdsc,cdt,value,type,id});
	}
	public int addMbShow(String name, String tname, String cname,
			String cdsc,String id) {
		// TODO Auto-generated method stub
		String sqlStr = "INSERT INTO mb_show VALUES(?,?,?,?,?)";
		return jdbcTemplate.update(sqlStr, new Object[]{name,tname,cname,cdsc,id});
	}
	public void delMoban(String name,String id){
		String sqlStr = "DELETE FROM mb_table WHERE name = ? and id = ?";
		jdbcTemplate.update(sqlStr, new Object[]{name,id});
		sqlStr = "DELETE FROM mb_cdt WHERE name = ? and id = ?";
		jdbcTemplate.update(sqlStr, new Object[]{name,id});
		sqlStr = "DELETE FROM mb_show WHERE name = ? and id = ?";
		jdbcTemplate.update(sqlStr, new Object[]{name,id});
		sqlStr = "DELETE FROM moban WHERE name = ? and id = ?";
		jdbcTemplate.update(sqlStr, new Object[]{name,id});
	}
	public List<Mb_tableVo> findMb_table(String name,String id) {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT * FROM mb_table WHERE name=? and id=?";
		final List<Mb_tableVo> list = new ArrayList<Mb_tableVo>();
		jdbcTemplate.query(sqlStr,new Object[]{name,id},
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                    	Mb_tableVo mb_table = new Mb_tableVo();
                    	mb_table.setName(rs.getString("name"));
                    	mb_table.setTname(rs.getString("tname"));
                    	mb_table.setTdsc(rs.getString("tdsc"));
                    	mb_table.setId(rs.getString("id"));
                        list.add(mb_table);
                    }
                });
		return list;
	}
	public List<Mb_showVo> findMb_show(String name,String id) {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT * FROM mb_show WHERE name=? and id=?";
		final List<Mb_showVo> list = new ArrayList<Mb_showVo>();
		jdbcTemplate.query(sqlStr,new Object[]{name,id},
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                    	Mb_showVo mb_show = new Mb_showVo();
                    	mb_show.setName(rs.getString("name"));
                    	mb_show.setTname(rs.getString("tname"));
                    	mb_show.setCname(rs.getString("cname"));
                    	mb_show.setCdsc(rs.getString("cdsc"));
                    	mb_show.setId(rs.getString("id"));
                        list.add(mb_show);
                    }
                });
		return list;
	}
	public List<Mb_cdtVo> findMb_cdt(String name,String id) {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT * FROM mb_cdt WHERE name=? and id=?";
		final List<Mb_cdtVo> list = new ArrayList<Mb_cdtVo>();
		jdbcTemplate.query(sqlStr,new Object[]{name,id},
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                    	Mb_cdtVo mb_cdt = new Mb_cdtVo();
                    	mb_cdt.setName(rs.getString("name"));
                    	mb_cdt.setTname(rs.getString("tname"));
                    	mb_cdt.setCname(rs.getString("cname"));
                    	mb_cdt.setCdsc(rs.getString("cdsc"));
                    	mb_cdt.setCdt(rs.getString("cdt"));
                    	mb_cdt.setValue(rs.getString("value"));
                    	mb_cdt.setType(rs.getString("type"));
                    	mb_cdt.setId(rs.getString("id"));
                        list.add(mb_cdt);
                    }
                });
		return list;
	}
	public List<Ms_tableVo> findMs_table() {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT * FROM ms_table";
		final List<Ms_tableVo> list = new ArrayList<Ms_tableVo>();
		jdbcTemplate.query(sqlStr,
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                    	Ms_tableVo ms_table = new Ms_tableVo();
                    	ms_table.setName(rs.getString("name"));
                    	ms_table.setDsc(rs.getString("dsc"));
                        list.add(ms_table);
                    }
                });
		return list;
	}
	public List<Ms_columnVo> findMs_column() {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT * FROM ms_column";
		final List<Ms_columnVo> list = new ArrayList<Ms_columnVo>();
		jdbcTemplate.query(sqlStr,
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                    	Ms_columnVo ms_column = new Ms_columnVo();
                    	ms_column.setTname(rs.getString("tname"));
                    	ms_column.setCname(rs.getString("cname"));
                    	ms_column.setDsc(rs.getString("dsc"));
                    	ms_column.setType(rs.getString("type"));
                        list.add(ms_column);
                    }
                });
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List listQuery(List<Mb_showVo> mb_showList,
			List<Mb_tableVo> mb_tableList, List<Mb_cdtVo> mb_cdtList) {
		// TODO Auto-generated method stub
		int i,j,tag=0;
		RelationVo relation;
		String sqlStr = "SELECT ";
		for(i=0;i<mb_showList.size();i++){
			Mb_showVo mb_show = mb_showList.get(i);
			sqlStr+=mb_show.getTname()+"."+mb_show.getCname()+",";
		}
		sqlStr=sqlStr.substring(0, sqlStr.length()-1);
		sqlStr+=" FROM ";
		for(i=0;i<mb_tableList.size();i++){
			Mb_tableVo mb_table = mb_tableList.get(i);
			sqlStr+=mb_table.getTname()+",";
		}
		sqlStr=sqlStr.substring(0, sqlStr.length()-1);
		sqlStr+=" WHERE ";
		if(mb_cdtList!=null)
		for(i=0;i<mb_cdtList.size();i++){
			tag = 1;
			Mb_cdtVo mb_cdt = mb_cdtList.get(i);
			if(mb_cdt.getType().equals("value")){
				sqlStr+=mb_cdt.getTname()+"."+mb_cdt.getCname()+" "+mb_cdt.getCdt()+" "+mb_cdt.getValue()+" and ";
			}
			else{
				if(mb_cdt.getCdt().equals("like")){
					sqlStr+=mb_cdt.getTname()+"."+mb_cdt.getCname()+" "+mb_cdt.getCdt()+" "+"'%"+mb_cdt.getValue()+"%'"+" and ";
				}
				else{
					sqlStr+=mb_cdt.getTname()+"."+mb_cdt.getCname()+" "+mb_cdt.getCdt()+" "+"'"+mb_cdt.getValue()+"'"+" and ";
				}
			}
		}
		for(i=0;i<mb_tableList.size();i++){
			Mb_tableVo table1 = mb_tableList.get(i);
			for(j=i+1;j<mb_tableList.size();j++){
				Mb_tableVo table2 = mb_tableList.get(j);
				relation = findRelation(table1.getTname(),table2.getTname());
				if(relation!=null){
					tag = 1;
					sqlStr+=relation.getT1()+"."+relation.getC1()+" = "+relation.getT2()+"."+relation.getC2()+" and ";
				}
			}
		}
		if(tag==1)
			sqlStr=sqlStr.substring(0, sqlStr.length()-5);
		else
			sqlStr=sqlStr.substring(0, sqlStr.length()-7);
		List rows = jdbcTemplate.queryForList(sqlStr);
		return rows;
	}
}
