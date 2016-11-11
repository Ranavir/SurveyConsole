package com.stl.surveyconsole.daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.stl.surveyconsole.dao.SurveyProcessDao;
import com.stl.surveyconsole.helper.MyGenericUtils;
import com.stl.surveyconsole.helper.Predicate;
import com.stl.surveyconsole.model.LimeAnswersModel;
import com.stl.surveyconsole.model.LimeConditionsModel;
import com.stl.surveyconsole.model.LimeGroupsModel;
import com.stl.surveyconsole.model.LimeQuestionAttributesModel;
import com.stl.surveyconsole.model.LimeQuestionsModel;
import com.stl.surveyconsole.model.LimeSurveyLanguagesettings;
import com.stl.surveyconsole.utils.DaoFactory;
import com.stl.surveyconsole.utils.ProjectConstants;
import com.stl.surveyconsole.utils.Utils;
/**
 * This is the Dao Implementation class for all the Survey processes
 * and related facts
 * @author Ranavir
 * @date 03-Nov-2016
 */
public class SurveyProcessDaoImpl implements SurveyProcessDao{
	private static SurveyProcessDaoImpl spDaoImpl = null;
	private static DaoFactory daoFactory ;
	private static Logger logger = null;
	String methodname="";
	String userId = "";
	
	public SurveyProcessDaoImpl() {
		logger = Logger.getLogger(SurveyProcessDaoImpl.class) ;
		daoFactory = DaoFactory.getDAOFactory(1);
	}
	
	public static SurveyProcessDaoImpl getInstance(){
		if(spDaoImpl == null){
			spDaoImpl = new SurveyProcessDaoImpl();
			logger.info("SurveyProcessDaoImpl Instantiated...");
		}
		return spDaoImpl;
	}
	static int  iCurrentGid ;
	static int iCurrentQid ;
	static int iCurrentQOrder ;
	static LimeQuestionsModel currentQuestModel ;
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		//getInstance();
		//daoFactory.getConnection() ;
		List<LimeQuestionsModel> listCurrentGroupQuests = new ArrayList<LimeQuestionsModel>();
		
		List<LimeGroupsModel> listLimeGrpModels = new ArrayList<LimeGroupsModel>();
		List<LimeQuestionsModel> listLimeQuestModels = new ArrayList<LimeQuestionsModel>();
		List<LimeQuestionAttributesModel> listLimeQuestAttrModels = new ArrayList<LimeQuestionAttributesModel>();
		List<LimeAnswersModel> listLimeAnsModels = new ArrayList<LimeAnswersModel>();
		List<LimeConditionsModel> listLimeCondModels = new ArrayList<LimeConditionsModel>();
		
