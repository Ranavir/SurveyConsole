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
	//Date: 02-Nov-2016
	public static final String QUERY_AUTH_BY_UID = "SELECT * FROM PUBLIC.OSS_USER_INFO WHERE USER_ID = ? AND PASSWORD = ? AND OTP_VALIDATION = TRUE" ;
	public static final String QUERY_AUTH_BY_EMAIL = "SELECT * FROM PUBLIC.OSS_USER_INFO WHERE EMAIL = ? AND PASSWORD = ? AND OTP_VALIDATION = TRUE" ;
	
	//Date: 04-Nov-2016
	public static final String QUERY_PANEL_SURVEY_MAP = "select lus.userid,lus.surveyid as surveyid,lus.flag as completed_flag,lus.timings as time_to_complete,"+
	"lsl.surveyls_title,lsl.surveyls_description,lsl.surveyls_welcometext,lsl.surveyls_endtext,lsl.surveyls_dateformat,"+
	"ls.active,ls.expires,ls.startdate,ls.format,ls.savetimings,ls.language,ls.datestamp,ls.allowsave,ls.allowprev,ls.datecreated,ls.showxquestions,ls.showqnumcode,ls.showwelcome,ls.showprogress "+
	"from lime_surveys ls,stl_user_survey lus,lime_surveys_languagesettings lsl where ls.sid = lus.surveyid and lus.surveyid = lsl.surveyls_survey_id "+
	"and ls.active = 'Y' and lus.userid = ? order by ls.datecreated desc" ;
	public static final String QUERY_CREATE_USERID_COLUMN = "ALTER TABLE %s ADD COLUMN userid integer" ;
	public static final String QUERY_CHECK_USER_SURVEY_MAPPING= "select * from stl_user_survey where surveyid = %s and userid = %s" ;
	public static final String QUERY_CHECK_USER_EXTRA= "select * from stl_user_survey_details where userid = %s" ;
	public static final String QUERY_INSERT_USER_SURVEY_MAPPING= "insert into stl_user_survey (id,surveyid,userid) values(%s,%s,%s)" ;
	public static final String QUERY_INSERT_USER_EXTRA= "insert into stl_user_survey_details (id,userid) values(%s,%s)" ;
	
	//Date: 05-Nov-2016
	public static final String QUERY_GET_LIME_GROUPS = "select * from lime_groups where sid = %s order by gid asc,group_order asc" ;
	public static final String QUERY_GET_LIME_QUESTIONS = "select * from lime_questions where sid = %s order by qid asc,gid asc" ;
	public static final String QUERY_GET_LIME_QUEST_ATTRS = "select * from lime_question_attributes where qid in(select qid from lime_questions where sid = %s) order by qaid asc,qid asc" ;
	public static final String QUERY_GET_LIME_ANSWERS = "select * from lime_answers where qid in (select qid from lime_questions where sid = %s) order by qid asc,sortorder asc" ;
	public static final String QUERY_GET_LIME_CONDITIONS = "select * from lime_conditions where qid in (select qid from lime_questions where sid = %s) order by cid asc,qid asc" ;
	public static final String QUERY_GET_LANGUAGE_SETTINGS = "select * from lime_surveys_languagesettings where surveyls_survey_id = %s" ;		
	
	//Date: 08-Nov-2016
	public static final String QUERY_GET_SURVEY_RESPONSES = "select * from lime_survey_%s where userid = %s" ;
	
	//Date: 10-11-2016
	public static final String QUERY_UPDATE_COMPLETE_STATUS = "UPDATE stl_user_survey SET flag='Y'  WHERE surveyid = %s and userid = %s" ;
	public static final String QUERY_UPDATE_COMPLETENESS_COUNT = "update stl_user_survey_details set completesurveycount = COALESCE(completesurveycount,0) + 1,incompletesurveycount = case when incompletesurveycount = null then null else incompletesurveycount - 1 END where userid = %s" ;
	public static final String QUERY_RAISE_INCOMPLETE_COUNT = "update stl_user_survey_details set incompletesurveycount = COALESCE(incompletesurveycount,0) + 1 where userid = %s" ; 
	
}