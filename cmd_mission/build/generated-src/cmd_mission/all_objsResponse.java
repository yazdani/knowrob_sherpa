package cmd_mission;

public interface all_objsResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/all_objsResponse";
  static final java.lang.String _DEFINITION = "string[] result_all";
  java.util.List<java.lang.String> getResultAll();
  void setResultAll(java.util.List<java.lang.String> value);
}
