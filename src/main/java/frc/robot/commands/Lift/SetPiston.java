/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SetPiston extends Command {
  private boolean In_or_out;
  public SetPiston(boolean In_or_out) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.In_or_out = In_or_out;
    requires(Robot.lt);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(In_or_out){
      Robot.lt.extendPiston();
    }
    if(!In_or_out){
      Robot.lt.retractPiston();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(In_or_out){
      Robot.lt.extendPiston();
    }
    if(!In_or_out){
      Robot.lt.retractPiston();
    }
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
