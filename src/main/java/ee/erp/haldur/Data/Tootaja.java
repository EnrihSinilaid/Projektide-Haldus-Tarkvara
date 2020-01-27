package ee.erp.haldur.Data;

import java.util.ArrayList;
import java.util.List;

public class Tootaja {
    private String nimi;
    private List<Tund> tund = new ArrayList();

    public Tootaja(String nimi) {
        this.nimi = nimi;
    }

    public Tootaja(String nimi, List<Tund> tunnid) {
        this.nimi = nimi;
        this.tund = tunnid;
    }

    public String getNimi() {
        return this.nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public List<Tund> getTund() {
        return this.tund;
    }

    public void addTunnid(Tund tund) {
        this.tund.add(tund);
    }
}

