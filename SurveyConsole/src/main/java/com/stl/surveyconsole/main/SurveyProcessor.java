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
package com.stl.surveyconsole.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.stl.surveyconsole.helper.MyGenericUtils;
import com.stl.surveyconsole.helper.Predicate;
import com.stl.surveyconsole.helper.QuestionConditionPredicate;
import com.stl.surveyconsole.model.LimeAnswersModel;
import com.stl.surveyconsole.model.LimeConditionsModel;
import com.stl.surveyconsole.model.LimeGroupsModel;
import com.stl.surveyconsole.model.LimeQuestionAttributesModel;
import com.stl.surveyconsole.model.LimeQuestionsModel;
import com.stl.surveyconsole.model.LimeSurveyLanguagesettings;
import com.stl.surveyconsole.service.SurveyProcessService;
import com.stl.surveyconsole.serviceImpl.SurveyProcessServiceImpl;
import com.stl.surveyconsole.utils.Keyin;
import com.stl.surveyconsole.utils.Utils;
import com.stl.surveyconsole.vo.PanelSurveyVO;

/**************************************************************
 * This class Responsible for Survey processing
 * @author Ranavir
 * @date 05/11/2016
 *
 **************************************************************/
public class SurveyProcessor {
	String methodname = "" ;
	String userId = "";
	
	private static Logger logger = null;
	private static SurveyProcessor singletonSurveyProcessor = null;
	private static SurveyProcessService processorService = null ;
	
	int iCurrentGid ;//This integer will store the survey current group id
	int iCurrentQid ;//This integer will store the survey current question id
	int iCurrentQOrder ;//This integer will store the current question order
	int iTotalQuestCount ; //This integer will store the total question count (max quest order)
	int lastpage ;//This integer will store the last question completed
	String submitdate ;//This will save the last answered timestamp
	
	List<LimeQuestionsModel> listCurrentGroupMainQuests ;//Stores the Main question lists in current Question Group
	
	LimeSurveyLanguagesettings surveyLanguageSettings ;//From Database
	List<LimeGroupsModel> listLimeGrpModels ;//From Database
	List<LimeQuestionsModel> listLimeQuestModels ;//From Database
	List<LimeQuestionAttributesModel> listLimeQuestAttrModels ;//From Database
	List<LimeAnswersModel> listLimeAnsModels ;//From Database
	List<LimeConditionsModel> listLimeCondModels ;//From Database
	HashMap<String,Object> hmSurveyDetails ;
	HashMap<String,String> hmSurveyAvailableAnswer ;//Store the Available Answer for the Survey
	HashMap<String,String> hmSurveyUpdatableAnswer ;//Store the Current Response by the user
	
	
	ArrayList<String> alResponseTblColList ;//From Database
	ArrayList<String> alResponseTblAllColList ;//From Database
	ArrayList<String> alResponseTimingTblColList ;//From Database
	
	public static SurveyProcessor getInstance(){
		if(singletonSurveyProcessor == null){
			singletonSurveyProcessor = new SurveyProcessor();
			logger.info("SurveyProcessor Instantiated...");
		}
		return singletonSurveyProcessor;
	}
	
	private SurveyProcessor() {
		logger = Logger.getLogger(SurveyProcessor.class) ;
		processorService =  SurveyProcessServiceImpl.getInstance();
	}
	
