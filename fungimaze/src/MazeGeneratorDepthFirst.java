/* Randomly generates a maze using depth first search */

import java.util.List;
import java.util.Deque;
import java.util.Queue;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Random;
import java.awt.Point;

public class MazeGeneratorDepthFirst implements MazeGenerator{
	private Point[][] cells;
	private boolean[][] visited;
	private static final int DIRECTIONS = 4;
	
	@Override
	public Maze generateMaze(int width, int height, int startX, int startY, int endX, int endY, boolean perfect){

		this.cells = new Point[width][height];
		this.visited = new boolean[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++){
				this.cells[x][y] = new Point(x, y);
				this.visited[x][y] = false;
			}
		}

		Maze maze = new Maze(width, height, startX, startY, endX, endY);
		Deque<Point> stack = new ArrayDeque<Point>();
		stack.push(new Point(startX,startY));
		visited[startX][startY] = true;
		
		while (!stack.isEmpty()){
			Queue<Point> dirs = getDirections();
			Point p = stack.peek();
			boolean found = false;
			while(!dirs.isEmpty()){
				Point dir = dirs.poll();
				Point pnext = new Point(dir.x + p.x, dir.y + p.y);
				if (!inBounds(pnext.x, pnext.y, width, height) || visited[pnext.x][pnext.y] == true){
					continue;
				}
				stack.push(pnext);
				visited[pnext.x][pnext.y] = true;
				connect(p, pnext, maze);
				connect(pnext, p, maze);
				found = true;
				break;
			}
			if (!found){
				stack.pop();
			}
			
		}
		if (!perfect){
			maze = breakWalls(maze, width, height);
		}
		return maze;
	}
	
    private Queue<Point> getDirections() {
        List<Point> dirs = new ArrayList<Point>(DIRECTIONS);
        dirs.add(new Point(1, 0));
        dirs.add(new Point(-1, 0));
        dirs.add(new Point(0, 1));
        dirs.add(new Point(0, -1));
        Collections.shuffle(dirs);
        return new ArrayDeque<Point>(dirs);
	}
    
    private boolean inBounds(final int px, final int py, int width, int height) {
        return (px >= 0) && (py >= 0) && (px < width) && (py < height);
    }
    
    private void connect(Point a, Point b, Maze maze) {
        if (b.x < a.x) {
            maze.breakWest(a);
        } else if (b.y < a.y) {
            maze.breakNorth(a);
        }
    }
    
	private Maze breakWalls(Maze maze, int width, int height){
		Random rand = new Random();
		int wallsToBreak = rand.nextInt(width)*(rand.nextInt(width) + rand.nextInt(height));
		
		for(int i=0; i<wallsToBreak; i++){
			boolean selectWestWall = rand.nextBoolean();
			
			if (selectWestWall){
				int cellX = rand.nextInt(width - 1) + 1;
				int cellY = rand.nextInt(height);
				maze.breakWest(new Point(cellX, cellY));
			}
			else{
				int cellX = rand.nextInt(width - 1) + 1;
				int cellY = rand.nextInt(height -1) + 1;
				maze.breakNorth(new Point(cellX, cellY));
			}
		}
		return maze;
	}
}