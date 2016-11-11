package com.stl.surveyconsole.vo;

public class RadioTypeQuestionVO {
	/*This String holds the code of the option from lime_answers table by qid*/
	private String code;
	/*This String holds the answer text of the option from lime_answers table by qid*/
	private String answer;
	
	@Override
	public String toString() {
		return "( " + code + " )\t" + answer + "\n";
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
	
	
}
