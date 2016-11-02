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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;




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
	}
	@Override
	public void finalize(){
		if(null != logger){
			logger.info("MyPostgresqlDaoFactory object destroyed...");
		}else{
			System.out.println("MyPostgresqlDaoFactory object destroyed...");
		}
	}
	public static void main(String...args){
		System.out.println(MyPostgresqlDaoFactory.getInstance().getConnection());
	}
		
}
