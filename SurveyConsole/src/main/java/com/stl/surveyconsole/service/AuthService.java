package com.stl.surveyconsole.service;

import com.stl.surveyconsole.model.UserModel;
/**
 * This is the contract for authentication or authorization
 * @author Ranavir
 * @date 02-Nov-2016
 */
public interface AuthService {
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
	boolean authenticate(String userId,String password,String type);
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
	UserModel getAuthUser(String userId,String password,String type);
}
