/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Robot;
import frc.robot.commands.Arm.controlArm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.RobotMap;


import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class Arm extends Subsystem implements RobotMap {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static Arm instance;
  private WPI_TalonSRX upLeft;
  private WPI_VictorSPX upRight, inLeft, inRight;

  public static Arm getInstance(){
    if(instance == null){
      instance = new Arm();
    }
    return instance;
  }

  private Arm(){
    upLeft = new WPI_TalonSRX(ARM_UP_LEFT);
    upRight = new WPI_VictorSPX(ARM_UP_RIGHT);
    inLeft = new WPI_VictorSPX(ARM_IN_LEFT);
    inRight = new WPI_VictorSPX(ARM_IN_RIGHT);
  }

  public void controlArm(){
    upLeft.set(ControlMode.PercentOutput, (Math.pow((Robot.oi.getArmStick().getY()), 1)));
    SmartDashboard.putNumber("Arm Encoder",upLeft.getSelectedSensorPosition());
  }

  public void setUp(double input){
    upLeft.set(input);
    upRight.set(input);
  }

  public void setIn(double input){
    inLeft.set(input);
    inRight.set(-input);
  }
  
  public void stopUp(){
    upLeft.set(0);
    upRight.set(0);
  }

  public void stopIn(){
    inLeft.set(0);
    inRight.set(0);
  }

  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new controlArm());
  }
}
