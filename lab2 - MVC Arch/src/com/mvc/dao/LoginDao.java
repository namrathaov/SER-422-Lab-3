package com.mvc.dao;

import com.mvc.bean.LoginBean;
import com.mvc.util.DatabaseMap;

public class LoginDao implements LoginDAOInterface {

	/* (non-Javadoc)
	 * @see com.mvc.dao.LoginDAOInterface#authenticateUser(com.mvc.bean.LoginBean)
	 */
	@Override
	public String authenticateUser(LoginBean loginBean)
	{
		DatabaseMap map = new DatabaseMap();
		if(!map.userExists(loginBean.getUserName())) {
			return "User does not exist";
		}
		
		if(!map.validateUser(loginBean)) {
			return "Incorrect password";
		}
		return "SUCCESS";
	}
	/* (non-Javadoc)
	 * @see com.mvc.dao.LoginDAOInterface#createUser(com.mvc.bean.LoginBean)
	 */
	@Override
	public String createUser(LoginBean loginBean)
	{
		DatabaseMap map = new DatabaseMap();
		if(map.userExists(loginBean.getUserName())) {
			return "User already exists";
		}
		
		return map.createuser(loginBean);
	}
	
	/* (non-Javadoc)
	 * @see com.mvc.dao.LoginDAOInterface#getUserType(com.mvc.bean.LoginBean)
	 */
	@Override
	public String getUserType(LoginBean loginBean) {
		DatabaseMap map = new DatabaseMap();
		return map.getUserType(loginBean);
	}
}