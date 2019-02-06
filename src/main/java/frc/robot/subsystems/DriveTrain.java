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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

  private AHRS gyro;

  private DoubleSolenoid driveSwitch;

  public static NetworkTable table;

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
    
    driveSwitch = new DoubleSolenoid(PCM, DT_SOLENOID_0, DT_SOLENOID_1);
    gyro = new AHRS(Port.kMXP);
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    table = inst.getTable("datatable");
    xEntry = table.getEntry("X");
    yEntry = table.getEntry("Y");
    setFollower();
  }

  public void setFollower(){
    frontLeft.follow(backLeft);
    middleLeft.follow(backLeft);
    frontRight.follow(backRight);
    middleRight.follow(backRight);
  }

  public void manualDrive(){
    backLeft.set(ControlMode.PercentOutput, (Math.pow(-(Robot.oi.getMainstick().getZ() - Robot.oi.getMainstick().getY()), 1)));
    backRight.set(ControlMode.PercentOutput, (Math.pow(-(Robot.oi.getMainstick().getZ() + Robot.oi.getMainstick().getY()), 1)));
    SmartDashboard.putNumber("Angle", gyro.getAngle());
    SmartDashboard.putNumber("Accel X", gyro.getWorldLinearAccelX());
    SmartDashboard.putNumber("Accel Y", gyro.getWorldLinearAccelY());
    SmartDashboard.putNumber("Accel Z", gyro.getWorldLinearAccelZ());
  }

  public void setOff(){
    driveSwitch.set(DoubleSolenoid.Value.kReverse);
  }

  public void setOn(){
    driveSwitch.set(DoubleSolenoid.Value.kForward);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ManualDrive());
  }
}
