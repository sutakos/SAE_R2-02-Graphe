package main.java.graphe.algorithme;

import main.java.graphe.graphes.IGrapheConst;

import java.util.*;

public class Dijkstra {
    public static void dijkstra(IGrapheConst graphe, String source, Map<String, Integer> dist, Map<String, String> pred) {
        PriorityQueue<String> sommetsAVisite = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        Set<String> sommetsDejaVisite = new HashSet<>();
        dist.put(source, 0);
        sommetsAVisite.offer(source);

        while (!sommetsAVisite.isEmpty()) {
            String sommetActuel = sommetsAVisite.poll();

            if (sommetsDejaVisite.contains(sommetActuel)) {continue;}

            sommetsDejaVisite.add(sommetActuel);
            updateDist(sommetActuel, dist, pred, sommetsAVisite, graphe);
        }
    }

    private static void updateDist(String sommetActuel, Map<String, Integer> dist, Map<String, String> pred,
                                   PriorityQueue<String> sommetsAVisite, IGrapheConst graphe) {
        for (String succ : graphe.getSucc(sommetActuel)) {
            int distToSucc = dist.get(sommetActuel) + graphe.getValuation(sommetActuel, succ);
            if (distToSucc < dist.getOrDefault(succ, Integer.MAX_VALUE)) {
                dist.put(succ, distToSucc);
                pred.put(succ, sommetActuel);
                sommetsAVisite.offer(succ);
            }
        }
    }
}
