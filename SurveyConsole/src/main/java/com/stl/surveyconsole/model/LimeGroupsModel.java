package com.stl.surveyconsole.model;

import java.io.Serializable;

/**
 * This class stores the structure of the table lime_groups
 * @author Ranavir Dash
 * @date 05-Nov-2016
 *
 */
public class LimeGroupsModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*gid serial NOT NULL,
	  sid integer NOT NULL DEFAULT 0,
	  group_name character varying(100) NOT NULL DEFAULT ''::character varying,
	  group_order integer NOT NULL DEFAULT 0,
	  description text,
	  language character varying(20) NOT NULL DEFAULT 'en'::character varying,
	  randomization_group character varying(20) NOT NULL DEFAULT ''::character varying,
	  grelevance text,*/
	private int gid;
	private int sid;
	private String group_name;
	private int group_order;
	private String description;
	private String language;
	private String randomization_group;//Y/N
	private String grelevance;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gid;
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
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
		LimeGroupsModel other = (LimeGroupsModel) obj;
		if (gid != other.gid)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LimeGroupsModel [gid=" + gid + ", sid=" + sid + ", group_name="
				+ group_name + ", group_order=" + group_order
				+ ", description=" + description + ", language=" + language
				+ ", randomization_group=" + randomization_group
				+ ", grelevance=" + grelevance + "]";
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public int getGroup_order() {
		return group_order;
	}
	public void setGroup_order(int group_order) {
		this.group_order = group_order;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRandomization_group() {
		return randomization_group;
	}
	public void setRandomization_group(String randomization_group) {
		this.randomization_group = randomization_group;
	}
	public String getGrelevance() {
		return grelevance;
	}
	public void setGrelevance(String grelevance) {
		this.grelevance = grelevance;
	}
	
}
