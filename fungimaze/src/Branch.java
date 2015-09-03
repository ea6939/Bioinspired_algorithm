import java.awt.Point;
import java.util.ArrayList;

public class Branch implements Cloneable{
	private int width;
	private int height;
	private Point start;
	private Point end;
	private ArrayList<Point> positions;
	private int length;
	private int startAngle;// 0 = east, 90 = north, 180 = west, 270 = south, 360 = east, etc...
	private int angle;
	

	public Branch(int width, int height, int startX, int startY, int endX, int endY) {
		this.width = width;
		this.height = height;
		this.start = new Point(startX, startY);
		this.end = new Point(endX, endY);
		this.positions = new ArrayList<Point>();
		this.positions.add(start);
		this.length = 1;
		this.startAngle = 0;
		this.angle = 0;
	}
	
	@SuppressWarnings("unchecked")
	public Branch(Branch b){
		this.width = b.width;
		this.height = b.height;
		this.start = (Point)b.start.clone();
		this.end = (Point)b.end.clone();
		this.positions = (ArrayList<Point>)b.positions.clone();
		this.length = b.length;
		this.startAngle = b.startAngle;
		this.angle = b.angle;
	}
	
	public Object clone(){
		return new Branch(this);
	}

	public ArrayList<Point> getPositions(){
		return positions;
	}

	public Point getStart(){
		return start;
	}

	public Point getLastPoint(){
		return positions.get(length-1);
	}
	
	public int getStartAngle(){
		return startAngle;
	}
	
	public int getAngle(){
		return angle;
	}
	
	public void setStartAngle(int angle){
		startAngle = angle;
		this.angle = angle;
	}

	public boolean isComplete() {
		return (getLastPoint().equals(end));
	}

	public int getLength() {
		return this.length;
	}
	
	public boolean has(Point p){
		if(positions.contains(p)){
			return true;
		}
		else return false;
	}
	
	public String getDirection(){
		if (mod(angle, 360)==0) return "EAST";
		else if (mod(angle, 360)==90) return "NORTH";
		else if (mod(angle, 360)==180) return "WEST";
		else if (mod(angle, 360)==270) return "SOUTH";
		else return "NONE";
	}
	
	public void turnLeft(){
		if (getDirection().equals("EAST")) moveNorth();
		else if (getDirection().equals("NORTH")) moveWest();
		else if (getDirection().equals("WEST")) moveSouth();
		else if (getDirection().equals("SOUTH")) moveEast();
	}
	
	public void turnRight(){
		if (getDirection().equals("EAST")) moveSouth();
		else if (getDirection().equals("NORTH")) moveEast();
		else if (getDirection().equals("WEST")) moveNorth();
		else if (getDirection().equals("SOUTH")) moveWest();
	}

	public void moveNorth(){
		if(getLastPoint().y > 0){	
			int newX = getLastPoint().x;
			int newY = getLastPoint().y - 1;
			positions.add(new Point(newX, newY));
			length++;
			if(getDirection().equals("EAST")){
				angle+=90;
			}
			if(getDirection().equals("WEST")){
				angle-=90;
			}
		}
	}

	public void moveSouth(){
		if(getLastPoint().y < height-1){
			int newX = getLastPoint().x;
			int newY = getLastPoint().y +1;
			positions.add(new Point(newX, newY));
			length++;
			if(getDirection().equals("EAST")){
				angle-=90;
			}
			if(getDirection().equals("WEST")){
				angle+=90;
			}
		}
	}

	public void moveEast(){
		if(getLastPoint().x < width-1){
			int newX = getLastPoint().x +1;
			int newY = getLastPoint().y;
			positions.add(new Point(newX, newY));
			length++;
			if(getDirection().equals("NORTH")){
				angle-=90;
			}
			if(getDirection().equals("SOUTH")){
				angle+=90;
			}
		}
	}

	public void moveWest(){
		if(getLastPoint().x > 0){
			int newX = getLastPoint().x -1;
			int newY = getLastPoint().y;
			positions.add(new Point(newX, newY));
			length++;
			if(getDirection().equals("NORTH")){
				angle+=90;
			}
			if(getDirection().equals("SOUTH")){
				angle-=90;
			}
		}
	}
	
	public void move(int angle){
		if (mod(angle, 360) == 0) moveEast();
		else if (mod(angle, 360) == 90) moveNorth();
		else if (mod(angle, 360) == 180) moveWest();
		else if (mod(angle, 360) == 270) moveSouth();
	}
	
	private int mod(int a, int b){
		int i = a%b;
		if (a<0) i += b;
		return i;
	}
}