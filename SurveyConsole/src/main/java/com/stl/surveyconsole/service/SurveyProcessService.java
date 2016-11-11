package com.stl.surveyconsole.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the contract for all the Survey processes and related facts
 * 
 * @author Ranavir
 * @date 05-Nov-2016
 */
public interface SurveyProcessService {
	/****************************************************************************
	 * This method will do the survey process for the user_id and surveyid
	 * 
	 * @param iSuveyIdChoosen
	 * @param user_id
	 * @author Ranavir
	 * @date 05-Nov-2016
	 ***************************************************************************/
	//void startSurvey(int iSuveyIdChoosen, String user_id);

	/****************************************************************************
	 * Get all Survey Related data for the survey id
	 * 
	 * @param surveyid
	 * @param rqHm
	 * @return HashMap<String, Object>
	 * @author Ranavir
	 * @date 05-Nov-2016
	 ***************************************************************************/

	HashMap<String, Object> getSurveyDetails(int surveyid,
			HashMap<String, Object> rqHm);
	/***************************************************************************
	 * This method takes the table name and returns the column 
	 * list by reading the metadata of the table 
	 * @param strRespTblName
	 * @return ArrayList<String>
	 * @author Ranavir
	 * @param colPattern 
	 * @date 05-Nov-2016
	 ***************************************************************************/
	ArrayList<String> getColumnListByTableName(String strRespTblName, String colPattern);
	
	/***********************************************************************************
	 * This method takes the survyeid, userid, dynamic column list and get the 
	 * responses give by the user
	 * @param surveyid
	 * @param user_id
	 * @param alResponseTblColList
	 * @param rqHm
	 * @return HashMap<String, String>
	 * @author Ranavir
	 * @date 08-Nov-2016
	 ************************************************************************************/
	HashMap<String, String> getSurveyResponse(int surveyid, String user_id,
			ArrayList<String> alResponseTblColList, HashMap<String, Object> rqHm);
	/*********************************************************************************
	 * This method saves and updates the user response in database when needed
	 * 
	 * @param sid
	 * @param userid
	 * @param hmAnswers
	 * @param alColumns
	 * @return boolean
	 * @author Ranavir
	 * @date 10-Nov-2016
	 **********************************************************************************/
	boolean saveResponse(String sid,String userid,HashMap<String, String> hmAnswers,
			ArrayList<String> alColumns);
	/**********************************************************************************
	 * This method updates the survey status to complete for the user
	 * 
	 * @param surveyid
	 * @param userid
	 * @param type
	 * @return boolean
	 * @author Ranavir
	 * @date 10-Nov-2016
	 *********************************************************************************/
	boolean updateSurveyStatus(int surveyid, String userid,String type);

}
