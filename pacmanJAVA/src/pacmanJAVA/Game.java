package pacmanJAVA;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * G³ówna klasa odpowiedzialna za przebieg gry
 *
 */
public class Game {
	/**
	 * Zmienna typu Board odpowiedzialna za plansze, na której rozgrywa siê rozgrywka
	 */
	public Board board = new Board();
	
	/**
	 * Pole reprezentuj¹ce pacmana, którym steruje u¿ytkownik podczas gry
	 */
	public Pacman pacman;
	/**
	 * Lista wszystkich ruchomych obiektów na planszy tj. pacman i duchy
	 */
	private ArrayList<MovingObjects> moving_objects = new ArrayList<MovingObjects>();
	
	
	/**
	 * Konstruktor bezparametryczny g³ównej klasy gry. Losuje plansze gry oraz tworzy obiekty odpowiadaj¹ce pacmanowi oraz duchom
	 */
	public Game() {
		Random map = new Random();
		board.generateBoard(map.nextInt(3));
		this.pacman=new Pacman(this);
		moving_objects.add(pacman);
		Clyde clyde = new Clyde(this);
		moving_objects.add(clyde);
		Pinky pinky = new Pinky(this);
		moving_objects.add(pinky);
		Blinky blinky = new Blinky(this);
		moving_objects.add(blinky);
		
	}
	
	/**
	 * Metoda uruchamiaj¹ca w¹tki odpowiedzialne za wszystkie ruchome obiekty na planszy tj. pacmana oraz duchy
	 */
	public void start() {
		for (MovingObjects moving_object: moving_objects) {
			new Thread(moving_object).start();
		}
	}
	
	/**
	 * Metoda wystêpuj¹ca, jêzeli pacman "zje" wszystkie punkty na danej planszy.
	 * Losuje now¹ planszê i ustawia wszystkie obiekty na poprawnych miejscach.
	 */
	public void nextMap() {
		for (MovingObjects moving_object: moving_objects) {
			moving_object.win();
			moving_object.defaultValues();
		}
		try 
		{
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		catch(InterruptedException ex)
		{
			System.out.println("Failed to wait 1 sec in nextMap.");
		}
		for (MovingObjects moving_object: moving_objects) {
			moving_object.resume();
		}
	}
	
	/**
	 * Metoda odpowiedzialna za reset wszystkich ruchomych obiektów na planszy do stanów pocz¹tkowych. Je¿eli pacman wci¹¿ posiada ¿ycia
	 * przywraca gre do dzia³ania po 1 sekundzie.
	 */
	public void reset() {
		if (pacman.lives>0) {
			for (MovingObjects moving_object: moving_objects) {
				moving_object.reset();
				moving_object.defaultValues();
			}
			try 
			{
				TimeUnit.MILLISECONDS.sleep(1000);
			}
			catch(InterruptedException ex)
			{
				System.out.println("Failed to wait 1 sec in reset.");
			}
			for (MovingObjects moving_object: moving_objects) {
				moving_object.resume();
			}
		}
		else {
			for (MovingObjects moving_object: moving_objects) {
				moving_object.end();
				moving_object.defaultValues();
			}
			
		}
		
	}

	/**
	 * Metoda przywracaj¹ca grê do stanu poczatkowego.
	 */
	public void gamereset() {
		this.pacman.lives=3;
		this.pacman.points=0;
		this.pacman.actualMove=null;
		for (MovingObjects moving_object: moving_objects) {
			moving_object.resume();
		}
	}
}
