package cmd_mission;

public interface check_obj_propertyRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/check_obj_propertyRequest";
  static final java.lang.String _DEFINITION = "string name\nstring property\n";
  java.lang.String getName();
  void setName(java.lang.String value);
  java.lang.String getProperty();
  void setProperty(java.lang.String value);
}
