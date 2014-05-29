package com.core.security;

import org.springframework.security.authentication.encoding.PasswordEncoder;

public class MD5Encoder implements PasswordEncoder{

	@Override
	public String encodePassword(String arg0, Object arg1) {
		return arg0;
	}

	@Override
	public boolean isPasswordValid(String arg0, String arg1, Object arg2) {
		return arg0.equals(encodePassword(arg1,arg2));
	}

}
