/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public interface RobotMap {
  public static int DT_BACK_LEFT = 1;//Talon
  public static int DT_MIDDLE_LEFT = 2;//Victor
  public static int DT_FRONT_LEFT = 3;//Victor
  public static int DT_BACK_RIGHT = 4;//Talon
  public static int DT_MIDDLE_RIGHT = 5;//Victor
  public static int DT_FRONT_RIGHT = 6;//Victor
  public static int PCM = 15;
  public static int LIFT_LEFT = 7;//Talon
  public static int LIFT_RIGHT = 8;//Victor
  public static int ARM_UP_LEFT = 9;//Talon
  public static int ARM_UP_RIGHT = 10;//Victor
  public static int ARM_IN_LEFT = 11;//Victor
  public static int ARM_IN_RIGHT = 12;//Victor

  public static int CHOOSER_TESTER = 0;//Temporary

  public static final int DT_SOLENOID_0 = 0;
  public static final int DT_SOLENOID_1 = 7;
  public static final int PISTON_SOLENOID_1 = 1;
  public static final int PISTON_SOLENOID_2 = 6;
}
