package ee.erp.haldur.Data;

public class Tund {
    private int tund;
    private String kuupaev;

    public Tund(int tund, String kuupaev) {
        this.tund = tund;
        this.kuupaev = kuupaev;
    }

    public int getTund() {
        return this.tund;
    }

    public String getKuupaev() {
        return this.kuupaev;
    }
}
