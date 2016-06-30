package cmd_mission;

public interface get_obj_typeRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/get_obj_typeRequest";
  static final java.lang.String _DEFINITION = "string objname\n";
  java.lang.String getObjname();
  void setObjname(java.lang.String value);
}
