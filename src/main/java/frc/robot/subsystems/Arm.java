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
  private WPI_TalonSRX upLeft, inLeft, inRight;
  private WPI_VictorSPX upRight;
  private Timer timer;

  private double deltaTime;
  private double lastEncoder;
  private double currentEncoder;
  
  private Timer doneTimer;

  public static Arm getInstance(){
    if(instance == null){
      instance = new Arm();
    }
    return instance;
  }

  private Arm(){
    
    upLeft = new WPI_TalonSRX(ARM_UP_LEFT);
    upRight = new WPI_VictorSPX(ARM_UP_RIGHT);
    inLeft = new WPI_TalonSRX(ARM_IN_LEFT);
    inRight = new WPI_TalonSRX(ARM_IN_RIGHT);
    timer = new Timer();
    timer.start();
    doneTimer = new Timer();
    doneTimer.start();

    upLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);

    upRight.follow(upLeft);
  }

  public void controlArm(){
    SmartDashboard.putNumber("Arm Encoder",upLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("Timer: ", timer.get());
    SmartDashboard.putNumber("doneTimer: ", doneTimer.get());

    deltaTime = timer.get();
    currentEncoder = upLeft.getSelectedSensorPosition();

    //Trying to dampen the falling of the arm.
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


    upLeft.set(ControlMode.PercentOutput, -(Math.pow((Robot.oi.getArmStick().getY()), 1)) + derivative);

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
    double derivative = 0;
    if(currentEncoder < lastEncoder){
      derivative = 0.1*((currentEncoder - lastEncoder)/deltaTime);
    }
    if(derivative < -0.3){
      derivative = -0.3;
    }
    if(derivative > 0.3){
      derivative = 0.3;
    }

    upLeft.set(ControlMode.PercentOutput, derivative);
    timer.reset();
    timer.start();
    lastEncoder = currentEncoder;
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
    inLeft.set(input);
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

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new controlArm());
  }
}
