package prolabproje21;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JPanel {

    private static final long serialVersionUID = 1L;

    public static Dugum node;
    public static Scanner scan;
    public static DosyaOku komsulukTxt, sehirMesafeTxt;
    public static List<Sehir> kargoGonderilecekSehirler;
    public static Sehir sehir;
    public static List<Sehir> sehirler;
    public static int plaka;
    public static Image picture;
    public static List<JCheckBox> checkBoxes;
    public static JComboBox<String> comboBox;
    public static List<GidilenYol> gidilenYol;
    public static Main ex;
    public static String Message;
    public static JCheckBox lastBox;

    public Main() {

        node = new Dugum(true);
        sehirler = new ArrayList<>();
        checkBoxes = new ArrayList<>();

        try {
            picture = ImageIO.read(Main.class.getResource("image.png"));
        } catch (IOException ex) {
            System.out.println("HARİTA BULUNAMADI");
        }
        sehirDoldur();
        initUI();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (gidilenYol != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            for (GidilenYol gidilenY : gidilenYol) {
                if (gidilenY.equals(gidilenYol.get(gidilenYol.size() - 1))) {
                    g2.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
                }
                List<Sehir> sehirler = gidilenY.getGidilenSehirler();
                for (int i = 0; i < sehirler.size(); i++) {
                    if (!sehirler.get(i).equals(sehirler.get(sehirler.size() - 1))) {
                        g2.drawLine(sehirler.get(i).getX() + 10, sehirler.get(i).getY() + 10, sehirler.get(i + 1).getX() + 10, sehirler.get(i + 1).getY() + 10);
                    }

                }
            }
        }

    }

    public void initUI() {

        JFrame frame = new JFrame();
        JButton hesaplaButton = new JButton("YOLLARI HESAPLA");
        JButton kargoTakipButton = new JButton("KARGO TAKİP");
        JButton resetButton = new JButton("SIFIRLA");
        kargoTakipButton.setEnabled(false);
        JLabel labelText1 = new JLabel("BAŞLANGIÇ NOKTASI SEÇİNİZ : ");
        JLabel labelText = new JLabel("HARİTA ÜZERİNDEN KARGO GÖNDERİLECEK ŞEHİRLERİ İŞARETLEYİNİZ");
        JLabel picLabel = new JLabel(new ImageIcon(picture));

        comboBox = new JComboBox<String>();
        comboBox.addItem("Seçiniz..");
        for (int i = 0; i < sehirler.size(); i++) {
            comboBox.addItem(sehirler.get(i).getSehirPlaka() + " - " + sehirler.get(i).getSehirIsmi());
        }
        comboBox.setBounds(200, 10, 120, 20);

        for (int i = 0; i < sehirler.size(); i++) {
            JCheckBox checkbox = new JCheckBox();
            checkbox.setBounds(sehirler.get(i).getX(), sehirler.get(i).getY(), 20, 20);
            checkBoxes.add(checkbox);
            this.add(checkBoxes.get(checkBoxes.size() - 1));
        }
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (lastBox != null) {
                    lastBox.setVisible(true);
                }
                int index = comboBox.getSelectedIndex();
                checkBoxes.get(index - 1).setVisible(false);
                lastBox = checkBoxes.get(index - 1);
                frame.repaint();
            }
        });
        labelText1.setBounds(10, 10, 180, 20);
        labelText.setBounds(330, 10, 400, 20);
        hesaplaButton.setBounds(740, 10, 150, 20);
        kargoTakipButton.setBounds(900, 10, 150, 20);
        resetButton.setBounds(1060, 10, 150, 20);
        picLabel.setBounds(0, 50, 1370, 700);

        hesaplaButton.addActionListener((ActionEvent e) -> {
            if (comboBox.getSelectedIndex() == 0) {
                Message = "Lütfen Başlangıç Noktası İçin Bir Şehir Seçiniz!!";
                JOptionPane.showMessageDialog(new JFrame(), Message, "Hata!",
                        JOptionPane.ERROR_MESSAGE);
            } else if (CheckBoxesCount() < 1 || CheckBoxesCount() > 10) {
                Message = "Lütfen Kargo Gönderilecek Şehirleri Harita Üzerinden Seçiniz!! \n En Az 1 En Fazla 10 Şehir Seçili Olmalıdır!";
                JOptionPane.showMessageDialog(new JFrame(), Message, "Hata!",
                        JOptionPane.ERROR_MESSAGE);
            } else {

                EnKisaYol enKisa = new EnKisaYol(sehirler.get(comboBox.getSelectedIndex() - 1), getCheckBoxList(), node);
                gidilenYol = enKisa.getGidilenYol();
                kargoTakipButton.setEnabled(true);
                hesaplaButton.setEnabled(false);
                frame.repaint();
            }
        });

        kargoTakipButton.addActionListener((ActionEvent e) -> {
            if (gidilenYol.size() > 0) {
                Message = "";
                int toplam = 0;
                for (GidilenYol g : gidilenYol) {
                    List<Sehir> gidilen = g.getGidilenSehirler();
                    for (Sehir s : gidilen) {

                        Message += s.getSehirIsmi() + " -> ";
                    }
                    Message += "Toplam : " + g.getGidilenMesafe() + "\n";
                    toplam += g.getGidilenMesafe();
                }
                Message += "----------------------------------------------------------\n";
                Message += "TOPLAM GİDİLEN MESAFE :" + toplam;

                JOptionPane.showMessageDialog(new JFrame(), Message, "Gidilen Yol Takip Listesi",
                        JOptionPane.INFORMATION_MESSAGE);

            } else {
                Message = "Birşeyler Ters Gitti!";
                JOptionPane.showMessageDialog(new JFrame(), Message, "Hata!",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        resetButton.addActionListener((ActionEvent e) -> {
            gidilenYol = null;

            ex = new Main();
            ex.setVisible(true);
            frame.dispose();
        });
        this.setLayout(null);
        this.add(resetButton);
        this.add(kargoTakipButton);
        this.add(labelText1);
        this.add(comboBox);
        this.add(picLabel);
        this.add(labelText);
        this.add(hesaplaButton);

        frame.setTitle("Türkiye İller Haritası");
        frame.setVisible(true);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(this);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                ex = new Main();

            }
        });
    }

    public int CheckBoxesCount() {
        int count = 0;
        for (JCheckBox box : checkBoxes) {
            if (box.isSelected()) {
                count++;
            }
        }
        return count;
    }

    public List<Sehir> getCheckBoxList() {
        List<Sehir> x = new ArrayList<Sehir>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                x.add(sehirler.get(i));
            }
        }
        return x;
    }

    public void sehirDoldur() {
        sehir = new Sehir("Adana", 1, 678, 537);
        sehirler.add(sehir);
        sehir = new Sehir("Adıyaman", 2, 888, 462);
        sehirler.add(sehir);
        sehir = new Sehir("Afyon", 3, 339, 374);
        sehirler.add(sehir);
        sehir = new Sehir("Ağrı", 4, 1210, 261);
        sehirler.add(sehir);
        sehir = new Sehir("Amasya", 5, 708, 209);
        sehirler.add(sehir);
        sehir = new Sehir("Ankara", 6, 496, 267);
        sehirler.add(sehir);
        sehir = new Sehir("Antalya", 7, 341, 542);
        sehirler.add(sehir);
        sehir = new Sehir("Artvin", 8, 1111, 139);
        sehirler.add(sehir);
        sehir = new Sehir("Aydın", 9, 143, 446);
        sehirler.add(sehir);
        sehir = new Sehir("Balıkesir", 10, 158, 284);
        sehirler.add(sehir);
        sehir = new Sehir("Bilecik", 11, 305, 247);
        sehirler.add(sehir);
        sehir = new Sehir("Bingöl", 12, 1037, 353);
        sehirler.add(sehir);
        sehir = new Sehir("Bitlis", 13, 1156, 387);
        sehirler.add(sehir);
        sehir = new Sehir("Bolu", 14, 419, 199);
        sehirler.add(sehir);
        sehir = new Sehir("Burdur", 15, 318, 467);
        sehirler.add(sehir);
        sehir = new Sehir("Bursa", 16, 242, 241);
        sehirler.add(sehir);
        sehir = new Sehir("Çanakkale", 17, 61, 233);
        sehirler.add(sehir);
        sehir = new Sehir("Çankırı", 18, 555, 215);
        sehirler.add(sehir);
        sehir = new Sehir("Çorum", 19, 646, 220);
        sehirler.add(sehir);
        sehir = new Sehir("Denizli", 20, 231, 458);
        sehirler.add(sehir);
        sehir = new Sehir("Diyarbakır", 21, 1025, 440);
        sehirler.add(sehir);
        sehir = new Sehir("Edirne", 22, 82, 96);
        sehirler.add(sehir);
        sehir = new Sehir("Elazığ", 23, 947, 377);
        sehirler.add(sehir);
        sehir = new Sehir("Erzincan", 24, 962, 280);
        sehirler.add(sehir);
        sehir = new Sehir("Erzurum", 25, 1084, 256);
        sehirler.add(sehir);
        sehir = new Sehir("Eskişehir", 26, 341, 282);
        sehirler.add(sehir);
        sehir = new Sehir("Gaziantep", 27, 825, 527);
        sehirler.add(sehir);
        sehir = new Sehir("Giresun", 28, 885, 181);
        sehirler.add(sehir);
        sehir = new Sehir("Gümüşhane", 29, 956, 216);
        sehirler.add(sehir);
        sehir = new Sehir("Hakkari", 30, 1281, 448);
        sehirler.add(sehir);
        sehir = new Sehir("Hatay", 31, 741, 608);
        sehirler.add(sehir);
        sehir = new Sehir("Isparta", 32, 337, 462);
        sehirler.add(sehir);
        sehir = new Sehir("Mersin", 33, 630, 554);
        sehirler.add(sehir);
        sehir = new Sehir("İstanbul", 34, 241, 164);
        sehirler.add(sehir);
        sehir = new Sehir("İzmir", 35, 97, 388);
        sehirler.add(sehir);
        sehir = new Sehir("Kars", 36, 1205, 181);
        sehirler.add(sehir);
        sehir = new Sehir("Kastamonu", 37, 566, 144);
        sehirler.add(sehir);
        sehir = new Sehir("Kayseri", 38, 686, 381);
        sehirler.add(sehir);
        sehir = new Sehir("Kırklareli", 39, 126, 95);
        sehirler.add(sehir);
        sehir = new Sehir("Kırşehir", 40, 594, 344);
        sehirler.add(sehir);
        sehir = new Sehir("Kocaeli", 41, 305, 189);
        sehirler.add(sehir);
        sehir = new Sehir("Konya", 42, 475, 460);
        sehirler.add(sehir);
        sehir = new Sehir("Kütahya", 43, 302, 312);
        sehirler.add(sehir);
        sehir = new Sehir("Malatya", 44, 888, 411);
        sehirler.add(sehir);
        sehir = new Sehir("Manisa", 45, 119, 375);
        sehirler.add(sehir);
        sehir = new Sehir("Maraş", 46, 793, 479);
        sehirler.add(sehir);
        sehir = new Sehir("Mardin", 47, 1069, 493);
        sehirler.add(sehir);
        sehir = new Sehir("Muğla", 48, 176, 505);
        sehirler.add(sehir);
        sehir = new Sehir("Muş", 49, 1110, 361);
        sehirler.add(sehir);
        sehir = new Sehir("Nevşehir", 50, 632, 393);
        sehirler.add(sehir);
        sehir = new Sehir("Niğde", 51, 631, 451);
        sehirler.add(sehir);
        sehir = new Sehir("Ordu", 52, 847, 177);
        sehirler.add(sehir);
        sehir = new Sehir("Rize", 53, 1024, 162);
        sehirler.add(sehir);
        sehir = new Sehir("Sakarya", 54, 336, 191);
        sehirler.add(sehir);
        sehir = new Sehir("Samsun", 55, 739, 150);
        sehirler.add(sehir);
        sehir = new Sehir("Siirt", 56, 1148, 430);
        sehirler.add(sehir);
        sehir = new Sehir("Sinop", 57, 644, 114);
        sehirler.add(sehir);
        sehir = new Sehir("Sivas", 58, 790, 287);
        sehirler.add(sehir);
        sehir = new Sehir("Tekirdağ", 59, 139, 163);
        sehirler.add(sehir);
        sehir = new Sehir("Tokat", 60, 758, 238);
        sehirler.add(sehir);
        sehir = new Sehir("Trabzon", 61, 969, 169);
        sehirler.add(sehir);
        sehir = new Sehir("Tunceli", 62, 971, 337);
        sehirler.add(sehir);
        sehir = new Sehir("Şanlıurfa", 63, 928, 515);
        sehirler.add(sehir);
        sehir = new Sehir("Uşak", 64, 258, 377);
        sehirler.add(sehir);
        sehir = new Sehir("Van", 65, 1246, 370);
        sehirler.add(sehir);
        sehir = new Sehir("Yozgat", 66, 637, 286);
        sehirler.add(sehir);
        sehir = new Sehir("Zonguldak", 67, 436, 137);
        sehirler.add(sehir);
        sehir = new Sehir("Aksaray", 68, 584, 415);
        sehirler.add(sehir);
        sehir = new Sehir("Bayburt", 69, 1091, 439);
        sehirler.add(sehir);
        sehir = new Sehir("Karaman", 70, 529, 523);
        sehirler.add(sehir);
        sehir = new Sehir("Kırıkkale", 71, 550, 283);
        sehirler.add(sehir);
        sehir = new Sehir("Batman", 72, 1091, 439);
        sehirler.add(sehir);
        sehir = new Sehir("Şırnak", 73, 1190, 464);
        sehirler.add(sehir);
        sehir = new Sehir("Bartın", 74, 471, 120);
        sehirler.add(sehir);
        sehir = new Sehir("Ardahan", 75, 1174, 141);
        sehirler.add(sehir);
        sehir = new Sehir("Iğdır", 76, 1277, 237);
        sehirler.add(sehir);
        sehir = new Sehir("Yalova", 77, 257, 203);
        sehirler.add(sehir);
        sehir = new Sehir("Karabük", 78, 493, 162);
        sehirler.add(sehir);
        sehir = new Sehir("Kilis", 79, 809, 560);
        sehirler.add(sehir);
        sehir = new Sehir("Osmaniye", 80, 746, 532);
        sehirler.add(sehir);
        sehir = new Sehir("Düzce", 81, 388, 191);
        sehirler.add(sehir);

        
        
       File dosya = new File(this.getClass().getResource("komsu.txt").getFile());
       
       komsulukTxt = new DosyaOku(dosya);
       int komsulukArray[][] = komsulukTxt.getArray();

       dosya =  new File(this.getClass().getResource("matris.txt").getFile());
       sehirMesafeTxt = new DosyaOku(dosya);
           //     File matrisDosyasi = new File(this.getClass().getResource("../src/resources/Matris.txt").getFile
        int mesafeArray[][] = sehirMesafeTxt.getArray();

        List<Integer> komsuSehirMesafe = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            komsuSehirMesafe = new ArrayList<>();
            for (int j = 0; j < 81; j++) {
                if (komsulukArray[i][j] > 0) {
                    node.dugumEkle(sehirler.get(i), sehirler.get(j), komsulukArray[i][j]);
                }
                komsuSehirMesafe.add(mesafeArray[i][j]);
            }
            sehirler.get(i).setSehirUzakliklari(komsuSehirMesafe);
        }

    }

}
