/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Lift;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SetLed extends Command {
  private boolean On_or_off;
  public SetLed(boolean On_or_off) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.lt);
    this.On_or_off = On_or_off;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(On_or_off){
      Robot.lt.ledOn();
    }
    if(!On_or_off){
      Robot.lt.ledOff();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
