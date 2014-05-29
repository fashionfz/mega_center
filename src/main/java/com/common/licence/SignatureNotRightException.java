// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SignatureNotRightException.java

package com.common.licence;


public class SignatureNotRightException extends Exception
{

	public SignatureNotRightException()
	{
		super("License ǩ����ȷ");
	}

	public SignatureNotRightException(String s)
	{
		super(s);
	}
}
