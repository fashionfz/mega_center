// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Translate.java

package com.common.licence;


public final class Translate
{

	private static final char chars[] = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		'A', 'B', 'C', 'D', 'E', 'F'
	};

	private Translate()
	{
	}

	public static String translateBytesToString(byte abyte0[])
	{
		StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
		for (int i = 0; i < abyte0.length; i++)
		{
			stringbuffer.append(chars[abyte0[i] >> 4 & 0xf]);
			stringbuffer.append(chars[abyte0[i] & 0xf]);
		}

		return stringbuffer.toString();
	}

	public static byte[] translateStringToBytes(String s)
	{
		byte abyte0[] = new byte[s.length() / 2];
		for (int i = 0; i < s.length(); i += 2)
		{
			if (i + 1 >= s.length())
				throw new IllegalArgumentException();
			byte byte0 = calculate(s.charAt(i));
			byte0 <<= 4;
			byte0 += calculate(s.charAt(i + 1));
			abyte0[i / 2] = byte0;
		}

		return abyte0;
	}

	private static byte calculate(char c1)
		throws IllegalArgumentException
	{
		byte byte0 = 0;
		if (c1 >= '0' && c1 <= '9')
			byte0 += c1 - 48;
		else
		if (c1 >= 'a' && c1 <= 'f')
			byte0 += (c1 - 97) + 10;
		else
		if (c1 >= 'A' && c1 <= 'F')
			byte0 += (c1 - 65) + 10;
		else
			throw new IllegalArgumentException();
		return byte0;
	}

}
