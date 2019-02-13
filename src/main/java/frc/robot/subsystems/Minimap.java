/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.Robot;
import java.net.*;
import java.util.Timer;
import java.io.*; 
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.AnalogAccelerometer;
import java.util.*;

/**
 * Add your docs here.
 */
public class Minimap extends Subsystem{
    
  public static Minimap instance;
  static double coordinates[] = new double[3];
   
   Socket socket = null; 
   DataOutputStream out = null; 
  
   double angle;


   
  public Minimap(){
    coordinates[0] = 0.0;
    coordinates[1] = 0.0;
    coordinates[2] = 0.0;
  }

  public static Minimap getInstance(){
    if(instance != null){
      instance = new Minimap();
    }
    return instance;
  }


  // establish a connection 
  public void makeSocket(){
  
  }
  
  /**********************************************************************
 * Finds a new position by integrating acceleration (multiply accel by time twice)
 * Then it updates it to the coordinate array
 * Still needs to confirm: ability to display 
 **********************************************************************/

 //initialize the coordinate array
 static void updatePosition() { 
    //begins the timer
    Robot.dt.startTime();
    double timeValue = Robot.dt.getTime();

    //creates an instance of the accelerometer to get the accel values
    BuiltInAccelerometer accel = new BuiltInAccelerometer();
    double xValue = accel.getX();
    double yValue = accel.getY();
    double zValue = accel.getZ();

    //multiply acceleration by time to get velocity
    double changeXVelocity = xValue * timeValue;
    double changeYVelocity = yValue * timeValue;
    double changeZVelocity = zValue * timeValue;

    /*TL;DR: save velocity into the coordinates - this works because 
      the initial velocity at time 0 is 0, then you add your 
      new v to that. That becomes your C for the next incremement
      so position is already solved for
      Message Will C. or Edmond W. if clarity is needed
    */
    coordinates[0] = coordinates[0] + changeXVelocity;
    coordinates[1] = coordinates[1] + changeYVelocity;
    coordinates[2] = coordinates[2] + changeZVelocity;

  }
  public void sendData() { 
      try{ 
        socket = new Socket("127.0.0.1", 12345); 
        System.out.println("Connected"); 
        
        // sends output to the socket 
        out = new DataOutputStream(socket.getOutputStream()); 
      } 
      catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
      catch(IOException i) 
        { 
            System.out.println(i); 
      }
      int j = 0;
      
      while (j < 9999999)
      {
        updatePosition();
        angle = Robot.dt.getGyro().getYaw();
        if(angle < 0) angle +=360;
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time < 1000) {}
        try{
          out.writeBytes(coordinates[0] + " " + coordinates[1]+ " " + angle + "\n");
        }catch(IOException i) 
        { 
            System.out.println(i); 
      }
        j++;
      }
      
      System.out.println("ended");
      // close the connection 
      try
      { 
          out.close(); 
          socket.close(); 
      } 
      catch(IOException i) 
      { 
          System.out.println(i); 
      } 
  } 
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