	public static void main(String[] args) {
		getInstance();

	}
	/**************************************************            Business Logic Starts            *********************************************/
	/************************************************************************************************
	 * This method used to start a survey by selecting one available 
	 * survey for the user
	 * @author Ranavir
	 * @date 05/11/2016
	 ***********************************************************************************************/
	@SuppressWarnings("unchecked")
	public void startSurvey(PanelSurveyVO survey, String user_id) {
		String methodname = "startSurvey" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		iCurrentGid = -1 ;//This integer will store the survey current group id
		iCurrentQid = -1 ;//This integer will store the survey current question id
		iCurrentQOrder = -1 ;//This integer will store the survey current question order
		lastpage = 0 ;
		listCurrentGroupMainQuests = new ArrayList<LimeQuestionsModel>();//Stores the Main question lists in current Question Group
		
		surveyLanguageSettings = null ;//From Database
		listLimeGrpModels = new ArrayList<LimeGroupsModel>();//From Database
		listLimeQuestModels = new ArrayList<LimeQuestionsModel>();//From Database
		listLimeQuestAttrModels = new ArrayList<LimeQuestionAttributesModel>();//From Database
		listLimeAnsModels = new ArrayList<LimeAnswersModel>();//From Database
		listLimeCondModels = new ArrayList<LimeConditionsModel>();//From Database
		hmSurveyDetails = null;
		hmSurveyAvailableAnswer = null;//Store the Available Answer for the Survey
		hmSurveyUpdatableAnswer = null;//Store the Current Response by the user
		
		
		alResponseTblColList = new ArrayList<String>();//From Database
		alResponseTblAllColList = new ArrayList<String>();//From Database
		alResponseTimingTblColList = new ArrayList<String>();//From Database
		
		HashMap<String,Object> rqHm = new HashMap<String, Object>();
		rqHm.put("user_id",user_id);
		
		
		//Store response in this JSONObject whose key fields are to be obtained from the dynamic table lime_survey_(sid)
		//String strRespTimingTblName = "lime_survey_"+survey.getSurveyid()+"_timings" ;
		
		
		
		//Get all Survey Related data for the survey id to do the process
		hmSurveyDetails = processorService.getSurveyDetails(survey.getSurveyid(),rqHm);
		surveyLanguageSettings = (LimeSurveyLanguagesettings)hmSurveyDetails.get("surveyLanguageSettings");
		listLimeGrpModels = (List<LimeGroupsModel>)hmSurveyDetails.get("listLimeGrpModels");
		listLimeQuestModels = (List<LimeQuestionsModel>)hmSurveyDetails.get("listLimeQuestModels");
		listLimeQuestAttrModels = (List<LimeQuestionAttributesModel>)hmSurveyDetails.get("listLimeQuestAttrModels");
		listLimeAnsModels = (List<LimeAnswersModel>)hmSurveyDetails.get("listLimeAnsModels");
		listLimeCondModels = (List<LimeConditionsModel>)hmSurveyDetails.get("listLimeCondModels");
		//Get total no of questions in this survey and display to user b4 start of survey
		iTotalQuestCount = MyGenericUtils.filterList(listLimeQuestModels, new Predicate<LimeQuestionsModel>() {
			public boolean test(LimeQuestionsModel qModel){
				if(qModel.getParent_qid() == 0){
					return true;
				}else{
					return false;
				}
			}
		}).size() ;
		
		
		logger.debug("\nlistLimeGrpModels :: "+listLimeGrpModels);
		logger.debug("\nlisLimeQuestModels :: "+listLimeQuestModels);
		logger.debug("\nlimeQuestAttrModels :: "+listLimeQuestAttrModels);
		logger.debug("\nlistLimeAnsModels :: "+listLimeAnsModels);
		logger.debug("\nlistLimeCondModels :: "+listLimeCondModels);
		
		//Get the Dynamic response table column list
		alResponseTblAllColList = processorService.getColumnListByTableName("lime_survey_"+survey.getSurveyid(),"");//pattern you can give as sid(survey.getSurveyid()) to get dynamic columns
		alResponseTblColList = processorService.getColumnListByTableName("lime_survey_"+survey.getSurveyid(),survey.getSurveyid()+"");//pattern you can give as sid(survey.getSurveyid()) to get dynamic columns
		logger.debug("\nalResponseTblColList :: "+alResponseTblColList);
		
		//get response of that survey from dynamic table
		hmSurveyAvailableAnswer = processorService.getSurveyResponse(survey.getSurveyid(),user_id,alResponseTblAllColList,rqHm);
		//Initialize user answer map to new one for begin
		hmSurveyUpdatableAnswer = new HashMap<String, String>() ;
				
		//show Welcome page if checked
		if(StringUtils.equalsIgnoreCase(survey.getShowwelcome(), "Y")){
			showWelcomePage(survey);
		}
		/**********************************************************************************/
		//Check format of survey A(All questions)/G(Group by Group)/S(Single question)
		//only Single question format is supported in console
		/**********************************************************************************/
		if(!hmSurveyAvailableAnswer.isEmpty()){
			lastpage = (!StringUtils.isEmpty(hmSurveyAvailableAnswer.get("lastpage")) ? Integer.parseInt(hmSurveyAvailableAnswer.get("lastpage")): 0);
		}
		//Check allowsave flag Y in survey details and Some response already received
		if(StringUtils.equalsIgnoreCase(survey.getAllowsave(), "Y") && lastpage != 0){
			//Resume survey
						
			//Get the group id and question id to start
			
		}else{//Check allowsave flag N
			//Start fresh survey
			
			
			//Get the group id and question id to start
			for(LimeGroupsModel lgm : listLimeGrpModels){
				iCurrentGid = lgm.getGid();
				logger.debug("Current GROUP ID :: "+iCurrentGid);
				listCurrentGroupMainQuests = MyGenericUtils.filterList(listLimeQuestModels, new Predicate<LimeQuestionsModel>() {
					public boolean test(LimeQuestionsModel qModel){
						if(qModel.getGid() == iCurrentGid && qModel.getParent_qid() == 0){
							return true;
						}else{
							return false;
						}
					}
				});
				//listCurrentGroupQuests = MyGenericUtils.filterList(listLimeQuestModels, new GroupQuestionPredicate(iCurrentGid));
				
				//Find maximum orderid for the questions where pqid = 0
				int iGMaxQuestOrdId = 0 ;
				for(LimeQuestionsModel qModel : listCurrentGroupMainQuests){
					if(qModel.getParent_qid() == 0 && qModel.getQuestion_order() > iGMaxQuestOrdId){
						iGMaxQuestOrdId = qModel.getQuestion_order();
					}
				}//end for getting max question in a group by order id in a group
				
				//Check GroupRandomization flag and Shuffle the Group questions
				if(StringUtils.equalsIgnoreCase(lgm.getRandomization_group(),"Y")){//No condition check 
					Collections.shuffle(listCurrentGroupMainQuests);//Shuffle the main question list and select sequentially
					//Select the random question from the list
					for(LimeQuestionsModel currentQuestModel : listCurrentGroupMainQuests){
						processQuest(survey,currentQuestModel);
					}
				}else{//No Randomization just do according to rule (Conditions can be checked)
					//Select the question by incrementing order id 
					for(int i = 0 ; i <= iGMaxQuestOrdId ; i++){
						iCurrentQOrder = i ;//Save current Question Order 
						LimeQuestionsModel currentQuestModel = MyGenericUtils.filterUnique(listCurrentGroupMainQuests,new Predicate<LimeQuestionsModel>() {
							public boolean test(LimeQuestionsModel qModel){
								if(qModel.getQuestion_order() == iCurrentQOrder){
									return true;
								}else{
									return false;
								}
							}
						});
						if(null == currentQuestModel){//Check question existence with iCurrentQOrder question order
							continue ;//Check for next question order
						}
						//Save current Qid
						iCurrentQid = currentQuestModel.getQid();
						
						logger.debug("Current Question id = "+iCurrentQid);
						//Get condition list for the current question
						List<LimeConditionsModel> listCurrentQuestConds = MyGenericUtils.filterList(listLimeCondModels,new QuestionConditionPredicate(currentQuestModel.getQid()));
						
						if(!listCurrentQuestConds.isEmpty()){
							logger.debug("\nCurrent Question listCurrentQuestConds :: "+listCurrentQuestConds);
							//Check conditions
							boolean condFlag = validateQuestConditions(currentQuestModel,listCurrentQuestConds);//Need to be implemented
							if(!condFlag){//Condition not satisfied so skip this question
								continue;
							}
						}else{
							//increase last page by 1 due to no condition
							lastpage++;
						}
						//Process question if no conditions available OR condition satisfied for this question
						processQuest(survey,currentQuestModel);//Need to be implemented
					}//end for selecting a question
				}//end if Randomization check
				
				
			}//end for Group iteration
			
			
			
		}//end if Check allowsave flag N
		
		
		
		
		logger.info("EXIT---> methodname : "+methodname);
	}//end of startSurvey
	/*********************************************************************
	 * This method takes Question and its Condition list and validates 
	 * the question based on it 
	 * @param currentQuestModel
	 * @param listCurrentQuestConds
	 * @return boolean
	 **********************************************************************/
	private boolean validateQuestConditions(LimeQuestionsModel currentQuestModel,List<LimeConditionsModel> listCurrentQuestConds) {
		String methodname = "validateQuestConditions" ;
		logger.info("ENTRY---> methodname : "+methodname);
		boolean flag = false ;
		//Check condition according to already given answers
		//Evaluate the available conditions
		
		
		logger.info("EXIT---> methodname : "+methodname);
		return flag;
	}//end of validateQuestConditions

