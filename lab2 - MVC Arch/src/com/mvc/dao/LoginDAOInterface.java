package com.mvc.dao;

import com.mvc.bean.LoginBean;

public interface LoginDAOInterface {

	String authenticateUser(LoginBean loginBean);

	String createUser(LoginBean loginBean);

	String getUserType(LoginBean loginBean);

}