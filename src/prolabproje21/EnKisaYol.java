package prolabproje21;

import java.util.ArrayList;
import java.util.List;


public class EnKisaYol {
	private Dugum node;
	private List<Sehir> kargoGonderilecekSehirler;
	private List<GidilenYol> gidilenYol;
	private Sehir bulundugumuzSehir;
	private Sehir hedefSehir;
	private int toplamMesafe;
	
	public EnKisaYol(Sehir baslangic , List<Sehir> kargoGonderilecekSehirler , Dugum node) {

		 this.node= node;
		 gidilenYol = new ArrayList<GidilenYol>();
		 this.kargoGonderilecekSehirler = kargoGonderilecekSehirler;
		
		 
		 bulundugumuzSehir = baslangic;
		 
		 while(true) {
			 
			 hedefSehir = hedefBelirle();
			 if(hedefSehir==null) {
				 break;
			 }

		     gidilenYol.add(node.enKisaYolBul(bulundugumuzSehir , hedefSehir));
			 bulundugumuzSehir = hedefSehir; 
			 node.resetNodesVisited();
		 }
		 
		 gidilenYol.add(node.enKisaYolBul(bulundugumuzSehir , baslangic));
		 
		 for(int i=0;i<gidilenYol.size();i++) {
			 List<Sehir> gidilenSehirler =gidilenYol.get(i).getGidilenSehirler();
			 for(int j=0;j<gidilenSehirler.size();j++) {
				 System.out.print(gidilenSehirler.get(j).getSehirIsmi()+" -> ");
			 }
			 System.out.println();
			 System.out.println("Gidilen Mesafe : " + gidilenYol.get(i).getGidilenMesafe());
			 toplamMesafe += gidilenYol.get(i).getGidilenMesafe();
		 }
		 
		 System.out.println("TOPLAM GİDİLEN MESAFE : " + toplamMesafe);
	}
	public Sehir hedefBelirle() {
		
	    for (int j = 0; j < kargoGonderilecekSehirler.size(); j++) {
			if (kargoGonderilecekSehirler.get(j).getSehirPlaka() == bulundugumuzSehir.getSehirPlaka()) {
					kargoGonderilecekSehirler.remove(j);
			}
		}
		
		if(kargoGonderilecekSehirler.size()>0) {
			int enKisa = kargoGonderilecekSehirler.get(0).getSehirUzaklikWithIndex(bulundugumuzSehir.getSehirPlaka()-1);
			int enKisaIndex = 0;
			for (int i = 0; i < kargoGonderilecekSehirler.size(); i++) {
				int temp = kargoGonderilecekSehirler.get(i).getSehirUzaklikWithIndex(bulundugumuzSehir.getSehirPlaka()- 1);
				if (temp < enKisa) {
					enKisa = temp;
					enKisaIndex = i;
				}
			}
			return kargoGonderilecekSehirler.get(enKisaIndex);
		}
		return null;
	}
	public Dugum getNode() {
		return node;
	}
	public void setNode(Dugum node) {
		this.node = node;
	}
	public List<Sehir> getKargoGonderilecekSehirler() {
		return kargoGonderilecekSehirler;
	}
	public void setKargoGonderilecekSehirler(List<Sehir> kargoGonderilecekSehirler) {
		this.kargoGonderilecekSehirler = kargoGonderilecekSehirler;
	}
	public List<GidilenYol> getGidilenYol() {
		return gidilenYol;
	}
	public void setGidilenYol(List<GidilenYol> gidilenYol) {
		this.gidilenYol = gidilenYol;
	}
	public Sehir getBulundugumuzSehir() {
		return bulundugumuzSehir;
	}
	public void setBulundugumuzSehir(Sehir bulundugumuzSehir) {
		this.bulundugumuzSehir = bulundugumuzSehir;
	}
	public Sehir getHedefSehir() {
		return hedefSehir;
	}
	public void setHedefSehir(Sehir hedefSehir) {
		this.hedefSehir = hedefSehir;
	}
	public int getToplamMesafe() {
		return toplamMesafe;
	}
	public void setToplamMesafe(int toplamMesafe) {
		this.toplamMesafe = toplamMesafe;
	}
	


}
