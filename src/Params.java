/**
 * Constants for simulation
 * Feng Zhao, 903591
 * Mingyang Zhang, 650242
 */

public class Params {
	
	// the maximum amount of people
	public final static int MAX_NUM_PEOPLE = 1000;
	
	// the maximum distance of vision
	public final static int MAX_VISION = 15;
	
	// the maximum amount of metabolism
	public final static int MAX_METABOLISM = 25;
	
	// the maximum ticks of life-expectancy
	public final static int MAX_LIFE = 100;
	
	// the maximum percentage of best-land
	public final static double MAX_BESTLAND_PER = 0.25;
	
	// the maximum amount of growth interval
	public final static int MAX_GROWTH_INTERVAL = 10;
	
	// the maximum amount of grain growth
	public final static int MAX_GRAIN_GROWTH = 10;
	
	// the direction of up
	public final static int DIRECTION_UP = 1;
	
	// the direction of down
	public final static int DIRECTION_DOWN = 2;
	
	// the direction of left
	public final static int DIRECTION_LEFT = 3;
		
	// the direction of right
	public final static int DIRECTION_RIGHT = 4;
	
	// the maximum amount of grain which a patch can have
    public final static int MAX_GRAIN = 50;

    // the width of board (world space)
    public static final int BOARD_WIDTH = 50;

    // the height of board (world space)
    public static final int BOARD_HEIGHT = 50;

    // the proportion of wealth inheritance
    public static final double WEALTH_INHERITANCE = 0.8;
    
    // higher class
    public static final int HIGHER_CLASS = 0;
    
    // tax for higher class
    public static final double TAX_HIGHER_CLASS = 0.5;
    
    // medium class
    public static final int MEDIUM_CLASS = 1;
    
    // tax for medium class
    public static final double TAX_MEDIUM_CLASS = 0.2;
    
    // lower class
    public static final int LOWER_CLASS = 2;
    
    // tax for lower class
    public static final double TAX_LOWER_CLASS = 0.0;
    
    // adjustment coefficient
    public static final double ADJUSTMENT_COEFFICIENT = 3.0;
    
}
