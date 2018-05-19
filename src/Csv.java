
/**
 * Feng Zhao, 903591
 * Mingyang Zhang, 650242
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Output formatting and csv file generation
 *
 * @author Feng Zhao
 * @date 18/05/2018
 */
public class Csv {

    // The board
    private Board board;

    // File writer
    private PrintWriter pw;

    // 2 decimal places formatting
    private DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Open or create a csv file and have titles ready
     *
     * @throws FileNotFoundException Error creating the csv file
     */
    public Csv(Board board, String filename) throws FileNotFoundException {

        this.board = board;

        pw = new PrintWriter(new File(filename + ".csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("Tick,");
        sb.append("Number of people in lower class,");
        sb.append("Number of people in medium class,");
        sb.append("Number of people in higher class,");
        sb.append("Avg wealth of lower class,");
        sb.append("Avg wealth of medium class,");
        sb.append("Avg wealth of higher class,");
        sb.append("Min wealth,");
        sb.append("Max wealth,");
        sb.append("Gini Index,");
        sb.append("\n");
        pw.write(sb.toString());
    }
    
    /**
     * Record information at a given tick
     *
     * @param board The current board
     * @param time  Current time tick
     */
    public void record(int tick) {
        StringBuilder sb = new StringBuilder();
        sb.append(tick + 1).append(",");
        sb.append(board.getLowerNum() + "," + board.getMediumNum() + "," + board.getHigherNum() + ",");
        sb.append(df.format(board.getLowerAvg()) + "," + df.format(board.getMediumAvg()) + "," + df.format(board.getHigherAvg()) + ",");
        sb.append(board.getAdjustLowestWealth() + "," + board.getAdjustHighestWealth() + ",");
        sb.append(board.getGiniIndex());
        sb.append("\n");
        pw.write(sb.toString());
    }

    /**
     * Close the file when simulation stops
     */
    public void closeFile() {
        pw.close();
    }
}
