import java.util.HashSet;
import java.util.Set;

public class Simulation {

	private Board board;
	
	/**
     * Run the simulation
     *
     * @param length time length of simulation
     */
    private void run(int ticks) {
    	board = new Board(250, 5 ,15, 1, 83, 0.1, 1, 4);
    	board.init();
    	board.assignTurtles();
        for (int n = 0; n < ticks; n++) {
        	//System.out.println("For tick " + n);
        	//System.out.println("	Lowest Wealth:" + board.getLowestWealth());
        	//System.out.println("	Highest Wealth:" + board.getHighestWealth());
        	board.tick();
        }
    }
    
    public static void main(String[] args) {
    	Simulation simulation = new Simulation();
    	simulation.run(100);
    	System.out.println("Congratulations");
    }
}
