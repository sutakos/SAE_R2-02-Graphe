package main.java.graphe.representation;

import main.java.graphe.graphes.Arc;
import main.java.graphe.graphes.Graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrapheLAdj extends Graphe {
    private HashMap<String, List<Arc>> liste;

    public GrapheLAdj(){
        this.liste = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!this.liste.containsKey(noeud))
            this.liste.put(noeud, new ArrayList<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (!contientArc(source, destination) && valeur > 0){
            ajouterSommet(source);
            ajouterSommet(destination);

            Arc arc = new Arc(source, destination, valeur);
            this.liste.get(source).add(arc);

            return;
        }
        throw new IllegalArgumentException("Impossible d'ajouter un arc ici");
    }

    @Override
    public void oterSommet(String noeud) {
        if (this.liste.containsKey(noeud)) {
            this.liste.remove(noeud);

            for (String s : this.liste.keySet()) {
                List<Arc> succ = this.liste.get(s);

                for (Arc a : succ)
                    if (a.getDestination().equals(noeud))
                        succ.remove(a);
            }
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (contientArc(source,destination)) {
            List<Arc> succ = this.liste.get(source);
            for (Arc a : succ)
                if (a.getDestination().equals(destination))
                    succ.remove(a);
            return;
        }

        throw new IllegalArgumentException("L'arc n'existe pas");
    }

    @Override
    public List<String> getSommets() {
        return new ArrayList<>(this.liste.keySet());
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<>();

        if (this.liste.containsKey(sommet))
            for (Arc a : this.liste.get(sommet))
                succ.add(a.getDestination());

        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        if (this.liste.containsKey(src)) {
            for (Arc a : this.liste.get(src))
                if (a.getDestination().equals(dest))
                    return a.getValuation();
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return this.liste.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (this.liste.containsKey(src))
            for (Arc a : this.liste.get(src))
                if (a.getDestination().equals(dest))
                    return true;

        return false;
    }
}
