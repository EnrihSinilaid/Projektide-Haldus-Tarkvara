package ee.erp.haldur.Data;

import java.time.LocalDate;

public class Logi {
    private String logi;
    private String kuupaev;

    public Logi(String logi) {
        this.logi = logi;
        this.kuupaev = LocalDate.now().toString();
    }

    public Logi(String logi, String kuupaev) {
        this.logi = logi;
        this.kuupaev = kuupaev;
    }

    public String getLogi() {
        return this.logi;
    }

    public void setLogi(String logi) {
        this.logi = logi;
    }

    public String getKuupaev() {
        return this.kuupaev;
    }
}

