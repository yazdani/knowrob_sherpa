package quadrotor_controller;

public interface cmd_srvRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "quadrotor_controller/cmd_srvRequest";
  static final java.lang.String _DEFINITION = "geometry_msgs/Pose goal\n";
  geometry_msgs.Pose getGoal();
  void setGoal(geometry_msgs.Pose value);
}