		HashMap<String,Object> hmSurveyDetails = getInstance().getSurveyDetails(111238,null);
		listLimeGrpModels = (List<LimeGroupsModel>)hmSurveyDetails.get("listLimeGrpModels");
		listLimeQuestModels = (List<LimeQuestionsModel>)hmSurveyDetails.get("listLimeQuestModels");
		listLimeQuestAttrModels = (List<LimeQuestionAttributesModel>)hmSurveyDetails.get("listLimeQuestAttrModels");
		listLimeAnsModels = (List<LimeAnswersModel>)hmSurveyDetails.get("listLimeAnsModels");
		listLimeCondModels = (List<LimeConditionsModel>)hmSurveyDetails.get("listLimeCondModels");
		logger.debug("\nlistLimeGrpModels :: "+listLimeGrpModels);
		logger.debug("\nlisLimeQuestModels :: "+listLimeQuestModels);
		logger.debug("\nlimeQuestAttrModels :: "+listLimeQuestAttrModels);
		logger.debug("\nlistLimeAnsModels :: "+listLimeAnsModels);
		logger.debug("\nlistLimeCondModels :: "+listLimeCondModels);
		
		
		for(LimeGroupsModel lgm : listLimeGrpModels){
			iCurrentGid = lgm.getGid();
			listCurrentGroupQuests = MyGenericUtils.filterList(listLimeQuestModels, new Predicate<LimeQuestionsModel>() {
				public boolean test(LimeQuestionsModel qModel){
					if(qModel.getGid() == iCurrentGid){
						return true;
					}else{
						return false;
					}
				}
			});
			
			//Find maximum orderid for the questions where pqid = 0
			int iGMaxQuestOrdId = 0 ;
			for(LimeQuestionsModel qModel : listCurrentGroupQuests){
				if(qModel.getParent_qid() == 0 && qModel.getQuestion_order() > iGMaxQuestOrdId){
					iGMaxQuestOrdId = qModel.getQuestion_order();
				}
			}//end for getting max question in a group by order id in a group
			
			//Select the question by incrementing order id
			for(int i = 0 ; i <= iGMaxQuestOrdId ; i++){
				iCurrentQOrder = i ;
				currentQuestModel = MyGenericUtils.filterUnique(listCurrentGroupQuests,new Predicate<LimeQuestionsModel>() {
					public boolean test(LimeQuestionsModel qModel){
						if(qModel.getGid() == iCurrentGid && qModel.getQuestion_order() == iCurrentQOrder && qModel.getParent_qid() == 0){
							return true;
						}else{
							return false;
						}
					}
				});
				if(currentQuestModel != null){
					System.out.println(currentQuestModel);
				}
			}
			
			
		}//end for Group iteration
		
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
		
		HashMap<String,Object> hmSurveyDetails = new HashMap<String, Object>();
		LimeSurveyLanguagesettings surveyLanguageSettings = null;
		List<LimeGroupsModel> listLimeGrpModels = new ArrayList<LimeGroupsModel>();
		List<LimeQuestionsModel> lisLimeQuestModels = new ArrayList<LimeQuestionsModel>();
		List<LimeQuestionAttributesModel> listLimeQuestAttrModels = new ArrayList<LimeQuestionAttributesModel>();
		List<LimeAnswersModel> listLimeAnsModels = new ArrayList<LimeAnswersModel>();
		List<LimeConditionsModel> listLimeCondModels = new ArrayList<LimeConditionsModel>();
		LimeGroupsModel lgModel;
		LimeQuestionsModel lqModel;
		LimeQuestionAttributesModel lqAttrModel;
		LimeAnswersModel laModel;
		LimeConditionsModel lcModel;
		
		String queryGetLanguageSettings = String.format(ProjectConstants.QUERY_GET_LANGUAGE_SETTINGS,surveyid) ;//select * from lime_surveys_languagesettings where surveyls_survey_id = 111238
		String queryGetLimeGroups = String.format(ProjectConstants.QUERY_GET_LIME_GROUPS,surveyid) ;//select * from lime_groups where sid = 111238 order by gid asc,group_order asc
		String queryGetLimeQuests = String.format(ProjectConstants.QUERY_GET_LIME_QUESTIONS,surveyid) ;//select * from lime_questions where sid = 111238 order by qid asc,gid asc
		String queryGetLimeQuestArrs = String.format(ProjectConstants.QUERY_GET_LIME_QUEST_ATTRS,surveyid) ;//select * from lime_question_attributes where qid in(select qid from lime_questions where sid = 111238) order by qaid asc,qid asc
		String queryGetLimeAnswers = String.format(ProjectConstants.QUERY_GET_LIME_ANSWERS,surveyid) ;//select * from lime_answers where qid in (select qid from lime_questions where sid = 111238) order by qid asc,sortorder asc
		String queryGetLimeConds = String.format(ProjectConstants.QUERY_GET_LIME_CONDITIONS,surveyid) ;//select * from lime_conditions where qid in (select qid from lime_questions where sid = 111238) order by cid asc,qid asc
		
