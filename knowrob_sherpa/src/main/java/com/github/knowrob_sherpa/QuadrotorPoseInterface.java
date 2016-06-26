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

import quadrotor_controller.*;

public class QuadrotorPoseInterface extends AbstractNodeMain{
    private String result;
    
    public ConnectedNode node;
    ServiceClient<quadrotor_controller.cmd_srvRequest, quadrotor_controller.cmd_srvResponse> serviceClient;

    //  public SARInterface() {
	
    //	System.out.println("SAR-Interface is starting");
    // }
    
 @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("quadrotor_controller/Client");
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
	String service_name = "setPoseToVel";
	serviceClient = node.newServiceClient(service_name, quadrotor_controller.cmd_srv._TYPE);
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
  }
    public void calledTheService (double[] objpose)
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
	    geometry_msgs.Pose pos = node.getTopicMessageFactory().newFromType(geometry_msgs.Pose._TYPE);
	    geometry_msgs.Point point = node.getTopicMessageFactory().newFromType(geometry_msgs.Point._TYPE);
	    geometry_msgs.Quaternion quat = node.getTopicMessageFactory().newFromType(geometry_msgs.Quaternion._TYPE);
	    point.setX(objpose[0]);
	    point.setY(objpose[1]);
	    point.setZ(12.0);
	    quat.setX(objpose[3]);
	    quat.setY(objpose[4]);
	    quat.setZ(objpose[5]);
	    quat.setW(objpose[6]);
            pos.setPosition(point);
	    pos.setOrientation(quat);
	    quadrotor_controller.cmd_srvRequest request;
	    request = serviceClient.newMessage();
	    request.setGoal(pos);
	    serviceClient.call(request,new ServiceResponseListener<quadrotor_controller.cmd_srvResponse>()				
			{
			    @Override
			    public void onSuccess(quadrotor_controller.cmd_srvResponse response) {
		
				response.getReply();
				
	}
			@Override
			public void onFailure(RemoteException e) {
				System.out.println("Failed to call the service");
				throw new RosRuntimeException(e);
			}
					    });
}

    public String sendPose(String text)
    {
	double[] array = getPose(text);
	calledTheService(array);
	return "finally";
   }


    public double[] getPose(String res)
    {
	String[] coms = res.split(",");

	double[] nums = new double[coms.length];
	
	for(int index= 0; index < coms.length; index++)
	    {
		nums[index] = Double.parseDouble(coms[index]);
		
	    }
 
	return nums;
    }

}
