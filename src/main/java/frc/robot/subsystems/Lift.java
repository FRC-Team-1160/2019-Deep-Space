/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Lift extends Subsystem implements RobotMap{
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static Lift instance;

  private DoubleSolenoid piston;
  private WPI_TalonSRX leftMotor;
  private WPI_VictorSPX rightMotor;

  public static Lift getInstance(){
    if(instance == null){
      instance = new Lift();
    }
    return instance;
  }
  private Lift(){
    leftMotor = new WPI_TalonSRX(LIFT_LEFT);
    rightMotor = new WPI_VictorSPX(LIFT_RIGHT);
    piston = new DoubleSolenoid(PCM, PISTON_SOLENOID_1, PISTON_SOLENOID_2);
  }

  public void setLift(double input){
    leftMotor.set(input);
    rightMotor.set(input);
  }

  public void stopLift(){
    leftMotor.set(0);
    rightMotor.set(0);
  }

  public void extendPiston(){
    piston.set(DoubleSolenoid.Value.kForward);
  }

  public void retractPiston(){
    piston.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
