package com.github.knowrob_sherpa;

import java.util.ArrayList;

public class SARInterface{
    
    /**
     * Getting all Objects of the map
     *
     **/
    public String[] getAllObjects(String[] objs)
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

    /**
     *  Getting all Properties
     *
     **/
    public String[] getAllProperties()
    {
	ArrayList<String> list1 = new ArrayList<>();	
	list1.add("red");
	list1.add("darkred");
	list1.add("green");
	list1.add("darkgreen");
	list1.add("blue");
	list1.add("white");
	list1.add("yellow");
	list1.add("darkgray");
	list1.add("gray");
	list1.add("large");
	list1.add("small");
	list1.add("tree");
	list1.add("victim");
	list1.add("mountain");
	list1.add("rock");
	list1.add("behind");
	list1.add("left");
	list1.add("right");
	list1.add("in-front-of");
	list1.add("close-to");
	
	String[] str = new String[list1.size()];

	int counter = 0;

	for(int index=0; index < list1.size() ; index++)
	    {
		counter++;
		if(counter <= 15)
		    {
			str[index] = list1.get(index)+" - "+"property";
		    }else
		    {
			str[index] = list1.get(index)+" - "+"relation";
		    }
	    }
	
	return str;
    }

    public String[] elemsToList(String type, String color)
    {
	String[] str = new String[2];
	if(type.contains("#"))
	    {
		System.out.println("test1");
		String[] parts = type.split("#");
		System.out.println(parts[1]);
		if(parts[1].equals("bigtree"))
		    {
		    str[0] = "tree";
		    }else
		    {
		    str[0] = parts[1];
		    }
	    }else if(!type.contains("#") && type.contains(":"))
	    {
		System.out.println("test2");
		String[] parts = type.split(":");
		if(parts[1].equals("bigtree"))
		    {
			str[0] = "tree";
		    }else
		    {
			str[0] = parts[1];
		    }
	    }else
	    {
		System.out.println("test3");
		if(type.equals("bigtree"))
		    {
			str[0] = "tree";
		    }else
		    {
			str[0] = type;
		    }
		
	    }
	str[1] = color;
	return str;
    }
    
    public String replaceString(String type)
    {
	String result = "";
	if(type.contains("#"))
	    {
		String[] parts = type.split("#");
		if(parts[1].equals("bigtree"))
		    {
		    result = "tree";
		    }else
		    {
		    result = parts[1];
		    }
	    }else if(!type.contains("#") && type.contains(":"))
	    {
		String[] parts = type.split(":");
		if(parts[1].equals("bigtree"))
		    {
			result = "tree";
		    }else
		    {
			result = parts[1];
		    }
	    }else
	    {
		if(type.equals("bigtree"))
		    {
			result = "tree";
		    }else
		    {
			result = type;
		    }
		
	    }
	return addNamespace(result);
    }
    public String addNamespace(String obj)
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
