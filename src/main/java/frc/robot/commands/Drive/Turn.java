/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Turn extends Command {
  private double targetAngle, currentAngle;
  private boolean isClockwise;
  private double speed = 0.6;

  public Turn() {
    requires(Robot.dt);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    targetAngle = 0;
    currentAngle = 0;
    isClockwise = false;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.dt.resetGyro();
    targetAngle = Robot.dt.getDisplacement() + Robot.dt.getAngle();;
    currentAngle = Robot.dt.getAngle();
    if(targetAngle < currentAngle){
      isClockwise = false;
    } else {
      isClockwise = true;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("target: "+targetAngle);
    System.out.println("current: "+currentAngle);
    currentAngle = Robot.dt.getAngle();
    if(targetAngle < currentAngle){
      Robot.dt.controlledDrive(-speed, -speed);
    } else {
      Robot.dt.controlledDrive(speed, speed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(isClockwise){
      if(targetAngle <= currentAngle){
        return true;
      }
    }else if(!isClockwise){
      if(targetAngle >= currentAngle){
        return true;
      }
    }
    return false;
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
