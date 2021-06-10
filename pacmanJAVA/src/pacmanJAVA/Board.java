package pacmanJAVA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa przechowuj¹ca wszystkie informacje o planszy gry.
 *
 */
public class Board {
	
	/**
	 * Pole opisuj¹ce szerokoœæ planszy gry
	 */
	public final int Width = 19;
	/**
	 * Pole opisuj¹ce wysokoœæ planszy gry
	 */
	public final int Height = 22;
	/**
	 * Pole opisuj¹ce aktualny stan pozosta³ych punktów do zebrania na planszy.
	 */
	public int maxpoints = 0;
	/**
	 * Pole opisuj¹ce domyœln¹ pozycjê Pacmana
	 */
	public static int[] DefaultPacman = {9,20};
	/**
	 * Pole opisuj¹ce domyœln¹ pozycjê Clyde'a
	 */
	public static int[] DefaultClyde = {9,10};
	/**
	 * Pole opisuj¹ce domyœln¹ pozycjê Blinky'iego
	 */
	public static int[] DefaultBlinky = {8,10};
	/**
	 * Pole opisuj¹ce domyœln¹ pozycjê Pinky'iego
	 */
	public static int[] DefaultPinky = {10,10};
	/**
	 * Pole przechowuj¹ce typy wyliczeniowe klasy BoardObjects, które znajduj¹ siê na odpowiednich polach planszy. 
	 */
	public List<BoardObjects>[][] board = null;



	/**
	 * Generuje ca³¹ planszê wskazan¹ przez parametr
	 * @param numberBoard - numer od 0 do 2 okreœlaj¹cy numer generowanej planszy
	 */
	@SuppressWarnings("unchecked")
	public void generateBoard(int numberBoard) {
		List<BoardObjects>[][] board = new List[Width][Height];
		
		for (int i = 0; i < Width; ++i) {
			for (int j = 0; j < Height; ++j) {
				board[i][j] = Collections.synchronizedList(new ArrayList<BoardObjects>());
				if (i==0 || j==0 || i==Width-1 || j==Height-1) {
					board[i][j].add(BoardObjects.Wall);
				}
			}
		}
		
		board[DefaultPacman[0]][DefaultPacman[1]].add(BoardObjects.Empty);
		board[DefaultPacman[0]][DefaultPacman[1]].add(BoardObjects.Pacman);
		board[DefaultClyde[0]][DefaultClyde[1]].add(BoardObjects.Empty);
		board[DefaultClyde[0]][DefaultClyde[1]].add(BoardObjects.Clyde);
		board[DefaultBlinky[0]][DefaultBlinky[1]].add(BoardObjects.Empty);
		board[DefaultBlinky[0]][DefaultBlinky[1]].add(BoardObjects.Blinky);
		board[DefaultPinky[0]][DefaultPinky[1]].add(BoardObjects.Empty);
		board[DefaultPinky[0]][DefaultPinky[1]].add(BoardObjects.Pinky);

		switch (numberBoard) {
		case 0:
			Board1(board);
			break;
		case 1:
			Board2(board);
			break;
		case 2:
			Board3(board);
			break;
		default:
			throw new IllegalArgumentException("The chosen number of board does not exist");
		}
		FillEmpty(board);
		/*for (int i = 0; i < Height; ++i) {
			System.out.print("\n");
			for (int j = 0; j < Width; ++j) {
				System.out.print(board[j][i]); 
			}
		}*/
		
		
		
		this.board=board;
	}
	
