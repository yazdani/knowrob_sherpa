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

public class QuadrotorScanInterface extends AbstractNodeMain{
    public static String result;
    
    public ConnectedNode node;
    ServiceClient<quadrotor_controller.scan_regRequest, quadrotor_controller.scan_regResponse> serviceClient;

    //  public SARInterface() {
	
    //	System.out.println("SAR-Interface is starting");
    // }
    
 @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("quadrotor_controller/ScanClient");
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
	String service_name = "scanRegion";
	serviceClient = node.newServiceClient(service_name, quadrotor_controller.scan_reg._TYPE);
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
  }
    public void calledTheService (String value)
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

	    quadrotor_controller.scan_regRequest request;
	    request = serviceClient.newMessage();
	    request.setStart(value);

	    serviceClient.call(request,new ServiceResponseListener<quadrotor_controller.scan_regResponse>()				
			{
			    @Override
			    public void onSuccess(quadrotor_controller.scan_regResponse response) {
		
				setResult(response.getReply());
				
	}
			@Override
			public void onFailure(RemoteException e) {
				System.out.println("Failed to call the service");
				throw new RosRuntimeException(e);
			}
					    });
}

    public String scanArea(String text)
    {
	result = "Executing task";
	calledTheService(text);

	return "Start task execution";
   }


    public void setResult(String name)
    {
	result = name;
    }

    public String checkResult()
    {
	return result;
    }

}
