package com.stl.surveyconsole.dao;

import java.util.List;

import com.stl.surveyconsole.helper.SurveyPanelStatusPredicate;
import com.stl.surveyconsole.model.UserModel;
import com.stl.surveyconsole.vo.PanelSurveyVO;
/**
 * This is the contract for Mapping of panel with Surveys
 * and related facts
 * @author Ranavir
 * @date 03-Nov-2016
 */
public interface PanelSurveyMappingDao {
	/****************************************************************************
	 * This method will return the list of Panel Survey mapping by panel id and 
	 * based upon a predicate(Ex- total survey,completed survey,incomplete survey)
	 * @param userId
	 * @return List<PanelSurveyVO>
	 * @author Ranavir
	 * @date 04-Nov-2016
	 ***************************************************************************/
	List<PanelSurveyVO> getSurveys(String userId, SurveyPanelStatusPredicate predicate);
	/*****************************************************************************
	 * This method used by Admin as init operation for survey participants
	 * @param surveyId
	 * @param userId
	 * @return boolean
	 * @author Ranavir
	 * @date 04-Nov-2016
	 ****************************************************************************/
	boolean initSurveyParticipation(int surveyId, int userId);
	

}//end class
