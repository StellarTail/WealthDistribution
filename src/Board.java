import java.lang.reflect.Array;
import java.util.Arrays;
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
	private double lower_avg;
	private double medium_avg;
	private double higher_avg;
	private int lower_num;
	private int medium_num;
	private int higher_num;
	private double gini_index;
	private int adjust_highest_wealth;
	private int adjust_lowest_wealth;
	
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
			int grainGrow,
			boolean tax,
			boolean inheritance,
			boolean guidance){
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
		this.tax = tax;
		this.inheritance = inheritance;
		this.guidance = guidance;
	}
	
	/** 
	 * Initialize the board
	 * Created corresponding to NetLogo Model
	 * !!!Miss one if sentence
	 */
	public void init() {
		double[][] maxGrainVals = new double[height][width];
		double[][] iniGrains = new double[height][width];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(random.nextDouble() < percentBestLand) {
					maxGrainVals[i][j] = Params.MAX_GRAIN;
					iniGrains[i][j] = Params.MAX_GRAIN;
				}
				else {
					maxGrainVals[i][j] = 0;
					iniGrains[i][j] = 0;
				}
			}
		}
		
		for(int n = 0; n < 5; n++) {
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					if (maxGrainVals[i][j] != 0) {
						iniGrains[i][j] = maxGrainVals[i][j];
                        diffuse(iniGrains, i, j, 0.25);
                    }
				}
			}
		}
		
		for(int n = 0; n < 10; n++) {
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					diffuse(iniGrains, i, j, 0.25);
				}
			}
		}
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int grainHere = (int) iniGrains[i][j];
				patches[i][j] = new Patch(grainHere, grainHere);
				patches[i][j].setX(i);
				patches[i][j].setY(j);
			}
		}
	}
	
	/** 
	 * Diffuse the board
	 * Created corresponding to NetLogo Model
	 */
	private void diffuse(double[][] iniGrains, int x, int y, double d) {
		
		// define adjacent patches' coordinate
		int x_prev = x - 1 < 0 ? 49 : x - 1;
        int x_next = x + 1 > 49 ? 0 : x + 1;
        int y_prev = y - 1 < 0 ? 49 : y - 1;
        int y_next = y + 1 > 49 ? 0 : y + 1;
		
        // diffuse grains to adjacent patches
		double share = iniGrains[x][y] * d / 8;
		iniGrains[x][y] -= iniGrains[x][y] * d;
		iniGrains[x_next][y] += share;
		iniGrains[x_prev][y] += share;
		iniGrains[x][y_next] += share;
		iniGrains[x][y_prev] += share;
		iniGrains[x_next][y_next] += share;
		iniGrains[x_prev][y_next] += share;
		iniGrains[x_next][y_prev] += share;
		iniGrains[x_prev][y_prev] += share;
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
		
		// get Gini index relative params
		relativeParams();
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
	
	/**
     * Count all params relative to gini index calculating
     */
	public void relativeParams() {
		double lower_sum = 0;
		double medium_sum = 0;
		double higher_sum = 0;
		
		double temp_wealth = 0.0;
		double current_income = 0;
		double prev_income = 0;
		double current_income_percent = 0.0;
		double prev_income_percent = 0.0;
		double current_population_percent = 0.0;
		double prev_population_percent = 0.0;
		double actual_area = 0.0;
		
		double[] wealth_array = new double[numPeople];
		for(int n = 0; n < numPeople; n++) {
			if(turtles[n].getWealth() >= medium_higher) {
				higher_num++;
				temp_wealth = turtles[n].getWealth();
				higher_sum += temp_wealth;
			}
			if(turtles[n].getWealth() < lower_medium) {
				lower_num++;
				temp_wealth = turtles[n].getWealth();
				lower_sum += temp_wealth;
			}
			if(turtles[n].getWealth() >= lower_medium && turtles[n].getWealth() < medium_higher) {
				medium_num++;
				temp_wealth = turtles[n].getWealth();
				medium_sum += temp_wealth;
			}
			wealth_array[n] = turtles[n].getWealth();
		}
		lower_avg = lower_sum / lower_num / Params.ADJUSTMENT_COEFFICIENT;
		medium_avg = medium_sum / medium_num / Params.ADJUSTMENT_COEFFICIENT;
		higher_avg = higher_sum / higher_num / Params.ADJUSTMENT_COEFFICIENT;
		adjust_highest_wealth = (int)(highest_wealth /  Params.ADJUSTMENT_COEFFICIENT);
		adjust_lowest_wealth = lowest_wealth;
		
		// count Gini index
		Arrays.sort(wealth_array);
		double total_sum = lower_sum + medium_sum + higher_sum;
		double precise_numPeople = numPeople;
		for(int n = 1; n < numPeople; n++) {
			
			prev_income += wealth_array[n-1];
			prev_income_percent = prev_income / total_sum;
			prev_population_percent = n / precise_numPeople;
			
			current_income = prev_income + wealth_array[n];
			current_income_percent = current_income / total_sum;
			current_population_percent = (n + 1) / precise_numPeople;
			
			actual_area += (current_population_percent - prev_population_percent) * (prev_income_percent + (1 / 2) * (current_income_percent - prev_income_percent));
		}
		gini_index = (0.5 - actual_area) / 0.5;
	}
	
	/**
     * Recover all params relative to gini index calculating
     */
	public void recover() {
		lower_avg = 0.0;
		medium_avg = 0.0;
		higher_avg = 0.0;
		lower_num = 0;
		medium_num = 0;
		higher_num = 0;
		gini_index = 0.0;
	}
	
	public int getLowerNum() {
		return lower_num;
	}
	
	public int getMediumNum() {
		return medium_num;
	}
	
	public int getHigherNum() {
		return higher_num;
	}
	
	public double getLowerAvg() {
		return lower_avg;
	}
	
	public double getMediumAvg() {
		return medium_avg;
	}
	
	public double getHigherAvg() {
		return higher_avg;
	}
	
	public double getGiniIndex() {
		return gini_index;
	}
	
	public int getAdjustHighestWealth() {
		return adjust_highest_wealth;
	}

	public int getAdjustLowestWealth() {
		return adjust_lowest_wealth;
	}
	
	
}
