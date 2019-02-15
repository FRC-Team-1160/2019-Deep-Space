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

  //Motor Controllers
  public static int DT_BACK_LEFT = 1;//Talon
  public static int DT_MIDDLE_LEFT = 2;//Victor
  public static int DT_FRONT_LEFT = 3;//Victor
  public static int DT_BACK_RIGHT = 4;//Talon
  public static int DT_MIDDLE_RIGHT = 5;//Victor
  public static int DT_FRONT_RIGHT = 6;//Victor
  public static int LIFT_LEFT = 7;//Talon
  public static int LIFT_RIGHT = 8;//Victor
  public static int ARM_UP_LEFT = 9;//Talon
  public static int ARM_UP_RIGHT = 10;//Victor
  public static int ARM_IN_LEFT = 11;//Talon
  public static int ARM_IN_RIGHT = 12;//Talon

  //Electrical System CAN Locations
  public static int PCM = 15;
  public static int PDP = 16;

  //Solenoid ports on PCM
  public static final int DT_SOLENOID_0 = 1;
  public static final int DT_SOLENOID_1 = 6;
  public static final int PISTON_SOLENOID_1 = 0;
  public static final int PISTON_SOLENOID_2 = 7;

  public static int CHOOSER_TESTER = 0;//Temporary

  //Drivetrain Control Constants
  public static final double WHEEL_DIAMETER = 6;
  public static final double CONTROLLER_CONSTANT_L = 2239/(WHEEL_DIAMETER*Math.PI);
  public static final double CONTROLLER_CONSTANT_R = 2261*.98/(WHEEL_DIAMETER*Math.PI);
    //Turnangle Constant
  public static final double GYRO_KP_2 = 0.025; 
  public static final double GYRO_KI = 0.0005;
  public static final double GYRO_KD = 0.000001;
  public static final double GYRO_TOLERANCE = 3;//Smaller value means higher accuracy but more time spent
  public static final double TURN_TIMEOUT = 2.5;
      //achieving said accuracy
  public static final double GYRO_CAP = 0.5; //max speed of the turn during TurnAngles
  public static final double GYRO_KI_CAP = 0.05;
  


  //Arm Control Constants
  public static final double ARM_SLOWYDOWNY = .00009;

  //Lift Control Constants

  /*
  // --- IGNORE ---
  // Drive Train Encoder Value
  // left forward 1 turn = -2228 2 turns = -4485 1 turn = -2249
  // -2228, -2242, -2249
  // right forward 1 turn = 2266 2 turn = 4516 3 turn = 6775
  // 2266, 2258, 2258
  // --- End Ignore ---
  */

}
