package com.github.knowrob_sherpa;

import java.util.ArrayList;

public class SARInterface{
    
    public String[] queryAllObjects(String[] objs)
    {
	//	System.out.println("queryAllObjects");
	ArrayList<String> list = new ArrayList<>();
	for(int index = 0; index < objs.length; index++)
	    {
		if(objs[index].contains("#"))
		    {
			String[] parts = objs[index].split("#");
			list.add(parts[1]);
		    }else if(!objs[index].contains("#") && objs[index].contains(":"))
		    {
			String[] parts = objs[index].split(":");
			list.add(parts[1]);
		    }else
		    {
			list.add(objs[index]);
		    }
	    }
	String[] array = new String[list.size()];
	array = list.toArray(array); 
	return array;
    }

    public String bindNamespace(String obj)
    {
	return "http://knowrob.org/kb/knowrob.owl#"+obj;
    }

    public boolean checkObjectType(String objname, String objtype)
    {
	if(objname.contains("#"))
	    {
		String[] parts = objname.split("#");
		return parts[1].equals(objtype);
	    }else if(!objname.contains("#") && objname.contains(":"))
	    {
		String[] parts = objname.split(":");
		 return parts[1].equals(objtype);
	    }
	
	return objname.equals(objtype);
    }

    public String getObjectType(String objtype)
    {
	if(objtype.contains("#"))
	    {
		String[] parts = objtype.split("#");
		return parts[1];
	    }else if(!objtype.contains("#") && objtype.contains(":"))
	    {
		String[] parts = objtype.split(":");
		 return parts[1];
	    }
		
	return objtype;
    }

    public SARInterface() {
	System.out.println("SAR-Interface is starting");
    }

}
