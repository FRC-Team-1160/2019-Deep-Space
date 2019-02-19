/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.Arm.*;
import frc.robot.commands.Lift.*;
import frc.robot.commands.Vision.*;
import frc.robot.commands.Drive.*;
import frc.robot.commands.Auto.CargoDelivery;
import frc.robot.commands.Auto.HatchPanelDelivery;
import frc.robot.commands.Auto.Arm.*;
import frc.robot.commands.Auto.Lift.*;
import frc.robot.commands.Auto.Drivetrain.*;
import frc.robot.commands.Minimap.sendData;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements RobotMap{
    private static OI instance;
    Joystick mainStick;
    Joystick armStick;
    Joystick liftStick;
    JoystickButton sendData, runVision, setOn, setOff, resetEncoderYaw,

     ArmUp,ArmDown, ArmIn, ArmOut, PistonOut, PistonIn,
      ArmCargoShipDelivery, ArmRocketLevel1Delivery, ArmRocketLevel2Delivery,

     LiftUp, LiftDown, ResetLift, LiftLevel1,  LiftLevel2, LiftLevel3, 

      driveForward ;

    public static OI getInstance(){
      if(instance == null){
        instance = new OI();
      }
      return instance;
    }

    private OI(){
      mainStick = new Joystick(0);
      armStick = new Joystick(1);
      liftStick = new Joystick(2);
      createButtons();
    }

    private void createButtons(){ 

      //MainStick Commands
      runVision = new JoystickButton(mainStick,1); 

      setOn = new JoystickButton(mainStick, 5);
      setOff = new JoystickButton(mainStick, 6);
      
      driveForward = new JoystickButton(mainStick , 4);

      resetEncoderYaw = new JoystickButton(mainStick, 10);
     // sendData = new JoystickButton(mainStick, 9);
      //Arm Commands
      //ArmUp = new JoystickButton(armStick, 6);
      //ArmDown = new JoystickButton(armStick, 7);
      ArmCargoShipDelivery = new JoystickButton(armStick,7);//good button
      ArmRocketLevel1Delivery = new JoystickButton(armStick,10);//good button
      ArmRocketLevel2Delivery = new JoystickButton(armStick, 11);//still testing
      ArmIn = new JoystickButton(mainStick, 8);
      ArmOut = new JoystickButton(armStick, 1);

      //Lift commands
      //LiftUp = new JoystickButton(liftStick, 6);
      //LiftDown = new JoystickButton(liftStick, 7);
      ResetLift = new JoystickButton(liftStick, 2);//temporary button
      LiftLevel1 = new JoystickButton(liftStick, 7);//temporary button
      LiftLevel2 = new JoystickButton(liftStick, 10);//temporary button
      LiftLevel3 = new JoystickButton(liftStick, 11);//temporary button
      
      PistonOut = new JoystickButton(liftStick, 1);
      PistonIn = new JoystickButton(liftStick, 3);

      //testButton = new JoystickButton(altStick, 1);
      tieButtons();
    }

    private void tieButtons(){
      //MainStick Buttons
      runVision.whenPressed(new runVision());
      setOn.whenPressed(new SetDrive(true)); //won't do anything
      setOff.whenPressed(new SetDrive(false)); //the pnuematics aren't attached to the gearbox

      driveForward.whenPressed(new DriveForward(45));

      resetEncoderYaw.whenPressed(new ResetEncoderYaw());
      //Arm Buttons
      //ArmUp.whileHeld(new SetUpArm(1));
      //ArmDown.whileHeld(new SetUpArm(-1))      
      ArmCargoShipDelivery.whenPressed(new CargoDelivery(-475,0.35));//temporary value - needs to be tuned.
      ArmRocketLevel1Delivery.whenPressed(new CargoDelivery(-350,0.5));//good for practice robot.
      ArmRocketLevel2Delivery.whenPressed(new CargoDelivery(-610,0.60));
      ArmIn.whileHeld(new SetInArmTele(-.3)); //intakes the cargo //PRACTICE - 0.2
      ArmOut.whileHeld(new SetInArmTele(.7)); //spits the cargo //PRACTICE - -1
                               
      
      //Lift Buttons
      //LiftUp.whileHeld(new SetLift(1));
      //LiftDown.whileHeld(new SetLift(-1));
      //LiftLevel1.whenPressed(new BangBangLiftFramework(0, 0.5, false));
      LiftLevel1.whenPressed(new HatchPanelDelivery(-3000));//temporary value - needs to be tuned.
      LiftLevel2.whenPressed(new HatchPanelDelivery(-53600));//temporary value - needs to be tuned.
      LiftLevel3.whenPressed(new HatchPanelDelivery(-103000));//temporary value - needs to be tuned.
      PistonOut.whenPressed(new SetPiston(true)); //delivers the hatch panel
      PistonIn.whenPressed(new SetPiston(false)); //resets the pistons

      //sendData.whenPressed(new sendData());
    }

    public Joystick getMainStick(){
      return mainStick;
    }

    public Joystick getLiftStick(){
      return liftStick;
    }

    public Joystick getArmStick(){
      return armStick;
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
