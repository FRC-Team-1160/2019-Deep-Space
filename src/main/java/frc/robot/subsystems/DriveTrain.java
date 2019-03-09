/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Drive.ManualDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

import com.kauailabs.navx.frc.AHRS;
/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem implements RobotMap {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static DriveTrain instance;
  private WPI_VictorSPX frontLeft, frontRight, middleLeft, middleRight;
  private WPI_TalonSRX backLeft, backRight;

  //turnangle variables
	private double deltaTime;
	private double angle_difference_now;
	private double angle_difference;
	private double derivative;
	private double proportion;
	private double integral; 


  private AHRS gyro;

  private DoubleSolenoid driveSwitch;

  public static NetworkTable table;

  private Timer timer,timerCheck;

  NetworkTableEntry xEntry;
  NetworkTableEntry yEntry;

  double x = 0;
  double y = 0;

  public static DriveTrain getInstance(){
    if(instance == null){
      instance = new DriveTrain();
    }
    return instance;
  }

  private DriveTrain(){
    backLeft = new WPI_TalonSRX(DT_BACK_LEFT);
    backRight = new WPI_TalonSRX(DT_BACK_RIGHT);
    middleLeft = new WPI_VictorSPX(DT_MIDDLE_LEFT);
    middleRight = new WPI_VictorSPX(DT_MIDDLE_RIGHT);
    frontLeft = new WPI_VictorSPX(DT_FRONT_LEFT);
    frontRight = new WPI_VictorSPX(DT_FRONT_RIGHT);
    
    backLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
    backRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);

    timer = new Timer();
    timerCheck = new Timer();

    driveSwitch = new DoubleSolenoid(PCM, DT_SOLENOID_0, DT_SOLENOID_1);
    gyro = new AHRS(Port.kMXP);
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    table = inst.getTable("datatable");
    //xEntry = table.getEntry("X");
    //yEntry = table.getEntry("Y");
    setFollower();
  }

  public void setFollower(){
    frontLeft.follow(backLeft);
    middleLeft.follow(backLeft);
    frontRight.follow(backRight);
    middleRight.follow(backRight);
  }

  public void manualDrive(){

    
    backLeft.set(ControlMode.PercentOutput, 0.7*(Math.pow((Robot.oi.getMainStick().getY()), 3) - (Math.pow(Robot.oi.getMainStick().getZ(),3) )));
    backRight.set(ControlMode.PercentOutput, -0.65*(Math.pow((Robot.oi.getMainStick().getY()), 3) + (Math.pow(Robot.oi.getMainStick().getZ(),3))));
    SmartDashboard.putNumber("Angle", gyro.getAngle());
    SmartDashboard.putNumber("Accel X", gyro.getWorldLinearAccelX());
    SmartDashboard.putNumber("Accel Y", gyro.getWorldLinearAccelY());
    SmartDashboard.putNumber("Accel Z", gyro.getWorldLinearAccelZ());
    SmartDashboard.putNumber("Right Drivetrain Encoder",backRight.getSelectedSensorPosition());
    SmartDashboard.putNumber("Left Drivetrain Encoder",backLeft.getSelectedSensorPosition());
		SmartDashboard.putNumber("Pitch", gyro.getPitch());
		SmartDashboard.putNumber("Yaw", gyro.getYaw());
		SmartDashboard.putNumber("Roll", gyro.getRoll());
	}

  public void resetAngleDifference() {
		angle_difference = 0;
	}
	public void resetTurnAngleIntegral() {
		integral = 0;
	}

	public void turnAngle(double targetAngle) { //ghetto PID with the navX sensor 
		double current_angle = gyro.getYaw();
		angle_difference_now = (targetAngle - current_angle);
		SmartDashboard.putNumber("Yaw", gyro.getYaw());
		proportion = GYRO_KP_2 * angle_difference;
 		deltaTime = getTime();
 		derivative = 0;//GYRO_KD * (angle_difference_now - angle_difference)/deltaTime;
 		if (Math.abs(angle_difference_now) < 15) {
		 integral += GYRO_KI*deltaTime*(angle_difference_now);
		 }
		if(integral > GYRO_KI_CAP) {
			integral = GYRO_KI_CAP;
		}

		integral = 0;
		//System.out.println("The angle difference is:\t " + angle_difference + "\t and the angle differenece now is: " + angle_difference_now);

 		angle_difference = angle_difference_now;
 		
 		//SmartDashboard.putNumber("turnAngle PercentOutput input", proportion+derivative+integral);
 		//backLeft.set(ControlMode.PercentOutput, proportion+derivative+integral);
 		//backRight.set(ControlMode.PercentOutput, proportion+derivative+integral);
		//System.out.println("P is: \t" + proportion + "I is: \t" + integral + "D is: \t" + derivative);
		SmartDashboard.putNumber("P", proportion);
		SmartDashboard.putNumber("I", integral);
		SmartDashboard.putNumber("D", derivative);
		
		// if (proportion+derivative+integral <= GYRO_CAP) {
			// if(targetAngle > 0){
		backLeft.set(ControlMode.PercentOutput, (proportion+derivative+integral));
	 	backRight.set(ControlMode.PercentOutput, -(proportion+derivative+integral));
			 //}//else{
			//	backLeft.set(ControlMode.PercentOutput, (proportion+derivative+integral));
			//	backRight.set(ControlMode.PercentOutput, rightSideBoost*(proportion+derivative+integral));
			 //}
	 		
 	//	}
 	//	else {
 	//		backLeft.set(GYRO_CAP);
 	//		backRight.set(GYRO_CAP);
	//	 }
 		
 		//printYaw();
 		resetTime();
 		startTime();
 	}
	
	public void turnAngleCheck(double targetAngle) {
		resetTimeCheck();
		startTimeCheck();
		while (getTimeCheck() < 1) {
			turnAngle(targetAngle);
		}
	}

  public void setOff(){
    driveSwitch.set(DoubleSolenoid.Value.kReverse);
  }

  public void setOn(){
    driveSwitch.set(DoubleSolenoid.Value.kForward);
  }

  public void setPercentOutput(double input){
    backLeft.set(ControlMode.PercentOutput,input);
    backRight.set(ControlMode.PercentOutput,input);
    
  }

  public void resetPosition() {
		backLeft.setSelectedSensorPosition(0,0,100);
		backRight.setSelectedSensorPosition(0,0,100);
		resetGyro();
	}

  	/*
	 * Timer Methods
	 */
	public void resetTime(){
		timer.reset();
	}
	
	public void startTime(){
		timer.start();
	}
	
	public void stopTime(){
		timer.stop();
	}
	
	public double getTime(){
		return timer.get();
	}
	
	public boolean done(double finishTime) {
		return (timer.get() >= finishTime);
	}
	public void resetTimeCheck() {
		timerCheck.reset();
	}
	public void startTimeCheck() {
		timerCheck.start();
	}
	public void stopTimeCheck() {
		timerCheck.stop();
	}
	public double getTimeCheck() {
		return timerCheck.get();
	}

  //Gyro Functions
	public void resetGyro()
	{
		System.out.println(gyro.getYaw());
		gyro.reset();
		gyro.zeroYaw();
		System.out.println(gyro.getYaw());
	}
	public void zeroGyro() {
		gyro.zeroYaw();
	}

	public AHRS getGyro() {
		return gyro;
	}

  public WPI_TalonSRX getLeftMaster(){
    return backLeft;
  }

  public WPI_TalonSRX getRightMaster(){
    return backRight;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ManualDrive());
  }
}
