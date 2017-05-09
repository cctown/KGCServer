package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import domain.UserVo;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int getMatchCount(String id, String password) {
		String sqlStr = " SELECT count(*) FROM user " + " WHERE id =? and password=? ";
		return jdbcTemplate.queryForInt(sqlStr, new Object[] { id, password });
	}

	public UserVo findUserById(final String id) {
		String sqlStr = " SELECT * " + " FROM user WHERE id =? ";
		final UserVo user = new UserVo();
		jdbcTemplate.query(sqlStr, new Object[] { id }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
			}
		});
		return user;
	}

	public int registerUser(UserVo userVo) {
		String sqlStr = " insert into user(id,password) values(?,?) ";
		try {
			jdbcTemplate.update(sqlStr, new Object[] { userVo.getId(), userVo.getPassword() });
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	public List<UserVo> allUsers() {
		String sqlStr = "SELECT * FROM user";
		final List<UserVo> list = new ArrayList<UserVo>();
		jdbcTemplate.query(sqlStr, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				UserVo user = new UserVo();
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				list.add(user);
			}
		});
		return list;
	}

	public void delUser(String id) {
		String sqlStr = "DELETE FROM mb_table WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
		sqlStr = "DELETE FROM mb_cdt WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
		sqlStr = "DELETE FROM mb_show WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
		sqlStr = "DELETE FROM moban WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
		sqlStr = "DELETE FROM user WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
	}

	public void toUser(String id) {
		String sqlStr = "UPDATE user SET role='N' WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
	}

	public void toAdmin(String id) {
		String sqlStr = "UPDATE user SET role='A' WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
		sqlStr = "DELETE FROM mb_table WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
		sqlStr = "DELETE FROM mb_cdt WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
		sqlStr = "DELETE FROM mb_show WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
		sqlStr = "DELETE FROM moban WHERE id = ?";
		jdbcTemplate.update(sqlStr, new Object[] { id });
	}
}
