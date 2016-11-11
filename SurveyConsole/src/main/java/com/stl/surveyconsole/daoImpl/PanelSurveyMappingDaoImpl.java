package com.stl.surveyconsole.daoImpl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.stl.surveyconsole.dao.PanelSurveyMappingDao;
import com.stl.surveyconsole.helper.SurveyPanelStatusPredicate;
import com.stl.surveyconsole.utils.DaoFactory;
import com.stl.surveyconsole.utils.ProjectConstants;
import com.stl.surveyconsole.utils.Utils;
import com.stl.surveyconsole.vo.PanelSurveyVO;
/**
 * This is the Dao Implementation class Mapping of panel with Surveys
 * and related facts
 * @author Ranavir
 * @date 03-Nov-2016
 */
public class PanelSurveyMappingDaoImpl implements PanelSurveyMappingDao{
	private static PanelSurveyMappingDaoImpl psmDaoImpl = null;
	private static DaoFactory daoFactory ;
	private static Logger logger = null;
	String methodname="";
	String userId = "";
	
	public PanelSurveyMappingDaoImpl() {
		logger = Logger.getLogger(PanelSurveyMappingDaoImpl.class) ;
		daoFactory = DaoFactory.getDAOFactory(1);
	}
	
	public static PanelSurveyMappingDaoImpl getInstance(){
		if(psmDaoImpl == null){
			psmDaoImpl = new PanelSurveyMappingDaoImpl();
			logger.info("PanelSurveyMappingDaoImpl Instantiated...");
		}
		return psmDaoImpl;
	}
	
