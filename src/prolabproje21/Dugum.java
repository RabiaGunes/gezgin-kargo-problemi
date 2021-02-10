package prolabproje21;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Dugum {
    private Set<Sehir> nodes;
    private boolean directed;

    public Dugum(boolean directed) {
        this.directed = directed;
        nodes = new HashSet<>();
    }
    
    public void addNode(Sehir... n) {
        // We're using a var arg method so we don't have to call
        // addNode repeatedly
    	
        nodes.addAll(Arrays.asList(n));
    }
    
    public void dugumEkle(Sehir sehir_1, Sehir sehir_2, int uzaklik) {

        nodes.add(sehir_1);
        nodes.add(sehir_2);

        komsuEkle(sehir_1, sehir_2, uzaklik);

        if (!directed && sehir_1 != sehir_2) {
        	komsuEkle(sehir_2, sehir_1, uzaklik);
        }
    }
    
    private void komsuEkle(Sehir sehir_1, Sehir sehir_2, int uzaklik) {

        for (Komsuluk komsu : sehir_1.komsular) {
            if (komsu.baslangic == sehir_1 && komsu.bitis == sehir_2) {
            	komsu.uzaklik = uzaklik;
                return;
            }
        }

        sehir_1.komsular.add(new Komsuluk(sehir_1, sehir_2, uzaklik));
    }
    
    public void komsuYazdir() {
        for (Sehir node : nodes) {
            LinkedList<Komsuluk> edges = node.komsular;

            if (edges.isEmpty()) {
                System.out.println(node.getSehirIsmi() + "'ne ait bir komşu yok.");
                continue;
            }
            System.out.print("-----  "+ node.getSehirIsmi() + " şehrinin komşuları -----");

            for (Komsuluk edge : edges) {
                System.out.print(edge.bitis.getSehirIsmi() + "(" + edge.uzaklik + ") ");
            }
            System.out.println();
        }
    }
    
    public boolean hasEdge(Sehir sehir_1, Sehir sehir_2) {
        LinkedList<Komsuluk> komsular = sehir_1.getKomsular();
        for (Komsuluk edge : komsular) {
            if (edge.bitis == sehir_2) {
                return true;
            }
        }
        return false;
    }
    public void resetNodesVisited() {
        for (Sehir node : nodes) {
            node.unvisit();
        }
    }
    
    public GidilenYol enKisaYolBul(Sehir sehir_2 , Sehir sehir_1) {

    	
        HashMap<Sehir, Sehir> changedAt = new HashMap<>();
        changedAt.put(sehir_1, null);
        
        GidilenYol gidilenSehirler = new GidilenYol();
        HashMap<Sehir, Integer> shortestPathMap = new HashMap<>();


        for (Sehir node : nodes) {
            if (node == sehir_1)
                shortestPathMap.put(sehir_1, 0);
            else shortestPathMap.put(node, Integer.MAX_VALUE);
        }


        for (Komsuluk edge : sehir_1.getKomsular()) {
            shortestPathMap.put(edge.bitis, edge.uzaklik);
            changedAt.put(edge.bitis, sehir_1);
        }

        sehir_1.visit();

        while (true) {
            Sehir currentNode = closestReachableUnvisited(shortestPathMap);

            if (currentNode == null) {
                return gidilenSehirler;
            }

            if (currentNode == sehir_2) {

                Sehir child = sehir_2;

                gidilenSehirler.addToList(sehir_2);
                while (true) {
                	Sehir parent = changedAt.get(child);
                    if (parent == null) {
                        break;
                    }

                    
                    child = parent;
                    gidilenSehirler.addToList(parent);
                }
                
                gidilenSehirler.setGidilenMesafe(shortestPathMap.get(sehir_2));
                return gidilenSehirler;
            }
            currentNode.visit();

            for (Komsuluk edge : currentNode.komsular) {
                if (edge.bitis.isVisited())
                    continue;

                if (shortestPathMap.get(currentNode)
                   + edge.uzaklik
                   < shortestPathMap.get(edge.bitis)) {
                    shortestPathMap.put(edge.bitis,
                                       shortestPathMap.get(currentNode) + edge.uzaklik);
                    changedAt.put(edge.bitis, currentNode);
                }
            }
        }
    }
   
    private Sehir closestReachableUnvisited(HashMap<Sehir, Integer> shortestPathMap) {

        double shortestDistance = Integer.MAX_VALUE;
        Sehir closestReachableNode = null;
        for (Sehir node : nodes) {
            if (node.isVisited())
                continue;

            double currentDistance = shortestPathMap.get(node);
            if (currentDistance == Integer.MAX_VALUE)
                continue;

            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                closestReachableNode = node;
            }
        }
        return closestReachableNode;
    }
}