	/**************************************************************************************************
	 * This method takes a question and Display it to user, Record answer and save answer to database
	 * @param survey
	 * @param currentQuestModel
	 **************************************************************************************************/
	private void processQuest(PanelSurveyVO survey,LimeQuestionsModel currentQuestModel) {
		String methodname = "processQuest" ;
		logger.info("ENTRY---> methodname : "+methodname);
		if(currentQuestModel != null){
			logger.debug("Current Question::"+currentQuestModel);
			//help field of currentQuestModel function not clear
			//scale_id of currentQuestModel function not clear
			//relevance usage not clear
			
			//Display Question
			promptQuestion(survey,currentQuestModel);
			
			//Record Response
			recordResponse(survey,currentQuestModel);
			
			//Update time stamp in current response
			if(alResponseTblAllColList.contains("datestamp")){
				hmSurveyUpdatableAnswer.put("datestamp",Utils.getDate("yyyy-MM-dd HH:mm:ss"));
			}
			//Update last page to current question order
			if(alResponseTblAllColList.contains("lastpage"))
				hmSurveyUpdatableAnswer.put("lastpage",lastpage+"");//lastpage
			//Check if last question answered
			if(lastpage == iTotalQuestCount)
				hmSurveyUpdatableAnswer.put("submitdate",Utils.getDate("yyyy-MM-dd HH:mm:ss"));
			
			//Save Response
			saveResponse(survey.getSurveyid()+"",survey.getUserid(),hmSurveyUpdatableAnswer,alResponseTblColList);
			
			//If Survey answer completed then update status of user
			if(lastpage == iTotalQuestCount){
				//Update Survey Status to complete
				boolean flag = processorService.updateSurveyStatus(survey.getSurveyid(),survey.getUserid(),"complete");
				logger.debug("updateSurveyStatus--->"+flag);
				System.out.println("\n"+survey.getSurveyls_endtext()+"\n");
			}
			
			
		}
		logger.info("EXIT---> methodname : "+methodname);
	}//end processQuest
	

