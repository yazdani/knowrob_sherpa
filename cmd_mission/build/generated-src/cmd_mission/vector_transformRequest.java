package cmd_mission;

public interface vector_transformRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/vector_transformRequest";
  static final java.lang.String _DEFINITION = "uint32[] goal\n";
  int[] getGoal();
  void setGoal(int[] value);
}
