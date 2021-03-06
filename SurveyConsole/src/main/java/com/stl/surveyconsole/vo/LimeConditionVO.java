package com.stl.surveyconsole.vo;

import java.io.Serializable;

/**
 * This class stores the structure of the table lime_conditions
 * @author Ranavir Dash
 * @date 05-Nov-2016
 *
 */
public class LimeConditionVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int cid;
	private int qid;
	private int cqid;
	private String cfieldname;
	private String method;
	private String value;
	private int scenario;
	/*This will store the evaluated value from this condition*/
	private boolean result;//
	/*This will hold the question type of the conditioned question or cqid(i.e on which question data this condition will be applied)*/
	private String condQuestType;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cid;
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
		LimeConditionVO other = (LimeConditionVO) obj;
		if (cid != other.cid)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LimeConditionVO [cid=" + cid + ", qid=" + qid + ", cqid="
				+ cqid + ", cfieldname=" + cfieldname + ", method=" + method
				+ ", value=" + value + ", scenario=" + scenario + ", result="
				+ result + ", condQuestType=" + condQuestType + "]";
	}
	public String getCondQuestType() {
		return condQuestType;
	}
	public void setCondQuestType(String condQuestType) {
		this.condQuestType = condQuestType;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public int getCqid() {
		return cqid;
	}
	public void setCqid(int cqid) {
		this.cqid = cqid;
	}
	public String getCfieldname() {
		return cfieldname;
	}
	public void setCfieldname(String cfieldname) {
		this.cfieldname = cfieldname;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getScenario() {
		return scenario;
	}
	public void setScenario(int scenario) {
		this.scenario = scenario;
	}
	
}
