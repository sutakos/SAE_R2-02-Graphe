package main.java.graphe;

public class Arc {
    private final String source, destination;
    private final int valuation;

    public Arc(String s, String d, int v){
        this.source = s;
        this.destination = d;
        this.valuation = v;
    }

    public String getSource(){
        return this.source;
    }

    public String getDestination(){
        return this.destination;
    }

    public int getValuation(){
        return this.valuation;
    }
}
