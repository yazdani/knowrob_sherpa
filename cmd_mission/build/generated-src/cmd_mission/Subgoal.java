package cmd_mission;

public interface Subgoal extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/Subgoal";
  static final java.lang.String _DEFINITION = "string property\nstring kind\n";
  java.lang.String getProperty();
  void setProperty(java.lang.String value);
  java.lang.String getKind();
  void setKind(java.lang.String value);
}
