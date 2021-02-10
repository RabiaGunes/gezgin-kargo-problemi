package prolabproje21;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sehir {
    
	private int sehirPlaka;
	private int x;
	private int y;
    private String sehirIsmi;
    private boolean visited;
    LinkedList<Komsuluk> komsular;
    private  List<Integer> sehirUzakliklari;

    public Sehir(String sehirIsmi , int sehirPlaka ,int x , int y) {
    	this.sehirPlaka=sehirPlaka;
        this.sehirIsmi = sehirIsmi;
        this.x = x;
        this.y=y;
        this.sehirUzakliklari = new ArrayList<>();
        visited = false;
        komsular = new LinkedList<>();
    }
   
    public int getSehirPlaka() {
		return sehirPlaka;
	}
    
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<Integer> getSehirUzakliklari() {
		return sehirUzakliklari;
	}

	public int getSehirUzaklikWithIndex(int index) {
		return this.sehirUzakliklari.get(index);
	}
	public void setSehirUzakliklari(List<Integer> sehirUzakliklari) {
		this.sehirUzakliklari = sehirUzakliklari;
	}
	public void setSehirPlaka(int sehirPlaka) {
		this.sehirPlaka = sehirPlaka;
	}

	public String getSehirIsmi() {
		return sehirIsmi;
	}

	public void setSehirIsmi(String sehirIsmi) {
		this.sehirIsmi = sehirIsmi;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public LinkedList<Komsuluk> getKomsular() {
		return komsular;
	}

	public void setKomsular(LinkedList<Komsuluk> komsular) {
		this.komsular = komsular;
	}

	boolean isVisited() {
        return visited;
    }

    void visit() {
        visited = true;
    }

    void unvisit() {
        visited = false;
    }
}
