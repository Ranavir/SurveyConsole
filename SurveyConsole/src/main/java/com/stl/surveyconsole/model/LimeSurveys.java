package com.stl.surveyconsole.model;
/**
 * This is the Master table for lime_surveys
 *  @author Ranavir Dash
 *  @date 03-Nov-2016
 *
 */
public class LimeSurveys {
	int sid;//pkey
	int owner_id;
	String admin;
	String active;//char Y/N
	String expires;//
	String startdate;//
	String adminemail;
	String anonymized;
	String faxto;
	String format;//char A/G/S
	String savetimings;//char Y/N
	String template;
	String language;
	String additional_languages;
	String datestamp;
	String usecookie;
	String allowregister;
	String allowsave;//char Y/N
	String autonumber_start;
	String autoredirect;
	String allowprev;//char Y/N
	String printanswers;
	String ipaddr;
	String refurl;
	String datecreated;//
	String publicstatistics;
	String publicgraphs;
	String listpublic;
	String htmlemail;
	String sendconfirmation;
	String tokenanswerspersistence;
	String assessments;
	String usecaptcha;
	String usetokens;
	String bounce_email;
	String attributedescriptions;
	String emailresponseto;
	String emailnotificationto;
	int tokenlength;
	String showxquestions;//char Y/N
	String showgroupinfo;
	String shownoanswer;
	String showqnumcode;//char X(hidden both)/B(Show both)/N(question number only)/C(question code only)
	int bouncetime;
	String bounceprocessing;
	String bounceaccounttype;
	String bounceaccounthost;
	String bounceaccountpass;
	String bounceaccountencryption;
	String bounceaccountuser;
	String showwelcome;//char Y/N
	String showprogress;//char Y/N
	int questionindex;
	int navigationdelay;
	String nokeyboard;
	String alloweditaftercompletion;
	String googleanalyticsstyle;
	String googleanalyticsapikey;
	
}
