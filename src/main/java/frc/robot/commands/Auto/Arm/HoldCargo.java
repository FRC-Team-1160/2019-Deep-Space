/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.Arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class HoldCargo extends Command {
  private int top;
  private int error;
  private int errorDirection; //-1 or 1
  public HoldCargo() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.am);
    top = -750;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    error = top - Robot.am.getPosition();
    if(error != 0){
      errorDirection = (int)(error / Math.abs(error));
    }  
    else{
      errorDirection = 0;
    }
    if (errorDirection*0.3 < 0 && ((error) < 0)) { //Only go up!
    		Robot.am.setPercentOutputArm(error*0.0012);
      }
      else{
        Robot.am.setPercentOutputArm(0);

      }
  }

  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //return ((Math.abs(error) < 100));
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.am.setPercentOutputArm(0);
  }
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.am.setPercentOutputArm(0);
  }
}
