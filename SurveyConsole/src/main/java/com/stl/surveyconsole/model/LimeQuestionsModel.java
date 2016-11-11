package com.stl.surveyconsole.model;

import java.io.Serializable;

/**
 * This class stores the structure of the table lime_questions
 * @author Ranavir Dash
 * @date 05-Nov-2016
 *
 */
public class LimeQuestionsModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*qid serial NOT NULL,
	  parent_qid integer NOT NULL DEFAULT 0,
	  sid integer NOT NULL DEFAULT 0,
	  gid integer NOT NULL DEFAULT 0,
	  type character varying(1) NOT NULL DEFAULT 'T'::character varying,
	  title character varying(20) NOT NULL DEFAULT ''::character varying,
	  question text NOT NULL,
	  preg text,
	  help text,
	  other character varying(1) NOT NULL DEFAULT 'N'::character varying,
	  mandatory character varying(1),
	  question_order integer NOT NULL,
	  language character varying(20) NOT NULL DEFAULT 'en'::character varying,
	  scale_id integer NOT NULL DEFAULT 0,
	  same_default integer NOT NULL DEFAULT 0,
	  relevance text,
	  modulename character varying(255),
	  CONSTRAINT lime_questions_pkey PRIMARY KEY (qid, language)*/
	private int qid;
	private int parent_qid;
	private int sid;
	private int gid;
	private String type;
	private String title;
	private String question;
	private String preg;
	private String help;
	private String other;//Y/N
	private String mandatory;//Y/N
	private int question_order;
	private String language;
	private int scale_id;
	private int same_default;
	private String relevance;
	private String modulename;
	
	@Override
	public String toString() {
		return "LimeQuestionsModel [qid=" + qid + ", parent_qid=" + parent_qid
				+ ", sid=" + sid + ", gid=" + gid + ", type=" + type
				+ ", title=" + title + ", question=" + question + ", preg="
				+ preg + ", help=" + help + ", other=" + other + ", mandatory="
				+ mandatory + ", question_order=" + question_order
				+ ", language=" + language + ", scale_id=" + scale_id
				+ ", same_default=" + same_default + ", relevance=" + relevance
				+ ", modulename=" + modulename + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result + qid;
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
		LimeQuestionsModel other = (LimeQuestionsModel) obj;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (qid != other.qid)
			return false;
		return true;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public int getParent_qid() {
		return parent_qid;
	}
	public void setParent_qid(int parent_qid) {
		this.parent_qid = parent_qid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getPreg() {
		return preg;
	}
	public void setPreg(String preg) {
		this.preg = preg;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public int getQuestion_order() {
		return question_order;
	}
	public void setQuestion_order(int question_order) {
		this.question_order = question_order;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getScale_id() {
		return scale_id;
	}
	public void setScale_id(int scale_id) {
		this.scale_id = scale_id;
	}
	public int getSame_default() {
		return same_default;
	}
	public void setSame_default(int same_default) {
		this.same_default = same_default;
	}
	public String getRelevance() {
		return relevance;
	}
	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
}
