/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;


public class SwitchCamera extends Command {
  private String camera_type;
  public SwitchCamera(String camera_type) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.camera_type = camera_type;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(camera_type.equalsIgnoreCase("axis")){
      Robot.cs.addAxisCamera("axis", "axis-camera.local" );
      Robot.cs.addServer("axis");
    }
    if(camera_type.equalsIgnoreCase("usb")){
      Robot.usb_camera = CameraServer.getInstance().startAutomaticCapture();
    }

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
