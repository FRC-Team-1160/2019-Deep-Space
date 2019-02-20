package frc.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.videoio.VideoCapture;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Vision extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static GripPipeline gp;
	private int switchButtonPressed;
	public ArrayList<MatOfPoint> contours;
	public CameraServer cs;
	public static Vision instance;
	Point[] Points1;
	Point[] Points2;
	public Mat matrix;
	public CvSink camera;
	public UsbCamera usbCamera;
	private VideoSink videoSink;

	public static double angleindegrees;
	public static double distanceToTarget;
	private Vision() {
		cs = CameraServer.getInstance();
		gp = new GripPipeline();
		cs.addAxisCamera("axis","axis-camera.local");
		cs.addServer("axis");

		//Initialize Usb Camera
		usbCamera = CameraServer.getInstance().startAutomaticCapture();

		camera = cs.getVideo();
		matrix = new Mat();
		angleindegrees = 0;
		distanceToTarget = 0;
		switchButtonPressed = 0;
		//camera.grabFrame(matrix);
		/*
		for(Point p: Points) {
			System.out.println(p.x + "  " + p.y);
		}
		*/

		//Create videoSink
		videoSink = CameraServer.getInstance().getServer();
		videoSink.setSource(camera.getSource());//default camera is set to axis camera

		
		
		
	}
	
	public static Vision getInstance() {
		if (instance == null)
		{
			System.out.println("Vision constructor ran");
			instance = new Vision();
		}
		return instance;
		
		
	}

	public void switchCamera(){
		if(switchButtonPressed == 0){//Switch to usb camera
			System.out.println("Switching to usb camera");
			videoSink.setSource(usbCamera);
			switchButtonPressed = 1;
		}
		if(switchButtonPressed == 1){
			System.out.println("Switching to axis camera");
			videoSink.setSource(camera.getSource());
			switchButtonPressed = 0;
		}
		
	}
	
	public void runVision() {
		

		camera.grabFrameNoTimeout(matrix);
		System.out.println(matrix);
		gp.process(matrix);
		if(!(matrix.empty())) {
			System.out.println("Got here");
			contours = gp.filterContoursOutput();
			if(contours.size() == 2){
			System.out.println("I GOT HERE!!!!" + contours.size());
			//change this to a loop at some point so it doesn't break if it can't find 2 points
		
			Points1 = contours.get(contours.size()-1).toArray();
			Points2 = contours.get(contours.size()-2).toArray();
			System.out.println("Did work");
			double maxHY1 = 0;
			double minHY1 = 1000;
			double maxX1 = 0;
			double minX1 = 0;
			double midX1 = 0;
			double maxHY2 = 0;
			double minHY2 = 1000;
			double maxX2 = 0;
			double minX2 = 0;
			double midX2 = 0;
			double distanceX1;
			double distanceX2;
			double midpointX = 0;
			double distanceX = 0;

			for(Point p : Points1) {
				//System.out.println("P.y is:"+p.y + "P.x is:" + p.x);
				if(p.y > maxHY1) {
					maxHY1 = p.y;
					maxX1 = p.x;
				}
				else if(p.y < minHY1) {
					minHY1 = p.y;
					minX1 = p.x;
				}
				midX1 = Math.abs(maxX1 + minX1)/2;
				distanceX1 = Math.abs(maxX1 - minX1);
				
			}
			for(Point p : Points2) {
				//System.out.println("P.y is:"+p.y + "P.x is:" + p.x);
				if(p.y > maxHY2) {
					maxHY2 = p.y;
					maxX2 = p.x;
				}
				else if(p.y < minHY2) {
					minHY2 = p.y;
					minX2 = p.x;
				}
				midX2 = Math.abs(maxX2 + minX2)/2;
				distanceX2 = Math.abs(maxX2 - minX2);
				
			}
			distanceX = Math.abs(midX1 - midX2);
			midpointX = (midX1 + midX2)/2;
			
			double cameraCenter = 320/2;

			double turnDistancePX = Math.abs(cameraCenter-midpointX);
			double turnDistancein = (turnDistancePX*14.311)/(2*distanceX);
			double LorR = 0;
			if(midpointX > cameraCenter){
				LorR = 1;
			}
			else{
				LorR = -1;
			}

			double height1 = Math.abs(maxHY1-minHY1);
			double height2 = Math.abs(maxHY2-minHY2);
			
			double distance1 = (5.824*283.01)/height1;	
			double distance2 = (5.824*283.01)/height2;	
			double midPointHeight = (distance1 + distance2)/2;

			double turnAngle = (Math.asin(turnDistancein/midPointHeight))*180/3.1415;
			turnAngle = turnAngle * LorR;


			System.out.println("The angle is: " + turnAngle);
			System.out.println("The Distance is: " + midPointHeight);
			angleindegrees = turnAngle;
			distanceToTarget = midPointHeight;


			//System.out.println("The pixel height is: " + height1);
			//System.out.println("The robot is: " + distance + "inches from the tape");
			
			//System.out.println(Points[0].y);
			
		}}
		else {
			System.out.println("Did not work");
		}
		
		
	}

	public void resetVision(){
		angleindegrees = 0;
		distanceToTarget = 0;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new org.usfirst.frc.team1160.robot.commands.vision.runVision());

    }
    
}

