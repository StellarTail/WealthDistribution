import java.util.Random;

public class Patch {

	private int grain;
    private int maxGrain;
    private int x;
    private int y;
    private Turtle turtle;
    
    Patch(){}
    
    Patch(int grain, int maxGrain){
    	this.grain = grain;
    	this.maxGrain = maxGrain;
    }
    
    public void init() {
    	grain = maxGrain;
    }
    
    public void setTurtle(Turtle turtle) {
    	this.turtle = turtle;
    }
    
    public void setGrain(int grain) {
    	this.grain = grain;
    }
    
    public void setMaxGrain(int maxGrain) {
    	this.maxGrain = maxGrain;
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
    
    public Turtle getTurtle() {
    	return turtle;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    public int getGrain() {
    	return grain;
    }
    
    public int getMaxGrain() {
    	return maxGrain;
    }
}