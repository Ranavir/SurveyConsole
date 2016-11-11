package com.stl.surveyconsole.model;

import java.io.Serializable;
/**
 * This class stores the structure of the table lime_answers
 * @author Ranavir Dash
 * @date 05-Nov-2016
 *
 */
public class LimeAnswersModel  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*qid integer NOT NULL DEFAULT 0,
	  code character varying(5) NOT NULL DEFAULT ''::character varying,
	  answer text NOT NULL,
	  sortorder integer NOT NULL,
	  language character varying(20) NOT NULL DEFAULT 'en'::character varying,
	  assessment_value integer NOT NULL DEFAULT 0,
	  scale_id integer NOT NULL DEFAULT 0,
	  CONSTRAINT lime_answers_pkey PRIMARY KEY (qid, code, language, scale_id)*/
	private int qid;
	private String code;
	private String answer;
	private int sortorder;
	private String language;
	private int assessment_value;
	private int scale_id;
	
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getSortorder() {
		return sortorder;
	}
	public void setSortorder(int sortorder) {
		this.sortorder = sortorder;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getAssessment_value() {
		return assessment_value;
	}
	public void setAssessment_value(int assessment_value) {
		this.assessment_value = assessment_value;
	}
	public int getScale_id() {
		return scale_id;
	}
	public void setScale_id(int scale_id) {
		this.scale_id = scale_id;
	}
	@Override
	public String toString() {
		return "LimeAnswers [qid=" + qid + ", code=" + code + ", answer="
				+ answer + ", sortorder=" + sortorder + ", language="
				+ language + ", assessment_value=" + assessment_value
				+ ", scale_id=" + scale_id + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result + qid;
		result = prime * result + scale_id;
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
		LimeAnswersModel other = (LimeAnswersModel) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (qid != other.qid)
			return false;
		if (scale_id != other.scale_id)
			return false;
		return true;
	}
	
}
