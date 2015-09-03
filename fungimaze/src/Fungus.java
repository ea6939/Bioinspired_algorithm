import java.awt.Point;
import java.util.ArrayList;

public class Fungus {
	
	private ArrayList<Branch> branches;
	private ArrayList<Point> positions;
	private int width;
	private int height;
	private Point mazeStart;
	private Point mazeEnd;
	
	public Fungus(int width, int height, int mazeStartX, int mazeStartY, int mazeEndX, int mazeEndY){
		branches = new ArrayList<Branch>();
		positions = new ArrayList<Point>();
	}
	
	public ArrayList<Branch> getBranches(){
		return branches;
	}
	
	public ArrayList<Point> getPositions(){
		return positions;
	}
	
	public int getMazeWidth() {
		return this.width;
	}

	public int getMazeHeight() {
		return this.height;
	}
	
	public Point getMazeStart(){
		return mazeStart;
	}
	
	public Point getMazeEnd(){
		return mazeEnd;
	}
	
	public Branch getBranch(int i){
		return branches.get(i);
	}
	
	public int getSize(){
		return branches.size();
	}
	
	public void addBranch(Branch b){
		branches.add(b);
		positions.addAll(b.getPositions());
	}
	
	public boolean has(Point p){
		return positions.contains(p);
	}
	
	public void removeBranch(Branch b){
		if (branches.contains(b)){
			branches.remove(b);
		}
	}

}
