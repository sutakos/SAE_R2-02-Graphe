package main.java.graphe.representation;

import main.java.graphe.graphes.Graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheMAdj extends Graphe {
	private int[][] matrice;
    private Map<String, Integer> indices;

    public GrapheMAdj() {
        matrice = new int[0][0];
        indices = new HashMap<>();
    }

	@Override
    public void ajouterSommet(String noeud) {
        if (!indices.containsKey(noeud)) {
            indices.put(noeud, indices.size());

            int newSize = indices.size();
            int[][] newMatrice = new int[newSize][newSize];
            for (int i = 0; i < matrice.length; i++) {
                System.arraycopy(matrice[i], 0, newMatrice[i], 0, matrice.length);
            }
            matrice = newMatrice;
        }
    }

    @Override
	public void ajouterArc(String source, String destination, Integer valeur) {
        if(!indices.containsKey(source))
            ajouterSommet(source);

        if (!indices.containsKey(destination))
            ajouterSommet(destination);

        if (matrice[indices.get(source)][indices.get(destination)] != 0 &&
                indices.containsKey(source) && indices.containsKey(destination) || valeur < 0)
            throw new IllegalArgumentException("L'arc est déjà présent");

        else
            matrice[indices.get(source)][indices.get(destination)] = valeur;
	}


    @Override
    public void oterSommet(String noeud) {
        if (indices.containsKey(noeud)) {
            int index = indices.get(noeud);
            indices.remove(noeud);

            // Reset the corresponding row and column in the adjacency matrix
            for (int i = 0; i < matrice.length; i++) {
                matrice[index][i] = 0;
                matrice[i][index] = 0;
            }
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if(!indices.containsKey(source) || !indices.containsKey(destination) ||
                matrice[indices.get(source)][indices.get(destination)] == 0)
            throw new IllegalArgumentException("L'arc n'existe pas");
        else
            matrice[indices.get(source)][indices.get(destination)] = 0;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successors = new ArrayList<>();
        if (indices.containsKey(sommet)) {
            int index = indices.get(sommet);
            for (Map.Entry<String, Integer> entry : indices.entrySet()) {
                if (matrice[index][entry.getValue()] > 0) {
                    successors.add(entry.getKey());
                }
            }
        }
        return successors;
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        sommets.addAll(indices.keySet());
        return sommets;
    }

    @Override
    public int getValuation(String src, String dest) {
        return matrice[indices.get(src)][indices.get(dest)];
    }

    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        return matrice[indices.get(src)][indices.get(dest)] > 0;
    }
}