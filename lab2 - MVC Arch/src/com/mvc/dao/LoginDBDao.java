package com.mvc.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mvc.bean.LoginBean;
import com.mvc.util.SQLMap;

public class LoginDBDao implements LoginDAOInterface {
	LoginBean loginBean=new LoginBean();
	String userName = loginBean.getUserName(); 
	String password = loginBean.getPassword();
	String role=loginBean.getRole();
	Connection con;
	Statement stmt;
	PreparedStatement stmt1;
	ResultSet rs,result;
	public String authenticateUser(LoginBean loginBean)
	{
		SQLMap map=new SQLMap();
		if(!map.userExists(loginBean.getUserName())) {
			return "User does not exist";
		}
		
		if(!map.validateUser(loginBean)) {
			return "Incorrect password";
		}
		return "SUCCESS";
	}
	public String createUser(LoginBean loginBean)
	{
		SQLMap map=new SQLMap();
		return map.createuser(loginBean);
	}
	@Override
	public String getUserType(LoginBean loginBean) {
		// TODO Auto-generated method stub
		SQLMap map=new SQLMap();
		return map.getUserType(loginBean);
	}
}