	/***********************************************************************
	 * This method used for showing welcome Screen to user for a survey
	 * @param survey
	 **********************************************************************/
	private void showWelcomePage(PanelSurveyVO survey) {
		String methodname = "showWelcomePage" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		System.out.println("\n\n"+surveyLanguageSettings.getSurveyls_title()+"\n");
		System.out.println("\n\n"+surveyLanguageSettings.getSurveyls_welcometext());
		if(StringUtils.equalsIgnoreCase(survey.getShowxquestions(),"Y")){
			
			System.out.println("There are "+iTotalQuestCount+" questions in this survey."+"\n");
		}
		Keyin.inChar("\t\t\t Type ENTER to Start...");
		
		if(hmSurveyAvailableAnswer.isEmpty()){//This is first time survey response started
			HashMap<String,String> hmInitResponse = new HashMap<String, String>();
			if(alResponseTblAllColList.contains("userid"))
				hmInitResponse.put("userid",survey.getUserid());
			if(alResponseTblAllColList.contains("startdate"))
				hmInitResponse.put("startdate",Utils.getDate("yyyy-MM-dd HH:mm:ss"));
			if(alResponseTblAllColList.contains("startlanguage"))
				hmInitResponse.put("startlanguage",survey.getLanguage());
			if(alResponseTblAllColList.contains("datestamp"))
				hmInitResponse.put("datestamp",Utils.getDate("yyyy-MM-dd HH:mm:ss"));
		    
			//Save Response
			saveResponse(survey.getSurveyid()+"",survey.getUserid(),hmInitResponse,null);
			//Update Survey Status to incomplete
			boolean flag = processorService.updateSurveyStatus(survey.getSurveyid(),survey.getUserid(),"incomplete");
			logger.debug("updateSurveyStatus--->"+flag);
		}
		logger.info("EXIT---> methodname : "+methodname);
	}//end showWelcomePage

	

