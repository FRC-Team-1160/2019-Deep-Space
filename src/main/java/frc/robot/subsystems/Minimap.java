/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.Robot;
import java.net.*;
import edu.wpi.first.wpilibj.Timer;
import java.io.*; 
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.AnalogAccelerometer;
import java.util.*;
import java.text.SimpleDateFormat;
import frc.robot.commands.Minimap.sendData;

/**
 * Add your docs here.
 */


 // 1 G = 32 ft/sec
public class Minimap extends Subsystem{
    
  public static Minimap instance;
  static double coordinates[] = {0.0, 0.0, 0.0};
   
   Socket socket = null; 
   DataOutputStream out = null; 
  
   double angle;

   Timer time = new Timer();

   double cur_time = time.get();

  private Minimap(){
    coordinates[0] = 0.0;
    coordinates[1] = 0.0;
    coordinates[2] = 0.0;
    angle = 0;
    System.out.println("minimap made");
    try{ 
      socket = new Socket("10.11.60.25", 5800); //the name matters: has to be address
      System.out.println("Connected"); 
      
      // sends output to the socket 
      out = new DataOutputStream(socket.getOutputStream()); 
     // out.writeBytes("0 0 0\n");
      System.out.println("sent successfully");
    } 
    catch(UnknownHostException u) 
      { 
        System.out.println("unknown host");
          System.out.println(u); 
      } 
    catch(IOException i) 
      { 
        System.out.println("io exception");
          System.out.println(i); 
     }
  }

  public static Minimap getInstance(){
    if(instance == null){
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

  public void sendData(){ 
     /*
      //Client client = new Client("127.0.0.1", 12345); 

        //Create text file to represent all the errors that occur during this run of the program. Includes date, time, and name
        PrintWriter errorPrinter = null;
          String fileName = "errors.txt";
        try{
          errorPrinter = new PrintWriter( new BufferedWriter(new FileWriter(fileName, true)));
        }catch(IOException i)
        {}
          //Write current date and time to the file. 
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          Date date = new Date();

          errorPrinter.println(dateFormat.format(date) + "\n\n");
      
        Socket socket = null; 
        DataOutputStream out = null; 
        String IP = "10.11.60.25";
        int port = 5800;

        // establish a connection 
        try
        { 
            socket = new Socket(IP, port); 
            System.out.println("Connected"); 
            
            // sends output to the socket 
            out = new DataOutputStream(socket.getOutputStream()); 
            System.out.println("out stream successfull made");
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
            //errorPrinter.println("Tried to create socket object and sent data output stream to it, UnknownHostException occurred.");
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
           // errorPrinter.println("Tried to create socket object and send data output stream to it, IOException occurred.");
        }
        */
        //int j = 0;
        //float xDis = 50, yDis = 70, angle = 45;
        updatePosition();
        angle = Robot.dt.getGyro().getYaw();
      //  double timenow = time.get();
       // while ((Math.abs(timenow - time.get())) < 15)
       // {
	        
	        
	        
          //while (System.currentTimeMillis() - time < 1000) {}
        if(Math.abs(time.get() - cur_time) < 500){ // change this value to tune it out which is in optimal time to output
          
        }else{
          try{
            System.out.println("attempting to write: " + coordinates[0] + " " + coordinates[1] + " " + angle + "\n");
            out.writeBytes(coordinates[0] + " " + coordinates[1] + " " + angle + "\n");
            out.flush(); // remove this if not useful but based on my knowledge, flush removes past data which is not needed, slowing a program
          } 
          catch(IOException i) 
          { 
              System.out.println(i); 
            // errorPrinter.println("Tried to create socket object and send data output stream to it, IOException occurred.");
          }
          cur_time = time.get();
        }
       // }
        
        System.out.println("ended");
        /* close the connection 
        try
        { 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.err.println(i); 
            //errorPrinter.println("Tried to close the data output stream and socket, IOException occurred");
        } 

        //Close the PrintWrite Object
        //errorPrinter.close();

        //write the messages to a single text file. 
        */
  } 
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    //setDefaultCommand(new sendData());
  }
}
