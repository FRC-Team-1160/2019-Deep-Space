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

public class DriveForward extends Command {
  private double distance;
  public DriveForward(double d) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.pid);
    requires(Robot.dt);
    requires(Robot.vs);
    this.distance = d;
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("The distance is: "+distance);
    Robot.dt.resetPosition();
    Robot.dt.resetGyro();
    Robot.dt.resetTurnAngleIntegral();
    Robot.dt.resetTime();
    Robot.dt.startTime();
    while(Math.abs(Robot.dt.getLeftMaster().getSelectedSensorPosition()) > 0.1 || Robot.dt.getRightMaster().getSelectedSensorPosition() > 0.1){
      //System.err.println("The reset method lies");
      Robot.dt.resetPosition();
    }
    //System.out.println("Im Stupid");
    Robot.pid.goDistance(distance*.90);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if((Math.abs(Robot.dt.getLeftMaster().getClosedLoopError(0)) < 100 ) && ((Math.abs(Robot.dt.getRightMaster().getClosedLoopError(0)) < 100))){
      System.out.println("Im finished Driving from input");
  
      return true;
    }
    System.out.println("Im still Driving from input");

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
