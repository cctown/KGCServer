package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.UserVo;

import dao.UserDao;

@Service
public class UserService {
	@Autowired
    private UserDao userDao;
	public boolean hasMatchUser(String id, String password) {
        return userDao.getMatchCount(id, password) > 0;
    }
	public UserVo findUserByUserName(String id) {
        return userDao.findUserById(id);
    }
	public boolean registerUser(UserVo userVo) {
		return userDao.registerUser(userVo) > 0;
	}
	public List<UserVo> allUsers() {
		return userDao.allUsers();
	}
	public void delUser(String id) {
		userDao.delUser(id);
	}
	public void toUser(String id) {
		userDao.toUser(id);
	}
	public void toAdmin(String id) {
		userDao.toAdmin(id);
	}
}
