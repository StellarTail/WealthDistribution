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


    // whether wealth inheritance is enabled for death from aging
    public static final boolean WEALTH_INHERITANCE_ENABLED = false;

    // the proportion of wealth inheritance
    // used only when inheritance is enabled
    public static final double WEALTH_INHERITANCE = 0.8;
    
    // higher class
    public static final int HIGHER_CLASS = 0;
    
    // tax for higher class
    public static final double TAX_HIGHER_CLASS = 0.3;
    
    // medium class
    public static final int MEDIUM_CLASS = 1;
    
    // tax for medium class
    public static final double TAX_MEDIUM_CLASS = 0.2;
    
    // lower class
    public static final int LOWER_CLASS = 2;
    
    // tax for lower class
    public static final double TAX_LOWER_CLASS = 0.1;
    
    // if set to true, patch growth rate will be a proportion of its maximum grain,
    // and absolute growth rate setting will be useless
    public static final boolean PROPORTIONAL_GROWTH_ENABLED = false;

    // the proportion of max grain as growth rate
    // used only proportional growth is enabled
    public static final double PATCH_GROWTH_PROPORTION = 0.2;

    // whether to generate all people in the same position at the beginning
    public static final boolean SAME_POSITION_ENABLED = false;

    // whether to apply tax on rich people
    public static final boolean TAXATION_ENABLED = false;

    // proportion of wealth taken away from wealth people as tax
    // used only when taxation is enabled
    public static final double TAX_PERCENTAGE = 0.5;

    // whether to enable proportional metabolism
    public static final boolean PROPORTIONAL_METABOLISM_ENABLED = false;

    // the proportion of metabolism relative to total wealth
    // used only when proportional metabolism is set
    public static final double METABOLISM_PROPORTION = 0.3;

    // minimum metabolism possible for a person
    // used only when proportional metabolism is set
    public static final int METABOLISM_MIN = 1;
}
