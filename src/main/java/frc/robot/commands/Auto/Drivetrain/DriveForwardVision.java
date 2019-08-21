/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.Drivetrain;
import frc.robot.Robot;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForwardVision extends Command implements RobotMap{
  private double lDistance, rDistance;
  public DriveForwardVision() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.pid);
    requires(Robot.dt);
    //requires(Robot.vs);
    this.lDistance = Robot.dt.getLeftMaster().getSelectedSensorPosition();
    this.rDistance = Robot.dt.getRightMaster().getSelectedSensorPosition();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //System.out.println("The distance is: "+distance);
    Robot.dt.resetGyro();
    Robot.dt.resetTurnAngleIntegral();
    Robot.dt.resetTime();
    Robot.dt.startTime();
    this.lDistance = Robot.dt.getLeftMaster().getSelectedSensorPosition();
    this.rDistance = Robot.dt.getRightMaster().getSelectedSensorPosition();

    //System.out.println("Im Stupid");
    //Robot.pid.goDistance(((Robot.vs.distanceToTarget*CONTROLLER_CONSTANT_L) + lDistance - (6*CONTROLLER_CONSTANT_L)), ((-Robot.vs.distanceToTarget*CONTROLLER_CONSTANT_R) + rDistance) + (6*CONTROLLER_CONSTANT_R));
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if((Math.abs(Robot.dt.getLeftMaster().getClosedLoopError(0)) < 100 ) && ((Math.abs(Robot.dt.getRightMaster().getClosedLoopError(0)) < 100))){
      System.out.println("Im finished driving from Vision");
  
      return true;
    }
    //System.out.println("Im still driving from Vision");

    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.vs.resetVision();

    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
