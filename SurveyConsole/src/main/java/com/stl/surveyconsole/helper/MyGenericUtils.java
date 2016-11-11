package com.stl.surveyconsole.helper;

import java.util.ArrayList;
import java.util.List;

public class MyGenericUtils {
	/**
	 * This generic method returns a selected list from any given list based
	 * on the dynamic condition for each value of the list
	 * @param list
	 * @param p
	 * @return
	 */
	public static <T> List<T> filterList(List<T> list,Predicate<T> p){
		ArrayList<T> tList = new ArrayList<T>();
		if(null != list){
			for(T tObj : list){
				if(p.test(tObj)){
					tList.add(tObj);
				}
			}
		}
		return tList;
	}// filterList
	/**
	 * This generic method returns a selected Element of any given list based
	 * on the dynamic condition for each value of the list
	 * @param list
	 * @param p
	 * @return
	 */
	public static <T> T filterUnique(List<T> list,Predicate<T> p){
		T t = null ;
		if(null != list){
			for(T tObj : list){
				if(p.test(tObj)){
					t = tObj;
					break;
				}
			}
		}
		return t;
	}// filterUnique
	
	
}
