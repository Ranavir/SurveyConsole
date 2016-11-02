/*Copyright (c) 2016 Silicon Tech Lab Pvt Ltd.  All rights reserved. 			           *	
 * This document is the property of Silicon Tech Lab Pvt Ltd..                          *
 * All ideas and information contained in this document are the intellectual property (IP) *
 * rights of Silicon Tech Lab Pvt Ltd.. This document is not for reference or general   *
 * distribution and is meant for use only for STL. This document shall not             *
 * be loaned to or shared with anyone, within or outside STL, including its customers. * 
 * Copying or unauthorized distribution of this document, in any form or means             *
 * including electronic, mechanical, photocopying or otherwise is illegal.                 * 
 * Use is subject to license terms only.                                                   *  
 *****************************************************************************************

 *****************************************************************************************
 *    Ver         Author                  Date        			Description		        *
 *    1.0         Ranavir               xx-April-2016          	Initial Version		    *
 *****************************************************************************************
 */
package com.stl.surveyconsole.utils;

import org.apache.log4j.Logger;

//**********************************************************
//**********************************************************
//Program: Keyin
//Reference: 
//Topics:
//1. Using the read() method of the ImputStream class
//  in the java.io package
//2. Developing a class for performing basic console
//  input of character and numeric types
//**********************************************************
//**********************************************************
public class Keyin {
	private static Logger logger = Logger.getLogger(Keyin.class) ;
	//*******************************
	  //   support methods
	  //*******************************
	  //Method to display the user's prompt string
	  public static void printPrompt(String prompt) {
	    System.out.print(prompt + " ");
	    System.out.flush();
	  }

	  //Method to make sure no data is available in the
	  //input stream
	  public static void inputFlush() {
	    int dummy;
	    int bAvail;

	    try {
	      while ((System.in.available()) != 0)
	        dummy = System.in.read();
	    } catch (java.io.IOException e) {
	      logger.error("Input error");
	    }
	  }

	  //********************************
	  //  data input methods for
	  //string, int, char, and double
	  //********************************
	  public static String inString(String prompt) {
	    inputFlush();
	    printPrompt(prompt);
	    return inString();
	  }

	  public static String inString() {
	    int aChar;
	    String s = "";
	    boolean finished = false;

	    while (!finished) {
	      try {
	        aChar = System.in.read();
	        if (aChar < 0 || (char) aChar == '\n')
	          finished = true;
	        else if ((char) aChar != '\r')
	          s = s + (char) aChar; // Enter into string
	      }

	      catch (java.io.IOException e) {
	    	logger.error("Input error");
	        finished = true;
	      }
	    }
	    return s;
	  }

	  public static int inInt(String prompt) {
	    while (true) {
	      inputFlush();
	      printPrompt(prompt);
	      try {
	        return Integer.valueOf(inString().trim()).intValue();
	      }

	      catch (NumberFormatException e) {
	    	 logger.error("Invalid input. Not an integer");
	      }
	    }
	  }

	  public static char inChar(String prompt) {
	    int aChar = 0;

	    inputFlush();
	    printPrompt(prompt);

	    try {
	      aChar = System.in.read();
	    }

	    catch (java.io.IOException e) {
	    	logger.error("Input error");
	    }
	    inputFlush();
	    return (char) aChar;
	  }

	  public static double inDouble(String prompt) {
	    while (true) {
	      inputFlush();
	      printPrompt(prompt);
	      try {
	        return Double.valueOf(inString().trim()).doubleValue();
	      }

	      catch (NumberFormatException e) {
	    	 logger.error("Invalid input. Not a floating point number");
	      }
	    }
	  }
}
