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
/***************************************************************
 * This class holds all Project level constants
 * @author office
 * @date 27042016
 ***************************************************************/
public class ProjectConstants {
	
	
	
	//Project CODEs
	public static final int CODE_QUERY_SUCCESS = 1 ;
	public static final int CODE_QUERY_FAILURE = -1 ;
	public static final int CODE_QUERY_NOREC_FOUND = 0 ;
	
	public static final int CODE_SELECT_SUCCESS = 3001 ;
	public static final int CODE_INSERT_SUCCESS = 3002 ;
	public static final int CODE_UPDATE_SUCCESS = 3003 ;
	public static final int CODE_DELETE_SUCCESS = 3004 ;
	
	
	
	//Project messages
	public static final String MSG_COMMON_SUCCESS = "SUCCESS" ;
	public static final String MSG_COMMON_FAILURE = "FAILURE" ;
	public static final String MSG_QUERY_NOT_FOUND = "Query does not Exists" ;
	public static final String MSG_QUERY_PARAM_NOT_FOUND = "Parameter does not Exists" ;
	
	
	
	//Project Rule Engine Queries
	//Date: 26042016
	public static final String QUERY_GET_RULE_MASTER = "SELECT MODULE,SUBMODULE,MAX_OCCUR FROM RULE_MASTER WHERE RULE_CODE = ?" ;
	public static final String QUERY_GET_RULE_DETAILS_LIST = "SELECT RULE_SEQ_NO,QUERY,COMPLEXITY FROM RULE_DETAILS WHERE RULE_CODE = ? AND RULE_SEQ_NO IN ( ? )" ;
	public static final String QUERY_GET_RULE_DETAILS = "SELECT RULE_SEQ_NO,QUERY,COMPLEXITY FROM RULE_DETAILS WHERE RULE_CODE = ? AND RULE_SEQ_NO = ?" ;
	
}