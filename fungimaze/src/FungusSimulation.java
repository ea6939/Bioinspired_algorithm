import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class FungusSimulation{
	
	private Branch branch;
	public Fungus solveMaze(Maze m, Listener listener){
		int width = m.getWidth();
		int height = m.getHeight();
		int d;
		
		Point mazeStart = m.getStart();
		Point mazeEnd = m.getEnd();
		
		Branch b = new Branch(width, height, mazeStart.x, mazeStart.y, mazeEnd.x, mazeEnd.y);
		Fungus f = new Fungus(width, height, mazeStart.x, mazeStart.y, mazeEnd.x, mazeEnd.y);
		ArrayList<Integer> dirs = new ArrayList<Integer>();
		dirs.add(0);
		dirs.add(90);
		dirs.add(180);
		dirs.add(270);
		Collections.shuffle(dirs);
		for (int dir : dirs){
			if (m.canMoveFrom(mazeStart, dir)){
				b.setStartAngle(dir);
			}
		}
		f.addBranch(b);
		listener.start(f);

		ArrayDeque<Branch> queue = new ArrayDeque<Branch>();
		queue.push(b);
		
		while (!queue.isEmpty()){
			Branch current = queue.pop();
			int startAngle = current.getStartAngle();
			while (m.canMoveFrom(current.getLastPoint(), startAngle) && !f.has(m.getPointOf(current.getLastPoint(), startAngle))){
				Branch newCurrent = (Branch)current.clone();
				queue.push(newCurrent);
				f.addBranch(newCurrent);
				current.move(startAngle);
				Branch next = new Branch(width, height, current.getLastPoint().x, current.getLastPoint().y, mazeEnd.x, mazeEnd.y);
				queue.push(next);
				f.addBranch(next);
				listener.change(f);
				if (current.isComplete()) {
					return f;
				}
			}
			Collections.shuffle(dirs);
			Point lastPoint = current.getLastPoint();
			for (int dir : dirs){
				if (m.canMoveFrom(lastPoint, dir) && !f.has(m.getPointOf(lastPoint, dir))){
					Branch next = new Branch(width, height, lastPoint.x, lastPoint.y, mazeEnd.x, mazeEnd.y);
					next.setStartAngle(dir);
					while (m.canMoveFrom(next.getLastPoint(), dir) && !f.has(m.getPointOf(next.getLastPoint(), dir))){
						Branch newNext = (Branch)next.clone();
						queue.push(newNext);
						f.addBranch(newNext);
						next.move(dir);
						listener.change(f);
					}
					queue.push(next);
					f.addBranch(next);
					if (next.isComplete()) {
						
						return f;
					}
				}
			}
		}
		return f;
	}
}