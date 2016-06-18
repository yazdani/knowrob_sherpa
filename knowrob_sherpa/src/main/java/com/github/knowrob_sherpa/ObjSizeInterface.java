package com.github.knowrob_sherpa;

import java.util.ArrayList;
import java.util.List;
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

public class ObjSizeInterface extends AbstractNodeMain{
    private String result;
    
    public ConnectedNode node;
    ServiceClient<cmd_mission.get_obj_typeRequest, cmd_mission.get_obj_typeResponse> serviceClient;

    //  public SARInterface() {
	
    //	System.out.println("SAR-Interface is starting");
    // }
    
 @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("cmd_mission/Client");
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
	String service_name = "get_obj_size";
	serviceClient = node.newServiceClient(service_name, cmd_mission.get_obj_type._TYPE);
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
  }
    public void calledTheService (String objname)
	{
	    try {
        	 serviceClient = null;
		 while(serviceClient == null) {
		     //System.out.println("Waiting for service client.");
		     Thread.sleep(100);
			 }
		 } catch (InterruptedException e) {

			 e.printStackTrace();
		 }
	    cmd_mission.get_obj_typeRequest request;
	    request = serviceClient.newMessage();
	    request.setObjname(objname);
	    serviceClient.call(request,new ServiceResponseListener<cmd_mission.get_obj_typeResponse>()				
			{
			    @Override
			    public void onSuccess(cmd_mission.get_obj_typeResponse response) {
		
				setResult(response.getResultType());
				
	}
			@Override
			public void onFailure(RemoteException e) {
				System.out.println("Failed to call the service");
				throw new RosRuntimeException(e);
			}
					    });
}

    public String getObjectSize(String name)
    {
	result = "";
	calledTheService(name);
	try {
	    while(result.equals("")) {
		Thread.sleep(100);
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	return result;
   }


  public void setResult(String res)
    {
	result = res;
    }

    public String getResult()
    {
	return result;
    }

}
