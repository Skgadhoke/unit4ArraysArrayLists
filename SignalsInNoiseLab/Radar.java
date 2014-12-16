import java.util.ArrayList;
/**
 * The model for radar scan and accumulator
 * 
 * @author @Simar Gadhoke
 * @version 19 July 2014
 */
public class Radar
{
        // stores whether each cell triggered detection for the current scan of the radar
        private boolean[][] currentScan;
        private boolean[][] previousScan;
        
        // value of each cell is incremented for each scan in which that cell triggers detection
        private ArrayList<Accumulator> accumulatorList = new ArrayList<Accumulator>(); // creates empty list for to hold Accumulator objects for each dx, dy
        private int[][] accumulator;
        
        // location of the monster
        private int monsterLocationRow;
        private int monsterLocationCol;
        
        // probability that a cell will trigger a false detection (must be >= 0 and < 1)
        private double noiseFraction;
        
        // number of scans of the radar since construction
        private int numScans;
        private int ROWS;
        private int COLS;
    /**
    * Constructor for objects of class Radar
    *
    * @param rows the number of rows in the radar grid
    * @param cols the number of columns in the radar grid
    */
    public Radar(int rows, int cols)
    {
        // initialize instance variables
        ROWS = rows;
        COLS = cols;
        currentScan = new boolean[rows][cols]; // elements will be set to false
        previousScan = new boolean[rows][cols]; // elements will be set to false
        accumulator = new int[rows][cols];
        
        // randomly set the location of the monster (can be explicitly set through the
        // setMonsterLocation method
        monsterLocationRow = (int)(Math.random() * rows);
        monsterLocationCol = (int)(Math.random() * cols);
        
        noiseFraction = 0.05;
        numScans= 0;
    }
    
    /**
     * The storePreviousScan is used to store the previous scan
     */
    private  void storePreviousScan()
    {   
        for(int i = 0; i < currentScan.length; i++)
        {       
            for(int j = 0; j < currentScan[0].length; j++)
            {
                previousScan[i][j] = currentScan[i][j];
            }
        }
    }
    
    /**
     * @param dx
     * @param dy
     * @return Accumulator
     * It finds the Accumulator (for dx and dy) in accumulatorList
     * if it finds dx,dy then returns Accumulator object else returns null
     */
    private Accumulator getAccumulator(int dx, int dy)
    {
        Accumulator accumulator = null;
        int accumDX;
        int accumDY;
        for (int i =0; i < accumulatorList.size(); i++)
        {
            accumulator = accumulatorList.get(i);
            accumDX = accumulator.getDx();
            accumDY = accumulator.getDy();
            
            if(accumDX == dx && accumDY== dy)
            {
                return accumulator;
            }
        }
        return null;
        
    }
    
