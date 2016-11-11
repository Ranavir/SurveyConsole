/*Copyright (c) 2016 Silicon Tech Lab Pvt Ltd.  All rights reserved. 			           *	
 * This document is the property of Silicon Tech Lab Pvt Ltd..                          *
 * All ideas and information contained in this document are the intellectual property (IP) *
 * rights of Silicon Tech Lab Pvt Ltd.. This document is not for reference or general   *
 * distribution and is meant for use only for STL. This document shall not             *
 * be loaned to or shared with anyone, within or outside STL, including its customers. * 
 * Copying or unauthorized distribution of this document, in any form or means             *
 * including electronic, mechanical, photocopying or otherwise is illegal.                 * 
 * Use is subject to license terms only.                                                   *  
 *****************************************************************************************

 *****************************************************************************************
 *    Ver         Author                  Date        			Description		        *
 *    1.0         Ranavir               24-April-2016          	Initial Version		    *
 *****************************************************************************************
 */
package com.stl.surveyconsole.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONObject;




/**
 * @author Ranavir
 * @description
 * @date Oct 28, 2016
 *
 */
public class MyPostgresqlDaoFactory extends DaoFactory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MyPostgresqlDaoFactory daoFactory = null;
	private static Logger logger = null;
	private Properties props = null;
	private InputStream is = null;
	public MyPostgresqlDaoFactory() {
		logger = Logger.getLogger(MyPostgresqlDaoFactory.class) ;
		props = new Properties();
		try {
			is = this.getClass().getClassLoader().getResourceAsStream("db.properties");
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MyPostgresqlDaoFactory getInstance(){
		if(daoFactory == null){
			daoFactory = new MyPostgresqlDaoFactory();
			logger.info("MyPostgresqlDaoFactory Instantiated...");
		}
		return daoFactory;
	}
	/**
	 * This method is used to get Oracle DB Connection
	 * 
	 * @return Connection
	 */
	public  Connection getConnection() {
		Connection conn = null;
		try {

			DriverManager.registerDriver(new org.postgresql.Driver());
			
			conn = DriverManager.getConnection(props.getProperty("POSTGRES_DB_URL"), props.getProperty("POSTGRES_DB_USERNAME"),props.getProperty("POSTGRES_DB_PASSWORD"));//182.156.93.61
			if (conn != null) {
				logger.info("Connection Established Successfully");
			}

		} catch (SQLException sqe) {
			logger.error("Not able to Establish Connection ");
		} catch (Exception exe) {
			logger.error("Not able to Establish Connection ");
		}
		
		
	
		return conn;
	}//end getConnection
	/**************************************************
	 * To get the maximum sequence id of a table 
	 * @param tableName
	 * @param pkid
	 * @return long
	 * @author Ranvir
	 * @date 02-Nov-2016
	 *************************************************/
	@Override
	public long getSequenceID(String tableName, String pkid) {
		long id = 0;
		Connection con = null ;
		Statement st = null ;
		ResultSet rs = null ;
		try
		{
			con = getConnection();
			st = con.createStatement();
			rs = st.executeQuery("select max("+pkid+") from "+tableName); 
			if(rs.next())
				id=rs.getLong(1);
			id++;
		}
		catch(SQLException se)
		{
			logger.warn(se);
		}
		catch(Exception e)
		{
			logger.warn(e);
		}
		finally
		{
			Utils.closeDB(st,rs,con);
		}
		logger.info("Next value of column "+pkid+" of table "+tableName+" = "+id);
		return id;
	}//end getSequenceID
	
	/**************************************************************************************************
	 * This method takes a table name as arguement and gives the keys of the table 
	 * in a List
	 * 
	 * @param tableName
	 * @return ArrayList<String>
	 * @author Ranvir
	 * @date 05-Nov-2016
	 *************************************************************************************************/
	@Override
	public ArrayList<String> getTableStructure(String tableName,String colPattern) {
		ArrayList<String> alResponseTblColList = new ArrayList<String>();
		Connection con = null ;
		ResultSet rs = null ;
		try
		{
			con = getConnection();
			DatabaseMetaData md = con.getMetaData();
			rs = md.getColumns(null, null, tableName, colPattern+"%");
			while(rs.next()) {//columns with this pattern exists
				alResponseTblColList.add(rs.getString(4));
			}
			rs.close();
		}
		catch(SQLException se)
		{
			logger.warn(se);
		}
		catch(Exception e)
		{
			logger.warn(e);
		}
		finally
		{
			Utils.closeDB(rs,con);
		}
		return alResponseTblColList;
	}//end getSequenceID
	@Override
	public void finalize(){
		if(null != logger){
			logger.info("MyPostgresqlDaoFactory object destroyed...");
		}else{
			System.out.println("MyPostgresqlDaoFactory object destroyed...");
		}
	}
	public static void main(String...args){
		//System.out.println(MyPostgresqlDaoFactory.getInstance().getConnection());
		System.out.println(MyPostgresqlDaoFactory.getInstance().getTableStructure("lime_survey_111238", "111238"));
	}
	
		
}
