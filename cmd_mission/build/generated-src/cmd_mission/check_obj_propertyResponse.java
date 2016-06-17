package cmd_mission;

public interface check_obj_propertyResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/check_obj_propertyResponse";
  static final java.lang.String _DEFINITION = "bool result_property";
  boolean getResultProperty();
  void setResultProperty(boolean value);
}
