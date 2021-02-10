package prolabproje21;

public class Komsuluk implements Comparable<Komsuluk> {

    Sehir baslangic;
    Sehir bitis;
    int uzaklik;

    public Komsuluk(Sehir sehir_1, Sehir sehir_2, int uzaklik) {
    	this.baslangic = sehir_1;
    	this.bitis = sehir_2;
        this.uzaklik = uzaklik;
    }

	@Override
	public int compareTo(Komsuluk arg0) {
	    if (this.uzaklik > arg0.uzaklik) {
	        return 1;
	    }
	    else return -1;
	}
	
	public String toString() {
	    return String.format("(%s -> %s, %d)", baslangic.getSehirIsmi(), bitis.getSehirIsmi(), uzaklik);
	}


}