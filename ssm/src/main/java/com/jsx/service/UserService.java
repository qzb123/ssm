package com.jsx.service;

import java.util.List;

import com.jsx.model.Page;
import com.jsx.model.User;

public interface UserService {
	String addInfo(User addInfo);
	String delete(int id);
	User findById(int id);
	String update(User addInfo);
	User login(User user);
	int getallpages();
	List<User> getAll(int startPos, int pageSize);

}
