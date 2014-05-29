// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VertifyLicence.java

package com.common.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.common.licence.DesUtils;
import com.common.licence.Licence;
import com.common.licence.LicenceUtil;
import com.common.licence.LicenseNotFoundException;
import com.common.licence.MachineNo;
import com.common.licence.SignatureNotRightException;

public class VertifyLicence
{

	private VertifyLicence(){
	}

	public static Map getInfo(String path)
		throws LicenseNotFoundException, SignatureNotRightException
	{
		Map map = new HashMap();
		String pubKeyString = "308201B83082012C06072A8648CE3804013082011F02818100FD7F53811D75122952DF4A9C2EECE4E7F611B7523CEF4400C31E3F80B6512669455D402251FB593D8D58FABFC5F5BA30F6CB9B556CD7813B801D346FF26660B76B9950A5A49F9FE8047B1022C24FBBA9D7FEB7C61BF83B57E7C6A8A6150F04FB83F6D3C51EC3023554135A169132F675F3AE2B61D72AEFF22203199DD14801C70215009760508F15230BCCB292B982A2EB840BF0581CF502818100F7E1A085D69B3DDECBBCAB5C36B857B97994AFBBFA3AEA82F9574C0B3D0782675159578EBAD4594FE67107108180B449167123E84C281613B7CF09328CC8A6E13C167A8B547C8D28E0A3AE1E2BB3A675916EA37F0BFA213562F1FB627A01243BCCA4F1BEA8519089A883DFE15AE59F06928B665E807B552564014C3BFECF492A0381850002818100B08CDA65720D16DE970920FD43E7BFB542CBCDDAF6C83C20424163BB7DE19C757FA58C5110EFE5F8E7D9E7AA5BFBEB5866E43932207EB85D86A947CB247BB12E1FF790A598E1A84F058C15737E5091294046A20320915FE4FBABAC6CD6B4D247ADB1F3745F137EE64D901FD4DF0F16C4CDCD41765C20D8827E6A819EE8802DAD";
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("../../licence/"+path);
		if (inputStream == null){
			throw new LicenseNotFoundException("License file not found filePath = " + path);
		}
			
		Licence licence=null;
		try
		{
			DesUtils des = new DesUtils("mega");
			String code = MachineNo.getMACAddress();
			code = des.encrypt(code).substring(0, 8);
			licence = LicenceUtil.readLicence(inputStream);
			licence.addFeature("machineCode", code.toUpperCase());
			
			System.out.println(licence.getFeaturesAsString(false));
		}
		catch (IOException e)
		{
			throw new LicenseNotFoundException("License file read error!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isRight = LicenceUtil.verify(licence, pubKeyString);
		if (isRight)
		{
			System.out.println("��֤�ɹ�");
			map = licence.getFeaturesAsMap();
		} else
		{  
			throw new SignatureNotRightException();
		}
		return map;
	}

	public static void main(String s[])
	{
		try
		{
			System.out.println(System.getProperty("java.class.path"));
			Map map = getInfo("megaeyes.licence");
			String key;
			String value;
			for (Iterator iterator = map.keySet().iterator(); iterator.hasNext(); System.out.println(key + "=" + value))
			{
				key = (String)iterator.next();
				value = (String)map.get(key);
			}

		}
		catch (LicenseNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SignatureNotRightException e)
		{
			e.printStackTrace();
		}
	}
}
