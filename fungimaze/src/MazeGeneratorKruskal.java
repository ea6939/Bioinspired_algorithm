/* Randomly generates a maze using randomized Kruskal's algorithm */

import java.util.Random;
import java.awt.Point;

public class MazeGeneratorKruskal implements MazeGenerator{
	private Point[][] cells;
	private int[][] count;

	@Override
	public Maze generateMaze(int width, int height, int startX, int startY, int endX, int endY, boolean perfect){
		Random rand = new Random();

		this.cells = new Point[width][height];
		this.count = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				this.cells[x][y] = new Point(x, y);
				this.count[x][y] = 1;
			}
		}
		
		int numDisjointSets = width * height; 

		Maze maze = new Maze(width, height, startX, startY, endX, endY);

		while (numDisjointSets > 1){
			
			boolean selectWestWall = rand.nextBoolean();
			
			if (selectWestWall)	{
				int cellX = rand.nextInt(width - 1) + 1;
				int cellY = rand.nextInt(height);

				Point p1 = new Point(cellX - 1, cellY);
				Point p2 = new Point(cellX, cellY);
				
				if (FIND(p1) != FIND(p2)){
					UNION(p1, p2);
					numDisjointSets--;

					maze.breakWest(p2);
				}
			}
			else {
				int cellX = rand.nextInt(width);
				int cellY = rand.nextInt(height - 1) + 1;

				Point p1 = new Point(cellX, cellY - 1);
				Point p2 = new Point(cellX, cellY);
				if (FIND(p1) != FIND(p2)){
					UNION(p1, p2);
					numDisjointSets--;

					maze.breakNorth(p2);
				}
			}
		}
		if (!perfect){
			maze = breakWalls(maze, width, height);
		}
		return maze;
	}

	private Point FIND(Point p){
		if (this.cells[p.x][p.y] != p) {
			this.cells[p.x][p.y] = FIND(this.cells[p.x][p.y]);
		}
		return this.cells[p.x][p.y];
	}

	private void UNION(Point p1, Point p2){
		Point a = FIND(p1);
		Point b = FIND(p2);
		if (this.count[a.x][a.y] > this.count[b.x][b.y]){
			this.cells[b.x][b.y] = a;
			this.count[a.x][a.y] += this.count[a.y][b.y];
		}
		else{		
			this.cells[a.x][a.y] = b;
			this.count[b.x][b.y] += this.count[a.y][a.y];
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
				int cellY = rand.nextInt(height - 1) + 1;
				maze.breakNorth(new Point(cellX, cellY));
			}
		}
		return maze;
	}
}