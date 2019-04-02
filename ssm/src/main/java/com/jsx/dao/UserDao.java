package com.jsx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jsx.model.User;

public interface UserDao {  
    int insert(User user);
    int insertSelective(User user);
    int deleteByPrimaryKey(int id);
    int updateByPrimaryKeySelective(User user);
    int updateByPrimaryKey(User user);
    int ALLcount();
    User selectByPrimaryKey(int id);
    List<User> getAll(@Param(value="startPos")int startPos,@Param(value="pageSize")int pageSize);
	User selectByUsernameAndPassword(User user);
}


