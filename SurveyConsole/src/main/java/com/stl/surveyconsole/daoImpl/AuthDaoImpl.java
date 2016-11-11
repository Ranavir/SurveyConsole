package com.stl.surveyconsole.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.stl.surveyconsole.dao.AuthDao;
import com.stl.surveyconsole.model.UserModel;
import com.stl.surveyconsole.utils.DaoFactory;
import com.stl.surveyconsole.utils.MD5;
import com.stl.surveyconsole.utils.ProjectConstants;
import com.stl.surveyconsole.utils.Utils;
/**
 * This is the Dao Implementation class for authentication or authorization
 * @author Ranavir
 * @date 02-Nov-2016
 */
public class AuthDaoImpl implements AuthDao{
	private static AuthDaoImpl authDaoImpl = null;
	private static DaoFactory daoFactory ;
	private static Logger logger = null;
	String methodname="";
	String userId = "";
	
	public AuthDaoImpl() {
		logger = Logger.getLogger(AuthDaoImpl.class) ;
		daoFactory = DaoFactory.getDAOFactory(1);
	}
	
	public static AuthDaoImpl getInstance(){
		if(authDaoImpl == null){
			authDaoImpl = new AuthDaoImpl();
			logger.info("AuthDaoImpl Instantiated...");
		}
		return authDaoImpl;
	}
	
	public static void main(String[] args) {
		getInstance();
		daoFactory.getConnection() ;
		//System.out.println(getInstance().authenticate("captain1888@gmail.com", "11111111", "email"));
		System.out.println(getInstance().authenticate("111", "11111111", "uid"));
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
		String methodname = "authenticate" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		boolean isAuthentic = false ;
		Connection con = null;
		PreparedStatement pstIsAuthentic = null ;
		ResultSet rsIsAuthentic = null ;
		String queryIsAuthentic =  "" ;
		if(!StringUtils.isEmpty(type)){
			if(type.equalsIgnoreCase("email")){
				queryIsAuthentic = ProjectConstants.QUERY_AUTH_BY_EMAIL ;
			}else{//by uid
				queryIsAuthentic = ProjectConstants.QUERY_AUTH_BY_UID ;
			}
			try{
				con = daoFactory.getConnection();
				if (con != null) {
					logger.info("query:"+queryIsAuthentic);
					pstIsAuthentic = con.prepareStatement(queryIsAuthentic) ;
					if(type.equalsIgnoreCase("email")){
						pstIsAuthentic.setString(1, id);
					}else{//by uid
						pstIsAuthentic.setInt(1, Integer.parseInt(id));//Latter change according to the datatype of user_id column
					}
					
					pstIsAuthentic.setString(2, MD5.getEncriptedPassword(password));
					rsIsAuthentic = pstIsAuthentic.executeQuery() ;
					if(rsIsAuthentic.next()){
						isAuthentic = true ;
					}
					
				}//end of If
			 
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Utils.closeDB(pstIsAuthentic,rsIsAuthentic,con);
			}//end of try / catch
		}
		
		logger.info("ENTRY---> methodname : "+methodname);
		return isAuthentic;
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
	public UserModel getAuthUser(String id, String password, String type) {
		String methodname = "getAuthUser" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		UserModel user = null ;
		Connection con = null;
		PreparedStatement pstGetAuthUser = null ;
		ResultSet rsGetAuthUser = null ;
		String queryGetAuthUser = "" ;
		if(!StringUtils.isEmpty(type)){
			if(type.equalsIgnoreCase("email")){
				queryGetAuthUser = ProjectConstants.QUERY_AUTH_BY_EMAIL ;
			}else{//by uid
				queryGetAuthUser = ProjectConstants.QUERY_AUTH_BY_UID ;
			}
			try{
				con = daoFactory.getConnection();
				if (con != null) {
					logger.info("query:"+queryGetAuthUser);
					pstGetAuthUser = con.prepareStatement(queryGetAuthUser) ;
					if(type.equalsIgnoreCase("email")){
						pstGetAuthUser.setString(1, id);
					}else{//by uid
						pstGetAuthUser.setInt(1, Integer.parseInt(id));//Latter change according to the datatype of user_id column
					}
					
					pstGetAuthUser.setString(2, MD5.getEncriptedPassword(password));
					rsGetAuthUser = pstGetAuthUser.executeQuery() ;
					if(rsGetAuthUser.next()){
						user = new UserModel();
						user.setUser_id(null!=rsGetAuthUser.getString("user_id")?rsGetAuthUser.getString("user_id").trim():"");
						user.setMobile_number(rsGetAuthUser.getString("mobile_number").trim());
						user.setEmail(rsGetAuthUser.getString("email").trim());
						user.setPin(rsGetAuthUser.getString("pin").trim());
						user.setDob(null!=rsGetAuthUser.getString("dob")?Utils.convertTimestampToString(rsGetAuthUser.getString("dob").trim()):"");
						user.setPassword(rsGetAuthUser.getString("password").trim());
						user.setGender(null!=rsGetAuthUser.getString("gender")?rsGetAuthUser.getString("gender").trim().charAt(0):'O');
						user.setFirst_name(null!=rsGetAuthUser.getString("first_name")?rsGetAuthUser.getString("first_name").trim():"");
						user.setLast_name(null!=rsGetAuthUser.getString("last_name")?rsGetAuthUser.getString("last_name").trim():"");
						user.setAddress_line1(null!=rsGetAuthUser.getString("address_line1")?rsGetAuthUser.getString("address_line1").trim():"");
						user.setAddress_line2(null!=rsGetAuthUser.getString("address_line2")?rsGetAuthUser.getString("address_line2").trim():"");
						user.setState(null!=rsGetAuthUser.getString("state")?rsGetAuthUser.getString("state").trim():"");
						user.setCity(null!=rsGetAuthUser.getString("city")?rsGetAuthUser.getString("city").trim():"");
						user.setLat_home(rsGetAuthUser.getDouble("lat_home"));
						user.setLng_home(rsGetAuthUser.getDouble("lng_home"));
						user.setAdd_home(null!=rsGetAuthUser.getString("add_home")?rsGetAuthUser.getString("add_home").trim():"");
					}
					
				}//end of If
			 
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Utils.closeDB(pstGetAuthUser,rsGetAuthUser,con);
			}//end of try / catch
		}
		logger.info("ENTRY---> methodname : "+methodname);
		return user;
	}//end getAuthUser
}//end class
