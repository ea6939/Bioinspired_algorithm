/* Class for maze object; represents a standard rectangular maze */
import java.awt.Point;
public class Maze implements Cloneable{
	
	private int width;
	private int height;
	private boolean[][] hasNorthWall;
	private boolean[][] hasWestWall;
	private Point start;
	private Point end;
	
	public Maze(int width, int height, int startX, int startY, int endX, int endY){
		this.width = width;
		this.height = height;
		this.start = new Point(startX, startY);
		this.end = new Point(endX, endY);
		this.hasNorthWall = new boolean[width][height + 1];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height + 1; j++) {
				this.hasNorthWall[i][j] = true;
			}
		}
		this.hasWestWall = new boolean[width + 1][height];
		for (int i = 0; i < width + 1; i++) {
			for (int j = 0; j < height; j++) {
				this.hasWestWall[i][j] = true;
			}
		}
	}
	
	/* clones maze */
	public Object clone(){
		return new Maze(this);
	}
	
	/* hidden constructor for cloning */
	private Maze(Maze m){
		this.width = m.width;
		this.height = m.height;
		this.hasNorthWall = ((boolean[][])m.hasNorthWall.clone());
		this.hasWestWall = ((boolean[][])m.hasWestWall.clone());
		this.start = (Point) m.start.clone();
		this.end = (Point) m.end.clone();
	}
	
	public int getWidth(){
		return this.width;
	}

	public int getHeight(){
		return this.height;
	};
	
	public Point getStart(){
		return start;
	}

	public Point getEnd(){
		return end;
	};


	/* The methods below throw an IllegalArgumentException if the
	 parameters indicate an invalid (x,y) coordinate for this maze (i.e.
	 x is negative or >= the width, y is negative or >= the height) */

	
	/* Check if the fungus can move to a neighboring position */
	public boolean canMoveNorthFrom(Point p){
		if(validPosition(p)){
			return this.hasNorthWall[p.x][p.y] == false;
		}
		else return false;
	}

	public boolean canMoveSouthFrom(Point p){
		if(validPosition(p)){
			return this.hasNorthWall[p.x][(p.y + 1)] == false;
		}
		else return false;
	}

	public boolean canMoveEastFrom(Point p){
		if(validPosition(p)){
			return this.hasWestWall[(p.x + 1)][p.y] == false;
		}
		else return false;
	}

	public boolean canMoveWestFrom(Point p){
		if(validPosition(p)){
			return this.hasWestWall[p.x][p.y] == false;
		}
		else return false;
	}
	
	public boolean canMoveFrom(Point p, int angle){
		if (mod(angle, 360) == 0) return canMoveEastFrom(p);
		else if (mod(angle, 360) == 90) return canMoveNorthFrom(p);
		else if (mod(angle, 360) == 180) return canMoveWestFrom(p);
		else if (mod(angle, 360) == 270) return canMoveSouthFrom(p);
		else return false;
	}
	
	private int mod(int a, int b){
		int i = a%b;
		if (a<0) i += b;
		return i;
	}
	
	/* returns a neighboring position */
	public Point getPointNorthOf(Point p){
		return new Point(p.x, p.y-1);
	}

	public Point getPointSouthOf(Point p){
		return new Point(p.x, p.y+1);
	}

	public Point getPointEastOf(Point p){
		return new Point(p.x+1, p.y);
	}

	public Point getPointWestOf(Point p){
		return new Point(p.x-1, p.y);
	}
	
	public Point getPointOf(Point p, int angle){
		if (angle == 0) return getPointEastOf(p);
		else if (angle == 90) return getPointNorthOf(p);
		else if (angle == 180) return getPointWestOf(p);
		else if (angle == 270) return getPointSouthOf(p);
		else return null;
	}

	/* Adds a wall */
	public void buildNorth(Point p){
		if(validPosition(p)){
			this.hasNorthWall[p.x][p.y] = true;
		}
	}

	public void buildWest(Point p){
		if(validPosition(p)){
			this.hasWestWall[p.x][p.y] = true;
		}
	}

	/* Removes a wall*/
	public void breakNorth(Point p){
		if(validPosition(p)){
			this.hasNorthWall[p.x][p.y] = false;
		}
	}


	public void breakWest(Point p){
		if (validPosition(p)){
			this.hasWestWall[p.x][p.y] = false;
		}
	}

	/* checks if a given position is valid */
	private boolean validPosition(Point p){
		if ((p.x < 0) || (p.x >= this.width) || (p.y < 0) || (p.y >= this.height)) {
			return false;
		}
		else return true;
	}
}
