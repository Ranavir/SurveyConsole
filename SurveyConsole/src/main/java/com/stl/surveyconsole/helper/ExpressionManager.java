package com.stl.surveyconsole.helper;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.stl.surveyconsole.vo.LimeConditionVO;

/**
 * This class helps in conversion of expressions into useful values or
 * evaluation of boolean expressions within parenthesis
 * 
 * @author Ranvir
 * @date 12-Nov-2016
 */
public class ExpressionManager {
	private static Logger logger = null;
	static{
		logger = Logger.getLogger(ExpressionManager.class) ;
	}
	/************************************************************************************
	 * This method does the followings
	 * Step 1 - Converts the relevance string by replacing (or/and) with (||/&&)
	 * Step 2 - For each record present in condition VOs evaluates the boolean result 
	 * 			field value using the hash map values(i.e previous responses)
	 * Step 3 - Replace the boolean result of each conditions in relevance string
	 * Step 4 - Evaluates the final boolean conditioned string to its final 
	 * 			boolean value  
	 * 
	 * @param  strRelevance
	 * @param  listConditionVO
	 * @param  hmSurveyRecentAnswer
	 * @param  hmSurveyHistoryAnswer
	 * @return bResult
	 * @author Ranavir Dash
	 * @date   12-Nov-2016
	 ************************************************************************************/
	public static boolean validateCondition(String strRelevance,
			List<LimeConditionVO> listConditionVO,
			HashMap<String, String> hmSurveyRecentAnswer,
			HashMap<String, String> hmSurveyHistoryAnswer) {
		String methodname = "validateCondition" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		boolean bResult      = false;
		/*This pattern used for conjunctive word replace with conjunctive symbol*/
		Pattern cnjPattern   = null;
		Matcher cnjMatcher   = null;
		/*This pattern used for each condition matching expression*/
		Pattern condPattern   = null;
		Matcher condMatcher   = null;
		//
		String key           = "";
		String oper          = "";
		String value         = "";
		String condQuestType = "" ;
		String userValue     = "";
		boolean result       = false;
		try{
			if(!StringUtils.isEmpty(strRelevance) && !listConditionVO.isEmpty() && (!hmSurveyRecentAnswer.isEmpty() || !hmSurveyHistoryAnswer.isEmpty())){
				//Step 1
				//Replace and with &&
				cnjPattern = Pattern.compile("(\\s)*and(\\s)*");
				cnjMatcher = cnjPattern.matcher(strRelevance);
				strRelevance = cnjMatcher.replaceAll(" && ");
				//Replace or with ||
				cnjPattern = Pattern.compile("(\\s)*or(\\s)*");
				cnjMatcher = cnjPattern.matcher(strRelevance);
				strRelevance = cnjMatcher.replaceAll(" || ");
				
				//Step 2
				for(LimeConditionVO voObj : listConditionVO){
					//Get predefined values from condition table
					key = voObj.getCfieldname();
					oper = voObj.getMethod();
					value = voObj.getValue();
					condQuestType = voObj.getCondQuestType();
					logger.debug("LimeConditionVO ::"+voObj);
					
					//Get user response for matching condition from Map
					if(value.equals("-oth-")){
						logger.debug("value is the other option(-oth-)");
						//to get other column value entered by user
						userValue = StringUtils.isEmpty(hmSurveyRecentAnswer.get(key + "other")) ? hmSurveyHistoryAnswer.get(key + "other") : hmSurveyRecentAnswer.get(key + "other");
					}else{
						userValue = StringUtils.isEmpty(hmSurveyRecentAnswer.get(key)) ? hmSurveyHistoryAnswer.get(key) : hmSurveyRecentAnswer.get(key);
					}
					
					userValue = StringUtils.isEmpty(userValue) ? "" : userValue;
					logger.debug("userValue ::"+userValue);
					
					if(value.equals("-oth-")){//Here only checked other value is entered or not
						result = StringUtils.isEmpty(userValue) ? false : true ;
					}else{//Go for any operator checking 
						//calculate result based on above data
						switch(oper){
							//<|<=|==|!=|>=|>|!
							case "<":
										logger.debug("oper :: <");
										if(condQuestType.trim().matches("[N]")){
											if(StringUtils.isEmpty(userValue)){
												result = false;
											}else{
												result = Double.parseDouble(userValue) < Double.parseDouble(value);
											}
										}else{
											result = userValue.compareTo(value) < 0 ? true : false ;
										}
								break;
							case "<=":
										logger.debug("oper :: <=");
										if(condQuestType.trim().matches("[N]")){
											if(StringUtils.isEmpty(userValue)){
												result = false;
											}else{
												result = Double.parseDouble(userValue) <= Double.parseDouble(value);
											}
											
										}else{
											result = (userValue.compareTo(value) <= 0) ? true : false ;
										}
								break;
							case "==":
										logger.debug("oper :: ==");
										if(condQuestType.trim().matches("[N]")){
											if(StringUtils.isEmpty(userValue)){
												result = false;
											}else{
												result = Double.parseDouble(userValue) == Double.parseDouble(value);
											}
										}else{
											result = (userValue.compareTo(value) == 0) ? true : false ;
										}
								break;
							case "!=":
										logger.debug("oper :: !=");
										if(condQuestType.trim().matches("[N]")){
											if(StringUtils.isEmpty(userValue)){
												result = false;
											}else{
												result = Double.parseDouble(userValue) != Double.parseDouble(value);
											}
										}else{
											result = (userValue.compareTo(value) != 0) ? true : false ;
										}
								break;
							case ">=":
										logger.debug("oper :: >=");
										if(condQuestType.trim().matches("[N]")){
											if(StringUtils.isEmpty(userValue)){
												result = false;
											}else{
												result = Double.parseDouble(userValue) >= Double.parseDouble(value);
											}
										}else{
											result = (userValue.compareTo(value) >= 0) ? true : false ;
										}
								break;
							case ">":
										logger.debug("oper :: >");
										if(condQuestType.trim().matches("[N]")){
											if(StringUtils.isEmpty(userValue)){
												result = false;
											}else{
												result = Double.parseDouble(userValue) > Double.parseDouble(value);
											}
										}else{
											result = (userValue.compareTo(value) > 0) ? true : false ;
										}
								break;
							case "!":
										logger.debug("oper :: !");
										if(condQuestType.trim().matches("[N]")){
											if(StringUtils.isEmpty(userValue)){
												result = false;
											}else{
												result = Double.parseDouble(userValue) == Double.parseDouble(value) * (-1) ;
											}
										}
								break;
							case "RX":
										logger.debug("oper :: RX");
										Pattern testPat = Pattern.compile(value);
										Matcher testMat = testPat.matcher(userValue);
										if(testMat.matches())
											result = true;
										else
											result = false;
								break;
							default:
								logger.error("No operator found for evaluation of condition...");
								break;
						}//end switch oper
					}//end if else other option
					
					//set the evaluated result for each VOs
					voObj.setResult(result);
					
					//Step 3 at the same time apply relevance string update
					
					
					switch(oper){
						case "<":
						case "<=":
						case "==":
						case "!=":
						case ">=":
						case ">":
						case "!":
							condPattern = Pattern.compile(key+".NAOK(\\s)*"+oper+"(\\s)*[\"]?"+value+"[\"]?");
							condMatcher = condPattern.matcher(strRelevance);
							strRelevance = condMatcher.replaceAll(result+"");
							break;
						case "RX":
							condPattern = Pattern.compile("regexMatch\\((.)*?"+value+"(.)*?"+key+".NAOK\\)");
							condMatcher = condPattern.matcher(strRelevance);
							strRelevance = condMatcher.replaceAll(result+"");
							break;
						default:
							break;
					}//end switch
				}//end for VOs
				
			}//end if valid details
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.debug("Relevance Equation :: "+strRelevance);
		try {
			bResult = (Boolean) new ScriptEngineManager().getEngineByName("javascript").eval(strRelevance);
			logger.info("Relevance final status --->"+bResult);
		} catch (ScriptException e) {
			logger.error("ScriptException!!!");
			e.printStackTrace();
		}
		logger.info("EXIT---> methodname : "+methodname);
		return bResult;
	}//end of startSurvey
}
