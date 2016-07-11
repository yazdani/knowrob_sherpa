package com.github.knowrob_sherpa;

import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import java.lang.Float;
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

import cmd_mission.*;

public class LispActionInterface extends AbstractNodeMain{
    public static String result;
    
    public ConnectedNode node;
    ServiceClient<cmd_mission.get_reason_poseRequest, cmd_mission.get_reason_poseResponse> serviceClient;

    //  public SARInterface() {
	
    //	System.out.println("SAR-Interface is starting");
    // }
    
 @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("cmd_controller_executer/Client");
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
	String service_name = "get_reason_pose";
	serviceClient = node.newServiceClient(service_name, cmd_mission.get_reason_pose._TYPE);
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
  }
    public void calledTheService (String action, String prep, String objname)
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
	    cmd_mission.get_reason_poseRequest request;
	    request = serviceClient.newMessage();
	    request.setAct(action);
	    request.setPrep(prep);
	    request.setObjname(objname);
	    serviceClient.call(request,new ServiceResponseListener<cmd_mission.get_reason_poseResponse>()				
			{
			    @Override
			    public void onSuccess(cmd_mission.get_reason_poseResponse response) {
		
				setResult(response.getResultPose());
				
	}
			@Override
			public void onFailure(RemoteException e) {
				System.out.println("Failed to call the service");
				throw new RosRuntimeException(e);
			}
					    });
}

    public String sendPose(String action, String prep, String name)
    {
	result = "Executing task";
	calledTheService(action, prep, name);

	return "Start task execution";
   }


    public void setResult(String name)
    {
	result = name;
    }

    public String getResult()
    {
	return result;
    }


}
