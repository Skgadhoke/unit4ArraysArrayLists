import static org.junit.Assert.*;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RadarTest {
	

	/**
	 * The test class Radar Test.
	 *
	 * @author  @Simar Gadhoke
	 * @version 19 July 2014
	 */
	
	   

	    /**
	     * Sets up the test fixture.
	     *
	     * Called before every test case method.
	     */
	    @Before
	    public void setUp()
	    {
	    }

	    /**
	     * Tears down the test fixture.
	     *
	     * Called after every test case method.
	     */
	    @After
	    public void tearDown()
	    {
	    }

	    /*
	     * Tests the initial state 
	     */
		@Test
	    public void testInitialState() throws Exception
	    {   
	        final int ROWS = 100;
	        final int COLS = 100;
	        int DX=1;
	        int DY = 1;
	        int dx = 2;
	        int dy = 4;
	        
	        double expectedVelocity = ((double)dy)/dx;
	        
	        int px = DX;
	        int py = DY;
	        
	        Radar radar = new Radar(ROWS, COLS);
	        radar.setNoiseFraction(0.01);
	        radar.setMonsterLocation(px, py);
	        radar.scan();
	        
	        for(int i = 0; i < 100; i++)
	        {
	            Thread.sleep(100); // sleep 100 milliseconds (1/10 second)
	            
	            //radar.scan(DX*i,DY*i);
	            px = px+dx;
	            py = py+dy;
	            if(px < ROWS && py < COLS && px > 0 && py > 0)
	            {
		            radar.setMonsterLocation(px, py);
		            radar.scan();                    
		           // frame.repaint();
	            }
	        }
	        
	        double velocity = radar.getMonsterVelocity();
	        
	        System.out.println("Velocity "+velocity);
	        System.out.println("expectedVelocity "+expectedVelocity);
	  
	       assertEquals(expectedVelocity, velocity,0.0);
	       
	        

	    }

	    
	    /*
	     * Tests the state after 3 generations
	     */
	    @Test
	    public void testFinalState()
	    {}


}
