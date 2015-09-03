/* Maze Generator interface */


public interface MazeGenerator{

	public Maze generateMaze(int width, int height, int startX, int startY, int endX, int endY, boolean perfect);
}