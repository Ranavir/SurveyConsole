package com.stl.surveyconsole.vo;
/**
 * This class is the template for User/Panel & Survey Mapping
 * Record as per required details
 * @author Ranavir Dash
 * @date 03-Nov-2016
 */
/*select lus.userid,lus.surveyid as surveyid,lus.flag as completed_flag,lus.timings as time_to_complete,
lsl.surveyls_title,lsl.surveyls_description,lsl.surveyls_welcometext,lsl.surveyls_endtext,lsl.surveyls_dateformat,
ls.active,ls.expires,ls.startdate,ls.format,ls.savetimings,ls.datestamp,ls.allowsave,ls.allowprev,ls.datecreated,ls.showxquestions,ls.showqnumcode,ls.showwelcome,ls.showprogress 
from lime_surveys ls,lime_user_survey lus,lime_surveys_languagesettings lsl where ls.sid = lus.surveyid and lus.surveyid = lsl.surveyls_survey_id 
and ls.active = 'Y' and lus.userid = 112 and (lus.flag <> 'Y' OR lus.flag IS NULL) order by ls.datecreated desc*/
public class PanelSurveyVO {
	/*This String will store the user id of the user who is assigned the survey*/
	private String userid;
	/*This will store the survey id*/
	private int surveyid;
	/*This will store the status of the survey whether completed or not*/
	private String completed_flag;
	/*This String will store the time for completing the survey*/
	private String time_to_complete;
	/*This String will store the title of the survey*/
	private String surveyls_title;
	/*This String will store the description of the survey*/
	private String surveyls_description;
	/*This String will store the welcome text of the survey*/
	private String surveyls_welcometext;
	/*This String will store the end text of the survey*/
	private String surveyls_endtext;
	/*This String will store the date format used in survey*/
	private int surveyls_dateformat;//will store 1-12 (ex- 2(dd-mm-yyyy) & 9(mm-dd-yyyy))
	/*This string will store the survey status whether active or deactive*/
	private String active;//char Y/N
	/*This String will store the expire date and time of the particular survey*/
	private String expires;
	/*This String will store the start date and time of the particular survey*/
	private String startdate;
	/*This String will store the format of the survey questionnaires shown to end panelist/user*/
	private String format;//char A(All questions)/G(Group by Group)/S(Single question)
	/*This String will store whether the saving of survey time option will be there or not*/
	private String savetimings;//char Y/N
	/*This String will store the local language of the survey*/
	private String language;//Ex- en
	
	/*This String stores whether date stamp storing is enabled or not*/
	//Need to understand the usage
	private String datestamp;//char Y/N 
	
	/*This String stores whether a survey can be resumed or not*/
	private String allowsave;//char Y/N
	/*This String stores whether Moving backward during a survey is possible or not*/
	private String allowprev;//char Y/N
	/*This String saves the created date and time of the survey*/
	private String datecreated;
	/*This String stores whether to show n no. of questions available during survey or not*/
	private String showxquestions;//char Y/N
	/*This String stores options for showing or hiding question numbers and codes*/
	private String showqnumcode;//char X(hidden both)/B(Show both)/N(question number only)/C(question code only)
	/*This String will store the whether to show welcome text to user or not*/
	private String showwelcome;//char Y/N
	/*This String will store the whether to show progress of survey as a progress bar or not*/
	private String showprogress;//char Y/N
	
	@Override
	public String toString() {
		return "PanelSurveyVO [userid=" + userid + ", surveyid=" + surveyid
				+ ", completed_flag=" + completed_flag + ", time_to_complete="
				+ time_to_complete + ", surveyls_title=" + surveyls_title
				+ ", surveyls_description=" + surveyls_description
				+ ", surveyls_welcometext=" + surveyls_welcometext
				+ ", surveyls_endtext=" + surveyls_endtext
				+ ", surveyls_dateformat=" + surveyls_dateformat + ", active="
				+ active + ", expires=" + expires + ", startdate=" + startdate
				+ ", format=" + format + ", savetimings=" + savetimings
				+ ", language=" + language + ", datestamp=" + datestamp
				+ ", allowsave=" + allowsave + ", allowprev=" + allowprev
				+ ", datecreated=" + datecreated + ", showxquestions="
				+ showxquestions + ", showqnumcode=" + showqnumcode
				+ ", showwelcome=" + showwelcome + ", showprogress="
				+ showprogress + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + surveyid;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PanelSurveyVO other = (PanelSurveyVO) obj;
		if (surveyid != other.surveyid)
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getSurveyid() {
		return surveyid;
	}
	public void setSurveyid(int surveyid) {
		this.surveyid = surveyid;
	}
	public String getCompleted_flag() {
		return completed_flag;
	}
	public void setCompleted_flag(String completed_flag) {
		this.completed_flag = completed_flag;
	}
	public String getTime_to_complete() {
		return time_to_complete;
	}
	public void setTime_to_complete(String time_to_complete) {
		this.time_to_complete = time_to_complete;
	}
	public String getSurveyls_title() {
		return surveyls_title;
	}
	public void setSurveyls_title(String surveyls_title) {
		this.surveyls_title = surveyls_title;
	}
	public String getSurveyls_description() {
		return surveyls_description;
	}
	public void setSurveyls_description(String surveyls_description) {
		this.surveyls_description = surveyls_description;
	}
	public String getSurveyls_welcometext() {
		return surveyls_welcometext;
	}
	public void setSurveyls_welcometext(String surveyls_welcometext) {
		this.surveyls_welcometext = surveyls_welcometext;
	}
	public String getSurveyls_endtext() {
		return surveyls_endtext;
	}
	public void setSurveyls_endtext(String surveyls_endtext) {
		this.surveyls_endtext = surveyls_endtext;
	}
	public int getSurveyls_dateformat() {
		return surveyls_dateformat;
	}
	public void setSurveyls_dateformat(int surveyls_dateformat) {
		this.surveyls_dateformat = surveyls_dateformat;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getSavetimings() {
		return savetimings;
	}
	public void setSavetimings(String savetimings) {
		this.savetimings = savetimings;
	}
	public String getDatestamp() {
		return datestamp;
	}
	public void setDatestamp(String datestamp) {
		this.datestamp = datestamp;
	}
	public String getAllowsave() {
		return allowsave;
	}
	public void setAllowsave(String allowsave) {
		this.allowsave = allowsave;
	}
	public String getAllowprev() {
		return allowprev;
	}
	public void setAllowprev(String allowprev) {
		this.allowprev = allowprev;
	}
	public String getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(String datecreated) {
		this.datecreated = datecreated;
	}
	public String getShowxquestions() {
		return showxquestions;
	}
	public void setShowxquestions(String showxquestions) {
		this.showxquestions = showxquestions;
	}
	public String getShowqnumcode() {
		return showqnumcode;
	}
	public void setShowqnumcode(String showqnumcode) {
		this.showqnumcode = showqnumcode;
	}
	public String getShowwelcome() {
		return showwelcome;
	}
	public void setShowwelcome(String showwelcome) {
		this.showwelcome = showwelcome;
	}
	public String getShowprogress() {
		return showprogress;
	}
	public void setShowprogress(String showprogress) {
		this.showprogress = showprogress;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}//end of class
