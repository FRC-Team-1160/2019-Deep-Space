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
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Lift extends Subsystem implements RobotMap{
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static Lift instance;

  private DoubleSolenoid piston;
  private WPI_TalonSRX leftTalon;
  private WPI_TalonSRX rightTalon;

  public static Lift getInstance(){
    if(instance == null){
      instance = new Lift();
    }
    return instance;
  }

  private Lift(){
    leftTalon = new WPI_TalonSRX(LIFT_LEFT);
    rightTalon = new WPI_TalonSRX(LIFT_RIGHT);
    piston = new DoubleSolenoid(PISTON_SOLENOID_1, PISTON_SOLENOID_2);
  }

  public void setLift(double input){
    leftTalon.set(input);
    rightTalon.set(input);
  }

  public void stopLift(){
    leftTalon.set(0);
    rightTalon.set(0);
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
