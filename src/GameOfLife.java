import java.util.Scanner;

/****************************************************************************** *
* Name: Colton Conley
* Block: A
* Date: 10/27/14
*
* Game of Life Description:
* 	This program simulates a system in which there is a grid of objects or 
* organisms whose state is dependent on the state of the surrounding objects. 
* For the purposes of this program, * will denote an alive organism and - will 
* denote a dead one.  If there are less than 2 neighbors for any given cell, it 
* dies.  2 neighbors means it stays the same, 3 means it becomes alive, and more
* than three also means that the cell/organism dies.  A neighbor is defined as
* any organism in an adjacent spot on the grid in the alive state.  
* 	This program also gives the added option to use a wrap-around function.  This
* means that any cells on one side of the grid will count any living cells on the
* opposite side as neighbors, as long as they would be touching if the board were 
* "wrapped around."
* ******************************************************************************/
public class GameOfLife {

	public static void main(String[] args) {
		Cell[][] world = createWorld();
		int timeIntervals = getTimeIntervals();
		fillWorld(world);
		//make starting world
		world = userMakesWorld(world);
		System.out.println("Here is the starting world:");
		printWorld(world);
		//run simulation
		Scanner input = new Scanner(System.in);
		System.out.println("Would you like to run the simulation "
				+ "with the wrap-around modification?");
		System.out.println("Enter 1 for yes, 2 for no");
		System.out.println(" ");
		if(input.nextInt() == 1)
		{
			runSimulation(world, timeIntervals, true);
		}
		else
		{
			runSimulation(world, timeIntervals, false);
		}
	}

