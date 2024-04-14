package main.java.graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrapheLAdj extends Graphe{
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

            Arc arc = new Arc(source, destination, valeur);
            this.liste.get(source).add(arc);

            return;
        }
        throw new IllegalArgumentException("L'arc est déjà présent");
    }

    @Override
    public void oterSommet(String noeud) {
        if (contientSommet(noeud)) {
            this.liste.remove(noeud);

            for (String s : this.liste.keySet())
                for (Arc a : this.liste.get(s))
                    if (a.getDestination().equals(noeud))
                        this.liste.get(s).remove(a);
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (contientArc(source, destination))
            for (Arc a : this.liste.get(source)){
                if (a.getSource().equals(source) && a.getDestination().equals(destination)) {
                    this.liste.get(source).remove(a);
                    return;
                }
            }
        throw new IllegalArgumentException("L'arc n'est pas présent");
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();

        for (String s : this.liste.keySet())
            if (!sommets.contains(s))
                sommets.add(s);

        return sommets;
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
        for (Arc a : this.liste.get(sommet))
            if (a.getSource().equals(sommet))
                return true;

        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Arc a : this.liste.get(src))
            if (a.getSource().equals(src) && a.getDestination().equals(dest))
                return true;

        return false;
    }
}
