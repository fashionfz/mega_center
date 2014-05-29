// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LicenseNotFoundException.java

package com.common.licence;


public class LicenseNotFoundException extends Exception
{

	public LicenseNotFoundException()
	{
		super("License �ļ�������");
	}

	public LicenseNotFoundException(String s)
	{
		super(s);
	}

	public LicenseNotFoundException(Exception e)
	{
		super(e);
	}
}
