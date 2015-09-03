import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MazeDisplay extends JPanel implements Listener{

	private static final long serialVersionUID = 1L;
	
	private Maze maze;
	private Fungus solution;
	private static final Color BACKGROUND_COLOR = Color.white;
	private static final Color WALL_COLOR = Color.black;
	private static final Color SOLUTION_COLOR = Color.green;
	int d;
	static Branch branch;
	
	public MazeDisplay(){
		maze = null;
		solution = null;
		setBackground(BACKGROUND_COLOR);
		setOpaque(true);

	}

	public void start(Fungus solution){
		setSolution(solution);
		try{
			Thread.sleep(30);
	
		}
		catch (InterruptedException localInterruptedException) {}

	}

	public void change(Fungus solution){
		setSolution(solution);
		try{
			Thread.sleep(30);
		}
		catch (InterruptedException localInterruptedException) {}
	}

	public Maze getMaze(){
		return maze;
	}

	public void setMaze(Maze maze){
		this.maze = maze;
		repaint();
	}

	public Fungus getSolution(){
		return solution;
	}

	public void setSolution(Fungus solution){
		this.solution = solution;
		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (maze != null){
			paintMaze(g);
			if (solution != null) {
				paintSolution(g);
			}
		}
	}

	private void paintMaze(Graphics g){
		g.setColor(WALL_COLOR);
		for (int x = 0; x < maze.getWidth(); x++) {
			for (int y = 0; y < maze.getHeight(); y++){
				Point position = new Point(x, y);
				Rectangle r = makeRectangle(g, position);
				if (!maze.canMoveNorthFrom(position)){
					boolean hasWall = true;
					if ( position.equals(maze.getStart()) || position.equals(maze.getEnd())){
						if ((x != 0) && (y == 0)) {
							hasWall = false;
						}
					}
					if (hasWall) {
						g.drawLine(r.x, r.y, r.x + r.width, r.y);
					}
				}
				if (!maze.canMoveWestFrom(position)){
					boolean hasWall = true;
					if ( position.equals(maze.getStart()) || position.equals(maze.getEnd())){
						if (x == 0) {
							hasWall = false;
						}
					}
					if (hasWall) {
						g.drawLine(r.x, r.y, r.x, r.y + r.height);
					}
				}
			}
		}
		for (int x = 0; x < maze.getWidth(); x++){
			Point position = new Point(x, maze.getHeight() -1);
			Rectangle r = makeRectangle(g, position);
			if (!maze.canMoveSouthFrom(position)){
				boolean hasWall = true;
				if ( position.equals(maze.getStart()) || position.equals(maze.getEnd())){
					if ((x != 0) && (x != maze.getWidth() - 1)) {
						hasWall = false;
					}
				}
				if (hasWall) {
					g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
				}
			}
		}
		for (int y = 0; y < maze.getHeight(); y++){
			Point position = new Point(maze.getWidth()-1, y);
			Rectangle r = makeRectangle(g, position);
			if (!maze.canMoveEastFrom(new Point(maze.getWidth() - 1, y))){
				boolean hasWall = true;
				if ( position.equals(maze.getStart()) || position.equals(maze.getEnd())){
					hasWall = false;
				}
				if (hasWall) {
					g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
				}
			}
		}
	}

	private void paintSolution(Graphics g)	{
		g.setColor(SOLUTION_COLOR);
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		for (int i = 0; i<solution.getSize(); i++){
			Branch thisBranch = solution.getBranch(i);
			int length = thisBranch.getLength();
			ArrayList<Point> positions = thisBranch.getPositions();
			Point startPoint = makeCenterPoint(g, positions.get(0));
			g2.drawLine(startPoint.x, startPoint.y, startPoint.x, startPoint.y);
			for (int j = 1; j < length; j++){
				Point p1 = makeCenterPoint(g, positions.get(j-1));
				Point p2 = makeCenterPoint(g, positions.get(j));
				g2.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		}
	}

	private Rectangle makeRectangle(Graphics g, Point p){
		if (maze == null) {
			return new Rectangle(0, 0, 0, 0);
		}
		Insets insets = getInsets();

		int componentWidth = getWidth() - insets.left - insets.right - 1;
		int componentHeight = getHeight() - insets.top - insets.bottom - 1;

		double xoffset = componentWidth * p.x / maze.getWidth();
		double xoffset2 = componentWidth * (p.x + 1) / maze.getWidth();
		double yoffset = componentHeight * p.y / maze.getHeight();
		double yoffset2 = componentHeight * (p.y + 1) / maze.getHeight();

		return new Rectangle(
				insets.left + (int)xoffset, 
				insets.top + (int)yoffset, 
				(int)xoffset2 - (int)xoffset, 
				(int)yoffset2 - (int)yoffset);
	}

	private Point makeCenterPoint(Graphics g, Point p){
		if (maze == null) {
			return new Point(0, 0);
		}
		Rectangle r = makeRectangle(g, p);

		return new Point(r.x + r.width / 2, r.y + r.height / 2);
	}
}