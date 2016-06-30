package cmd_mission;

public interface internal_relationRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "cmd_mission/internal_relationRequest";
  static final java.lang.String _DEFINITION = "string property\nstring obj1\nstring obj2\n";
  java.lang.String getProperty();
  void setProperty(java.lang.String value);
  java.lang.String getObj1();
  void setObj1(java.lang.String value);
  java.lang.String getObj2();
  void setObj2(java.lang.String value);
}
