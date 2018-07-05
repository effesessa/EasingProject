package it.unical.utils;

import java.util.Random;

public class ArrayTest extends TestCase{
	
	public ArrayTest()
	{
		
	}
	
	public String generate()
	{
		int cont = 0;
		String ris=""; 
		while(cont<50)
		{
	    Random random = new Random();

	    int n = random.nextInt(20);
	    for (int i = 0; i < n; i++)
	    {
	        ris = ris+random.nextInt(70)+" ";
	    }
	    	ris = ris+"\n";
	    	cont++;
		}
		System.out.println(ris);
		return ris;
	}
}