    /**
     * Updates the frequency for dx and dy
     * Compares every true point with the previous and current scans
     * get dx and dy. If dx and dy >=0 and <=5, then finds the  Accumulator for this dx and dy in accumulatorList
     * If finds the dx, dy then increments the frequency
     * else will create a new Accumulat object for dx and dy.
     */
    private void updateAccumulator()
    {
        int dx, dy;
        Accumulator velocityAccumulator = null;
        
        for(int prevRow = 0; prevRow < previousScan.length; prevRow++)
        {
            for(int prevCol = 0; prevCol < previousScan[0].length; prevCol++)
            {
                if(previousScan[prevRow][prevCol])
                {
                    for(int currentRow = 0; currentRow < currentScan.length; currentRow++)
                    {
                        for(int currentCol = 0; currentCol < currentScan[0].length; currentCol++)
                        {
                            if(currentScan[currentRow][currentCol])
                            {
                                dx = currentRow-prevRow;
                                dy = currentCol - prevCol;
                                
                                if( Math.abs(dx)  >= 0 && Math.abs(dx) <= 5 && Math.abs(dy) >= 0 && Math.abs(dy) <= 5)
                                {
                                    velocityAccumulator = getAccumulator(dx, dy);
                                    if(velocityAccumulator == null)
                                    {
                                        velocityAccumulator = new Accumulator(dx, dy);
                                        accumulatorList.add(velocityAccumulator);
                                    }else
                                    {
                                        velocityAccumulator.incrementFrequency();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
    * Scans the radar
    * noise is injected into the grid and accumulator i s updated
    */
    public void scan()
    {           
        // zero the current scan grid
        for(int row = 0; row < currentScan.length; row++)
        {
            for(int col = 0; col < currentScan[0].length; col++)
            {
                currentScan[row][col] = false;
            }
        }
        // detect the monster
        currentScan[monsterLocationRow][monsterLocationCol] = true;
        
        // inject noise into the grid
        injectNoise();
        
        for(int row = 0; row < currentScan.length; row++)
        {
            for(int col = 0; col < currentScan[0].length; col++)
            {
                    if(currentScan[row][col] == true)
                    {
                        accumulator[row][col]++;
                    }
            }
    
        }
        // udpate the accumulator
        if(numScans > 0)
            {
                updateAccumulator();
            }
        
        // keep track of the total number of scans
        numScans++;
        
        // After the scan, copy the current scan into previousScan array for available to compare with current scan.
        storePreviousScan();
    }
    
    /**
    * Sets the location of the monster
    *
    * @param row the row in which the monster is located
    * @param col the column in which the monster is located
    * @pre row and col must be within the bounds of the radar grid
    */
    public void setMonsterLocation(int row, int col)
    {
        // remember the row and col of the monster's location
            monsterLocationRow = row;
            monsterLocationCol = col;
            // update the radar grid to show that something was detected at the specified location
            currentScan[row][col] = true;
        
        
    }
    
    /**
    * Sets the probability that a given cell will generate a false detection
    *
    * @param fraction the probability that a given cell will generate a flase detection expressed
    * as a fraction (must be >= 0 and < 1)
    */
    public void setNoiseFraction(double fraction)
    {
        noiseFraction = fraction;
    }
    
    /**
    * Returns true if the specified location in the radar grid triggered a detection.
    *
    * @param row the row of the location to query for detection
    * @param col the column of the location to query for detection
    * @return true if the specified location in the radar grid triggered a detection
    */
    public boolean isDetected(int row, int col)
    {
        return currentScan[row][col];
    }
    
    /**
    * Returns the number of times that the specified location in the radar grid has triggered a
    * detection since the constructor of the radar object.
    *
    * @param row the row of the location to query for accumulated detections
    * @param col the column of the location to query for accumulated detections
    * @return the number of times that the specified location in the radar grid has
    * triggered a detection since the constructor of the radar object
    */
    public int getAccumulatedDetection(int row, int col)
    {
        return accumulator[row][col];
    }
    
    /**
    * Returns the number of rows in the radar grid
    *
    * @return the number of rows in the radar grid
    */
    public int getNumRows()
    {
        return currentScan.length;
    }
    
    /**
    * Returns the number of columns in the radar grid
    *
    * @return the number of columns in the radar grid
    */
    public int getNumCols()
    {
        return currentScan[0].length;
    }
    
    /**
    * Returns the number of scans that have been performed since the radar object was constructed
    *
    * @return the number of scans that have been performed since the radar object was constructed
    */
    public int getNumScans()
    {
        return numScans;
    }
    
    /**
    * Sets cells as falsely triggering detection based on the specified probability
    */
    private void injectNoise()
    {
        for(int row = 0; row < currentScan.length; row++)
        {
             for(int col = 0; col < currentScan[0].length; col++)
                 {
                    // each cell has the specified probablily of being a false positive
                    if(Math.random() < noiseFraction)
                        {
                            currentScan[row][col] = true;
                        }
                 }
        }
    }
    
    /**
     * 
     * @return double
     * finds highest frequency of Accumultor object in accumlatorList.
     * Velocity = dy/dx of Accumulator having highest frequency.
     */
    public double getMonsterVelocity()
    {
          int highestFrequency = 0;
          int dx = 0;
          int dy = 0;
          double velocity = 0.0;
          boolean chainDetected = false;
          Accumulator accumulator = null;
          int frequency=0;
          
         for(int i = 0; i < accumulatorList.size(); i++)
         {
             accumulator = accumulatorList.get(i);
             frequency = accumulator.getFrequncy();
             if(frequency > highestFrequency)
             {
                  highestFrequency = frequency;
                 dx = accumulator.getDx();
                 dy = accumulator.getDy();
             }
             
         }
           System.out.println("highestFrequency  "+dx+","+dy+"="+highestFrequency);
           
          velocity = ((double)dy)/dx;
          return velocity;
          
    }

}