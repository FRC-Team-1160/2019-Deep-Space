/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj.command.Command;
import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Robot;

public class manualArm extends Command {
  public manualArm() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.am);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //Robot.am.getMaster().set(ControlMode.PercentOutput, (1*-(Math.pow((Robot.oi.getArmStick().getY()), 1))));;
    Robot.am.controlArm();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.am.getMaster().set(ControlMode.PercentOutput, (1*-(Math.pow((Robot.oi.getArmStick().getY()), 1))));
    Robot.am.controlArmWithFF();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
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
