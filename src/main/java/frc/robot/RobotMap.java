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
  public static int DT_LEFT_1 =1;
  public static int DT_LEFT_2 = 2;
  public static int DT_LEFT_3 = 3;
  public static int DT_RIGHT_1 = 4;
  public static int DT_RIGHT_2 = 5;
  public static int DT_RIGHT_3 = 6;
  public static int PCM = 10;
  public static int LIFT_LEFT = 0;//temporary
  public static int LIFT_RIGHT = 8;
  public static int ARM_UP_LEFT = 0;//temporary
  public static int ARM_UP_RIGHT = 0; // temporary
  public static int ARM_IN_LEFT = 0;//temporary
  public static int ARM_IN_RIGHT = 0;//temporary
  public static int CHOOSER_TESTER = 7;

  public static final int DT_SOLENOID_0 = 0;
  public static final int DT_SOLENOID_1 = 7;
  public static final int PISTON_SOLENOID_1 = 1;
  public static final int PISTON_SOLENOID_2 = 6;
}