		Connection con = null;
		Statement stmt = null ;
		ResultSet rs = null ;
		try{
			con = daoFactory.getConnection();
			//Step 0 - QUERY_GET_LANGUAGE_SETTINGS
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryGetLanguageSettings);
			logger.info("query:"+queryGetLanguageSettings);
			if(rs.next()){
				surveyLanguageSettings = new LimeSurveyLanguagesettings();
				
				surveyLanguageSettings.setSurveyls_survey_id(rs.getInt("surveyls_survey_id"));
				surveyLanguageSettings.setSurveyls_language(rs.getString("surveyls_language"));
				surveyLanguageSettings.setSurveyls_title(rs.getString("surveyls_title"));
				surveyLanguageSettings.setSurveyls_welcometext(rs.getString("surveyls_welcometext"));
				surveyLanguageSettings.setSurveyls_endtext(rs.getString("surveyls_endtext"));
				surveyLanguageSettings.setSurveyls_url(rs.getString("surveyls_url"));
				surveyLanguageSettings.setSurveyls_urldescription(rs.getString("surveyls_urldescription"));
				surveyLanguageSettings.setSurveyls_dateformat(rs.getInt("surveyls_dateformat"));
				surveyLanguageSettings.setSurveyls_numberformat(rs.getInt("surveyls_numberformat"));
				
			}
			rs.close();
			stmt.close();
			
