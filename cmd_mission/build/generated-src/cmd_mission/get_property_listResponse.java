package cmd_mission;

public interface get_property_listResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/get_property_listResponse";
  static final java.lang.String _DEFINITION = "Subgoal[] result_props";
  java.util.List<cmd_mission.Subgoal> getResultProps();
  void setResultProps(java.util.List<cmd_mission.Subgoal> value);
}
