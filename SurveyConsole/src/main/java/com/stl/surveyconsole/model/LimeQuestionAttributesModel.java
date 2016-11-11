package com.stl.surveyconsole.model;

import java.io.Serializable;

/**
 * This class stores the structure of the table lime_question_attributes
 * @author Ranavir Dash
 * @date 05-Nov-2016
 *
 */
public class LimeQuestionAttributesModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*qaid serial NOT NULL,
	  qid integer NOT NULL DEFAULT 0,
	  attribute character varying(50),
	  value text,
	  language character varying(20),
	  CONSTRAINT lime_question_attributes_pkey PRIMARY KEY (qaid)*/
	private int qaid;
	private int qid;
	private String attribute;
	private String value;
	private String language;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + qaid;
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
		LimeQuestionAttributesModel other = (LimeQuestionAttributesModel) obj;
		if (qaid != other.qaid)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LimeQuestionAttributes [qaid=" + qaid + ", qid=" + qid
				+ ", attribute=" + attribute + ", value=" + value
				+ ", language=" + language + "]";
	}
	public int getQaid() {
		return qaid;
	}
	public void setQaid(int qaid) {
		this.qaid = qaid;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
}