			//Step 1 - QUERY_GET_LIME_GROUPS
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryGetLimeGroups);
			logger.info("query:"+queryGetLimeGroups);
			while(rs.next()){
				lgModel = new LimeGroupsModel();
				
				lgModel.setGid(rs.getInt(1));
				lgModel.setSid(rs.getInt(2));
				lgModel.setGroup_name(rs.getString(3));
				lgModel.setGroup_order(rs.getInt(4));
				lgModel.setDescription(rs.getString(5));
				lgModel.setLanguage(rs.getString(6));
				lgModel.setRandomization_group(rs.getString(7));
				lgModel.setGrelevance(rs.getString(8));
				
				listLimeGrpModels.add(lgModel);
			}
			rs.close();
			stmt.close();
			
			//Step 2 - QUERY_GET_LIME_QUESTIONS
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryGetLimeQuests);
			logger.info("query:"+queryGetLimeQuests);
			while(rs.next()){
				lqModel = new LimeQuestionsModel();
				
				lqModel.setQid(rs.getInt(1));
				lqModel.setParent_qid(rs.getInt(2));
				lqModel.setSid(rs.getInt(3));
				lqModel.setGid(rs.getInt(4));
				lqModel.setType(rs.getString(5));
				lqModel.setTitle(rs.getString(6));
				lqModel.setQuestion(rs.getString(7));
				lqModel.setPreg(rs.getString(8));
				lqModel.setHelp(rs.getString(9));
				lqModel.setOther(rs.getString(10));
				lqModel.setMandatory(rs.getString(11));
				lqModel.setQuestion_order(rs.getInt(12));
				lqModel.setLanguage(rs.getString(13));
				lqModel.setScale_id(rs.getInt(14));
				lqModel.setSame_default(rs.getInt(15));
				lqModel.setRelevance(rs.getString(16));
				lqModel.setModulename(rs.getString(17));
				
				lisLimeQuestModels.add(lqModel);
			}
			rs.close();
			stmt.close();
			
			//Step 3 - QUERY_GET_LIME_QUEST_ATTRS
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryGetLimeQuestArrs);
			logger.info("query:"+queryGetLimeQuestArrs);
			while(rs.next()){
				lqAttrModel = new LimeQuestionAttributesModel();
				
				lqAttrModel.setQaid(rs.getInt(1));
				lqAttrModel.setQid(rs.getInt(2));
				lqAttrModel.setAttribute(rs.getString(3));
				lqAttrModel.setValue(rs.getString(4));
				lqAttrModel.setLanguage(rs.getString(5));
				  
				listLimeQuestAttrModels.add(lqAttrModel);
			}
			rs.close();
			stmt.close();
			
			//Step 4 - QUERY_GET_LIME_ANSWERS
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryGetLimeAnswers);
			logger.info("query:"+queryGetLimeAnswers);
			while(rs.next()){
				laModel = new LimeAnswersModel();
				
				laModel.setQid(rs.getInt(1));
				laModel.setCode(rs.getString(2));
				laModel.setAnswer(rs.getString(3));
				laModel.setSortorder(rs.getInt(4));
				laModel.setLanguage(rs.getString(5));
				laModel.setAssessment_value(rs.getInt(6));
				laModel.setScale_id(rs.getInt(7));
				  
				listLimeAnsModels.add(laModel);
			}
			rs.close();
			stmt.close();
			
			//Step 5 - QUERY_GET_LIME_CONDITIONS
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryGetLimeConds);
			logger.info("query:"+queryGetLimeConds);
			while(rs.next()){
				lcModel = new LimeConditionsModel();
				
				lcModel.setCid(rs.getInt(1));
				lcModel.setQid(rs.getInt(2));
				lcModel.setCqid(rs.getInt(3));
				lcModel.setCfieldname(rs.getString(4));
				lcModel.setMethod(rs.getString(5));
				lcModel.setValue(rs.getString(6));
				lcModel.setScenario(rs.getInt(7));
				  
				listLimeCondModels.add(lcModel);
			}
			rs.close();
			stmt.close();
			
			//Add the details to HashMap 
			hmSurveyDetails.put("surveyLanguageSettings", surveyLanguageSettings);
			hmSurveyDetails.put("listLimeGrpModels", listLimeGrpModels);
			hmSurveyDetails.put("listLimeQuestModels", lisLimeQuestModels);
			hmSurveyDetails.put("listLimeQuestAttrModels", listLimeQuestAttrModels);
			hmSurveyDetails.put("listLimeAnsModels", listLimeAnsModels);
			hmSurveyDetails.put("listLimeCondModels", listLimeCondModels);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Utils.closeDB(rs,stmt,con);
		}//end of try / catch	
		
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
		
		ArrayList<String> alResponseTblColList = daoFactory.getTableStructure(strRespTblName,colPattern) ;
		
		logger.info("EXIT---> methodname : "+methodname);
		return alResponseTblColList;
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
		
		HashMap<String, String> hmSurveyAnswer = new HashMap<String, String>(); 
		String queryGetSurveyResponses = String.format(ProjectConstants.QUERY_GET_SURVEY_RESPONSES,surveyid,user_id) ;//select * from lime_survey_%s where userid = %s
		
		Connection con = null;
		Statement stmt = null ;
		ResultSet rs = null ;
		try{
				con = daoFactory.getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(queryGetSurveyResponses);
				logger.info("query:"+queryGetSurveyResponses);
				if(rs.next()){
					for(String colString : alResponseTblColList){
						hmSurveyAnswer.put(colString, rs.getString(colString));
					}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Utils.closeDB(rs,stmt,con);
		}//end of try / catch
		
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
		
		boolean bStatus = true ;
		Connection con = null;
		Statement stmt = null ;
		int iUpdateCtr = 0 ;
		/**
		 * id - auto update due to serial data type
		 * submitdate -    last question this key available
		 * lastpage -      every question this key available
		 * startlanguage - first time this key available
		 * startdate -     first time this key available(may or may not so check)
		 * datestamp -     first time and for every question this key available(may or may not check)
		 * sidXgidXqid(*) -every question this key available dynamically(may or may not check)
		 * userid -        first time this key available
		 */
		//Prepare query
		String query = "" ;
		StringBuffer sbInsertQuery = new StringBuffer("INSERT INTO lime_survey_"+sid+"(");
		StringBuffer sbUpdateQuery = new StringBuffer("UPDATE lime_survey_"+sid+" SET ");
		StringBuffer sbInsertParams = new StringBuffer();
		int iParamCtr = 0 ;
		StringBuffer sbInsertValues = new StringBuffer(" VALUES (");
		int iValueCtr = 0 ;
		//ProjectConstants.QUERY_INSERT_RESPONSE 
		//OR 
		//ProjectConstants.QUERY_UPDATE_RESPONSE
		if(alColumns == null){//no dynamic data column available only insert base record 
			if(hmAnswers.containsKey("userid")){//first time 
				sbInsertParams.append(iParamCtr++ > 0 ? ",userid" : "userid");
				sbInsertValues.append(iValueCtr++ > 0 ? ","+hmAnswers.get("userid") : hmAnswers.get("userid"));//Integer
			}
			if(hmAnswers.containsKey("startlanguage")){//first time 
				sbInsertParams.append(iParamCtr++ > 0 ? ",startlanguage" : "startlanguage");
				sbInsertValues.append(iValueCtr++ > 0 ? ",'"+hmAnswers.get("startlanguage")+"'" : "'"+hmAnswers.get("startlanguage")+"'");//String
			}
			if(hmAnswers.containsKey("startdate")){//first time 
				sbInsertParams.append(iParamCtr++ > 0 ? ",startdate" : "startdate");
				sbInsertValues.append(iValueCtr++ > 0 ? ",'"+hmAnswers.get("startdate")+"'" : "'"+hmAnswers.get("startdate")+"'");//String
			}
			if(hmAnswers.containsKey("datestamp")){//first time 
				sbInsertParams.append(iParamCtr++ > 0 ? ",datestamp" : "datestamp");
				sbInsertValues.append(iValueCtr++ > 0 ? ",'"+hmAnswers.get("datestamp")+"'" : "'"+hmAnswers.get("datestamp")+"'");//String
			}
			//do for dynamic columns also
			
			sbInsertParams.append(" )");
			sbInsertValues.append(" )");
			sbInsertQuery.append(sbInsertParams);
			sbInsertQuery.append(sbInsertValues);
			query = sbInsertQuery.toString() ;
			logger.debug("InsertQuery---->"+query);
		}else{//prepare update query
			for(int i = 0 ; i < alColumns.size() ; i++){
				if(hmAnswers.containsKey(alColumns.get(i))){
					sbUpdateQuery.append(iParamCtr++ > 0 ? ",\""+alColumns.get(i)+"\" = '"+hmAnswers.get(alColumns.get(i))+"'" : "\""+alColumns.get(i)+"\" = '"+hmAnswers.get(alColumns.get(i))+"'");//String
				}
			}
			if(hmAnswers.containsKey("datestamp")){//first time 
				sbUpdateQuery.append(iParamCtr++ > 0 ? ",\"datestamp\" = '"+hmAnswers.get("datestamp")+"'" : "\"datestamp\" = '"+hmAnswers.get("datestamp")+"'");//String
			}
			if(hmAnswers.containsKey("submitdate")){//first time 
				sbUpdateQuery.append(iParamCtr++ > 0 ? ",\"submitdate\" = '"+hmAnswers.get("submitdate")+"'" : "\"submitdate\" = '"+hmAnswers.get("submitdate")+"'");//String
			}
			if(hmAnswers.containsKey("lastpage")){//first time 
				sbUpdateQuery.append(iParamCtr++ > 0 ? ",\"lastpage\" = "+hmAnswers.get("lastpage") : "\"lastpage\" = "+hmAnswers.get("lastpage"));//Integer
			}
			sbUpdateQuery.append(" WHERE userid = "+userid);
			query = sbUpdateQuery.toString() ;
			logger.debug("UpdateQuery---->"+query);
		}
		/*UPDATE lime_survey_648211
		   SET id=?, token=?, submitdate=?, lastpage=?, startlanguage=?, "648211X14X64"=?, 
		       "648211X14X65"=?, "648211X14X66"=?, "648211X14X67"=?, userid=?
		 WHERE <condition>;*/
		
		/*INSERT INTO lime_survey_648211(
	            id, submitdate, lastpage, startlanguage, "648211X14X64", 
	            "648211X14X65", "648211X14X66", "648211X14X67", userid)
	    VALUES (?, ?, ?, ?, ?, ?, 
	            ?, ?, ?, ?);*/
		
		/*id, submitdate, lastpage, startlanguage, startdate, datestamp, 
        ipaddr, 
        "111238X3X71", "111238X3X72", "111238X3X73", "111238X3X74", 
        "111238X3X75", "111238X3X76", "111238X3X77", "111238X3X78", "111238X3X79", 
        "111238X3X710", "111238X3X8SQ001", "111238X3X8SQ002", "111238X3X8SQ003", 
        "111238X3X8other", "111238X3X12", "111238X3X13", "111238X3X14", 
        "111238X3X14other", "111238X3X15", "111238X3X15other", "111238X3X16SQ001", 
        "111238X3X16SQ002", "111238X3X16SQ003", "111238X3X16SQ004", userid)*/
		
		
		ResultSet rs = null ;
		try{
				con = daoFactory.getConnection();
				stmt = con.createStatement();
				iUpdateCtr = stmt.executeUpdate(query);
				logger.info("Update Count--->"+iUpdateCtr);
				if(iUpdateCtr == 0){
					bStatus = false ;
				}
				
		} catch (Exception e) {
			bStatus = false ;
			e.printStackTrace();
		} finally {
			Utils.closeDB(rs,stmt,con);
		}//end of try / catch
		
		logger.info("EXIT---> methodname : "+methodname);
		return bStatus ;
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
	public boolean updateSurveyStatus(int surveyid, String userid, String type) {
		methodname = "updateSurveyStatus" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		boolean bStatus = true ;
		int iUpdateCtr = 0 ;
		//UPDATE stl_user_survey SET flag='Y'  WHERE surveyid = %s and userid = %s
		String queryUpdateCompleteStatus = String.format(ProjectConstants.QUERY_UPDATE_COMPLETE_STATUS,surveyid,userid) ;
		//update stl_user_survey_details set completesurveycount = COALESCE(completesurveycount,0) + 1,incompletesurveycount = COALESCE(incompletesurveycount,1) - 1 where userid = %s
		String queryUpdateCompletenessCount = String.format(ProjectConstants.QUERY_UPDATE_COMPLETENESS_COUNT,userid) ;
		
		//update stl_user_survey_details set incompletesurveycount = COALESCE(incompletesurveycount,0) + 1 where userid = %s
		String queryRaiseIncompleteCount = String.format(ProjectConstants.QUERY_RAISE_INCOMPLETE_COUNT,userid) ;
				
		Connection con = null;
		Statement stmt = null ;
		try{
				con = daoFactory.getConnection();
				if(StringUtils.equalsIgnoreCase(type,"complete")){
					//Step - 1
					stmt = con.createStatement();
					logger.info("query:"+queryUpdateCompleteStatus);
					iUpdateCtr = stmt.executeUpdate(queryUpdateCompleteStatus);
					logger.debug("iUpdateCtr--->"+iUpdateCtr);
					if(iUpdateCtr <= 0){
						logger.debug("Unable to update !!!");
						bStatus = false ;
					}
					stmt.close();
					//Step - 2
					stmt = con.createStatement();
					logger.info("query:"+queryUpdateCompletenessCount);
					iUpdateCtr = stmt.executeUpdate(queryUpdateCompletenessCount);
					logger.debug("iUpdateCtr--->"+iUpdateCtr);
					if(iUpdateCtr <= 0){
						logger.debug("Unable to update !!!");
						bStatus = false ;
					}
					stmt.close();
				}else{//incomplete
					//Step - 1
					stmt = con.createStatement();
					logger.info("query:"+queryRaiseIncompleteCount);
					iUpdateCtr = stmt.executeUpdate(queryRaiseIncompleteCount);
					logger.debug("iUpdateCtr--->"+iUpdateCtr);
					if(iUpdateCtr <= 0){
						logger.debug("Unable to update !!!");
						bStatus = false ;
					}
					stmt.close();
				}
		} catch (Exception e) {
			bStatus = false ;
			e.printStackTrace();
		} finally {
			Utils.closeDB(stmt,con);
		}//end of try / catch
		
		logger.info("EXIT---> methodname : "+methodname);
		return bStatus ;
	}//end of updateSurveyStatus
}//end class
