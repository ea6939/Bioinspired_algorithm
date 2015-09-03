import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class MazeFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private MazeDisplay mazeDisplay;
	private JRadioButton depthFirstButton;
	private JRadioButton kruskalButton;
	private JLabel widthLabel;
	private JFormattedTextField widthField;
	private JLabel heightLabel;
	private JFormattedTextField heightField;
	private JRadioButton perfectButton;
	private JLabel empty;
	private JLabel startLabel;
	private JLabel startComma;
	private JFormattedTextField startXField;
	private JFormattedTextField startYField;
	private JLabel endLabel;
	private JLabel endComma;
	private JFormattedTextField endXField;
	private JFormattedTextField endYField;
	private JButton generateButton;
	private JButton solveButton;
	private Branch branch;
	int d = 1;//branch.getLength();



	private int width = 50;
	private int height = 50;

	private int startX = 0;
	private int startY = 0;
	private int endX = width-1;
	private int endY = height-1;

	public MazeFrame(){
		super("ECSE 456/457 Fungus Simulation");
		setSize(800, 600);
		setResizable(true);
		buildUI();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	public void buildUI(){

		GridBagLayout layout = new GridBagLayout();

		getContentPane().setLayout(layout);

		this.mazeDisplay = new MazeDisplay();
		getContentPane().add(this.mazeDisplay);
		layout.addLayoutComponent(
				this.mazeDisplay, 
				new GridBagConstraints(0, 0, 1, 2, 1.0D, 1.0D, 
						17, 1, 
						new Insets(5, 5, 5, 5), 0, 0));

		JPanel generatePanel = new JPanel();
		GridBagLayout generateLayout = new GridBagLayout();
		generatePanel.setLayout(generateLayout);
		getContentPane().add(generatePanel);
		layout.addLayoutComponent(
				generatePanel, 
				new GridBagConstraints(1, 0, 1, 1, 0.0D, 1.0D, 
						13, 0, 
						new Insets(5, 5, 5, 5), 0, 0));


		this.depthFirstButton = new JRadioButton("Depth First Search", true);
		this.depthFirstButton.addActionListener(this);
		generatePanel.add(this.depthFirstButton);
		generateLayout.addLayoutComponent(
				this.depthFirstButton, 
				new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 
						17, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.kruskalButton = new JRadioButton("Randomized Kruskal", false);
		this.kruskalButton.addActionListener(this);
		generatePanel.add(this.kruskalButton);
		generateLayout.addLayoutComponent(
				this.kruskalButton, 
				new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 
						17, 0, 
						new Insets(5, 5, 10, 5), 0, 0));


		this.widthLabel = new JLabel("  Width: ");
		generatePanel.add(this.widthLabel);
		generateLayout.addLayoutComponent(
				this.widthLabel, 
				new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_START, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.widthField = new JFormattedTextField(widthField);
		widthField.setColumns(6);
		widthField.setValue(new Integer(width));
		this.widthField.addActionListener(this);
		generatePanel.add(this.widthField);
		generateLayout.addLayoutComponent(
				this.widthField, 
				new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_END, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.heightLabel = new JLabel("  Height: ");
		generatePanel.add(this.heightLabel);
		generateLayout.addLayoutComponent(
				this.heightLabel, 
				new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_START, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.heightField = new JFormattedTextField(heightField);
		heightField.setValue(new Integer(height));
		heightField.setColumns(6);
		this.heightField.addActionListener(this);
		generatePanel.add(this.heightField);
		generateLayout.addLayoutComponent(
				this.heightField, 
				new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_END, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.startLabel = new JLabel("  Start: ");
		generatePanel.add(this.startLabel);
		generateLayout.addLayoutComponent(
				this.startLabel, 
				new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_START, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.startComma = new JLabel(",");
		generatePanel.add(this.startComma);
		generateLayout.addLayoutComponent(
				this.startComma, 
				new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_START, 0, 
						new Insets(5, 100, 0, 5), 0, 0));

		this.startXField = new JFormattedTextField(startXField);
		startXField.setColumns(3);
		startXField.setValue(new Integer(startX));
		this.startXField.addActionListener(this);
		generatePanel.add(this.startXField);
		generateLayout.addLayoutComponent(
				this.startXField, 
				new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.CENTER, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.startYField = new JFormattedTextField(startYField);
		startYField.setColumns(3);
		startYField.setValue(new Integer(startY));
		this.startYField.addActionListener(this);
		generatePanel.add(this.startYField);
		generateLayout.addLayoutComponent(
				this.startYField, 
				new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_END, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.endLabel = new JLabel("  End: ");
		generatePanel.add(this.endLabel);
		generateLayout.addLayoutComponent(
				this.endLabel, 
				new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_START, 0, 
						new Insets(5, 5, 0, 5), 3, 0));

		this.endComma = new JLabel(",");
		generatePanel.add(this.endComma);
		generateLayout.addLayoutComponent(
				this.endComma, 
				new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_START, 0, 
						new Insets(5, 100, 0, 5), 0, 0));

		this.endXField = new JFormattedTextField(endXField);
		endXField.setValue(new Integer(endX));
		endXField.setColumns(3);
		this.endXField.addActionListener(this);
		generatePanel.add(this.endXField);
		generateLayout.addLayoutComponent(
				this.endXField, 
				new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.CENTER, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.endYField = new JFormattedTextField(endYField);
		endYField.setValue(new Integer(endY));
		endYField.setColumns(3);
		this.endYField.addActionListener(this);
		generatePanel.add(this.endYField);
		generateLayout.addLayoutComponent(
				this.endYField, 
				new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 
						GridBagConstraints.LINE_END, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.perfectButton = new JRadioButton("Perfect", true);
		this.perfectButton.addActionListener(this);
		generatePanel.add(this.perfectButton);
		generateLayout.addLayoutComponent(
				this.perfectButton, 
				new GridBagConstraints(0, 7, 1, 1, 0.0D, 0.0D, 
						17, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.empty = new JLabel(" ");
		generatePanel.add(this.empty);
		generateLayout.addLayoutComponent(
				this.empty, 
				new GridBagConstraints(0, 8, 1, 1, 0.0D, 0.0D, 
						17, 0, 
						new Insets(5, 5, 0, 5), 0, 0));

		this.generateButton = new JButton("Generate Maze");
		this.generateButton.addActionListener(this);
		generatePanel.add(this.generateButton);
		generateLayout.addLayoutComponent(
				this.generateButton, 
				new GridBagConstraints(0, 9, 1, 1, 0.0D, 0.0D, 
						15, 0, 
						new Insets(5, 5, 5, 5), 0, 0));


		JPanel solveButtonPanel = new JPanel();
		GridBagLayout solveButtonLayout = new GridBagLayout();
		solveButtonPanel.setLayout(solveButtonLayout);
		getContentPane().add(solveButtonPanel);
		layout.addLayoutComponent(
				solveButtonPanel, 
				new GridBagConstraints(1, 1, 1, 1, 0.0D, 1.0D, 
						13, 0, 
						new Insets(5, 5, 5, 5), 0, 0));

		this.solveButton = new JButton("Solve Maze");
		this.solveButton.addActionListener(this);
		solveButtonPanel.add(this.solveButton);
		solveButtonLayout.addLayoutComponent(
				this.solveButton, 
				new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 
						15, 0, 
						new Insets(5, 5, 5, 5), 0, 0));


		enableGeneratorButtons(true);
		enableSolverButtons(false);
		centerFrame();
		//calculateNodes();
	}

	private int calculateNodes() {
		// TODO Auto-generated method stub
		d = branch.getLength();
		return 	d;
	}


	private void enableButtons(){
		enableGeneratorButtons(true);
		enableSolverButtons(true);
	}

	private void disableButtons(){
		enableGeneratorButtons(false);
		enableSolverButtons(false);
	}

	private void enableGeneratorButtons(boolean enabled){
		this.depthFirstButton.setEnabled(enabled);
		this.kruskalButton.setEnabled(enabled);
		this.widthField.setEnabled(enabled);
		this.heightField.setEnabled(enabled);
		this.startXField.setEnabled(enabled);
		this.startYField.setEnabled(enabled);
		this.endXField.setEnabled(enabled);
		this.endYField.setEnabled(enabled);
		this.generateButton.setEnabled(enabled);
		this.perfectButton.setEnabled(enabled);
		this.widthLabel.setEnabled(enabled);
		this.heightLabel.setEnabled(enabled);
		this.startLabel.setEnabled(enabled);
		this.endLabel.setEnabled(enabled);
		this.startComma.setEnabled(enabled);
		this.endComma.setEnabled(enabled);
	}

	private void enableSolverButtons(boolean enabled){
		this.solveButton.setEnabled(enabled);
	}


	private void generateMaze(){
		MazeGenerator g = null;
		if (this.depthFirstButton.isSelected()) {
			g = new MazeGeneratorDepthFirst();
		} 
		else {
			g = new MazeGeneratorKruskal();
		}
		MazeFrame.this.mazeDisplay.setSolution(null);
		MazeFrame.this.disableButtons();

		width = (int) widthField.getValue();
		height = (int) heightField.getValue();
		startX = (int) startXField.getValue();
		startY = (int) startYField.getValue();
		endX = (int) endXField.getValue();
		endY = (int) endYField.getValue();
		boolean perfect = false;
		if (MazeFrame.this.perfectButton.isSelected()){
			perfect = true;
		}
		try	{
			Maze maze = g.generateMaze(width, height, startX, startY, endX, endY, perfect);
			MazeFrame.this.mazeDisplay.setMaze(maze);
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		MazeFrame.this.enableButtons();
	}

	public class MazeSolutionThread	extends Thread {
		private FungusSimulation solver;
		private Maze maze;


		public MazeSolutionThread(FungusSimulation solver, Maze maze)	{
			this.solver = solver;
			this.maze = maze;
		}

		public void run(){
			
			MazeFrame.this.disableButtons();
			Fungus solution = this.solver.solveMaze(this.maze, MazeFrame.this.mazeDisplay);
			MazeFrame.this.mazeDisplay.setSolution(solution);
			MazeFrame.this.enableButtons();

		}
	}

	private void solveMaze(){
		FungusSimulation s = null;
		s = new FungusSimulation();
		Thread solverThread = new MazeFrame.MazeSolutionThread(s, this.mazeDisplay.getMaze());
		solverThread.start();




	}

	public void actionPerformed(ActionEvent e){
		Object o = e.getSource();
		if (o == this.depthFirstButton){
			this.depthFirstButton.setSelected(true);
			this.kruskalButton.setSelected(false);
		}
		else if (o == this.kruskalButton){
			this.kruskalButton.setSelected(true);
			this.depthFirstButton.setSelected(false);
		}
		else if (o == this.generateButton){
			generateMaze();
		}
		else if (o == this.solveButton){
			solveMaze();

		}

	}

	private void centerFrame() {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - (centerPoint.x/2) - 50; //(windowSize.width * 2) ;
		int dy = centerPoint.y - (centerPoint.y/2) - 100;// + 50; //(windowSize.height * 2) ;    
		setLocation(dx, dy);
	}

	public static void main(String[] args){
		new MazeFrame().setVisible(true);
		
	}
}
