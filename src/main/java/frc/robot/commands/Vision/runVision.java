package frc.robot.commands.Vision;

import frc.robot.Robot;

import frc.robot.Robot.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class runVision extends Command {

    public runVision() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.vs);
        requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    Robot.dt.resetGyro();
    Robot.vs.runVision();
    }
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
