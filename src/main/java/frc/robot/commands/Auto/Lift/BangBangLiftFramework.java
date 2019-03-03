/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.Lift;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;


public class BangBangLiftFramework extends Command implements RobotMap{
  private double setpoint;
  private double relativezero;
	private double error;
	private int errorDirection; //-1 or 1
    private double speedCap;
    private boolean driveDirection;
  public BangBangLiftFramework(double setpoint,double speedCap, boolean direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lt);
    	this.setpoint = setpoint;
        this.speedCap = speedCap;
        this.driveDirection = direction;
        this.relativezero = Robot.lt.getLeftTalon().getSelectedSensorPosition();
    }

    // Called just before this Command runs the first time
    protected void initialize() {


    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	error = Math.abs(setpoint - Robot.lt.getPosition());
    	errorDirection = (int)(error / Math.abs(error));
    	if ((driveDirection)) { //Go up
    		Robot.lt.setPercentOutputLift(speedCap);
        }
        
        else if ((!(driveDirection))) { //Go down
    		Robot.lt.setPercentOutputLift(-speedCap);
        }
        SmartDashboard.putNumber("Lift Encoder During Bangbang", Robot.lt.getLeftTalon().getSelectedSensorPosition());
        SmartDashboard.putNumber("Error during Lift Bangbang", error);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       // return ((Math.abs(error) < 500))  ; //arbitrary ceiling
       if(driveDirection){
        return (Robot.lt.getLeftTalon().getSelectedSensorPosition() < setpoint);
       }
       else if(!driveDirection){
        return (Robot.lt.getLeftTalon().getSelectedSensorPosition() > setpoint);
       }

       return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lt.setPercentOutputLift(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lt.setPercentOutputLift(0);
    }
}
