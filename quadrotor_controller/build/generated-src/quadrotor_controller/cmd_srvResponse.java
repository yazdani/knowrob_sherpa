package quadrotor_controller;

public interface cmd_srvResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "quadrotor_controller/cmd_srvResponse";
  static final java.lang.String _DEFINITION = "string reply";
  java.lang.String getReply();
  void setReply(java.lang.String value);
}
