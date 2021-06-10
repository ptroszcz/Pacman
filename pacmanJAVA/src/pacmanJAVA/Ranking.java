package pacmanJAVA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 * Klasa przechowuj¹ca informacjê o graczu i jego wyniku w grze.
 *
 */
class PlayerScore{
	/**
	 * Pole odpowiedzialne za nazwê gracza.
	 */
	public String name;
	/**
	 * Pole odpowiedzialne za wynik gracza.
	 */
	public int score;
	
	/**
	 * Kontruktor klasy PlayerScore. Ustawia nazwê oraz wynik na te wskazane w parametrach
	 * @param name - nazwa gracza, który osi¹gn¹³ wskazany wynik
	 * @param score - wynik punktowy gracza
	 */
	PlayerScore(String name, int score){
		this.name=name;
		this.score=score;
	}
}


/**
 * Klasa odpowiedzialna za tablicê najlepszych wyników gry.
 *
 */
public class Ranking {
	
	/**
	 * Lista przechowuj¹ca obiekty klasy PlayerScore - czyli przechowuje listê par gracz-wynik.
	 */
	public ArrayList<PlayerScore> ranking = new ArrayList<PlayerScore>();
	
	/**
	 * Kontruktor domœylny klasy Ranking. Wczytuje aktualn¹ tablicê wyników z pliku.
	 */
	public Ranking() {
		this.loadRankingFromFile();
	}
	
	/**
	 * Metoda odpowiedzialna za dodanie nowego wyniku do tablicy wyników.
	 * @param Player - nazwa gracza, którego wynik dotyczy
	 * @param score - wynik punktowy gracza
	 */
	public void AddScore(String Player, int score) {
		PlayerScore newscore=new PlayerScore(Player,score);
		if (ranking.size()<=0) {
			this.ranking.add(newscore);
		}
		else {
			for (int i=0;i<ranking.size()+1;++i) {
				if (i>=ranking.size()) {
					this.ranking.add(ranking.size(),newscore);
					break;
				}
				if (score>this.ranking.get(i).score) {
					this.ranking.add(i, newscore);
					break;
				}
			}
		}
	}
	
	
	/**
	 * Metoda zapisuj¹ca maksymalnie 10 najlepszych wyników do pliku. 
	 */
	protected void saveRankingToFile() {
		File savefile = new File("Save.sav");
		
		try {
			FileWriter writertofile = new FileWriter(savefile,false);
			for (int i=0;i<ranking.size();++i) {
				if (i>=10) break; //only 10 best scores
				writertofile.write(this.ranking.get(i).name + " " +String.valueOf(this.ranking.get(i).score)+"\n");
			}
			writertofile.close();
		}
		catch(IOException e) {
			System.out.println("Failed save scores to file.");
		}
	}
	
	/**
	 * Metoda odpowiedzialna za wczytanie najlepszych wyników z pliku.
	 */
	public void loadRankingFromFile() {
		File loadfile = new File("Save.sav");
		ranking.clear();
		
		try {
			Scanner filescanner = new Scanner(loadfile);
			String PlayerName;
			int PlayerScore;
			while(filescanner.hasNext()) {
				PlayerName=filescanner.next();
				PlayerScore=filescanner.nextInt();
				this.AddScore(PlayerName, PlayerScore);
			}
			filescanner.close();
		}
		catch(IOException e) {
			System.out.println("Failed load scores from file.");
		}
	}
	
	/**
	 * Metoda odpowiedzialna za tworzenie obiektu graficznego odpowiadaj¹cemu
	 * tabeli najelepszych wyników
	 * @return Obiekt klasy JTable - reprezentuj¹cy 10 najlepszych wyników odpczytanych z pliku zapisu
	 */
	@SuppressWarnings("serial")
	public JTable RankingList() {
		this.loadRankingFromFile();
		
		String column[]= {"Place", "Player", "Score"};
		
		String data[][] = new String[11][3];
		data[0][0]="Place";
		data[0][1]="Player";
		data[0][2]="Score";
		
		for (int i=0;i<ranking.size();++i) {
			if (i>=10) break;
			data[i+1][0]=String.valueOf(i+1);
			data[i+1][1]=ranking.get(i).name;
			data[i+1][2]=String.valueOf(ranking.get(i).score);
		}
		
		JTable jt = new JTable(data,column) {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		TableColumn firstcolumn = jt.getColumnModel().getColumn(0);
		firstcolumn.setPreferredWidth(30);
		TableColumn lastcolumn = jt.getColumnModel().getColumn(2);
		lastcolumn.setPreferredWidth(40);
		jt.setFocusable(false);
		jt.setCellSelectionEnabled(false);

		
		return jt;
		
	}
}

