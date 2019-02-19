/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.Arm;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.RobotMap;


public class BangBangArmFramework extends Command implements RobotMap{
  private double setpoint;
	private double error;
	private int errorDirection; //-1 or 1
	private double speedCap;
  public BangBangArmFramework(double setpoint,double speedCap) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.am);
    	this.setpoint = setpoint;
    	this.speedCap = speedCap;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.setpoint = this.setpoint + Robot.am.getPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	error = setpoint - Robot.am.getPosition();
    	errorDirection = (int)(error / Math.abs(error));
    	if (errorDirection*0.3 < 0) { //Only go up!
    		Robot.am.setPercentOutputArm(errorDirection*speedCap);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((Math.abs(error) < 100)); //arbitrary ceiling
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.am.setPercentOutputArm(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.am.setPercentOutputArm(0);
    }
}
