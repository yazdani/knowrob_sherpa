package com.github.knowrob_sherpa;

import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import java.lang.Float;
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

import quadrotor_controller.*;

public class QuadrotorPointsInterface extends AbstractNodeMain{
    public static String result;
    public static float[] nums  = new float[7];
    public ConnectedNode node;
    ServiceClient<quadrotor_controller.cmd_pointsRequest, quadrotor_controller.cmd_pointsResponse> serviceClient;

    //  public SARInterface() {
	
    //	System.out.println("SAR-Interface is starting");
    // }
    
 @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("quadrotor_controller_points/Client");
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
	String service_name = "setRobotPoints";
	serviceClient = node.newServiceClient(service_name, quadrotor_controller.cmd_points._TYPE);
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
  }
    public void calledTheService (float[] objpose)
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
	    //int[] ret = getPose(objpose);
	    quadrotor_controller.cmd_pointsRequest request;
	    request = serviceClient.newMessage();
	    request.setX(objpose[0]);
	    request.setY(objpose[1]);
	    request.setZ(objpose[2]);
	    request.setQx(objpose[3]);
	    request.setQy(objpose[4]);
	    request.setQz(objpose[5]);
	    request.setQw(objpose[6]);
	    serviceClient.call(request,new ServiceResponseListener<quadrotor_controller.cmd_pointsResponse>()				
			{
			    @Override
			    public void onSuccess(quadrotor_controller.cmd_pointsResponse response) {
		
				setResult(response.getRepl());
				
	}
			@Override
			public void onFailure(RemoteException e) {
				System.out.println("Failed to call the service");
				throw new RosRuntimeException(e);
			}
					    });
}

    public String sendPose()
    {
	result = "Executing task";
	//float[] array = getPose(text);
	calledTheService(nums);

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

    public void getPose(String res)
    {
	String[] coms = res.split(",");
	String[] attach = new String[7];
	String[] sec = new String[2];
	for(int intel = 0; intel < coms.length; intel++)
	    {
		sec = coms[intel].split("d");
		attach[intel] = sec[0];
		
	    }

	   int intVal = 0;
 
	for(int index= 0; index < attach.length; index++)
	    {
		    System.out.println(Float.parseFloat(attach[index]));
		try {
		    double tmp = Double.parseDouble(attach[index]);
		    float tmp2 = (float) tmp;
		    nums[index] = tmp2;
		} catch (NumberFormatException e) {
		    //Log it if needed
		    float tmp = 0.0f;
		    nums[index] = tmp;

			}
	    }
 
	//	return nums;
    }

}
