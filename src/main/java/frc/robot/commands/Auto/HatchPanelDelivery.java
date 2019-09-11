/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import frc.robot.commands.Auto.Lift.*;
import frc.robot.commands.Lift.*;


import edu.wpi.first.wpilibj.command.CommandGroup;


public class HatchPanelDelivery extends CommandGroup {
  /**
   * Add your docs here.
   */
  public HatchPanelDelivery(double input) {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.
    addSequential(new BangBangLiftFramework(input, 0.8, true));
    addSequential(new SetPiston(false));
    addSequential(new LiftWait(0.5));
    addSequential(new SetPiston(true));
    addSequential(new BangBangLiftFramework(-25000, 0.2, false)); //-14000
    

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
