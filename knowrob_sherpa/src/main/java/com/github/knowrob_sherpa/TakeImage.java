package com.github.knowrob_sherpa;

import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import java.lang.Double;
import org.ros.exception.RemoteException;
import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;

import geometry_msgs.Pose;

import img_mission.*;

public class TakeImage extends AbstractNodeMain{
    public static String result;
    
    public ConnectedNode node;
    ServiceClient<img_mission.returnStringRequest, img_mission.returnStringResponse> serviceClient;

    //  public SARInterface() {
	
    //	System.out.println("SAR-Interface is starting");
    // }
    
 @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("img_mission/TakeImageClient");
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
	String service_name = "store_image";
	serviceClient = node.newServiceClient(service_name, img_mission.returnString._TYPE);
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
  }

    public void calledTheService (String value)
	{
	    System.out.println("tes3");
	    try {
        	 serviceClient = null;
		 while(serviceClient == null) {
		     //	     System.out.println("Waiting for service client.");
		     Thread.sleep(100);
			 }
		 } catch (InterruptedException e) {
  System.out.println("twewwes3");
			 e.printStackTrace();
		 }
	    System.out.println("tes3sss");
	     img_mission.returnStringRequest request;
  System.out.println("tes3ddsd");
	    request = serviceClient.newMessage();
	    request.setGoal(value);
	    System.out.println("test5");
	    serviceClient.call(request,new ServiceResponseListener< img_mission.returnStringResponse>()				
			{
			    @Override
			    public void onSuccess( img_mission.returnStringResponse response) {
		
				setResult(response.getResult());
				
	}
			@Override
			public void onFailure(RemoteException e) {
				System.out.println("Failed to call the service");
				throw new RosRuntimeException(e);
			}
					    });
}

    public String takeImage(String text)
    {
	System.out.println("test");
	result = "";
	System.out.println(text);
	calledTheService(text);
	System.out.println("test1");
	try {
	    while(result.equals("")) {
		Thread.sleep(100);
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println("test2");
	return result;
   }


  public void setResult(String res)
    {
	result = res;
    }


}
