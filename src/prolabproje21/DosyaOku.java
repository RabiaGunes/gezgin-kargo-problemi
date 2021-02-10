package prolabproje21;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author MONSTER
 */
public class DosyaOku {

	private int array[][];
	
	
	public DosyaOku(File file) {

		this.array = new int[81][81];
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			int j = 0;
			while ((line = br.readLine()) != null) {
				String dizi[];
				dizi = line.split(",");
				for (int i = 0; i < dizi.length; i++) {
					this.array[j][i] = Integer.parseInt(dizi[i]);
				}
				j++;
			}
		} catch (Exception ex) {

		}
	}

	public int getIndexArray(int satir, int sutun) {
		return this.array[satir][sutun];
	}

	public int[][] getArray() {
		return this.array;
	}

	public void setArray(int[][] array) {
		this.array = array;
	}

	public void setIndexArray(int satir, int sutun, int eleman) {
		this.array[satir][sutun] = eleman;
	}
}
