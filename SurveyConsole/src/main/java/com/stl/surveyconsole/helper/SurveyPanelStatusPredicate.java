package com.stl.surveyconsole.helper;

import com.stl.surveyconsole.vo.PanelSurveyVO;

/**
 * This is the contract for testing survey for a panelist
 * with some status criteria
 * @author office
 *
 */
public interface SurveyPanelStatusPredicate {
	
	public boolean test(PanelSurveyVO obj) ;
}
