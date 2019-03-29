/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.Arm;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDArmFramework extends Command {
private double setpoint;
//private double oldSetpoint;
private double speedCap;
private double startpoint;
private double movetopoint;


  public PIDArmFramework(double setpoint, double speedCap) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.am);
    this.setpoint = setpoint;
    this.speedCap = speedCap;

    startpoint = Robot.am.getPosition();

    movetopoint = startpoint + setpoint; //shift of frame of reference
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("init arm PID");
  
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.am.PIDControl(movetopoint, speedCap); 
    System.out.println("running arm PID"); 

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println(Robot.am.getMaster().getSelectedSensorPosition());
    SmartDashboard.putNumber("arm error", (Math.abs(Robot.am.getMaster().getSelectedSensorPosition() - movetopoint)));
    if((Math.abs(Robot.am.getMaster().getSelectedSensorPosition() - movetopoint)) <= 10) {
      System.out.println("end");

    }
    return ((Math.abs(Robot.am.getMaster().getSelectedSensorPosition() - movetopoint)) <= 10);
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
