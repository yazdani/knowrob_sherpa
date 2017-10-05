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

public class ClientInterface extends AbstractNodeMain{
    
    private String property;
    private String object1;
    private String object2;
    public ArrayList<String> results;

    public int result;
    
    public ConnectedNode node;
    ServiceClient<cmd_mission.salient_objsRequest, cmd_mission.salient_objsResponse> serviceClient;
    
 @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("cmd_mission/Client");
  }
    
  @Override
  public void onStart(final ConnectedNode connectedNode) {
      this.node = connectedNode;
      try {
	  while(node == null) {
	      Thread.sleep(100);
	  }
      } catch (InterruptedException e) {
	  e.printStackTrace();
      }

    try {
	String service_name = "salient_objs";
	serviceClient = node.newServiceClient(service_name, cmd_mission.salient_objs._TYPE);
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
  }
    public void calledService ()
	{
	    try {
        	 serviceClient = null;
		 while(serviceClient == null) {
		     Thread.sleep(100);
			 }
		 } catch (InterruptedException e) {

			 e.printStackTrace();
		 }
	    cmd_mission.salient_objsRequest request;
	    request = serviceClient.newMessage();
	    request.setSal("test");
	    serviceClient.call(request,new ServiceResponseListener<cmd_mission.salient_objsResponse>()				
			{
			    @Override
			    public void onSuccess(cmd_mission.salient_objsResponse response) {
		
				setResults(response.getResultSalient());
				
	}
			@Override
			public void onFailure(RemoteException e) {
				System.out.println("Failed to call the service");
				throw new RosRuntimeException(e);
			}
					    });
}

    public ArrayList<String> setResults(List<String> res)
    {
	for(int index = 0; index < res.size(); index++)
	    {
		results.add(res.get(index));
	    }

	return results;
    }

    public String[] getAllSalientObjects()
    {
	results = new ArrayList<String>();
	calledService();
	try {
	    while(results.size() == 0) {
		Thread.sleep(100);
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	String[] array = new String[results.size()];
	array = results.toArray(array); 
	return array;

   }
}
