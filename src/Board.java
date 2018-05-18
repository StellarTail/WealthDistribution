import java.util.Random;

public class Board {

	// All parameters can be settled by user in the board
	private Random random = new Random();
	private int width;
    private int height;
	private int numPeople;
	private int maxVision;
	private int maxMetabolism;
	private int minLife;
	private int maxLife;
	private double percentBestLand;
	private int growthInterval;
	private int grainGrow;

	// Arrays of patches and turtles
	// Criterion for classes dividing and tax
	// Will be used for Gini index counting
	private Patch[][] patches;
	private Turtle[] turtles;
	private int lower_medium;
	private int medium_higher;
	private int lowest_wealth;
	private int highest_wealth;
	private int tick_count = 0;
	
	// Parameters will be printed
	private int lower_avg;
	private int medium_avg;
	private int higher_avg;
	private int lower_num;
	private int medium_num;
	private int higher_num;
	private int gini_index;
	
	// Extension implements switches
	private boolean tax = false;
	private boolean inheritance = false;
	private boolean guidance = false;
	
	Board(){}
	
	Board(int numPeople, 
			int maxVision, 
			int maxMetabolism, 
			int minLife, 
			int maxLife, 
			double percentBestLand,
			int growthInterval,
			int grainGrow){
		width = 50;
		height = 50;
		patches = new Patch[height][width];
		turtles = new Turtle[numPeople];
		this.numPeople = numPeople;
		this.maxVision = maxVision;
		this.maxMetabolism = maxMetabolism;
		this.minLife = minLife;
		this.maxLife = maxLife;
		this.percentBestLand = percentBestLand;
		this.growthInterval = growthInterval;
		this.grainGrow = grainGrow;
	}
	
