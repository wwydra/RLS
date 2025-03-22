package Projekt_1.Wagony;

public abstract class Wagon
        implements Comparable<Wagon>{

    protected int numerIdentyfikacyjny;
    protected String nadawca;
    protected String zabezpieczenia;
    protected double wagaBrutto;
    protected double wagaNetto;
    protected static int counter = 1;
    protected boolean podlaczenie = false;
    protected String ladunek;

    public abstract String getData();

    public double getWagaBrutto() {
        return wagaBrutto;
    }

    public boolean isPodlaczenie() {
        return podlaczenie;
    }

    public String getLadunek() {
        return ladunek;
    }

    @Override
    public int compareTo(Wagon o) {
        return Double.compare(this.wagaBrutto, o.getWagaBrutto());
    }
}
