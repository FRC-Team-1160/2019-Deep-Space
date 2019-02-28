/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;


public class BangBangTurnAngle extends Command {
  private double endAngle;
  private double error;
  private int turnDirection;
  public BangBangTurnAngle(double angle) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.dt);
    this.endAngle = angle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.dt.resetPosition();
    Robot.dt.resetGyro();

    this.endAngle += Robot.dt.getGyro().getYaw();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      error = Math.abs(endAngle+180 - Robot.dt.getGyro().getYaw()+180);
    	if ((error > 0)) { //Go up
        Robot.dt.getLeftMaster().set(ControlMode.PercentOutput, (0.2));
        Robot.dt.getRightMaster().set(ControlMode.PercentOutput, (0.2));
      }else if (((error < 0))) { //Go down
        Robot.dt.getLeftMaster().set(ControlMode.PercentOutput, (-0.2));
        Robot.dt.getRightMaster().set(ControlMode.PercentOutput, (-0.2));
    	}
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((Math.abs(error) < 0.5));
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.dt.setPercentOutput(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.dt.setPercentOutput(0);
  }
}
