package ee.erp.haldur.Data;

import java.util.ArrayList;
import java.util.List;

public class Projekt {
    private String projektiNimi;
    private String tahtaeg;
    private String haldur;
    private List<Toode> tooted = new ArrayList();
    private List<Tootaja> tootajad = new ArrayList();
    private List<Logi> logid = new ArrayList();

    public Projekt(String projektiNimi, String tahtaeg, String haldur) {
        this.projektiNimi = projektiNimi;
        this.tahtaeg = tahtaeg;
        this.haldur = haldur;

    }

    public String getProjektiNimi() {
        return this.projektiNimi;
    }

    public String getTahtaeg() {
        return this.tahtaeg;
    }

    public List<Toode> getTooted() {
        return this.tooted;
    }

    public List<Tootaja> getTootajad() {
        return this.tootajad;
    }

    public List<Logi> getLogid() {
        return this.logid;
    }

    public void addToode(Toode toode) {
        this.tooted.add(toode);
    }

    public void addTootaja(Tootaja tootaja) {
        this.tootajad.add(tootaja);
    }

    public void addLogi(Logi logi) {
        this.logid.add(logi);
    }

    public void setTooted(List<Toode> tooted) {
        this.tooted = tooted;
    }

    public void setTootajad(List<Tootaja> tootajad) {
        this.tootajad = tootajad;
    }

    public void setLogid(List<Logi> logid) {
        this.logid = logid;
    }

    public void setProjektiNimi(String projektiNimi) {
        this.projektiNimi = projektiNimi;
    }

    public void setTahtaeg(String tahtaeg) {
        this.tahtaeg = tahtaeg;
    }

    public String getHaldur() {
        return haldur;
    }

    public void setHaldur(String haldur) {
        this.haldur = haldur;
    }

    public String toString() {
        return this.projektiNimi;
    }

}

