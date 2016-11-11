package com.stl.surveyconsole.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;

import com.stl.surveyconsole.dao.PanelSurveyMappingDao;
import com.stl.surveyconsole.daoImpl.PanelSurveyMappingDaoImpl;
import com.stl.surveyconsole.helper.SurveyPanelStatusPredicate;
import com.stl.surveyconsole.model.UserModel;
import com.stl.surveyconsole.service.PanelSurveyMappingService;
import com.stl.surveyconsole.vo.PanelSurveyVO;

/**
 * This is the Service Implementation class for Mapping of panel with Surveys
 * and related facts
 * @author Ranavir
 * @date 03-Nov-2016
 */
public class PanelSurveyMappingServiceImpl implements PanelSurveyMappingService{
	private static PanelSurveyMappingServiceImpl psmServiceImpl = null;
	private static PanelSurveyMappingDao psmDao = null ;
	private static Logger logger = null;
	String methodname="";
	String userId = "";
	
	
	public PanelSurveyMappingServiceImpl() {
		logger = Logger.getLogger(PanelSurveyMappingServiceImpl.class) ;
		psmDao = PanelSurveyMappingDaoImpl.getInstance() ;
	}
	
	public static PanelSurveyMappingServiceImpl getInstance(){
		if(psmServiceImpl == null){
			psmServiceImpl = new PanelSurveyMappingServiceImpl();
			logger.info("PanelSurveyMappingServiceImpl Instantiated...");
		}
		return psmServiceImpl;
	}
	/**************************************************            Business Logic Starts            *********************************************/
	/****************************************************************************
	 * This method will return the list of Panel Survey mapping by panel id and 
	 * based upon a predicate(Ex- total survey,completed survey,incomplete survey)
	 * @param userId
	 * @return List<PanelSurveyVO>
	 * @author Ranavir
	 * @date 04-Nov-2016
	 ***************************************************************************/

	public List<PanelSurveyVO> getSurveys(String userId,
			SurveyPanelStatusPredicate predicate) {
		methodname = "getSurveys" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		List<PanelSurveyVO> listPanelSurvey = psmDao.getSurveys(userId,predicate) ; 
		
		logger.info("EXIT---> methodname : "+methodname);
		return listPanelSurvey ;
	}//end of getSurveys
	/*****************************************************************************
	 * This method used by Admin as init operation for survey participants
	 * @param surveyId
	 * @param userId
	 * @return boolean
	 * @author Ranavir
	 * @date 04-Nov-2016
	 ****************************************************************************/
	public boolean initSurveyParticipation(int surveyId, int userId) {
		methodname = "getSurveys" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		boolean flag = psmDao.initSurveyParticipation(surveyId,userId) ; 
		
		logger.info("EXIT---> methodname : "+methodname);
		return flag ;
	}//end of initSurveyParticipation
	
}//end class
