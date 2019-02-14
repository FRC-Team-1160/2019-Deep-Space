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

public class DriveForward extends Command implements RobotMap{
  private double leftDistance, rightDistance;
  public DriveForward(double d) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.pid);
    requires(Robot.dt);
    requires(Robot.vs);
    this.leftDistance = d*CONTROLLER_CONSTANT_L + Robot.dt.getLeftMaster().getSelectedSensorPosition();
    this.rightDistance = d*(CONTROLLER_CONSTANT_R) + Robot.dt.getRightMaster().getSelectedSensorPosition();

  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //System.out.println("The distance is: "+ leftDistance);

    Robot.dt.resetGyro();
    Robot.dt.resetTurnAngleIntegral();
    Robot.dt.resetTime();
    Robot.dt.startTime();

    //System.out.println("Im Stupid");
    Robot.pid.goDistance(leftDistance, rightDistance);
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
