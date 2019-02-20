/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.Drivetrain;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnAngle extends Command implements RobotMap{

	public double targetAngle;
    
	public TurnAngle(double target) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
      requires(Robot.dt);
      requires(Robot.vs);
      this.targetAngle = Vision.angleindegrees;
    	//this.targetAngle = target;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.dt.setLowGear();
      Robot.dt.resetPosition();
    	Robot.dt.resetGyro();
    	Robot.dt.resetTurnAngleIntegral();
    	Robot.dt.resetTime();
      Robot.dt.startTime();
      //while(Math.abs(Robot.dt.getGyro().getYaw()) > 0.1){
        //System.err.println("The reset method lies");
        //Robot.dt.resetGyro();
      
      this.targetAngle = Vision.angleindegrees + Robot.dt.getGyro().getYaw();
      

    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
      Robot.dt.turnAngle(targetAngle);
    //this.targetAngle = Robot.vs.angleindegrees;
		//time for the ghetto isFinished()
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
      //System.out.println("Gyro Yaw is: " + Robot.dt.getGyro().getYaw() + " and the target angle is: " + targetAngle);
      if ((Math.abs(Robot.dt.getGyro().getYaw() - targetAngle) < GYRO_TOLERANCE)) {
			//Robot.dt.turnAngleCheck(targetAngle);
        System.out.println("Im finished turning from Vision");
        return true;
    }
      System.out.println("Im still turning from Vision");
      SmartDashboard.putNumber("error from isfinished", (Math.abs(Robot.dt.getGyro().getYaw() - targetAngle)));
    	return false;
    	
    }

    // Called once after isFinished returns true
    protected void end() {
      Robot.dt.setPercentOutput(0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//Robot.dt.setPercentOutput(0);
    }
}