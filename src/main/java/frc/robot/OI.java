/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.Drive.ManualDrive;
import frc.robot.commands.Drive.SetDrive;
import frc.robot.commands.Lift.SetLift;
import frc.robot.commands.Arm.SetUpArm;
import frc.robot.commands.Arm.SetInArm;
import frc.robot.commands.Lift.SetPiston;



/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements RobotMap{
    private static OI instance;
    Joystick mainStick;
    Joystick altStick;
    JoystickButton setOn, setOff, LiftUp, LiftDown, ArmUp, ArmDown, ArmIn, ArmOut, PistonOut, PistonIn;

    public static OI getInstance(){
      if(instance == null){
        instance = new OI();
      }
      return instance;
    }

    private OI(){
      mainStick = new Joystick(0);
      altStick = new Joystick(1);
      createButtons();
    }

    private void createButtons(){ 
      setOn = new JoystickButton(mainStick, 1);
      setOff = new JoystickButton(mainStick, 2);
      LiftUp = new JoystickButton(mainStick, 3);
      LiftDown = new JoystickButton(mainStick, 4);
      ArmUp = new JoystickButton(mainStick, 5);
      ArmDown = new JoystickButton(mainStick, 6);
      ArmIn = new JoystickButton(mainStick, 7);
      ArmOut = new JoystickButton(mainStick, 8);

      PistonOut = new JoystickButton(altStick, 5);
      PistonIn = new JoystickButton(altStick, 6);

      System.out.println("Hello");
      tieButtons();
    }

    private void tieButtons(){
      setOn.whenPressed(new SetDrive(true));
      setOff.whenPressed(new SetDrive(false));
      LiftUp.whileHeld(new SetLift(0.25));
      LiftDown.whileHeld(new SetLift(-0.25));
      ArmUp.whileHeld(new SetUpArm(0.25));
      ArmDown.whileHeld(new SetUpArm(-0.25));
      ArmIn.whileHeld(new SetInArm(0.25));
      ArmOut.whileHeld(new SetInArm(-0.25));
      PistonOut.whileHeld(new SetPiston(true));
      PistonIn.whileHeld(new SetPiston(false));
    }

    public Joystick getMainstick(){
      return mainStick;
    }
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
