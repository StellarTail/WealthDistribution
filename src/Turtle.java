import java.util.Random;

public class Turtle {
	
	private Random random = new Random();
	
	// Attributes of turtles
	private int vision;
	private int wealth;
    private int metabolism;
    private int age;
    private int maxAge;
    private int direction;
    private int x;
    private int y;
    private int status;
    
    Turtle(){}
    
    /** 
	 * Constructor for first generation
	 */
    Turtle(int maxVision, int maxMetabolism, int minLife, int maxLife, int x, int y){
    	int random_wealth = random.nextInt(Params.MAX_GRAIN);
    	vision = 1 + random.nextInt(maxVision);
    	metabolism = 1 + random.nextInt(maxMetabolism);
    	wealth = metabolism + random_wealth;
    	age = 0;
    	maxAge = minLife + random.nextInt(maxLife - minLife);
    	this.x = x;
    	this.y = y;
    }
    
    /** 
	 * Constructor for offsprings without inheritance
	 */
    Turtle(int lowest_wealth, int highest_wealth, int maxVision, int maxMetabolism, int minLife, int maxLife, int x, int y){
    	int random_wealth = lowest_wealth + random.nextInt(highest_wealth - lowest_wealth);
    	vision = 1 + random.nextInt(maxVision);
    	metabolism = 1 + random.nextInt(maxMetabolism);
    	wealth = metabolism + random_wealth;
    	age = 0;
    	maxAge = minLife + random.nextInt(maxLife - minLife);
    	this.x = x;
    	this.y = y;
    }
    
    /** 
	 * Constructor for offsprings with inheritance
	 */
    Turtle(int lowest_wealth, int highest_wealth, int inheritage, int maxVision, int maxMetabolism, int minLife, int maxLife, int x, int y){
    	int random_wealth = lowest_wealth + random.nextInt(highest_wealth - lowest_wealth);
    	vision = 1 + random.nextInt(maxVision);
    	metabolism = 1 + random.nextInt(maxMetabolism);
    	wealth = (int)(metabolism + random_wealth + inheritage * Params.WEALTH_INHERITANCE);
    	age = 0;
    	maxAge = minLife + random.nextInt(maxLife - minLife);
    	this.x = x;
    	this.y = y;
    }
    
    /** 
	 * Simulation tick without tax
	 * Only be called when this turtle is alive
	 */
    public int tick(int grains) {
    	if(grains <= 0){
    		age++;
    		wealth = wealth - metabolism;
    		return 0;
    	}
    	int meal = random.nextInt(grains);
    	age++;
    	wealth += meal;
    	wealth = wealth - metabolism;
    	int reservation = grains - meal;
    	return reservation;
    }
    
    /** 
	 * Simulation tick with tax
	 * Only be called when this turtle is alive
	 */
    public int tick(int grains, int status) {
    	if(grains <= 0){
    		age++;
    		wealth = wealth - metabolism;
    		return 0;
    	}
    	int meal = random.nextInt(grains);
    	int meal_with_tax = 0;
    	switch(status) {
    	case Params.HIGHER_CLASS: meal_with_tax = (int) (meal * (1 - Params.TAX_HIGHER_CLASS));break;
    	case Params.MEDIUM_CLASS: meal_with_tax = (int) (meal * (1 - Params.TAX_MEDIUM_CLASS));break;
    	case Params.LOWER_CLASS: meal_with_tax = (int) (meal * (1 - Params.TAX_LOWER_CLASS));break;
    	default:break;
    	}
    	age++;
    	wealth += meal_with_tax;
    	wealth = wealth - metabolism;
    	int reservation = grains - meal;
    	return reservation;
    }
    
    public void setVision(int vision) {
    	this.vision = vision;
    }
    
    public void setWealth(int wealth) {
    	this.wealth = wealth;
    }
    
    public void setMetabolism(int metablolism) {
    	this.metabolism = metablolism;
    }
    
    public void setAge(int age) {
    	this.age = age;
    }
    
    public void setMaxAge(int maxAge) {
    	this.maxAge = maxAge;
    }
    
    public void setDirection(int direction) {
    	this.direction = direction;
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
    
    public int getVision() {
    	return vision;
    }
    
    public int getWealth() {
    	return wealth;
    }
    
    public int getMetabolism() {
    	return metabolism;
    }
    
    public int getAge() {
    	return age;
    }
     
    public int getMaxAge() {
    	return maxAge;
    }
       
    public int getDirection() {
    	return direction;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
}
