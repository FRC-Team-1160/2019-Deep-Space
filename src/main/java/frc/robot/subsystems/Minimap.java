/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import java.net.*;
import java.util.Timer;
import java.io.*; 
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.Minimap.sendData;

/**
 * Add your docs here.
 */
public class Minimap extends Subsystem {
  static Timer timer = new Timer();
  public static Minimap instance;

  public Minimap(){
  }

  public static Minimap getInstance(){
    if(instance != null){
      instance = new Minimap();
    }
    return instance;
  }

  static double f(double x) {
    return Math.exp(- x * x / 2) / Math.sqrt(2 * Math.PI);
  }

/**********************************************************************
 * Integrate f from a to b using the trapezoidal rule.
 * Increase N for more precision.
 **********************************************************************/
 static double integrate(double a, double b, int N) {
    double h = (b - a) / N;              // step size
    double sum = 0.5 * (f(a) + f(b));    // area
    for (int i = 1; i < N; i++) {
       double x = a + h * i;
       sum = sum + f(x);
    }

    return sum * h;
  }
  public void sendData()
  { 
      //Client client = new Client("127.0.0.1", 12345); 
      Socket socket = null; 
      DataOutputStream out = null; 
      // establish a connection 
      try
      { 
          socket = new Socket("127.0.0.1", 5800); 
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
      float xDis = 50, yDis = 70, angle = 45;
      while (j < 9999999)
      {
        
        
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time < 1000) {}
        //out.writeBytes(xDis + " " + yDis + " " + angle + "\n");
        xDis+=5;
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
    setDefaultCommand(new sendData());
  }
}