	/**
	 * Generuje pierwszy wariant planszy.
	  * @param board - lista pól, do której ma zostaæ zapisana wygenerowana plansza.
	 */
	public static void Board1(List<BoardObjects>[][] board) {
		int[][] Walls= {{},
						{2,3,5,6,7,9,11,12,13,15,16},
						{2,3,5,6,7,9,11,12,13,15,16},
						{},
						{2,3,5,7,8,9,10,11,13,15,16},
						{5,9,13},
						{1,2,3,5,6,7,9,11,12,13,15,16,17},
						{1,2,3,5,13,15,16,17},
						{1,2,3,5,7,8,10,11,13,15,16,17},
						{1,2,3,7,11,15,16,17},
						{1,2,3,5,7,8,9,10,11,13,15,16,17},
						{1,2,3,5,13,15,16,17},
						{1,2,3,5,7,8,9,10,11,13,15,16,17},
						{9},
						{2,3,5,6,7,9,11,12,13,15,16},
						{3,15},
						{1,3,5,7,8,9,10,11,13,15,17},
						{5,9,13},
						{2,3,4,5,6,7,9,11,12,13,14,15,16},
						{}
						};
		for (int i=0;i<Walls.length;++i) {
			for (int j=0;j<Walls[i].length;++j) {
				board[Walls[i][j]][i+1].add(BoardObjects.Wall);
			}
		}
		board[9][9].add(BoardObjects.Empty);
	}
	
	/**
	 * Generuje drugi wariant planszy.
	  * @param board - lista pól, do której ma zostaæ zapisana wygenerowana plansza.
	 */
	public static void Board2(List<BoardObjects>[][] board) {
		int[][] Walls= {{1,2,3,4,5},
				{7,9,11,12,13,14,15,16},
				{2,3,4,5,6,7,9,11,16},
				{13,14},
				{2,4,5,6,8,9,10,11,13,16},
				{2,6,9,13,15},
				{2,3,4,6,7,9,11,12,13,15,17},
				{17},
				{1,2,3,4,5,7,8,10,11,13,14,15,16,17},
				{5,7,11},
				{2,3,7,8,9,10,11,13,14,15,16},
				{2,3,5,16},
				{2,3,5,6,7,8,9,10,11,13,14,16},
				{8},
				{2,3,5,6,8,10,11,12,13,14,15,16},
				{5,6,8},
				{2,3,6,8,10,11,12,14,15,16,17},
				{2,3,4,6,17},
				{2,3,4,6,7,8,9,10,11,13,14,15,17},
				{17}
				};
		for (int i=0;i<Walls.length;++i) {
			for (int j=0;j<Walls[i].length;++j) {
				board[Walls[i][j]][i+1].add(BoardObjects.Wall);
			}
		}
		board[9][9].add(BoardObjects.Empty);
	}
	/**
	 * Generuje trzeci wariant planszy.
	 * @param board - lista pól, do której ma zostaæ zapisana wygenerowana plansza.
	 */
	public static void Board3(List<BoardObjects>[][] board) {
		int[][] Walls= {{7,8,9},
				{2,3,5,11,12,14,15,16},
				{5,6,8,10,11,12,14},
				{2,3,4,5,6,14,16},
				{8,9,10,12,16},
				{1,3,4,6,9,12,13,15,16},
				{1,3,4,6,7,9,11,12,13},
				{1,15,17},
				{1,3,4,5,7,8,10,11,13,14,15,17},
				{1,3,4,5,7,11,13,14,15,17},
				{1,3,4,5,7,8,9,10,11,13,14,15,17},
				{},
				{2,3,4,5,7,8,9,10,11,13,14,15,16},
				{4,5,7,8,9,10,11},
				{2,7,8,9,10,11,13,15,17},
				{2,4,5,13,15,17},
				{2,5,6,7,8,10,12,13,15},
				{3,10,15,16},
				{1,5,6,8,9,10,12,13,14,15,16},
				{1,2,3}
				};
		for (int i=0;i<Walls.length;++i) {
			for (int j=0;j<Walls[i].length;++j) {
				board[Walls[i][j]][i+1].add(BoardObjects.Wall);
			}
		}
		board[9][9].add(BoardObjects.Empty);
	}
	
	/**
	 * Dope³nia planszê zastêpuj¹c ka¿de nieopisane wczeœniej pole jako pole puste z punktem do zebrania.
	 * @param board - plansza, na której dokonaæ dope³nienia
	 */
	public void FillEmpty(List<BoardObjects>[][] board) {
		this.maxpoints=0;
		for (int i = 0; i < Width; ++i) {
			for (int j = 0; j < Height; ++j) {
				if (board[i][j].isEmpty()) {
					this.maxpoints++;
					board[i][j].add(BoardObjects.Empty);
					board[i][j].add(BoardObjects.Point);
				}
			}	
		}
	}


}