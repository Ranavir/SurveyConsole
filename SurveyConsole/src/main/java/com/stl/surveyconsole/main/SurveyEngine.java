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
 *    1.0         Ranavir               02-November-2016          	Initial Version		    *
 *****************************************************************************************
 */
package com.stl.surveyconsole.main;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.stl.surveyconsole.helper.SurveyPanelStatusPredicate;
import com.stl.surveyconsole.model.UserModel;
import com.stl.surveyconsole.service.AuthService;
import com.stl.surveyconsole.service.PanelSurveyMappingService;
import com.stl.surveyconsole.service.SurveyProcessService;
import com.stl.surveyconsole.serviceImpl.AuthServiceImpl;
import com.stl.surveyconsole.serviceImpl.PanelSurveyMappingServiceImpl;
import com.stl.surveyconsole.serviceImpl.SurveyProcessServiceImpl;
import com.stl.surveyconsole.utils.Keyin;
import com.stl.surveyconsole.vo.PanelSurveyVO;



/**************************************************************
 * This is the main class which controls the entire flow
 * of the system using helper classes for Survey
 * @author Ranavir
 * @date 02/11/2016
 *
 **************************************************************/
public class SurveyEngine {
	/*
	 * Declare and initialize  required variables
	 */
	String methodname = "" ;
	String userId = "";
	
	private static Logger logger = null;
	private static SurveyEngine singletonSurveyEngine = null;
	private static AuthService authService = null ;
	private static PanelSurveyMappingService psmService = null ;
	private static SurveyProcessor sp = null ;
	public static UserModel logged_user = null ;
	
	
	
	public static SurveyEngine getInstance(){
		if(singletonSurveyEngine == null){
			singletonSurveyEngine = new SurveyEngine();
			logger.info("SurveyEngine Instantiated...");
		}
		return singletonSurveyEngine;
	}
	
	private SurveyEngine() {
		this.init();
	}
	/**
	 * Initialize required utilities
	 * @param none
	 * @return void
	 */
	private void init() {
		try {
			
			
			File f = new File("D:\\SurveyEngineLog");
			if(!f.exists()){
				System.out.println("Logger path doesn't exist");
				System.out.println("Creating Logger folder...");
				f.mkdir() ;
			}
			logger = Logger.getLogger(SurveyEngine.class) ;
			authService = AuthServiceImpl.getInstance();
			psmService = PanelSurveyMappingServiceImpl.getInstance();
			sp = SurveyProcessor.getInstance();
			} catch (Exception ge) {
		    
		}
	}
	
	/**************************************************            Business Logic Starts            *********************************************/
	/********************************************************************************************************************************************
	 		
	 															SURVEY ENGINE STARTING POINT
	 
	 *******************************************************************************************************************************************/
	public static void main(String[] args) {
		// Local variable
	    int swValue ;
	    String id = "";
	    String password = "";
	    String type = "email";//email or uid
	    
	    /*Instantiate Survey Engine for first time*/
		SurveyEngine.getInstance() ;
		/*Make authentication before proceed*/
		while(true){
			System.out.print("User ID/Email ID : ");
			String inputStr = Keyin.inString();
			if(inputStr.matches("[a-zA-Z0-9.]*@gmail.com$")){
				id = inputStr ;
				type = "email" ;
				logger.info("Email id-"+id);
			}else{
				id = inputStr ;
				type = "uid" ;
				logger.info("User id-"+id);
			}
			System.out.print("Password : ");
			password = Keyin.inString();
			if(authService.authenticate(id, password, type)){
				logged_user = authService.getAuthUser(id, password, type);
				logger.debug(logged_user);
				while(true){
					// Display menu graphics
				    System.out.println("=======================================================");
				    System.out.println("|                  SURVEY ENGINE MENU                 |");
				    System.out.println("=======================================================");
				    System.out.println("| Options:                                            |");
				    System.out.println("|        0. Initialize Survey Participation(If Admin) |");
				    System.out.println("|        1. Get available surveys                     |");
				    System.out.println("|        2. Get completed surveys                     |");
				    System.out.println("|        3. Get in completed surveys                  |");
				    System.out.println("|        4. Start doing a survey                      |");
				    System.out.println("|        5. Logout and Exit                           |");
				    System.out.println("=======================================================");
				    swValue = Keyin.inInt(" Select option: ");
			
				    // Switch construct
				    switch (swValue) {
				    case 0:
						      logger.info("Selected Survey Initialization");
						      initSurveyParticipation();
						      break;
				    case 1:
						      logger.info("Selected Get available surveys");
						      showAvailableSurveys();
						      break;
				    case 2:
						      logger.info("Selected Get completed surveys");
						      showCompletedSurveys();
						      break;
				    case 3:
						      logger.info("Selected Get Incompleted surveys");
						      showIncompletedSurveys();
						      break;
				    case 4:
					    	  logger.info("Selected Start doing a survey");
					    	  startSurvey();
					    	  break;
				    case 5:
				    		  logger.info("Selected logout and exit");
				    		  logged_user = null ;
						      System.out.println("Successfully Logged out and Exit application!!!");
						      System.exit(0);
						      break;
				    default:
						      System.out.println("Invalid selection");
						      break; // This break is not really necessary
				    }//end switch statement
				}
			}else{
				System.out.println("\nAuthentication failed. Try Again.\n");
			}
		}//end while authentication
	  }//end main method
	/**
	 * This method used to start a survey by selecting one available 
	 * survey for the user
	 * @author Ranavir
	 * @date 05/11/2016
	 */
	private static void startSurvey() {
		String methodname = "startSurvey" ;
		logger.info("ENTRY---> methodname : "+methodname);
		int iOption ;
		
		//Get incompleted surveys
		List<PanelSurveyVO> listPanelSurvey = psmService.getSurveys(logged_user.getUser_id(), new SurveyPanelStatusPredicate(){
			public boolean test(PanelSurveyVO obj){
				if(null == obj.getCompleted_flag() && !"Y".equalsIgnoreCase(obj.getCompleted_flag())){
					return true;
				}else{
					return false;
				}
				
			}
		});
		if(listPanelSurvey.size() > 0){
			System.out.println("\n\nAvailable Survey List ::\n\n");
			//Display surveys for choosing
			int i = 0 ;
			for(PanelSurveyVO psvo : listPanelSurvey){
				System.out.println("\t"+"( "+ (++i) +" ) "+psvo.getSurveyid()+" "+psvo.getSurveyls_title());
			}
			//Ask panelist for choosing a survey
			do{
				iOption = Keyin.inInt("\nSelect the serial no to start :: ");
			}while(iOption <= 0 || iOption > listPanelSurvey.size());
			//Get the survey from list by id
			
			//Call Survey Process with surveyid and userid
			sp.startSurvey(listPanelSurvey.get(iOption-1),logged_user.getUser_id());
		}else{
			System.out.println("\n\nThank you for your interest. You will get a new survey soon.\n\n");
		}
		
		
		logger.info("EXIT---> methodname : "+methodname);
	}//end of startSurvey

