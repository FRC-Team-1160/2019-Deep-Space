/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Breakout;
import frc.robot.commands.Auto.Drivetrain.*;
import frc.robot.commands.Arm.*;
import frc.robot.commands.Auto.Arm.*;

public class CargoLevel2 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CargoLevel2(double level,double output) {


    addParallel(new DriveForward(-27,0.5));

    //addParallel(new PIDArmFramework(850, .5));
    //addSequential(new SetInArm(strength));
    
    //addSequential(new ArmWait(.5));
    
    addParallel(new holdArm(1));
    //addSequential(new SetInArm(0));
  
    
    //addSequential(new Breakout());
    addSequential(new ArmWait(0.1));
    
    addSequential(new PIDArmFramework(level, .5));
    
    addSequential(new ArmWait(0.05));
    addSequential(new holdArm(.3));

    addSequential(new SetInArm(output));
    
    addSequential(new ArmWait(.25));
    //addSequential(new holdArm(5));
    addSequential(new SetInArm(-.3));
    
    addSequential(new ArmWait(1));

    addSequential(new SetInArm(0));
    
    
    
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
