/******************************************************************************
* Cell class
*
*	Stores the status, symbol and number of neighbors each cell has.  Provides
*setters, getters, and a method to update the status and symbol of a cell.
* ******************************************************************************/
public class Cell {

	private int neighbors;
	private boolean alive = false;
	private char symbol = '-';
	
	/**
	 * Setters and getters for cell class
	 * 
	 * Setting alive to true or false also changes the 
	 * symbol appropriately
	 */
	public void setNeighbors(int numNeighbors)
	{
		neighbors = numNeighbors;
	}
	
	public int getNeighbors()
	{
		return neighbors;
	}
	
	public void setAlive(boolean isAlive)
	{
		alive = isAlive;
		if(alive)
		{
			symbol = '*';
		}
		else
		{
			symbol = '-';
		}
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public char getSymbol()
	{
		return symbol;
	}
	
	/**
	 * Changes (or doesn't change) the status of
	 *  a cell according to the number of neighbors 
	 *  it has.
	 * 
	 * @param cell		Not the cell object being modified,
	 * 					but the corresponding cell object 
	 * 					from the last time interval 
	 */
	public void changeStatus(Cell cell)
	{
		if(neighbors < 2)
		{
			setAlive(false);
		}
		else if(neighbors == 2)
		{
			setAlive(cell.alive);
		}
		else if(neighbors == 3)
		{
			setAlive(true);
		}
		else
		{
			setAlive(false);
		}
	}
	
}
