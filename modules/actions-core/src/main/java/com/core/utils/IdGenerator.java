package com.core.utils;

import java.util.UUID;

public class IdGenerator {

	private IdGenerator()
	{
		
	}
	
	public static String getNewId()
	{
		return UUID.randomUUID().toString();
	}
}
