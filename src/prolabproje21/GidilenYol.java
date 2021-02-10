package prolabproje21;

import java.util.ArrayList;
import java.util.List;

public class GidilenYol {

	private List<Sehir> gidilenSehirler;
	private int gidilenMesafe;
	
	public GidilenYol() {
		this.gidilenSehirler = new ArrayList<>();
		this.gidilenMesafe =0;
	}
	
	public void setGidilenMesafe(int gidilenMesafe) {
		this.gidilenMesafe = gidilenMesafe;
	}
	public void addToList(Sehir sehir) {
		this.gidilenSehirler.add(sehir);
	}

	public List<Sehir> getGidilenSehirler() {
		return gidilenSehirler;
	}

	public void setGidilenSehirler(List<Sehir> gidilenSehirler) {
		this.gidilenSehirler = gidilenSehirler;
	}

	public int getGidilenMesafe() {
		return gidilenMesafe;
	}
	
}
