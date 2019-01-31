/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;


/**
 * Add your docs here.
 */
public class CameraSwitcher extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static CameraSwitcher instance;
  private int buttonPressed;
  private CameraServer cs;
  private UsbCamera usbCamera;


  public static CameraSwitcher getInstance(){
    if(instance == null){
      instance = new CameraSwitcher();
    }
    return instance;
  }

  private CameraSwitcher(){
    buttonPressed = 0;
    cs = CameraServer.getInstance();
    cs.addAxisCamera("axis", "axis-camera.local");
    cs.addServer("axis");
  }

  public void Switch(){
    if(buttonPressed == 0){//Switch from the default, axis camera, to usb camera
      cs.removeServer("axis");
      usbCamera = CameraServer.getInstance().startAutomaticCapture();
      buttonPressed += 1;
      System.out.println("Usb Camera");
    }
    if(buttonPressed == 1){//Switch back to axis camera
      usbCamera = null;//I could not find any method to remove the usbCamera or stop captruing
      cs.addServer("axis");
      buttonPressed = 0;
      System.out.println("Axis Camera");
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
