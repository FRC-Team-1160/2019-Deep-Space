/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Robot;
import frc.robot.commands.Arm.controlArm;
import frc.robot.commands.Auto.Arm.PIDArmFramework;
import frc.robot.commands.Auto.Arm.holdArm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import frc.robot.RobotMap;


import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class Arm extends Subsystem implements RobotMap {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static Arm instance;
  private WPI_TalonSRX upLeft, inLeft, inRight, upRight;
  private Timer timer;

  private double deltaTime;
  private double lastEncoder;
  private double currentEncoder;
  
  private Timer doneTimer;

  private double distanceLast;
  private double distanceNow;
  private double proportion;
  private double derivative;
  private double feedForward;

  public static Arm getInstance(){
    if(instance == null){
      instance = new Arm();
    }
    return instance;
  }

  private Arm(){
    
    upLeft = new WPI_TalonSRX(ARM_UP_LEFT);
    upRight = new WPI_TalonSRX(ARM_UP_RIGHT);
    inLeft = new WPI_TalonSRX(ARM_IN_LEFT);
    inRight = new WPI_TalonSRX(ARM_IN_RIGHT);
    timer = new Timer();
    timer.start();
    doneTimer = new Timer();
    doneTimer.start();

    upLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);

    upRight.follow(upLeft);

    proportion = 0;
    derivative = 0;
    distanceLast = 0;
    feedForward = 0;


  }

  public void controlArm(){
    SmartDashboard.putNumber("Arm Encoder",upLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("Timer: ", timer.get());
    SmartDashboard.putNumber("doneTimer: ", doneTimer.get());

    deltaTime = timer.get();
    currentEncoder = upLeft.getSelectedSensorPosition();
    
    //Trying to dampen the falling of the arm.
    //*
    double derivative = 0;
    if(currentEncoder < lastEncoder){
      derivative = ARM_SLOWYDOWNY*((currentEncoder - lastEncoder)/deltaTime);
    }
    if(derivative < -0.3){
      derivative = -0.3;
    }
    if(derivative > 0.3){
      derivative = 0.3;
    }
    //derivative = 0;
    feedForward = (Math.cos((upLeft.getSelectedSensorPosition()+247)*ARM_ANGLE_ENCODER_CONVERSION))*ARM_90_HOLD;  
    upLeft.set(ControlMode.PercentOutput, (0.4*-(Math.pow((Robot.oi.getArmStick().getY()), 1))) + derivative);
   //upLeft.set(ControlMode.PercentOutput, -0.2);

    timer.reset();
    timer.start();
    lastEncoder = currentEncoder;
  }
  public void controlArmWithFF(){
    SmartDashboard.putNumber("Arm Encoder",upLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("Timer: ", timer.get());
    SmartDashboard.putNumber("doneTimer: ", doneTimer.get());

    deltaTime = timer.get();
    currentEncoder = upLeft.getSelectedSensorPosition();
    
    //Trying to dampen the falling of the arm.
    //*
    double derivative = 0;
    if(currentEncoder < lastEncoder){
      derivative = ARM_SLOWYDOWNY*((currentEncoder - lastEncoder)/deltaTime);
    }
    if(derivative < -0.3){
      derivative = -0.3;
    }
    if(derivative > 0.3){
      derivative = 0.3;
    }
    //derivative = 0;
    feedForward = (Math.cos((upLeft.getSelectedSensorPosition()+247)*ARM_ANGLE_ENCODER_CONVERSION))*ARM_90_HOLD;  
    upLeft.set(ControlMode.PercentOutput, (0.5*-(Math.pow((Robot.oi.getArmStick().getY()), 1))) + feedForward);
   //upLeft.set(ControlMode.PercentOutput, -0.2);

    timer.reset();
    timer.start();
    lastEncoder = currentEncoder;
  }
  	/*
	 * Timer Methods
	 */

  public void holdArm(){
    deltaTime = timer.get();
    currentEncoder = upLeft.getSelectedSensorPosition();

    //Trying to hold the the arm.
    /*
    double derivative = 0;
    if(currentEncoder < lastEncoder){
      derivative = 0.003*((currentEncoder - lastEncoder)/deltaTime);
    }
    if(derivative < -0.2){
      derivative = -0.2;
    }
    if(derivative > 0.2){
      derivative = 0.2;
    }
    */
    upLeft.set(ControlMode.PercentOutput,(Math.cos((upLeft.getSelectedSensorPosition()+247)*ARM_ANGLE_ENCODER_CONVERSION))*ARM_90_HOLD);
    timer.reset();
    timer.start();
    //lastEncoder = currentEncoder;
  }

  //the priciple behind this is that we use a PID loop to move the arm to a position and use the cosine of the angle times a constant (ARM_90_HOLD) to hold it)
  // cos 0 (straight) = 1; cos 90 (directly up) = 0;
  public void PIDControl(double targetDistance, double maxSpeed){
    SmartDashboard.putNumber("target distance", targetDistance);

    //update proportion and derivative
    deltaTime = timer.get();
    distanceNow = (targetDistance - upLeft.getSelectedSensorPosition());
    proportion = distanceNow*ARM_P;
    derivative = ARM_D * ((distanceNow - distanceLast )/ deltaTime);
    feedForward = (Math.cos((upLeft.getSelectedSensorPosition()+247)*ARM_ANGLE_ENCODER_CONVERSION))*ARM_90_HOLD;
    //Math.cos is for radians
    SmartDashboard.putNumber("Cosine stuff", Math.cos((upLeft.getSelectedSensorPosition()+247)*ARM_ANGLE_ENCODER_CONVERSION));
    //If the pid is setting the arm to move faster than the max speed, move the max speed in the direction that the pid was going to move.
    SmartDashboard.putNumber("P ARM", proportion);
    SmartDashboard.putNumber("D ARM", derivative);
    if (Math.abs(proportion+derivative) < maxSpeed){
      upLeft.set(ControlMode.PercentOutput, proportion + derivative + feedForward);//make proportion pos or neg dep. on robot 
    }
    else{
      upLeft.set(ControlMode.PercentOutput, feedForward + maxSpeed*((proportion+derivative)/Math.abs(proportion+derivative)));
    }

    //update constants for the next time the loop runs.
    distanceLast = distanceNow;
    timer.reset();
    timer.start();
  }

  public WPI_TalonSRX getMaster(){
    return upLeft;
  }
  
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
  
  public void resetDoneTime(){
		doneTimer.reset();
	}
	
	public void startDoneTime(){
		doneTimer.start();
  }
  
  public double getDoneTime(){
		return doneTimer.get();
	}
  
	public boolean done(double finishTime){
    boolean isDone = (getDoneTime() >= finishTime);
    return isDone;
	}
	

  public void setUp(double input){
    upLeft.set(input);
    upRight.set(input);
  }

  public void setPercentOutputArm(double input){
    upLeft.set(ControlMode.PercentOutput,input);
  }

  public void setIn(double input){
    inLeft.set(-input);
    inRight.set(-input);
  }
  
  public void stopUp(){
    upLeft.set(0);
    upRight.set(0);
  }

  public void stopIn(){
    inLeft.set(0);
    inRight.set(0);
  }

  public int getPosition(){
    return upLeft.getSelectedSensorPosition();
  }

  public void resetPosition(){
    upLeft.setSelectedSensorPosition(0, 0, 10);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new controlArm());
  }
}
