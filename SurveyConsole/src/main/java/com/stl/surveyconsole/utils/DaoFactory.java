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

import java.io.Serializable;
import java.sql.Connection;

/**
 * @author Ranavir
 * @description
 * @date Apr 24, 2016
 *
 */
public abstract class DaoFactory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ORACLE = 2;
	public static final int POSTGRESQL = 1;
	
	
	
	
	
	/**
	 * To get Database Connection object
	 * 
	 * @author Ranavir
	 * @param whichFactory
	 * @return DaoFactory
	 */
	public abstract Connection getConnection() ;
	
	public static DaoFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case POSTGRESQL:
			return new MyPostgresqlDaoFactory();
		/*case ORACLE:
			return new MyOracleDaoFactory();*/
		default:
			return null;
		}
	}


	
}
