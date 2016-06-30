package cmd_mission;

public interface salient_objsResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/salient_objsResponse";
  static final java.lang.String _DEFINITION = "string[] result_salient";
  java.util.List<java.lang.String> getResultSalient();
  void setResultSalient(java.util.List<java.lang.String> value);
}
