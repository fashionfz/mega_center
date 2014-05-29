// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LicenceUtil.java

package com.common.licence;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package mega.eyes.licence:
//			Translate, Licence

public class LicenceUtil
{

	private static KeyFactory keyFactory;

	private LicenceUtil()
	{
	}

	private static PublicKey getPublicKey(byte abyte0[])
		throws InvalidKeySpecException
	{
		return keyFactory.generatePublic(new X509EncodedKeySpec(abyte0));
	}

	private static PrivateKey getPrivateKey(byte abyte0[])
		throws InvalidKeySpecException
	{
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(abyte0));
	}

	public static void createKeyPair()
	{
		try
		{
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
			keyPairGenerator.initialize(1024, new SecureRandom((new SecureRandom()).generateSeed(16)));
			KeyPair keys = keyPairGenerator.generateKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();
			FileOutputStream out = new FileOutputStream("myprikey.dat");
			out.write(Translate.translateBytesToString(prikey.getEncoded()).getBytes());
			out.close();
			out = new FileOutputStream("mypubkey.dat");
			out.write(Translate.translateBytesToString(pubkey.getEncoded()).getBytes());
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String sign(Licence licence, String priKeyString)
	{
		String s = "";
		try
		{
			byte priKeyEncoded[] = Translate.translateStringToBytes(priKeyString);
			Signature signet = Signature.getInstance("DSA");
			signet.initSign(getPrivateKey(priKeyEncoded));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			licence.addFeature("issuse", formatter.format(new Date()));
			signet.update(licence.getFeaturesAsString(false).getBytes());
			byte signed[] = signet.sign();
			String signature = Translate.translateBytesToString(signed);
			s = licence.getFeaturesAsString(true);
			s = s + "signature=" + signature;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}

	public static boolean verify(Licence licence, String pubKeyString)
	{
		boolean isRight = false;
		try
		{
			byte pubKeyEncoded[] = Translate.translateStringToBytes(pubKeyString);
			Signature signetcheck = Signature.getInstance("DSA");
			signetcheck.initVerify(getPublicKey(pubKeyEncoded));
			signetcheck.update(licence.getFeaturesAsString(false).getBytes());
			if (signetcheck.verify(Translate.translateStringToBytes(licence.getSignature())))
				isRight = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return isRight;
	}

	public static Licence readLicence(InputStream inputstream)
		throws IOException
	{
		BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
		Licence license = new Licence();
		String line;
		while ((line = bufferedreader.readLine()) != null) 
			if (!"".equals(line.trim()))
			{
				int i = line.indexOf('=');
				String key;
				String info;
				if (i > 0)
				{
					key = line.substring(0, i).trim();
					info = line.substring(i + 1).trim();
				} else
				{
					key = line.trim();
					info = "";
				}
				if ("signature".equals(key))
					license.setSignature(info);
				else
					license.addFeature(key, info);
			}
		return license;
	}

	static 
	{
		try
		{
			keyFactory = KeyFactory.getInstance("DSA");
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
}