	/***********************************************************************
	 * This method used to prompt a question to the end user considering
	 * all the related facts and dimensions
	 * @param survey
	 * @param currentQuestModel
	 **********************************************************************/
	private void promptQuestion(PanelSurveyVO survey,LimeQuestionsModel currentQuestion) {
		String methodname = "promptQuestion" ;
		logger.info("ENTRY---> methodname : "+methodname);
		System.out.println("\n\n"+surveyLanguageSettings.getSurveyls_title()+"\n");
		
		//surveyid known,group id known, qid known
		
		//print qnum or qcode
		if(StringUtils.equalsIgnoreCase(survey.getShowqnumcode(),"B")){//char X(hidden both)/B(Show both)/N(question number only)/C(question code only)
			System.out.print(currentQuestion.getQuestion_order()+" "+currentQuestion.getTitle()+" ");
		}else if(StringUtils.equalsIgnoreCase(survey.getShowqnumcode(),"N")){
			System.out.print(currentQuestion.getQuestion_order()+" ");
		}else if(StringUtils.equalsIgnoreCase(survey.getShowqnumcode(),"C")){
			System.out.print(currentQuestion.getTitle()+" ");
		}//else nothing
		
		//print question
		System.out.println(currentQuestion.getQuestion()+"\n");
		
		char chQuestType = StringUtils.isEmpty(currentQuestion.getType()) ? ' ' : currentQuestion.getType().trim().charAt(0) ;
		boolean isOther = (StringUtils.isEmpty(currentQuestion.getOther()) || StringUtils.equalsIgnoreCase(currentQuestion.getOther(), "N")) ? false : true ;
		
		if(chQuestType == 'S'){//Short Free Text
			
			//Text box UI needed
			System.out.println("Short Text Entry Type");
		}//end if S
		if(chQuestType == 'T'){//Long Free Text
			
			//Text box UI needed	
			System.out.println("Long Text Entry Type");
		}//end if T
		if(chQuestType == 'D'){//Date/Time
			//Calendar UI needed
			System.out.println("Date/Time Entry Type");
	
		}//end if D
		if(chQuestType == 'N'){//Numeric Input
			//Numeric text UI needed
			System.out.println("Numeric Entry Type");
			
		}//end if N
		if(chQuestType == 'G'){//Gender
			//Gender UI needed
			//check lime_question_attributes for this qid to get the attribute display_type = 1(Radio group) / 2(Buttons)
			LimeQuestionAttributesModel lqam = MyGenericUtils.filterUnique(listLimeQuestAttrModels, new Predicate<LimeQuestionAttributesModel>() {

				public boolean test(LimeQuestionAttributesModel obj) {
					if(obj.getQid() == iCurrentQid){
						return true;
					}
					return false;
				}
				
			});
			if((!StringUtils.isEmpty(lqam.getAttribute())) && StringUtils.equalsIgnoreCase(lqam.getAttribute(),"display_type") && StringUtils.equalsIgnoreCase(lqam.getValue(),"1")){
				System.out.println("Gender Select Radio Type\n");
			}else{
				System.out.println("Gender Select Button Type\n");
			}
			System.out.println("Male/Female");
			
		}//end if G
		if(chQuestType == 'Y'){//Yes/No
			//Yes/No  UI needed
			System.out.println("Yes/No Button Type");
			
		}//end if Y
		if(chQuestType == 'L'){//List Radio
			//Radio List  UI needed
			System.out.println("Radio List Choose Type");
			showQuestionRadioListType(currentQuestion.getQid(),'L',isOther);
			
		}//end if L
		if(chQuestType == '!'){//List Radio
			//Radio List  UI needed
			System.out.println("Radio List Dropdown Choose Type");
			showQuestionRadioListType(currentQuestion.getQid(),'!',isOther);
			
		}//end if !
		if(chQuestType == 'M'){//Multiple Choice
			//Multiple Choice Check box  UI needed
			System.out.println("Multiple Choice Type");
			showQuestionMultiChoiceType(currentQuestion.getQid(),'M',isOther);
			
		}//end if M
		if(chQuestType == 'Q'){//Multiple Short Text
			//Multiple Short Text UI needed
			System.out.println("Multiple Short Text Type");
			showQuestionMultiShortTextType(currentQuestion.getQid());
			
		}//end if Q
		if(chQuestType == 'R'){//Multiple Short Text
			//Ranking Type UI needed
			System.out.println("Ranking Type");
			showQuestionRankingType(currentQuestion.getQid());
			
		}//end if R
		logger.info("EXIT---> methodname : "+methodname);
	}//end promptQuestion
	

	

	

	