	/**
	 * Creates a world with a size dependent on user imput
	 * 
	 * @return		returns a 2d array of cell objects
	 */
	public static Cell[][] createWorld()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("How many rows would you like?");
		int rows = input.nextInt();
		System.out.println("How many columns would you like?");
		int columns = input.nextInt();
		Cell[][] world = new Cell[rows + 2][columns + 2];
		return world;
	}

	/**
	 * Finds out how many time intervals the user would like to 
	 * run the simulation for
	 * 
	 * @return		Returns an integer representing the number
	 * 				of times intervals the user would like to 
	 * 				observe the program for
	 */
	public static int getTimeIntervals()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("How many time intervals would you like to observe?");
		int timeIntervals = input.nextInt();
		return timeIntervals;
	}
	/**
	 * Fills a 2d array of cell objects with new cell objects
	 * 
	 * @param world		2d array to be filled with new cell objects
	 */
	private static void fillWorld(Cell[][] world)
	{
		for(int row = 0; row < world.length; row++)
		{
			for(int column = 0; column < world[row].length; column++)
			{
				world[row][column] = new Cell();
			}
		}
	}

	/**
	 * Fills an array of cell objects with new cell objects
	 * 
	 * @param row	Array to be filled with cell objects
	 */
	private static void fillRow(Cell[] row)
	{
		for(int index = 0; index < row.length; index++)
		{
			row[index] = new Cell();
		}
	}

	/**
	 * Asks the user to create the 2d world of cell objects by making
	 * certain cells alive
	 * 
	 * @param world 	A 2d array of cell objects with all dead cells
	 * @return			a 2d array edited by the user
	 */
	private static Cell[][] userMakesWorld(Cell[][] world)
	{
		System.out.println("Now you will set the state of the starting world. "
				+ "Entering an invalid integer will end this process.");
		Scanner imput = new Scanner(System.in);
		int rowNumber = 0;
		int columnNumber = 0;
		while (rowNumber >= 0 && 
				columnNumber >= 0 &&
				rowNumber < world.length - 1&&
				columnNumber < world[0].length - 1)
		{
			System.out.println("Please enter a column number");
			columnNumber = imput.nextInt();
			System.out.println("Please enter a row number");
			rowNumber = imput.nextInt();
			
			//check to see if input is valid
			if(rowNumber >= 0 && 
					columnNumber >= 0 &&
					rowNumber < world.length - 1&&
					columnNumber < world[0].length - 1)
			{
				world[rowNumber][columnNumber].setAlive(true);
			}	
		}
		return world;
	}
	/**
	 * Prints a 2d array of cell objects, displaying alive
	 * cells with * and dead cells with -
	 * 
	 * @param world		2d array to be printed
	 */
	private static void printWorld(Cell[][] world)
	{
		for(int row = 1; row < world.length - 1; row++)
		{
			System.out.println("");
			for(int column = 1; column < world[0].length - 1;  column ++)
			{
				System.out.print(world[row][column].getSymbol() + "  ");
			}
		}
	}
	/**
	 * Runs the simulation for a designated number of time clicks and 
	 * specified world, printing after each iteration
	 * 
	 * @param world		initial 2d array of cell objects to be used 
	 * 					in the simulation
	 * @param time		Number of time clicks to run the simulation for
	 * @param mod		Whether or not the modification is being used
	 */
	private static void runSimulation (Cell[][] world, int time, boolean mod)
	{
		//run with or without wrap around
		if(mod)
		{
			for(int counter = 0; counter < time; counter++)
			{
				world = runSingleTime(world, true);
				printWorld(world);
				System.out.println(" ");
			}
		}
		else
		{
			for(int counter = 0; counter < time; counter++)
			{
				world = runSingleTime(world, false);
				printWorld(world);
				System.out.println(" ");
			}
		}
	}

	/**
	 * Takes a 2d array of cell objects and adjusts them for one 
	 * iteration of the simulation, returning an edited version
	 * 
	 * @param world		original 2d array of cell objects
	 * @return			edited 2d array of cell objects
	 * @param mod		Whether or not the modification is being used
	 */
	private static Cell[][] runSingleTime (Cell[][] world, boolean mod)
	{
		//make a new grid to hold changes
		Cell[][] newWorld = new Cell[world.length][world[0].length];
		fillWorld(newWorld);
		
		//edit new world row by row
		for(int row = 1; row < world.length - 1; row++)
		{
			newWorld[row] = runRow(world, row, mod);
		}
		return newWorld;
	}

	/**
	 * Adjusts a specified row of cell objects from an original
	 * 2d array of cell objects
	 * 	
	 * @param originalWorld		Original 2d array of cell objects
	 * @param row				the row number to adjust from the original
	 * 							world
	 * @param mod				Whether or not the modification is being used
	 * @return					returns an adjusted array of cell objects 
	 * 							corresponding to a row from the original
	 * 							2d array
	 */
	private static Cell[] runRow(Cell[][] originalWorld, int row, boolean mod)
	{
		Cell[] newWorld = new Cell[originalWorld[row].length];
		fillRow(newWorld);
		
		//evaluate each cell in the row individually
		for (int column = 1; column < newWorld.length - 1; column++)
		{
			evaluateCell(newWorld[column], originalWorld, row, column, mod);
		}
		return newWorld;
	}

	/**
	 * evaluates and changes the status of a cell depending on how many 
	 * neighbors it has
	 * 
	 * @param cell					The individual cell being evaluated
	 * @param originalWorld			2d array of cells containing information
	 * 								about the cell's neighbors
	 * @param row					Row number where cell is located in the
	 * 								larger 2d array
	 * @param column				Column number where cell is located in the 
	 * 								larger 2d array
	 * @param mod					Whether or not the modification is being used
	 */
	private static void evaluateCell(Cell cell, Cell[][] originalWorld, int row, int column, boolean mod)
	{
		cell.setNeighbors(findNeighbors(originalWorld, row, column, mod));
		cell.changeStatus(originalWorld[row][column]);	
	}

	/**
	 * Finds the number of neighbors a specific cell has within a 2d array of 
	 * cell objects 
	 * 
	 * @param originalWorld		2d array containing a cell to be evaluated
	 * @param row				row number containing the cell to be evaluated
	 * @param column			column number containing the cell to be evaluated
	 * @param mod				Whether or not the modification is being used
	 * @return					returns the number of neighbors the specified 
	 * 							cell has
	 */
	private static int findNeighbors(Cell[][] originalWorld, int row, int column, boolean mod)
	{
		if(mod)
		{
			return findSurroundingNeighborsWrap(originalWorld, row, column);
		}
		else
		{
			return findSurroundingNeighbors(originalWorld, row, column);
		}
	}
	
	/**
	 * Finds the number of living neighbors in adjacent cells
	 * 
	 * @param originalWorld		2d array containing a cell to be evaluated
	 * @param row				row number containing the cell to be evaluated
	 * @param column			column number containing the cell to be evaluated
	 * @return					returns the number of neighbors in adjacent cells
	 */
	private static int findSurroundingNeighbors(Cell[][] originalWorld, int row, int column)
	{
		//check all spaces around the cell for a living neighbor
		int neighbors = findNeighbor(originalWorld, row - 1, column - 1) +
				findNeighbor(originalWorld, row - 1, column) +
				findNeighbor(originalWorld, row - 1, column + 1) +
				findNeighbor(originalWorld, row, column - 1) +
				findNeighbor(originalWorld, row, column + 1) +
				findNeighbor(originalWorld, row + 1, column - 1) +
				findNeighbor(originalWorld, row + 1, column) +
				findNeighbor(originalWorld, row + 1, column + 1);
		return neighbors;
	}
	
	/**
	 * Finds the number of neighbors, incorperating the wrap around add on
	 * 
	 * @param originalWorld		2d array containing a cell to be evaluated
	 * @param row				row number containing the cell to be evaluated
	 * @param column			column number containing the cell to be evaluated
	 * @return					returns the number of neighbors in adjacent or
	 * 							opposite cells
	 */
	private static int findSurroundingNeighborsWrap(Cell[][] originalWorld, int row, int column)
	{
		//check all adjacent cells then possible wrap around cells
		int neighbors = findSurroundingNeighbors(originalWorld, row, column)
				+ addNeighborsVertically(originalWorld, row, column)
				+ addNeighborsHorizontally(originalWorld, row, column);
		return neighbors;
	}
	
	/**
	 * Finds the number of neighbors for a cell if it is on the top or 
	 * bottom of the grid
	 * 
	 * @param originalWorld		2d array containing a cell to be evaluated
	 * @param row				row number containing the cell to be evaluated
	 * @param column			column number containing the cell to be evaluated
	 * @return					returns the number of neighbors in opposite cells
	 */
	private static int addNeighborsVertically(Cell[][] originalWorld, int row, int column)
	{
		int neighbors = 0;
		
		//check if cell is at top of grid
		if(row == 1)
		{
			neighbors += findNeighbor(originalWorld, originalWorld.length - 2, column - 1) +
					findNeighbor(originalWorld, originalWorld.length - 2, column) +
					findNeighbor(originalWorld, originalWorld.length - 2, column + 1);
		}
		
		//check if cell is at bottom
		if(row == originalWorld.length - 2)
		{
			neighbors += findNeighbor(originalWorld, 1, column - 1) +
			findNeighbor(originalWorld, 1, column) +
			findNeighbor(originalWorld, 1, column + 1);
		}
		return neighbors;
	}
	/**
	 * Finds the number of neighbors for a cell if it is on either side of the grid
	 * 
	 * @param originalWorld		2d array containing a cell to be evaluated
	 * @param row				row number containing the cell to be evaluated
	 * @param column			column number containing the cell to be evaluated
	 * @return					returns the number of neighbors in opposite cells
	 */
	private static int addNeighborsHorizontally(Cell[][] originalWorld, int row, int column)
	{
		int neighbors = 0;
		
		//check if cell is to the left of grid
		if(column == 1)
		{
			neighbors += findNeighbor(originalWorld, row - 1, originalWorld[0].length - 2) +
					findNeighbor(originalWorld, row, originalWorld[0].length - 2) +
					findNeighbor(originalWorld, row + 1, originalWorld[0].length - 2);
		}
		
		//check to see if cell is on the right edge
		if (column == originalWorld[0].length - 2) 
		{
			neighbors += findNeighbor(originalWorld, row - 1, 1) +
					findNeighbor(originalWorld, row, 1) +
					findNeighbor(originalWorld, row + 1, 1);
		}
		return neighbors;
	}

	/**
	 * Looks at a specific location in a 2d array and determines if a living 
	 * neighbor resides in that location.  If one is found, a value of 1 is 
	 * returned
	 * 
	 * @param originalWorld		The 2d array containing a possible neighbor
	 * @param row				the row of a possible neighbor
	 * @param column			the column of a possible neighbor
	 * @return					returns one if the specified cell is alive, 0
	 * 							if it is dead
	 */
	private static int findNeighbor(Cell[][] originalWorld, int row, int column)
	{
		if(originalWorld[row][column].isAlive())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}




}