	/** 
	 * Initialize the board
	 * Created corresponding to NetLogo Model
	 * !!!Miss one if sentence
	 */
	public void init() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				patches[i][j] = new Patch();
			}
		}
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				patches[i][j].setX(i);
				patches[i][j].setY(j);
				if(random.nextDouble() < percentBestLand) {
					patches[i][j].setMaxGrain(Params.MAX_GRAIN);
					patches[i][j].setGrain(Params.MAX_GRAIN);
				}
				else {
					patches[i][j].setMaxGrain(0);
					patches[i][j].setGrain(0);
				}
			}
		}
		
		for(int n = 0; n < 5; n++) {
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					if (patches[i][j].getMaxGrain() != 0) {
                        patches[i][j].setGrain(patches[i][j].getMaxGrain());
                        diffuse(i, j, 0.25);
                    }
				}
			}
		}
		
		for(int n = 0; n < 10; n++) {
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					diffuse(i, j, 0.25);
				}
			}
		}/*for(int i = 0; i < width; i++) {
			//for(int j = 0; j < height; j++) {
			System.out.println("Track Patche" );
			System.out.println("	MaxGrains: " + patches[i][0].getMaxGrain());
		}//}*/
	}
	
	/** 
	 * Diffuse the board
	 * Created corresponding to NetLogo Model
	 */
	private void diffuse(int x, int y, double d) {
		
		int x_prev = x - 1 < 0 ? 49 : x - 1;
        int x_next = x + 1 > 49 ? 0 : x + 1;
        int y_prev = y - 1 < 0 ? 49 : y - 1;
        int y_next = y + 1 > 49 ? 0 : y + 1;
		
		double share = patches[x][y].getGrain() * d / 8;
		System.out.println(share);
		patches[x][y].setMaxGrain(patches[x][y].getMaxGrain() - (int)(patches[x][y].getGrain() * d));
		patches[x][y].init();
		
		patches[x_next][y].setMaxGrain(patches[x][y].getMaxGrain() + (int)share);
		patches[x_next][y].init();
		
		patches[x_prev][y].setMaxGrain(patches[x][y].getMaxGrain() + (int)share);
		patches[x_prev][y].init();
		
		patches[x][y_next].setMaxGrain(patches[x][y].getMaxGrain() + (int)share);
		patches[x][y_next].init();
		
		patches[x][y_prev].setMaxGrain(patches[x][y].getMaxGrain() + (int)share);
		patches[x][y_prev].init();
		
		patches[x_next][y_next].setMaxGrain(patches[x][y].getMaxGrain() + (int)share);
		patches[x_next][y_next].init();
		
		patches[x_prev][y_next].setMaxGrain(patches[x][y].getMaxGrain() + (int)share);
		patches[x_prev][y_next].init();
		
		patches[x_next][y_prev].setMaxGrain(patches[x][y].getMaxGrain() + (int)share);
		patches[x_next][y_prev].init();
		
		patches[x_prev][y_prev].setMaxGrain(patches[x][y].getMaxGrain() + (int)share);
		patches[x_prev][y_prev].init();
	}
	
	/** 
	 * Set directions for all turtles
	 */
	public void setDirections() {
		int direction = -1;
		
		int vision = 0;
		int x = 0;
		int y = 0;
		
		int up_sum = 0;
		int down_sum = 0;
		int left_sum = 0;
		int right_sum = 0;
		for(int n = 0; n < numPeople; n++) {
			vision = turtles[n].getVision();
			x = turtles[n].getX();
			y = turtles[n].getY();
			int x_prev = x - 1 < 0 ? 49 : x - 1;
	        int x_next = x + 1 > 49 ? 0 : x + 1;
	        int y_prev = y - 1 < 0 ? 49 : y - 1;
	        int y_next = y + 1 > 49 ? 0 : y + 1;
			for(int v = 0; v < vision; v++) {
		        right_sum += patches[x_next][y].getGrain();
		        left_sum += patches[x_prev][y].getGrain();
		        down_sum += patches[x][y_prev].getGrain();
		        up_sum += patches[x][y_next].getGrain();
				x_prev = x_prev - 1 < 0 ? 49 : x_prev - 1;
		        x_next = x_next + 1 > 49 ? 0 : x_next + 1;
		        y_prev = y_prev - 1 < 0 ? 49 : y_prev - 1;
		        y_next = y_next + 1 > 49 ? 0 : y_next + 1;
			}
			if(guidance) {
				int total_sum = up_sum + down_sum + left_sum + right_sum;
				double scale = random.nextDouble();
				if(scale < up_sum/total_sum) {
					direction = Params.DIRECTION_UP;
				}
				if(scale >= up_sum/total_sum && scale < (up_sum + down_sum)/total_sum) {
					direction = Params.DIRECTION_DOWN;
				}
				if(scale >= (up_sum + down_sum)/total_sum && scale < (up_sum + down_sum + left_sum)/total_sum) {
					direction = Params.DIRECTION_LEFT;
				}
				if(scale >= (up_sum + down_sum + left_sum)/total_sum) {
					direction = Params.DIRECTION_RIGHT;
				}
			}
			else {
				if(up_sum >= down_sum && up_sum >= left_sum && up_sum >= right_sum) direction = Params.DIRECTION_UP;
				if(down_sum >= up_sum && down_sum >= left_sum && down_sum >= right_sum) direction = Params.DIRECTION_DOWN;
				if(left_sum >= up_sum && left_sum >= down_sum && left_sum >= right_sum) direction = Params.DIRECTION_LEFT;
				if(right_sum >= up_sum && right_sum >= down_sum && right_sum >= left_sum) direction = Params.DIRECTION_RIGHT;
			}
			turtles[n].setDirection(direction);
		}
	}

	/** 
	 * Assign directions to initial board
	 */
	public void assignTurtles() {
		int index = 0;
		int interval = width * height / numPeople;
		int point = 0;
		int row = 0;
		for(int n = 0; n < numPeople; n++) {
			point = index + random.nextInt(interval);

			if(index > 49) {
				index -= 50;
			}
			if(point > 49) {
				point -= 50;
				row = row + 1;
			}
			turtles[n] = new Turtle(maxVision, maxMetabolism, minLife, maxLife, point, row);
			patches[point][row].setTurtle(turtles[n]);
			index += interval;
		}
	}
	
	/** 
	 * Simulation ticks
	 * Must call after init() and assignTurtles() have been called
	 */
	public void tick() {
		setDirections();
		
		/*for(int n = 0; n <10;n++) {
			System.out.println("Track Patche" + n);
			System.out.println("	Grains: " + patches[n][0].getGrain());
			System.out.println("	MaxGrains: " + patches[n][0].getMaxGrain());
		}*/
		
		/*System.out.println("Track Turtle1:");
		System.out.println("	Turtle1's wealth: " + turtles[1].getWealth());
		System.out.println("	Turtle1's age: " + turtles[1].getAge());
		System.out.println("	Turtle1's metabolism: " + turtles[1].getMetabolism());
		System.out.println("	Turtle1's vision: " + turtles[1].getVision());
		System.out.println("	Turtle1's direction: " + turtles[1].getDirection());
		System.out.println("	Turtle1's x: " + turtles[1].getX());
		System.out.println("	Turtle1's y: " + turtles[1].getY());*/
		
		// Count criterion for classes
		// if a turtle has less than a third
		// the wealth of the richest turtle, it is lower class.  
		// If between one and two thirds, it is medium class.  
		// If over two thirds, it is higher class.
		lowest_wealth = turtles[0].getWealth();
		highest_wealth = turtles[0].getWealth();
		for(int n = 1; n < numPeople; n++) {
			lowest_wealth = lowest_wealth < turtles[n].getWealth() ? lowest_wealth : turtles[n].getWealth();
			highest_wealth = highest_wealth > turtles[n].getWealth() ? highest_wealth : turtles[n].getWealth();
		}
		lower_medium = highest_wealth / 3;
		medium_higher = lower_medium * 2;
		
		// simulate actions
		turtleAction();
		
		// harvest grains after given intervals
		if(tick_count == growthInterval) {
			harvest();
			tick_count = 0;
		}
		else {
			tick_count++;
		}
	}

	/** 
	 * Simulation turtles actions
	 * If the turtle is dead because of starvation
	 * then call endLife(n) instead of moveTurtle(n)
	 * otherwise call moveTurtle(n)
	 */
	public void turtleAction() {
		for(int n = 0; n < numPeople; n++) {
			if(turtles[n].getAge() == turtles[n].getMaxAge()) {
				endLife(n);
			}
			else {
				if(tax) {
					int status = -1;
					if(turtles[n].getWealth() >= medium_higher) status = Params.HIGHER_CLASS;
					if(turtles[n].getWealth() < lower_medium) status = Params.LOWER_CLASS;
					if(turtles[n].getWealth() >= lower_medium && turtles[n].getWealth() < medium_higher) status = Params.MEDIUM_CLASS;
					int reservation = turtles[n].tick(patches[turtles[n].getX()][turtles[n].getY()].getGrain(), status);
					if(turtles[n].getWealth() < 0) {
						endLife(n);
					}
					else {
						moveTurtle(n);
					}
					patches[turtles[n].getX()][turtles[n].getY()].setGrain(reservation);
				}
				else {
					int reservation = turtles[n].tick(patches[turtles[n].getX()][turtles[n].getY()].getGrain());
					if(turtles[n].getWealth() <= 0) {
						endLife(n);
					}
					else {
						moveTurtle(n);
					}
					patches[turtles[n].getX()][turtles[n].getY()].setGrain(reservation);
				}
			}
		}
	}
	
	/**
     * Reborn turtle when life ends
     *
     * @param n index of turtle in turtles
     */
	public void endLife(int n) {
		if(inheritance) {
			Turtle offspring = new Turtle(lowest_wealth, 
					highest_wealth, (int)(turtles[n].getWealth() * Params.WEALTH_INHERITANCE), maxVision, maxMetabolism,
					minLife, maxLife, turtles[n].getX(), turtles[n].getY());
			patches[turtles[n].getX()][turtles[n].getY()].setTurtle(offspring);
			turtles[n] = offspring;
		}
		else {
			Turtle offspring = new Turtle(lowest_wealth, highest_wealth, maxVision, maxMetabolism,
					minLife, maxLife, turtles[n].getX(), turtles[n].getY());
			patches[turtles[n].getX()][turtles[n].getY()].setTurtle(offspring);
			turtles[n] = offspring;
		}
	}
	
	
	/**
     * Move turtles according to direction got before
     * When the corresponding patch is not occupied
     *
     * @param n index of turtle in turtles
     */
	public void moveTurtle(int n) {
		int x = turtles[n].getX();
		int y = turtles[n].getY();
		int x_prev = x - 1 < 0 ? 49 : x - 1;
        int x_next = x + 1 > 49 ? 0 : x + 1;
        int y_prev = y - 1 < 0 ? 49 : y - 1;
        int y_next = y + 1 > 49 ? 0 : y + 1;
		switch(turtles[n].getDirection()) {
		case Params.DIRECTION_RIGHT:{
			if(patches[x_next][y].getTurtle() == null) {
				patches[x_next][y].setTurtle(turtles[n]);
				turtles[n].setX(x_next);
				patches[x][y].setTurtle(null);
			}
		}break;
		case Params.DIRECTION_LEFT:{
			if(patches[x_prev][y].getTurtle() == null) {
				patches[x_prev][y].setTurtle(turtles[n]);
				turtles[n].setX(x_prev);
				patches[x][y].setTurtle(null);
			}
		}break;
		case Params.DIRECTION_DOWN:{
			if(patches[x][y_prev].getTurtle() == null) {
				patches[x][y_prev].setTurtle(turtles[n]);
				turtles[n].setY(y_prev);
				patches[x][y].setTurtle(null);
			}
		}break;
		case Params.DIRECTION_UP:{
			if(patches[x][y_next].getTurtle() == null) {
				patches[x][y_next].setTurtle(turtles[n]);
				turtles[n].setY(y_next);
				patches[x][y].setTurtle(null);
			}
		}break;
		default:break;
		}
	}
	
	
	/**
     * Harvest grains after given intervals
     */
	public void harvest() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int reservation = patches[i][j].getGrain();
				int total_grains = grainGrow + reservation;
				total_grains = total_grains < patches[i][j].getMaxGrain() ? total_grains : patches[i][j].getMaxGrain();
				patches[i][j].setGrain(total_grains);
			}
		}
	}
	
	public int getHighestWealth() {
		return highest_wealth;
	}
	
	public int getLowestWealth() {
		return lowest_wealth;
	}
	
}
