package com.stl.surveyconsole.model;

import java.io.Serializable;

/**
 * This is the POJO model of the USER/PANEL MEMBER
 * @author office
 *
 */
public class UserModel implements Serializable{
	/**
	 * Used for serialization and de-serialization
	 */
	private static final long serialVersionUID = 1L;
	/*This field holds the user id of the member/panelist*/
	private String user_id;
	/*This field holds the mobile number of the member/panelist*/
	private String mobile_number;
	/*This field holds the primary email id of the member/panelist*/
	private String email;
	/*This field holds the MD5 encrypted password of the member/panelist*/
	private String password;
	/*This field holds the home pin code of the member/panelist*/
	private String pin;
	/*This field holds the first name of the member/panelist*/
	private String first_name;
	/*This field holds the last name of the member/panelist*/
	private String last_name;
	/*This character field holds the gender of the member/panelist*/
	private char gender;
	/*This field holds the Date of Birth of the member/panelist*/
	private String dob ;
	/*This field holds the address line 1 of the member/panelist*/
	private String address_line1;
	/*This field holds the address line 2 of the member/panelist*/
	private String  address_line2;
	/*This field holds the state of the member/panelist*/
	private String state;
	/*This field holds the city of the member/panelist*/
	private String city;
	/*This field holds the home location latitude of the member/panelist*/
	private double lat_home;
	/*This field holds the home location longitude of the member/panelist*/
	private double lng_home;
	/*This field holds the home address of the member/panelist*/
	private String add_home;
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", mobile_number="
				+ mobile_number + ", email=" + email + ", pin=" + pin
				+ ", first_name=" + first_name + ", last_name=" + last_name
				+ ", gender=" + gender + ", dob=" + dob + ", address_line1="
				+ address_line1 + ", address_line2=" + address_line2
				+ ", state=" + state + ", city=" + city + ", lat_home="
				+ lat_home + ", lng_home=" + lng_home + ", add_home="
				+ add_home + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((mobile_number == null) ? 0 : mobile_number.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
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
		UserModel other = (UserModel) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (mobile_number == null) {
			if (other.mobile_number != null)
				return false;
		} else if (!mobile_number.equals(other.mobile_number))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAddress_line1() {
		return address_line1;
	}
	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}
	public String getAddress_line2() {
		return address_line2;
	}
	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getLat_home() {
		return lat_home;
	}
	public void setLat_home(double lat_home) {
		this.lat_home = lat_home;
	}
	public double getLng_home() {
		return lng_home;
	}
	public void setLng_home(double lng_home) {
		this.lng_home = lng_home;
	}
	public String getAdd_home() {
		return add_home;
	}
	public void setAdd_home(String add_home) {
		this.add_home = add_home;
	}
	
}