	/***************************************************************
	 * This method used to record the Response from the user 
	 * taking question type into consideration to the Response HM
	 * @param survey
	 * @param currentQuestModel
	 **************************************************************/
	private void recordResponse(PanelSurveyVO survey,LimeQuestionsModel currentQuestion) {
		String methodname = "recordResponse" ;
		logger.info("ENTRY---> methodname : "+methodname);
		//surveyid known,group id known, qid known
		
		char chQuestType = StringUtils.isEmpty(currentQuestion.getType()) ? ' ' : currentQuestion.getType().trim().charAt(0) ;
		boolean isOther = (StringUtils.isEmpty(currentQuestion.getOther()) || StringUtils.equalsIgnoreCase(currentQuestion.getOther(), "N")) ? false : true ;
		boolean isMandatory = (StringUtils.isEmpty(currentQuestion.getMandatory()) || StringUtils.equalsIgnoreCase(currentQuestion.getMandatory(), "N")) ? false : true ;
		boolean isOtherAnswered = false ;//Init other answered to false
		String txtResp = "" ;
		logger.debug("chQuestType--->"+chQuestType);
		logger.debug("isOther--->"+isOther);
		logger.debug("isMandatory--->"+isMandatory);
		
		do{//Check mandatory field for Blank response error handling
			if(isMandatory){
				System.out.println("* Question is compulsory");
			}
			
			if(chQuestType == 'S'){
				//Get input text by user
				txtResp = Keyin.inString() ;
				//test preg col value if given
				
			}//end if S
			if(chQuestType == 'T'){
				//Get input text by user
				txtResp = Keyin.inString() ;
				//test preg col value if given
			}//end if T
			if(chQuestType == 'D'){
				//Get Input date from user
				do{
					System.out.println("Valid Date Format ("+Utils.getGlobalDateFormat(survey.getSurveyls_dateformat())+")");//surveyls_dateformat;//will store 1-12 (ex- 2(dd-mm-yyyy) & 9(mm-dd-yyyy))
					txtResp = Keyin.inString() ;
				}while(!Utils.validateDateFormat(txtResp,survey.getSurveyls_dateformat()));
				
			}//end if D
			if(chQuestType == 'N'){
				//Get Input number string from user
				do{
					System.out.println("* Only numbers may be entered in this field.");//surveyls_dateformat;//will store 1-12 (ex- 2(dd-mm-yyyy) & 9(mm-dd-yyyy))
					txtResp = Keyin.inString() ;
				}while(!txtResp.matches("[0-9]*"));
			}//end if N
			if(chQuestType == 'G'){
				//Get Input value from user
				do{
					txtResp = Keyin.inString() ;//display No answer  if isMandatory = false
					//convert it to M/F
					txtResp = txtResp.toUpperCase();
					txtResp = txtResp.matches("(M|MALE)") ? "M" :  txtResp ;
					txtResp = txtResp.matches("(F|FEMALE)") ? "F" :  txtResp ;
				}while(!txtResp.matches("(M|F)"));
			}//end if G
			if(chQuestType == 'Y'){
				//Get Input value from YES/NO Button
				do{
					txtResp = Keyin.inString() ;
					//convert it to Y/N
					txtResp = txtResp.toUpperCase();
					txtResp = txtResp.matches("(YES|Y)") ? "Y" :  txtResp ;
					txtResp = txtResp.matches("(NO|N)") ? "N" :  txtResp ;
				}while(!txtResp.matches("(Y|N)"));
				
			}//end if Y
			if(chQuestType == 'L' || chQuestType == '!'){//List Radio OR List Radio Dropdown
				//Get the option code as user input
				List<LimeAnswersModel> alOptions = MyGenericUtils.filterList(listLimeAnsModels, new Predicate<LimeAnswersModel>() {

					public boolean test(LimeAnswersModel answer) {
						if(answer.getQid() == iCurrentQid){
							return true ;
						}
						return false ;
					}
					
				});
				//logger.debug("alOptions ::"+alOptions);
				//String []aCodes = new String[alOptions.size()] ;
				ArrayList<String> alCodes = new ArrayList<String>();
				//Get all codes
				for(int i = 0 ; i < alOptions.size() ; i++){
					//aCodes[i] = alOptions.get(i).getCode() ;
					alCodes.add(alOptions.get(i).getCode());
				}
				
				//initialize other if present
				if(isOther){
					hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid()+"other", "");//111238X3X12other column name
				}
				//Initialize column data to blank
				hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid(), "");//111238X3X12 column name
				
				boolean bInValid = true ;
				do{
					//Get user input value as the code of the answer
					txtResp = Keyin.inString("Please select the option :") ;//allows skip if isMandatory = false
					if(txtResp.matches("(O|o|other|Other|OTHER)")){
						txtResp = Keyin.inString("Enter your option Data");
						bInValid = false ;
						isOtherAnswered = true ;
					}else if(alCodes.contains(txtResp)){
							bInValid = false ;
					}else{
						System.out.println("\nInvalid input found!!!");
					}
				}while(bInValid);
				
			}//end if L/!
			if(chQuestType == 'M'){//Multiple Choice
				//Get the option code as user input
				List<LimeQuestionsModel> alSubQuests = MyGenericUtils.filterList(listLimeQuestModels, new Predicate<LimeQuestionsModel>() {

					public boolean test(LimeQuestionsModel subQuestion) {
						if(subQuestion.getParent_qid() == iCurrentQid){
							return true ;
						}
						return false ;
					}
					
				});
				//String []aCodes = new String[alSubQuests.size()] ;
				ArrayList<String> alCodes = new ArrayList<String>();
				//Get all codes/titles
				for(int i = 0 ; i < alSubQuests.size() ; i++){
					//aCodes[i] = alSubQuests.get(i).getTitle() ;
					alCodes.add(alSubQuests.get(i).getTitle());
					//Initialize each column data to blank string
					hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid()+alSubQuests.get(i).getTitle(), "");//111238X3X12(SQ1) column name
				}
				//initialize other if present
				if(isOther){
					hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid()+"other", "");//111238X3X12other column name
				}
				boolean bInValid = true ;
				do{
					//Get user input value as the subquestion title value
					txtResp = Keyin.inString("Please choose the options using ',' :") ;//allows skip if isMandatory = false
					StringTokenizer stnz = new StringTokenizer(txtResp,",");
					String token = "";
					String myOption = "" ;
					while(stnz.hasMoreTokens()){
						token = stnz.nextToken();
						if(token.matches("(other|Other|OTHER)")){//The sub question title must not be any of these key words
							isOtherAnswered = true ;
							myOption = Keyin.inString("Enter your option Data");
							hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid()+"other", myOption);//111238X3X12other column name
							bInValid = false ;//at least other option chosen
						}else if(alCodes.contains(token)){
							bInValid = false ;
							//save the selected data
							hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid()+token, "Y");//111238X3X12SQ1 column name
							//Remove this element to validate duplicate entry
							alCodes.remove(token);
						}else{
							System.out.println("\nInvalid input found!!!");
						}
						
						
					}
					
					
				}while(bInValid);
				
			}//end if M
			if(chQuestType == 'Q'){//Multiple Short Text
				//Get the number of inputs as per required no of values
				List<LimeQuestionsModel> alSubQuests = MyGenericUtils.filterList(listLimeQuestModels, new Predicate<LimeQuestionsModel>() {

					public boolean test(LimeQuestionsModel subQuestion) {
						if(subQuestion.getParent_qid() == iCurrentQid){
							return true ;
						}
						return false ;
					}
					
				});
				String subQuestCode = "" ;
				
				for(int i = 0 ; i < alSubQuests.size() ; i++){
					subQuestCode = alSubQuests.get(i).getTitle();
					do{
						//Get user input value as text data
						txtResp = Keyin.inString("Enter value for "+alSubQuests.get(i).getQuestion()+" : ") ;//allows skip if isMandatory = false
					}while(txtResp.isEmpty());
					hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid()+subQuestCode, txtResp);//111238X3X12(SQ4) column name
				}
				
			}//end if Q
			if(chQuestType == 'R'){//Multiple Short Text
				//Get the option code as user input
				
				List<LimeAnswersModel> alOptions = MyGenericUtils.filterList(listLimeAnsModels, new Predicate<LimeAnswersModel>() {

					public boolean test(LimeAnswersModel answer) {
						//System.out.println(answer.getQid()+" "+iCurrentQid);
						if(answer.getQid() == iCurrentQid){
							return true ;
						}
						return false ;
					}
					
				});
				//logger.debug("alOptions ::"+alOptions);
				//String []aCodes = new String[alOptions.size()] ;
				ArrayList<String> alCodes = new ArrayList<String>();
				//Get all codes
				for(int i = 0 ; i < alOptions.size() ; i++){
					//aCodes[i] = alOptions.get(i).getCode() ;
					alCodes.add(alOptions.get(i).getCode());
				}
				
				boolean bInValid = true ;
				System.out.println("Please Rank the options with their codes :");
				for(int i = 0 ; i < alOptions.size() ; i++){
					do{
						bInValid = true ;
						//Get user input value as the code of the answer
						txtResp = Keyin.inString() ;//allows skip if isMandatory = false
						if(alCodes.contains(txtResp)){
							bInValid = false ;
							hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid()+(i+1), txtResp);//111238X3X12(1/2/3...n) column name
							//Remove this element to validate duplicate entry
							alCodes.remove(txtResp);
						}else{
							System.out.println("Invalid input !!!");
						}
						
					}while(bInValid);
				}
				
				
			}//end if R
		}while(isMandatory && txtResp.isEmpty());//end mandatory Response entry check if mandatory check enabled for the question
		//put in Response Map
		if(currentQuestion.getType().matches("[STDNYG]")){
			hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid(), txtResp);//111238X3X12 column name
		}
		if(currentQuestion.getType().matches("[L!]")){//List Radio OR List Radio Dropdown
			if(isOtherAnswered){
				hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid()+"other", txtResp);//111238X3X12other column name
			}else{
				hmSurveyUpdatableAnswer.put(survey.getSurveyid()+"X"+iCurrentGid+"X"+currentQuestion.getQid(), txtResp);//111238X3X12 column name
			}
		}
		
		
		logger.info("EXIT---> methodname : "+methodname);
	}//end recordResponse
	/********************************************************************************
	 * 
	 * @param surveyid
	 * @param userid
	 * @param hmAnswers
	 * @param alColumns
	 *******************************************************************************/
	private void saveResponse(String surveyid,String userid,HashMap<String, String> hmAnswers, ArrayList<String> alColumns) {
		String methodname = "saveResponse" ;
		logger.info("ENTRY---> methodname : "+methodname);
		if(!hmAnswers.isEmpty()){
			//Print data set
			logger.debug("*********************To be Updated in Database*****************************");
			Iterator it = hmAnswers.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        logger.debug(pair.getKey() + " = " + pair.getValue());
		    }
		    logger.debug("***************************************************************************");
		    //Save into database
		    boolean flag = processorService.saveResponse(surveyid,userid,hmAnswers,alColumns);
		    if(flag){
		    	logger.info("User Response updated sucessfully...");
		    }else{
		    	logger.info("User Response update failed !!!");
		    }
		}else{
			logger.info("No data available in Response !!!");
		}
		
	    
		logger.info("EXIT---> methodname : "+methodname);
		
	}//end saveResponse
	/******************************************************************************
	 * This method is for showing a Radio List type question to user taking type
	 * of Radio(List L/Dropdown !), Available Answers and Other option availability 
	 * into consideration
	 * 
	 * @param qid
	 * @param chType
	 * @param isOther
	 *******************************************************************************/
	private void showQuestionRadioListType(final int qid, char chType, boolean isOther) {
		String methodname = "showQuestionRadioListType" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		List<LimeAnswersModel> alOptions = MyGenericUtils.filterList(listLimeAnsModels, new Predicate<LimeAnswersModel>() {

			public boolean test(LimeAnswersModel answer) {
				if(answer.getQid() == qid){
					return  true ;
				}
				return false ;
			}
			
		});
		
		//Show options
		System.out.println();
		for(LimeAnswersModel lam : alOptions){
			System.out.println("( " + lam.getCode() + " )\t" + lam.getAnswer());
		}
		if(isOther){
			System.out.println("( Other )\t" );
		}
		logger.info("EXIT---> methodname : "+methodname);
	}//end of showQuestionRadioListType
	/********************************************************************************
	 * This method is for showing Multi Choice type questions to user taking the
	 * type of Multi Choice (MultiChoice M/MultiChoice with comment P), The sub
	 * questions(options) and other option allowed into consideration
	 * 
	 * @param qid
	 * @param chType
	 * @param isOther
	 *******************************************************************************/
	private void showQuestionMultiChoiceType(final int qid, char chType, boolean isOther) {
		String methodname = "showQuestionMultiChoiceType" ;
		logger.info("ENTRY---> methodname : "+methodname);
		//Get the list of questions whose parent Question id is current Question id
		List<LimeQuestionsModel> alSubQuests = MyGenericUtils.filterList(listLimeQuestModels, new Predicate<LimeQuestionsModel>() {

			public boolean test(LimeQuestionsModel subQuestion) {
				if(subQuestion.getParent_qid() == qid){
					return true ;
				}
				return false ;
			}
			
		});
		
		//Show options
		System.out.println();
		for(LimeQuestionsModel lqm : alSubQuests){
			System.out.println("( " + lqm.getTitle() + " )\t" + lqm.getQuestion());
		}
		if(isOther){
			System.out.println("( Other )\t" );
		}
		logger.info("EXIT---> methodname : "+methodname);
	}//end of showQuestionMultiChoiceType
	/*********************************************************************************
	 * This method is for showing Multiple Short Text type questions to user taking
	 * the Sub questions as the prompted keys to enter value against the same
	 * 
	 * @param qid
	 ********************************************************************************/
	private void showQuestionMultiShortTextType(final int qid) {
		String methodname = "showQuestionMultiShortTextType" ;
		logger.info("ENTRY---> methodname : "+methodname);
		//Get the list of questions whose parent Question id is current Question id
		List<LimeQuestionsModel> alSubQuests = MyGenericUtils.filterList(listLimeQuestModels, new Predicate<LimeQuestionsModel>() {

			public boolean test(LimeQuestionsModel subQuestion) {
				if(subQuestion.getParent_qid() == qid){
					return true ;
				}
				return false ;
			}
			
		});
		
		//Show options
		System.out.println();
		for(LimeQuestionsModel lqm : alSubQuests){
			System.out.println("\t" + lqm.getQuestion());
		}
		
		logger.info("EXIT---> methodname : "+methodname);
	}//end of showQuestionMultiShortTextType
	/**********************************************************************************
	 * This method displays the Ranking type question taking the option code and
	 * answer values from lime_answer table by qid
	 * 
	 * @param qid
	 *********************************************************************************/
	private void showQuestionRankingType(final int qid) {
		String methodname = "showQuestionRankingType" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		List<LimeAnswersModel> alOptions = MyGenericUtils.filterList(listLimeAnsModels, new Predicate<LimeAnswersModel>() {

			public boolean test(LimeAnswersModel answer) {
				if(answer.getQid() == qid){
					return  true ;
				}
				return false ;
			}
			
		});
		
		//Show options
		System.out.println();
		for(LimeAnswersModel lam : alOptions){
			System.out.println("( " + lam.getCode() + " )\t" + lam.getAnswer());
		}
		
		logger.info("EXIT---> methodname : "+methodname);
	}//end of showQuestionRankingType
	
	/**************************************************            Business Logic Ends              *********************************************/
}
