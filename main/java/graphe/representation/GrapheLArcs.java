package main.java.graphe.representation;

import java.util.ArrayList;
import java.util.List;

import main.java.graphe.graphes.Arc;
import main.java.graphe.graphes.Graphe;

public class GrapheLArcs extends Graphe {
    private List<Arc> arcs;

    public GrapheLArcs() {
        arcs = new ArrayList<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        if(!contientSommet(noeud))
            arcs.add(new Arc(noeud, "", 0));
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if(!contientArc(source, destination) && valeur > 0){
            arcs.add(new Arc(source, destination, valeur));
            return;
        }
        throw new IllegalArgumentException("Impossible d'ajouter un arc ici");
    }

    @Override
    public void oterSommet(String noeud) {
        for (Arc a : arcs)
            if (contientSommet(noeud))
                arcs.remove(a);
    }

    @Override
    public void oterArc(String source, String destination) {
        for (Arc a : arcs) {
            if (a.getSource().equals(source) && a.getDestination().equals(destination)) {
                arcs.remove(a);
                return;
            }
        }
        throw new IllegalArgumentException("L'arc n'existe pas");
    }

    @Override
    public List<String> getSommets() {
        List <String> sommets = new ArrayList<>();
        for (Arc a : arcs) {
            if (!sommets.contains(a.getSource()))
                sommets.add(a.getSource());

            if (!sommets.contains(a.getDestination()) && !a.getDestination().equals(""))
                sommets.add(a.getDestination());
        }
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> succ = new ArrayList<>();
        for (Arc a : arcs)
            if (a.getSource().equals(sommet) && !a.getDestination().equals(""))
                succ.add(a.getDestination());

        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        for (Arc a : arcs)
            if (a.getSource().equals(src) && a.getDestination().equals(dest))
                return a.getValuation();

        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        for (Arc a : arcs)
            if (a.getSource().equals(sommet))
                return true;

        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Arc a : arcs)
            if (a.getSource().equals(src) && a.getDestination().equals(dest))
                return true;

        return false;
    }
}