/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.commands.SetChooser;

/**
 * Add your docs here.
 */
public class Tester extends Subsystem implements RobotMap {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static Tester instance;
  private WPI_TalonSRX testTalon;

  public static Tester getInstance(){
    if(instance == null){
      instance = new Tester();
    }
    return instance;
  }

  private Tester(){
    testTalon = new WPI_TalonSRX(CHOOSER_TESTER);
  }

  public void set(double input){
    testTalon.set(input);
  }
  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
