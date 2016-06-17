package cmd_mission;

public interface cmd_parserRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/cmd_parserRequest";
  static final java.lang.String _DEFINITION = "string goal\n";
  java.lang.String getGoal();
  void setGoal(java.lang.String value);
}
