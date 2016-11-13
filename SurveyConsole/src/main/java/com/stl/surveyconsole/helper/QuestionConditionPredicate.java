package com.stl.surveyconsole.helper;

import com.stl.surveyconsole.vo.LimeConditionVO;

public class QuestionConditionPredicate implements Predicate<LimeConditionVO> {
	int qid ;
	public QuestionConditionPredicate(int qid){
		this.qid = qid ;
		//System.out.println("QuestionConditionPredicate instantiated...");
	}
	public boolean test(LimeConditionVO condVO){
		if(condVO.getQid() == this.qid){
			return true;
		}else{
			return false;
		}
	}

}
