package com.stl.surveyconsole.model;

import java.io.Serializable;

/**
 * This class stores the structure of the table lime_surveys_languagesettings
 * @author Ranavir Dash
 * @date 05-Nov-2016
 *
 */
public class LimeSurveyLanguagesettings implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*surveyls_survey_id integer NOT NULL,
	  surveyls_language character varying(45) NOT NULL DEFAULT 'en'::character varying,
	  surveyls_title character varying(200) NOT NULL,
	  surveyls_description text,
	  surveyls_welcometext text,
	  surveyls_endtext text,
	  surveyls_url text,
	  surveyls_urldescription character varying(255),
	  surveyls_email_invite_subj character varying(255), x
	  surveyls_email_invite text, x
	  surveyls_email_remind_subj character varying(255), x
	  surveyls_email_remind text, x
	  surveyls_email_register_subj character varying(255), x
	  surveyls_email_register text, x
	  surveyls_email_confirm_subj character varying(255), x
	  surveyls_email_confirm text, x
	  surveyls_dateformat integer NOT NULL DEFAULT 1,
	  surveyls_attributecaptions text,
	  email_admin_notification_subj character varying(255),
	  email_admin_notification text,
	  email_admin_responses_subj character varying(255),
	  email_admin_responses text,
	  surveyls_numberformat integer NOT NULL DEFAULT 0,
	  attachments text, x
	  CONSTRAINT lime_surveys_languagesettings_pkey PRIMARY KEY (surveyls_survey_id, surveyls_language)*/
	private int surveyls_survey_id;
	private String surveyls_language;
	private String surveyls_title;
	private String surveyls_welcometext;
	private String surveyls_endtext;
	private String surveyls_url;
	private String surveyls_urldescription;
	private int surveyls_dateformat;
	private int surveyls_numberformat;
	
	@Override
	public String toString() {
		return "LimeSurveyLanguagesettings [surveyls_survey_id="
				+ surveyls_survey_id + ", surveyls_language="
				+ surveyls_language + ", surveyls_title=" + surveyls_title
				+ ", surveyls_welcometext=" + surveyls_welcometext
				+ ", surveyls_endtext=" + surveyls_endtext + ", surveyls_url="
				+ surveyls_url + ", surveyls_urldescription="
				+ surveyls_urldescription + ", surveyls_dateformat="
				+ surveyls_dateformat + ", surveyls_numberformat="
				+ surveyls_numberformat + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + surveyls_survey_id;
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
		LimeSurveyLanguagesettings other = (LimeSurveyLanguagesettings) obj;
		if (surveyls_survey_id != other.surveyls_survey_id)
			return false;
		return true;
	}
	public int getSurveyls_survey_id() {
		return surveyls_survey_id;
	}
	public void setSurveyls_survey_id(int surveyls_survey_id) {
		this.surveyls_survey_id = surveyls_survey_id;
	}
	public String getSurveyls_language() {
		return surveyls_language;
	}
	public void setSurveyls_language(String surveyls_language) {
		this.surveyls_language = surveyls_language;
	}
	public String getSurveyls_title() {
		return surveyls_title;
	}
	public void setSurveyls_title(String surveyls_title) {
		this.surveyls_title = surveyls_title;
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
	public String getSurveyls_url() {
		return surveyls_url;
	}
	public void setSurveyls_url(String surveyls_url) {
		this.surveyls_url = surveyls_url;
	}
	public String getSurveyls_urldescription() {
		return surveyls_urldescription;
	}
	public void setSurveyls_urldescription(String surveyls_urldescription) {
		this.surveyls_urldescription = surveyls_urldescription;
	}
	public int getSurveyls_dateformat() {
		return surveyls_dateformat;
	}
	public void setSurveyls_dateformat(int surveyls_dateformat) {
		this.surveyls_dateformat = surveyls_dateformat;
	}
	public int getSurveyls_numberformat() {
		return surveyls_numberformat;
	}
	public void setSurveyls_numberformat(int surveyls_numberformat) {
		this.surveyls_numberformat = surveyls_numberformat;
	}
	
}
