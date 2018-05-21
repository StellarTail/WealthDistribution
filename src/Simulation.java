
/**
 * Feng Zhao, 903591
 * Mingyang Zhang, 650242
 */
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * Simulation class
 *
 * @author Feng Zhao
 * @date 18/05/2018
 */
public class Simulation {

	private Board board;
	
	private Csv csv;
	
	/**
     * Run the simulation
     *
     * @param length time length of simulation
     */
    private void run(int numPeople, 
    		int maxVision, 
    		int maxMetabolism, 
    		int minLife,
    		int maxLife, 
    		double percentBestLand,
			int growthInterval,
			int grainGrow, 
			int ticks) {
    	originModel(numPeople, maxVision , maxMetabolism, minLife, maxLife, percentBestLand, growthInterval, grainGrow, ticks);
    	modelWithTax(numPeople, maxVision , maxMetabolism, minLife, maxLife, percentBestLand, growthInterval, grainGrow, ticks);
    	modelWithInheri(numPeople, maxVision , maxMetabolism, minLife, maxLife, percentBestLand, growthInterval, grainGrow, ticks);
    	modelWithGuidance(numPeople, maxVision , maxMetabolism, minLife, maxLife, percentBestLand, growthInterval, grainGrow, ticks);
    }
    
    /**
     * Original model
     *
     * @param length time length of simulation
     */
    private void originModel(int numPeople, 
    		int maxVision, 
    		int maxMetabolism, 
    		int minLife,
    		int maxLife, 
    		double percentBestLand,
			int growthInterval,
			int grainGrow,
			int ticks) {
    	board = new Board(numPeople, maxVision , maxMetabolism, minLife, maxLife, percentBestLand, growthInterval, grainGrow, false, false ,false);
    	try {
			csv = new Csv(board, "origin");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	board.init();
    	board.assignTurtles();
        for (int n = 0; n < ticks; n++) {
        	board.tick();
        	csv.record(n);
        	board.recover();
        }
        csv.closeFile();
        board = null;
    }
    
    /**
     * Model with tax open
     *
     * @param length time length of simulation
     */
    private void modelWithTax(int numPeople, 
    		int maxVision, 
    		int maxMetabolism, 
    		int minLife,
    		int maxLife, 
    		double percentBestLand,
			int growthInterval,
			int grainGrow,
			int ticks) {
    	board = new Board(numPeople, maxVision , maxMetabolism, minLife, maxLife, percentBestLand, growthInterval, grainGrow, true, false, false);
    	try {
			csv = new Csv(board, "tax");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	board.init();
    	board.assignTurtles();
        for (int n = 0; n < ticks; n++) {
        	board.tick();
        	csv.record(n);
        	board.recover();
        }
        csv.closeFile();
        board = null;
    }
    
    /**
     *  Model with inheritance open
     *
     * @param length time length of simulation
     */
    private void modelWithInheri(int numPeople, 
    		int maxVision, 
    		int maxMetabolism, 
    		int minLife,
    		int maxLife, 
    		double percentBestLand,
			int growthInterval,
			int grainGrow,
			int ticks) {
    	board = new Board(numPeople, maxVision , maxMetabolism, minLife, maxLife, percentBestLand, growthInterval, grainGrow, false, true, false);
    	try {
			csv = new Csv(board, "inheritance");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	board.init();
    	board.assignTurtles();
        for (int n = 0; n < ticks; n++) {
        	board.tick();
        	csv.record(n);
        	board.recover();
        }
        csv.closeFile();
        board = null;
    }
    
    /**
     * Model with guidance open
     *
     * @param length time length of simulation
     */
    private void modelWithGuidance(int numPeople, 
    		int maxVision, 
    		int maxMetabolism, 
    		int minLife,
    		int maxLife, 
    		double percentBestLand,
			int growthInterval,
			int grainGrow,
			int ticks) {
    	board = new Board(numPeople, maxVision , maxMetabolism, minLife, maxLife, percentBestLand, growthInterval, grainGrow, false, false, true);
    	try {
			csv = new Csv(board, "guidance");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	board.init();
    	board.assignTurtles();
        for (int n = 0; n < ticks; n++) {
        	board.tick();
        	csv.record(n);
        	board.recover();
        }
        csv.closeFile();
        board = null;
    }
    
    public static void main(String[] args) {
    	Simulation simulation = new Simulation();
    	int numPeople = Integer.parseInt(args[0]);
    	int maxVision = Integer.parseInt(args[1]);
    	int maxMetabolism = Integer.parseInt(args[2]);
    	int minLife = Integer.parseInt(args[3]);
    	int maxLife = Integer.parseInt(args[4]);
    	double percentBestLand = Double.parseDouble(args[5]);
    	int growthInterval = Integer.parseInt(args[6]);
    	int grainGrow = Integer.parseInt(args[7]);
    	int ticks = 200;
    	simulation.run(numPeople, maxVision , maxMetabolism, minLife, maxLife, percentBestLand, growthInterval, grainGrow, ticks);
    	System.out.println("Congratulations");
    }
}
