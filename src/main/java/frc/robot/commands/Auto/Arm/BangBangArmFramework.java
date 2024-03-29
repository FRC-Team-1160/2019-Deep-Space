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
    private double oldSetpoint;
    private double error;
    private int errorDirection; //-1 or 1
    private double speedCap;
    public BangBangArmFramework(double setpoint,double speedCap) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.am);
        this.setpoint = setpoint;
        this.oldSetpoint = setpoint;
        this.speedCap = speedCap;
        this.setpoint = this.oldSetpoint + Robot.am.getPosition(); //This is important to be in the initialize because if it's in the constructor it will not update the position when the button is pressed.

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        error = setpoint - Robot.am.getPosition();
        errorDirection = (int)(error / Math.abs(error));
        if (errorDirection < 0) { //go up!
            Robot.am.setPercentOutputArm(errorDirection*speedCap);
        }
        //new code
        else if (errorDirection > 0){ //go down!
            Robot.am.setPercentOutputArm(errorDirection*0.08);
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
