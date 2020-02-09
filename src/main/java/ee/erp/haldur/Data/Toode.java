package ee.erp.haldur.Data;

public class Toode {
    private String kood;
    private int kogus;
    private String kohal;
    private String saabumiseKuupaev;
    private String makseTahtaeg;
    private String makstud;
    private Double hind;

    public Toode(String kood, int kogus, String kohal, String saabumiseKuupaev, String makseTahtaeg, String makstud, Double hind) {
        this.kood = kood;
        this.kogus = kogus;
        this.kohal = kohal;
        this.saabumiseKuupaev = saabumiseKuupaev;
        this.makseTahtaeg = makseTahtaeg;
        this.makstud = makstud;
        this.hind = hind;
    }

    public String getKood() {
        return this.kood;
    }

    public void setKood(String kood) {
        this.kood = kood;
    }

    public int getKogus() {
        return this.kogus;
    }

    public void setKogus(int kogus) {
        this.kogus = kogus;
    }

    public String getKohal() {
        return this.kohal;
    }

    public void setKohal(String kohal) {
        this.kohal = kohal;
    }

    public String getSaabumiseKuupaev() {
        return this.saabumiseKuupaev;
    }

    public void setSaabumiseKuupaev(String saabumiseKuupaev) {
        this.saabumiseKuupaev = saabumiseKuupaev;
    }

    public Double getHind() {
        return this.hind;
    }

    public void setHind(Double hind) {
        this.hind = hind;
    }

    public String getMakseTahtaeg() {
        return makseTahtaeg;
    }

    public void setMakseTahtaeg(String makseTahtaeg) {
        this.makseTahtaeg = makseTahtaeg;
    }

    public String getMakstud() {
        return makstud;
    }

    public void setMakstud(String makstud) {
        this.makstud = makstud;
    }
}
