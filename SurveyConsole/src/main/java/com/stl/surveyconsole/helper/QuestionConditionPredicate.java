package com.stl.surveyconsole.helper;

import com.stl.surveyconsole.model.LimeConditionsModel;

public class QuestionConditionPredicate implements Predicate<LimeConditionsModel> {
	int qid ;
	public QuestionConditionPredicate(int qid){
		this.qid = qid ;
		//System.out.println("QuestionConditionPredicate instantiated...");
	}
	public boolean test(LimeConditionsModel condModel){
		if(condModel.getQid() == this.qid){
			return true;
		}else{
			return false;
		}
	}

}
