package com.stl.surveyconsole.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.stl.surveyconsole.dao.SurveyProcessDao;
import com.stl.surveyconsole.daoImpl.SurveyProcessDaoImpl;
import com.stl.surveyconsole.service.SurveyProcessService;
import com.stl.surveyconsole.vo.PanelSurveyVO;

/**
 * This is the Service Implementation for all the Survey processes
 * and related facts
 * @author Ranavir
 * @date 05-Nov-2016
 */
public class SurveyProcessServiceImpl implements SurveyProcessService{
	private static SurveyProcessServiceImpl spServiceImpl = null;
	private static SurveyProcessDao spDao = null ;
	private static Logger logger = null;
	String methodname="";
	String userId = "";
	
	
	public SurveyProcessServiceImpl() {
		logger = Logger.getLogger(SurveyProcessServiceImpl.class) ;
		spDao = SurveyProcessDaoImpl.getInstance() ;
	}
	
	public static SurveyProcessServiceImpl getInstance(){
		if(spServiceImpl == null){
			spServiceImpl = new SurveyProcessServiceImpl();
			logger.info("SurveyProcessServiceImpl Instantiated...");
		}
		return spServiceImpl;
	}
	/**************************************************            Business Logic Starts            *********************************************/
	
	/****************************************************************************
	 * Get all Survey Related data for the survey id
	 * 
	 * @param surveyid
	 * @param rqHm
	 * @return HashMap<String, Object>
	 * @author Ranavir
	 * @date 05-Nov-2016
	 ***************************************************************************/
	public HashMap<String, Object> getSurveyDetails(int surveyid,
			HashMap<String, Object> rqHm) {
		methodname = "getSurveyDetails" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		HashMap<String,Object> hmSurveyDetails = spDao.getSurveyDetails(surveyid,rqHm) ; 
		
		logger.info("EXIT---> methodname : "+methodname);
		return hmSurveyDetails ;
	}//end of getSurveyDetails
	/***************************************************************************
	 * This method takes the table name and returns the column 
	 * list by reading the metadata of the table 
	 * @param strRespTblName
	 * @param colPattern
	 * @return ArrayList<String>
	 * @author Ranavir
	 * @date 05-Nov-2016
	 ***************************************************************************/
	public ArrayList<String> getColumnListByTableName(String strRespTblName,String colPattern) {
		methodname = "getColumnListByTableName" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		ArrayList<String> alResponseTblColList = spDao.getColumnListByTableName(strRespTblName,colPattern) ; 
		
		logger.info("EXIT---> methodname : "+methodname);
		return alResponseTblColList ;
	}//end of getColumnListByTableName 
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
	public HashMap<String, String> getSurveyResponse(int surveyid,
			String user_id, ArrayList<String> alResponseTblColList,
			HashMap<String, Object> rqHm) {
		methodname = "getSurveyResponse" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		HashMap<String, String> hmSurveyAnswer = spDao.getSurveyResponse(surveyid,user_id,alResponseTblColList,rqHm) ; 
		
		logger.info("EXIT---> methodname : "+methodname);
		return hmSurveyAnswer ;
	}//end of getSurveyResponse 
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
	public boolean saveResponse(String sid,String userid,HashMap<String, String> hmAnswers,
			ArrayList<String> alColumns) {
		methodname = "saveResponse" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		boolean flag = spDao.saveResponse(sid,userid,hmAnswers,alColumns) ; 
		
		logger.info("EXIT---> methodname : "+methodname);
		return flag ;
	}//end of saveResponse 
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
	public boolean updateSurveyStatus(int surveyid, String userid,String type) {
		methodname = "updateSurveyStatus" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		boolean flag = spDao.updateSurveyStatus(surveyid,userid,type) ; 
		
		logger.info("EXIT---> methodname : "+methodname);
		return flag ;
	}//end of updateSurveyStatus 

	
	
}//end class
