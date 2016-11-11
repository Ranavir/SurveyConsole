package com.stl.surveyconsole.serviceImpl;

import org.apache.log4j.Logger;
import com.stl.surveyconsole.dao.AuthDao;
import com.stl.surveyconsole.daoImpl.AuthDaoImpl;
import com.stl.surveyconsole.model.UserModel;
import com.stl.surveyconsole.service.AuthService;

/**
 * This is the Service Implementation class for authentication or authorization
 * @author Ranavir
 * @date 02-Nov-2016
 */
public class AuthServiceImpl implements AuthService{
	private static AuthServiceImpl authServiceImpl = null;
	private static AuthDao authDao = null ;
	private static Logger logger = null;
	String methodname="";
	String userId = "";
	
	
	public AuthServiceImpl() {
		logger = Logger.getLogger(AuthServiceImpl.class) ;
		authDao = AuthDaoImpl.getInstance() ;
	}
	
	public static AuthServiceImpl getInstance(){
		if(authServiceImpl == null){
			authServiceImpl = new AuthServiceImpl();
			logger.info("AuthServiceImpl Instantiated...");
		}
		return authServiceImpl;
	}
	/**************************************************            Business Logic Starts            *********************************************/
	/**************************************************************************
	 * This method will take id,password and email/uid as arguements and
	 * authenticate the credentials
	 * @param userId
	 * @param password
	 * @param type
	 * @return boolean
	 * @author Ranavir
	 * @date 02-Nov-2016
	 ***************************************************************************/
	public boolean authenticate(String id, String password, String type) {
		methodname = "authenticate" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		boolean flag = authDao.authenticate(id,password,type) ;
		
		logger.info("EXIT---> methodname : "+methodname);
		return flag;
	}//end authenticate
	/*****************************************************************************
	 *This method will take id,password and email/uid as arguements and
	 * authenticate the credentials then return the User details if valid else null
	 * @param userId
	 * @param password
	 * @param type
	 * @return UserModel
	 * @author Ranavir
	 * @date 02-Nov-2016
	 ****************************************************************************/
	public UserModel getAuthUser(String userId, String password, String type) {
		methodname = "getAuthUser" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		UserModel user = null ;
		user = authDao.getAuthUser(userId,password,type) ; 
		
		logger.info("EXIT---> methodname : "+methodname);
		return user ;
	}//end getAuthUser
}//end class
