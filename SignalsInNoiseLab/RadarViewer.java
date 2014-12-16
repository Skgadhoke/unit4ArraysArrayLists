import javax.swing.JFrame;
import java.util.Scanner;


/**
 * Class that contains the main method for the program and creates the frame containing the component.
 * 
 * @author @gcschmit
 * @version 19 July 2014
 */
public class RadarViewer
{
    /**
     * main method for the program which creates and configures the frame for the program
     * Takes user input for initial position and velocity
     *
     */
    public static void main(String[] args) throws InterruptedException
    {
        //num of rows and cols
        final int ROWS = 100;
        final int COLS = 100;
        
        //dx,dy
        int DX=0;
        int DY=0;
        
        // create scanner object
        Scanner scanner = new Scanner(System.in);
        
        int dx;
        
        // asking user input
        System.out.println("Enter initial X Position of Monster between 1 to 100: ");
        DX = scanner.nextInt();
        
        System.out.println("Enter initial Y Position of Monster between 1 to 100: ");
        DY = scanner.nextInt();
        
        System.out.print("Enter dx value between 1 and 5: ");
         dx = scanner.nextInt();
   
        System.out.print("Enter dy value between 1 and 5: ");
        int dy = scanner.nextInt();
        
        int px = DX;
        int py = DY;
        
        Radar radar = new Radar(ROWS, COLS);
        radar.setNoiseFraction(0.01);
        radar.setMonsterLocation(px, py);
        radar.scan();
        
        JFrame frame = new JFrame();
        
        frame.setTitle("Signals in Noise Lab");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // a frame contains a single component; create the radar component and add it to the frame
        RadarComponent component = new RadarComponent(radar);
        frame.add(component);
        
        // set the size of the frame to encompass the contained component
        frame.pack();
        
        // make the frame visible which will result in the paintComponent method being invoked on the
        //  component.
        frame.setVisible(true);
        
        // perform 100 scans of the radar with a slight pause between each
        // after each scan, instruct the Java Run-Time to redraw the window
        for(int i = 0; i < 100; i++)
        {
            Thread.sleep(80); // sleep 100 milliseconds 
            px = px+dx;
            py = py+dy;
            
            if(px < ROWS && py < COLS && px > 0 && py > 0)
            {
                radar.setMonsterLocation(px, py);
                radar.scan();                    
                frame.repaint();
            }
        }
  
        System.out.println("Monster Velocity "+radar.getMonsterVelocity());
        System.out.println("done ");
     
        }
        
}