package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class PID extends Subsystem implements RobotMap{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private WPI_TalonSRX leftController,rightController;
	private static PID instance;
	private double proportion, integral, derivative, distance_difference, distance_difference_now, deltaTime, current_distance;

	private PID(WPI_TalonSRX lController, WPI_TalonSRX rController) {
		leftController = lController;
		rightController = rController;
	

    
    	leftController.configAllowableClosedloopError(0, 75, 0);
    	rightController.configAllowableClosedloopError(0, 75, 0);

		leftController.config_kP(0, .0005, 0);
		leftController.config_kI(0, 0.000, 0);
		leftController.config_kD(0, 0.000, 0);
		rightController.config_kP(0, 0.0005, 0);
		rightController.config_kI(0, 0.000, 0);
    	rightController.config_kD(0, 0.000, 0);
	}
	
	public static PID getInstance(WPI_TalonSRX lController, WPI_TalonSRX rController) {
		if(instance == null) {
			instance = new PID(lController, rController);
		}
		return instance;
	}
	
	public void goDistance(double lTargetDistance, double rTargetDistance) {
		SmartDashboard.putNumber("Back Left Encoder", leftController.getSelectedSensorPosition());
		SmartDashboard.putNumber("Back Right Encoder", rightController.getSelectedSensorPosition());
		SmartDashboard.putNumber("Back Left Error", leftController.getClosedLoopError());
		SmartDashboard.putNumber("Back Right Error", rightController.getClosedLoopError());
	
		leftController.set(ControlMode.Position, (lTargetDistance*RobotMap.CONTROLLER_CONSTANT_L));
		rightController.set(ControlMode.Position,(rTargetDistance*RobotMap.CONTROLLER_CONSTANT_R));
 	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}