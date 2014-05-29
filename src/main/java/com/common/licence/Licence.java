// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Licence.java

package com.common.licence;

import java.util.*;

public class Licence
{

	private Map featuresMap;
	private List featuresList;
	private String signature;

	public Licence()
	{
		featuresMap = new HashMap();
		featuresList = new ArrayList();
	}

	public void addFeature(String key, String info)
	{
		featuresMap.put(key, info);
		featuresList.add(key + "=" + info);
	}

	public void setSignature(String signature)
	{
		this.signature = signature;
	}

	public String getSignature()
	{
		return signature;
	}

	public List getFeaturesAsLsit()
	{
		return featuresList;
	}

	public Map getFeaturesAsMap()
	{
		return featuresMap;
	}

	public String getFeaturesAsString(boolean isEnter)
	{
		String features = "";
		for (Iterator iterator = featuresList.iterator(); iterator.hasNext();)
		{
			String feature = (String)iterator.next();
			if (isEnter)
				feature = feature + "\n";
			features = features + feature;
		}

		return features;
	}
}
