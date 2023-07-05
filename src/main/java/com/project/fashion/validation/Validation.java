package com.project.fashion.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

	public boolean nameValidation(String name)
	{
		Pattern p=Pattern.compile("[a-zA-Z]+");
		Matcher m=p.matcher(name);
		boolean name1=m.matches();
		if(name1)
		return true;
		else
		return false;
	}
	public String adminEmailValidation(String email)
	{
		Pattern p=Pattern.compile("^(.+)@(fashion)(.+)$");
		Matcher m=p.matcher(email);
		boolean mail=m.matches();
		if(mail)
			return "true";
			else
				return "false";
		
	}
	
	public boolean emailValidation(String email)
	{
		Pattern p=Pattern.compile("^(.+)@(.+)$");
		Matcher m=p.matcher(email);
		boolean mail=m.matches();
		if(mail)
			return true;
			else
				return false;
						}
	public boolean passwordValidation(String password)
	{
		Pattern p=Pattern.compile("[a-zA-Z0-9]{8,}");
		Matcher m=p.matcher(password);
		boolean pw=m.matches();
		if(pw)
			return true;
		else
			return false;	
	}
	public boolean phoneNumberValidation(String phoneNumber)
	{
		Pattern p=Pattern.compile("(0|91)?[6-9][0-9]{9}");
	    Matcher m=p.matcher(phoneNumber);
	    boolean phn=m.matches();
	    if(phn)
	       return true;
	    else
	    	return false; 
	}
	
	
}