	/**
	 * This method is the part of admin or Quota mgmt with following functionalities
	 * 1 - Create userid column in lime_survey_(sid) if not present by sid
	 * 2 - Map survey to user by userid by inserting one record to stl_user_survey if (userid,surveyid) record not exist
	 * 3 - Insert one record to stl_user_survey_details for the userid to track complete/incomplete count and timings if no record with userid is present
	 */
	private static void initSurveyParticipation() {
		String methodname = "initSurvey" ;
		logger.info("ENTRY---> methodname : "+methodname);	
			int surveyId ;
			int userId;
			surveyId = Keyin.inInt("\nEnter survey id to initialize Participants :: ");
			userId = Keyin.inInt("\nEnter user id of Participant :: ");
			boolean flag = psmService.initSurveyParticipation(surveyId,userId);
			if(flag){
				System.out.println("Survey Participation Initialization success...");
			}else{
				System.out.println("Survey Participation Initialization failed !!!");
			}
		logger.info("EXIT---> methodname : "+methodname);
	}//end of initSurvey

	private static void showIncompletedSurveys() {
		String methodname = "showIncompletedSurveys" ;
		logger.info("ENTRY---> methodname : "+methodname);
		List<PanelSurveyVO> listPanelSurvey = psmService.getSurveys(logged_user.getUser_id(), new SurveyPanelStatusPredicate(){
			public boolean test(PanelSurveyVO obj){
				if(null == obj.getCompleted_flag() && !"Y".equalsIgnoreCase(obj.getCompleted_flag())){
					return true;
				}else{
					return false;
				}
				
			}
		});
		//System.out.println(listPanelSurvey);
		if(listPanelSurvey.size() > 0){
			System.out.println("\n\nIncompleted Survey List ::\n\n");
			//Display surveys for choosing
			for(PanelSurveyVO psvo : listPanelSurvey){
				System.out.println("\t"+psvo.getSurveyid()+" "+psvo.getSurveyls_title());
			}
			System.out.println("\n");
		}else{
			System.out.println("\n\nYou do not have any incompleted survey.\n\n");
		}
		logger.info("EXIT---> methodname : "+methodname);
	}//end of showIncompletedSurveys

	private static void showAvailableSurveys() {
		String methodname = "showAvailableSurveys" ;
		logger.info("ENTRY---> methodname : "+methodname);
		List<PanelSurveyVO> listPanelSurvey = psmService.getSurveys(logged_user.getUser_id(), new SurveyPanelStatusPredicate(){
			public boolean test(PanelSurveyVO obj){
				return true;
			}
		});
		//System.out.println(listPanelSurvey);
		if(listPanelSurvey.size() > 0){
			System.out.println("\n\nAvailable Survey List ::\n\n");
			//Display surveys for choosing
			for(PanelSurveyVO psvo : listPanelSurvey){
				System.out.println("\t"+psvo.getSurveyid()+" "+psvo.getSurveyls_title());
			}
			System.out.println("\n");
		}else{
			System.out.println("\n\nThank you for your interest. You will get a new survey soon.\n\n");
		}
		logger.info("EXIT---> methodname : "+methodname);
	}//end of showAvailableSurveys

	private static void showCompletedSurveys() {
		String methodname = "showCompletedSurveys" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		List<PanelSurveyVO> listPanelSurvey = psmService.getSurveys(logged_user.getUser_id(), new SurveyPanelStatusPredicate(){
			public boolean test(PanelSurveyVO obj){
				if(null != obj.getCompleted_flag() && "Y".equalsIgnoreCase(obj.getCompleted_flag())){
					return true;
				}else{
					return false;
				}
				
			}
		});
		//System.out.println(listPanelSurvey);
		if(listPanelSurvey.size() > 0){
			System.out.println("\n\nCompleted Survey List ::\n\n");
			//Display surveys for choosing
			for(PanelSurveyVO psvo : listPanelSurvey){
				System.out.println("\t"+psvo.getSurveyid()+" "+psvo.getSurveyls_title());
			}
			System.out.println("\n");
		}else{
			System.out.println("\n\nYou have not completed any survey till now.\n\n");
		}
		logger.info("EXIT---> methodname : "+methodname);
	}//end of showCompletedSurveys
	
	
	/**************************************************            Business Logic Ends              *********************************************/
	

}