	public static void main(String[] args) {
		getInstance();
		daoFactory.getConnection() ;
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
		String methodname = "getSurveys" ;
		logger.info("ENTRY---> methodname : "+methodname);
		
		List<PanelSurveyVO> listPanelSurvey = new ArrayList<PanelSurveyVO>() ;
		PanelSurveyVO mapObj = null ;
		Connection con = null;
		PreparedStatement pstGetPanelSurveyMap = null ;
		ResultSet rsGetPanelSurveyMap = null ;
		String queryGetPanelSurveyMap = "" ;
		queryGetPanelSurveyMap = ProjectConstants.QUERY_PANEL_SURVEY_MAP ;
			
		try{
			con = daoFactory.getConnection();
			if (con != null) {
				logger.info("query:"+queryGetPanelSurveyMap);
				pstGetPanelSurveyMap = con.prepareStatement(queryGetPanelSurveyMap) ;
				pstGetPanelSurveyMap.setInt(1, Integer.parseInt(userId.trim()));
				
				/*"select lus.userid,lus.surveyid as surveyid,lus.flag as completed_flag,lus.timings as time_to_complete,"+
				"lsl.surveyls_title,lsl.surveyls_description,lsl.surveyls_welcometext,lsl.surveyls_endtext,lsl.surveyls_dateformat,"+
				"ls.active,ls.expires,ls.startdate,ls.format,ls.savetimings,ls.language,ls.datestamp,ls.allowsave,ls.allowprev,ls.datecreated,ls.showxquestions,ls.showqnumcode,ls.showwelcome,ls.showprogress "+
				"from lime_surveys ls,lime_user_survey lus,lime_surveys_languagesettings lsl where ls.sid = lus.surveyid and lus.surveyid = lsl.surveyls_survey_id "+
				"and ls.active = 'Y' and lus.userid = ? order by ls.datecreated desc"*/
				
				rsGetPanelSurveyMap = pstGetPanelSurveyMap.executeQuery() ;
				while(rsGetPanelSurveyMap.next()){
					mapObj = new PanelSurveyVO();
					mapObj.setUserid(rsGetPanelSurveyMap.getString(1));
					mapObj.setSurveyid(rsGetPanelSurveyMap.getInt(2));
					mapObj.setCompleted_flag(rsGetPanelSurveyMap.getString(3));
					mapObj.setTime_to_complete(rsGetPanelSurveyMap.getString(4));
					
					mapObj.setSurveyls_title(rsGetPanelSurveyMap.getString(5));
					mapObj.setSurveyls_description(rsGetPanelSurveyMap.getString(6));
					mapObj.setSurveyls_welcometext(rsGetPanelSurveyMap.getString(7));
					mapObj.setSurveyls_endtext(rsGetPanelSurveyMap.getString(8));
					mapObj.setSurveyls_dateformat(rsGetPanelSurveyMap.getInt(9));
					
					mapObj.setActive(rsGetPanelSurveyMap.getString(10));
					mapObj.setExpires(rsGetPanelSurveyMap.getString(11));
					mapObj.setStartdate(rsGetPanelSurveyMap.getString(12));
					mapObj.setFormat(rsGetPanelSurveyMap.getString(13));
					mapObj.setSavetimings(rsGetPanelSurveyMap.getString(14));
					mapObj.setLanguage(rsGetPanelSurveyMap.getString(15));
					mapObj.setDatestamp(rsGetPanelSurveyMap.getString(16));
					mapObj.setAllowsave(rsGetPanelSurveyMap.getString(17));
					mapObj.setAllowprev(rsGetPanelSurveyMap.getString(18));
					mapObj.setDatecreated(rsGetPanelSurveyMap.getString(19));
					mapObj.setShowxquestions(rsGetPanelSurveyMap.getString(20));
					mapObj.setShowqnumcode(rsGetPanelSurveyMap.getString(21));
					mapObj.setShowwelcome(rsGetPanelSurveyMap.getString(22));
					mapObj.setShowprogress(rsGetPanelSurveyMap.getString(23));
					
					if(predicate.test(mapObj)){
						listPanelSurvey.add(mapObj);
					}
				}
				
			}//end of If
		 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Utils.closeDB(pstGetPanelSurveyMap,rsGetPanelSurveyMap,con);
		}//end of try / catch
		logger.info("ENTRY---> methodname : "+methodname);
		return listPanelSurvey;
	}//end of getSurveys
	/*****************************************************************************
	 * This method used by Admin as init operation for survey participants
	 * 1 - Create userid column in lime_survey_(sid) if not present by sid
	 * 2 - Create userid column in lime_survey_(sid)_timings if not present by sid
	 * 3 - Map survey to user by userid by inserting one record to stl_user_survey if (userid,surveyid) record not exist
	 * 4 - Insert one record to stl_user_survey_details for the userid to track complete/incomplete count and timings if no record with userid is present
	 * @param surveyId
	 * @param userId
	 * @return boolean
	 * @author Ranavir
	 * @date 04-Nov-2016
	 ****************************************************************************/
	public boolean initSurveyParticipation(int surveyId, int userId) {
		String methodname = "initSurveyParticipation" ;
		logger.info("ENTRY---> methodname : "+methodname);
		boolean flag = true ;
		int uc = 0 ;
		Connection con = null;
		Statement stmt1 = null,stmt2 = null;
		ResultSet rs = null,rs1 = null ;
		String queryCreateUserIdColumn = ProjectConstants.QUERY_CREATE_USERID_COLUMN ;
		String queryCheckUserSurveyMappingRecord = ProjectConstants.QUERY_CHECK_USER_SURVEY_MAPPING ;
		String queryCheckUserExtra = ProjectConstants.QUERY_CHECK_USER_EXTRA ;
		String queryInsertUserSurveyMappingRecord = ProjectConstants.QUERY_INSERT_USER_SURVEY_MAPPING ;
		String queryInsertUserExtra = ProjectConstants.QUERY_INSERT_USER_EXTRA ;
		try{
			con = daoFactory.getConnection();
			//Step - 1
			DatabaseMetaData md = con.getMetaData();
			rs = md.getColumns(null, null, "lime_survey_"+surveyId, "userid");
			if (!rs.next()) {//No column with userid exists then create one
				logger.info("userid column not available in table "+"lime_survey_"+surveyId);
			     //create a column in the table
				queryCreateUserIdColumn = String.format(queryCreateUserIdColumn, "lime_survey_"+surveyId);
				logger.info("query:"+queryCreateUserIdColumn);
				stmt1 = con.createStatement();
				stmt1.execute(queryCreateUserIdColumn);
				logger.info("userid column is added into table "+"lime_survey_"+surveyId+" successfully");
				stmt1.close();
				rs.close();
			}
			//Step - 2
			rs = md.getTables(null, null, "lime_survey_"+surveyId+"_timings", new String[]{"TABLE"});
			if (rs.next()) {//Table exist with name lime_survey_(sid)_timings
				logger.info("Table "+"lime_survey_"+surveyId+"_timings"+" exists for time saving");
				logger.info("Checking userid column existence in this table...");
				
				rs1 = md.getColumns(null, null, "lime_survey_"+surveyId+"_timings", "userid");
				if (!rs1.next()) {//No column with userid exists then create one
					logger.info("userid column not available in table "+"lime_survey_"+surveyId+"_timings");
				     //create a column in the table
					queryCreateUserIdColumn = String.format(queryCreateUserIdColumn, "lime_survey_"+surveyId+"_timings");
					logger.info("query:"+queryCreateUserIdColumn);
					stmt1 = con.createStatement();
					stmt1.execute(queryCreateUserIdColumn);
					logger.info("userid column is added into table "+"lime_survey_"+surveyId+"_timings"+" successfully");
					stmt1.close();
					rs1.close();
				}
				
			     
				rs.close();
			}else{
				logger.info("Save time option not selected for survey id "+surveyId);
			}
			//Step - 3
			queryCheckUserSurveyMappingRecord = String.format(queryCheckUserSurveyMappingRecord, surveyId,userId);//select * from stl_user_survey where surveyid = %s and userid = %s"
			logger.info("query:"+queryCheckUserSurveyMappingRecord);
			stmt1 = con.createStatement();
			rs = stmt1.executeQuery(queryCheckUserSurveyMappingRecord);
			if (!rs.next()) {//If no record available then insert one
				queryInsertUserSurveyMappingRecord = String.format(queryInsertUserSurveyMappingRecord,daoFactory.getSequenceID("stl_user_survey", "id"),surveyId,userId);//insert into stl_user_survey (id,surveyid,userid) values(?,?,?)
				stmt2 = con.createStatement();
				uc = stmt2.executeUpdate(queryInsertUserSurveyMappingRecord);
				logger.info("Update Count :: "+uc);
				uc = 0 ;//Re-init
				stmt2.close();
				rs.close();
				stmt1.close();
			}
			//Step - 4
			queryCheckUserExtra = String.format(queryCheckUserExtra, userId);//select * from stl_user_survey_details where userid = %s
			logger.info("query:"+queryCheckUserExtra);
			stmt1 = con.createStatement();
			rs = stmt1.executeQuery(queryCheckUserExtra);
			if (!rs.next()) {//If no record available then insert one
				queryInsertUserExtra = String.format(queryInsertUserExtra,daoFactory.getSequenceID("stl_user_survey_details", "id"),userId);//insert into stl_user_survey_details (id,userid) values(%s,%s)
				stmt2 = con.createStatement();
				uc = stmt2.executeUpdate(queryInsertUserExtra);
				logger.info("Update Count :: "+uc);
				uc = 0 ;//Re-init
				stmt2.close();
				rs.close();
				stmt1.close();
			}
			
			
		} catch (Exception e) {
			flag = false ;
			e.printStackTrace();
		} finally {
			Utils.closeDB(rs,stmt1,stmt2,con);
		}//end of try / catch	 
		logger.info("EXIT---> methodname : "+methodname);
		return flag;
	}//end of initSurveyParticipation
	
}//end class
