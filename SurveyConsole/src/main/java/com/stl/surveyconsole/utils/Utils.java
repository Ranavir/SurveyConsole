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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

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
		System.out.println(getDate("yyyy-MM-dd HH:mm:ss"));
	}

	/********************************************************************************************************/
	/****************************************************
	 * This utility method is used to close the opened database connections
	 * 
	 * @param Object
	 * @author Ranavir Dash
	 * @date 12092016
	 ***************************************************/
	public static void closeDB(Object... args) {
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i] != null) {
					if (args[i] instanceof ResultSet) {
						((ResultSet) args[i]).close();
					}
					if (args[i] instanceof Statement) {// work for
														// statement,preparedstmt,callablestmt
						((Statement) args[i]).close();
					}
					if (args[i] instanceof Connection) {
						((Connection) args[i]).close();
					}
				}
			}// end for
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// end of closeDB

	public static String getDate(String requestFormat) {
		Calendar currDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(requestFormat);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		String dateNow = formatter.format(currDate.getTime());
		return dateNow;
	}
	
	public static String convertTimestampToString(String timestamp) {
		String result = "";
		SimpleDateFormat inputFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			result = outputFormat.format(inputFormat.parse(timestamp));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}
	/**
	 * This method holds Global date formats for any Survey according to a code
	 *
	 * @param surveyls_dateformat
	 * @return String
	 */
	public static String getGlobalDateFormat(int surveyls_dateformat) {
		//will store 1-12 (ex- 2(dd-mm-yyyy) & 9(mm-dd-yyyy))
		String format = "dd-mm-yyyy" ;
		switch(surveyls_dateformat){
			case 2 :
					format = "dd-mm-yyyy" ;
					break;
			case 9 :
					format = "mm-dd-yyyy" ;
					break;
			default :
					break;
		}
		return format;
	}//end getGlobalDateFormat
	/**
	 * This utility method validates a date format with
	 * regards to a particular code
	 * 
	 * @param value
	 * @param iCode
	 * @return
	 */
	public static boolean validateDateFormat(String value,int iCode) {
		boolean flag = false;
		switch(iCode){
			case 2 :
					//format = "dd-mm-yyyy" ;
					flag = value.matches("([0][1-9]|[1-2][0-9]|[3][0-1])-([0][1-9]|[1][0-2])-([0-9]{4})");
					break;
			case 9 :
					//format = "mm-dd-yyyy" ;
					flag = value.matches("([0][1-9]|[1][0-2])-([0][1-9]|[1-2][0-9]|[3][0-1])-([0-9]{4})");
					break;
			default :
					break;
		}
		return flag;
	}//end validateDateFormat
	/**
	 * This utility method takes current value and total value 
	 * to return approximate percentage value
	 * 
	 * @param current
	 * @param total
	 * @return int
	 */
	public static int getPercentage(double current,double total){
		Double p = (current/total) * 100 ;
		return p.intValue();
	}//end getPercentage
}// end class
