/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import frc.robot.commands.Auto.Arm.*;
import frc.robot.commands.Arm.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class holdCargo extends CommandGroup {
  /**
   * Add your docs here.
   */
  public holdCargo() {
    
    addSequential(new PIDArmFramework(-800, .4));
    //addSequential(new SetInArm(strength));
    
    addSequential(new ArmWait(.5));
    addSequential(new holdArm());
    //addSequential(new SetInArm(0));

  }

  public holdCargo(double time){
    addSequential(new PIDArmFramework(-750, .5));
    //addSequential(new SetInArm(strength));
    
    addSequential(new ArmWait(.5));
    addSequential(new holdArm(time));
    //addSequential(new SetInArm(0));
  
  }
}
