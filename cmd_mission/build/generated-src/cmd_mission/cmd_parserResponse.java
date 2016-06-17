package cmd_mission;

public interface cmd_parserResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/cmd_parserResponse";
  static final java.lang.String _DEFINITION = "string result";
  java.lang.String getResult();
  void setResult(java.lang.String value);
}
