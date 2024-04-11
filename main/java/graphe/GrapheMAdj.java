package graphe;

import java.util.ArrayList;
import java.util.Collections;
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

    public GrapheMAdj(String str) {
    	this();
		this.peupler(str);
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

	public void ajouterArc(String source, String destination, Integer valeur) {
		if (!indices.containsKey(source)) {
	        ajouterSommet(source);
	    }
	    if (!indices.containsKey(destination)) {
	        ajouterSommet(destination);
	    }
	    int indexSource = indices.get(source);
	    int indexDestination = indices.get(destination);

	    if (matrice[indexSource][indexDestination] > 0) {
	        throw new IllegalArgumentException("Un arc existe déjà entre les sommets : " + source + " et " + destination);
	    }
	    if (valeur < 0)
	    	throw new IllegalArgumentException("Les valuations ne doivent pas etre negatives " + valeur);

	    matrice[indexSource][indexDestination] = valeur;
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
        if (indices.containsKey(source) && indices.containsKey(destination)) {
            int indexSource = indices.get(source);
            int indexDestination = indices.get(destination);

            if (matrice[indexSource][indexDestination] == 0) {
                throw new IllegalArgumentException("Aucun arc n'existe entre les sommets : " + source + " et " + destination);
            }

            matrice[indexSource][indexDestination] = 0;
        } else {
            throw new IllegalArgumentException("Sommet source et/ou sommet de destination introuvable : " + source + ", " + destination);
        }
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
    	List<String> vertices = new ArrayList<>(indices.keySet());
        Collections.sort(vertices);
        return vertices;
    }

    @Override
    public int getValuation(String src, String dest) {
        int indexSource = indices.get(src);
        int indexDestination = indices.get(dest);
        return matrice[indexSource][indexDestination];
    }

    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        int indexSource = indices.get(src);
        int indexDestination = indices.get(dest);
        return matrice[indexSource][indexDestination] > 0;
    }

}
