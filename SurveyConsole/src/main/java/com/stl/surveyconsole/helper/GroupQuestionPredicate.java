package com.stl.surveyconsole.helper;

import com.stl.surveyconsole.model.LimeQuestionsModel;

public class GroupQuestionPredicate implements Predicate<LimeQuestionsModel> {
	int gid ;
	public GroupQuestionPredicate(int gid){
		this.gid = gid ;
	}
	public boolean test(LimeQuestionsModel qModel){
		if(qModel.getGid() == this.gid){
			return true;
		}else{
			return false;
		}
	}

}
