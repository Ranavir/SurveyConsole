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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/***************************************************************
 * This class holds all public static utility methods can be used by any class
 * object
 * 
 * @author office
 * @date 25042016
 ***************************************************************/
public class Utils {
	private static Logger logger = Logger.getLogger(Utils.class);

	public static void main(String[] args) {

	}
	/********************************************************************************************************/	
	/****************************************************
	  * This utility method is used to close the opened database
	  * connections
	  * @param Object
	  * @author Ranavir Dash
	  * @date 12092016
	  ***************************************************/
	 public static void closeDB(Object...args) {
	  try {
	   for(int i=0; i<args.length; i++ ){
	    if(args[i] != null){
	     if(args[i] instanceof ResultSet){
	      ((ResultSet)args[i]).close();
	     }
	     if(args[i] instanceof Statement){//work for statement,preparedstmt,callablestmt
	      ((Statement)args[i]).close();
	     }
	     if(args[i] instanceof Connection){
	      ((Connection)args[i]).close();
	     }
	    }
	   }//end for
	  } catch (SQLException e) {
	   e.printStackTrace();
	  }
	  
	  
	 }//end of closeDB
}
