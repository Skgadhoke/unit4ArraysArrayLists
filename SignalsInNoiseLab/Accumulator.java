/**
 * 
 * @author Simar Gadhoke
 * This is Accumulator class which tracks the frequency for each dx and dy.
 */
public class Accumulator 
{
    private int dx = 0; // creating variable dx
    private int dy = 0; // creating variable dy
    private int frequency = 0; // creating variable ferquency for counting the number o times

    /**
     * Constructor
     * @param dx
     * @param dy
     */
    public Accumulator(int dx, int dy)
    {
        this.dx = dx;
        this.dy = dy;
        this.frequency++;
    
    }
    
    /**
     * @return int 
     */
    public int getDx(){
        return this.dx;
    }
    
    /**
     * @return int
     */
    public int getDy(){
        return this.dy;
    }
    
    /**
     * @return int
     */
    public int getFrequncy()
    {
        return frequency;
    }
    
    /**
     * increments the frequency by 1
     */
    public void incrementFrequency()
        {
        
            this.frequency++;
    
        }
    
    /**
     * returns string in format of dx,dy; frequency
     */
    public String toString()
    {
        return dx+","+dy+" ; "+frequency;
    }

}