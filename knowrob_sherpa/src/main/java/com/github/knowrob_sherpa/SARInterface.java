package com.github.knowrob_sherpa;

import java.util.ArrayList;

import org.ros.exception.RemoteException;
import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;

import cmd_mission.*;

public class SARInterface extends AbstractNodeMain{
    
    private String property;
    private String object1;
    private String object2;
    public int result;
    
    public ConnectedNode node;
    ServiceClient<cmd_mission.check_objs_relationRequest, cmd_mission.check_objs_relationResponse> serviceClient;

    //  public SARInterface() {
	
    //	System.out.println("SAR-Interface is starting");
    // }
    
 @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("cmd_mission/client");
  }
    
  @Override
  public void onStart(final ConnectedNode connectedNode) {
      this.node = connectedNode;
      // wait for node to be ready
      try {
	  while(node == null) {
	      Thread.sleep(100);
	  }
      } catch (InterruptedException e) {
	  e.printStackTrace();
      }

    try {
	String service_name = "check_objs_relation";
	serviceClient = node.newServiceClient(service_name, cmd_mission.check_objs_relation._TYPE);
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
  }
    public void callService(String property, String obj1, String obj2)
	{
	    setProp(property);
	    setObject1(obj1);
	    setObject2(obj2);
	    //System.out.println(serviceClient+" haha ");
	    try {
		serviceClient = null;
		while(serviceClient == null) {
		    System.out.println("Waiting for service client.");
		    Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	    cmd_mission.check_objs_relationRequest request;
	    request = serviceClient.newMessage();
	    request.setProperty(property);
	    request.setObj1(obj1);
	    request.setObj2(obj2);
	    //System.out.println(serviceClient+" haha2 ");
	    serviceClient.call(request,new ServiceResponseListener<cmd_mission.check_objs_relationResponse>()				
			{
			    @Override
			    public void onSuccess(cmd_mission.check_objs_relationResponse response) {
		
				setResult(response.getResultCheck());		 
	}
			@Override
			public void onFailure(RemoteException e) {
				System.out.println("Failed to call the service");
				throw new RosRuntimeException(e);
			}
					    });
}
  public void setResult(boolean res)
    {
	if(!res)
	    {
		result = 0;
	    }else
	    {
		result = 1;
	    }
    }

    public int getResult()
    {
	return result;
    }
    
    public int checkRelationBetweenObjects(String property, String object1, String object2)
    {
	result = 3;
	callService(property,object1,object2);
	try {
	    while(result == 3) {
		Thread.sleep(100);
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
      return result;

   }

  

    public void setProp(String prop)
    {
	property = prop;
    }

    public String getProp()
    {
	return property;
    }

    public void setObject1(String prop)
    {
	object1 = prop;
    }

    public String getObject1()
    {
	return object1;
    }

    public void setObject2(String prop)
    {
	object2 = prop;
    }

    public String getObject2()
    {
	return object2;
    }

    /**
     * Getting all Objects of the map
     *
     **/
    public String[] getAllObjects(String[] objs)
    {
	//	System.out.println("queryAllObjects");
	ArrayList<String> list = new ArrayList<String>();
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
	ArrayList<String> list1 = new ArrayList<String>();	
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
    
  

}
