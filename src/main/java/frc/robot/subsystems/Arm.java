/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem implements RobotMap {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static Arm instance;
  private WPI_TalonSRX UpLeft;
  private WPI_VictorSPX UpRight, InLeft, InRight;

  public static Arm getInstance(){
    if(instance == null){
      instance = new Arm();
    }
    return instance;
  }

  private Arm(){
    UpLeft = new WPI_TalonSRX(ARM_UP_LEFT);
    UpRight = new WPI_VictorSPX(ARM_UP_RIGHT);
    InLeft = new WPI_VictorSPX(ARM_IN_LEFT);
    InRight = new WPI_VictorSPX(ARM_IN_RIGHT);
  }

  public void setUp(double input){
    UpLeft.set(input);
    UpRight.set(input);
  }

  public void setIn(double input){
    InLeft.set(input);
    InRight.set(-input);
  }
  
  public void stopUp(){
    UpLeft.set(0);
    UpRight.set(0);
  }

  public void stopIn(){
    InLeft.set(0);
    InRight.set(0);
  }

  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
