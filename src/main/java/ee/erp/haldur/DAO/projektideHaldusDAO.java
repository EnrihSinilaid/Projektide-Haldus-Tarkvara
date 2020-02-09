package ee.erp.haldur.DAO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ee.erp.haldur.Data.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class projektideHaldusDAO {
    private List<Projekt> projektid = new ArrayList();

    public projektideHaldusDAO() {
    }

    public List<Projekt> getProjektid() {
        return this.projektid;
    }

    public void salvesta() throws IOException {
        JSONObject kluster = new JSONObject();
        int loendur = 0;
        Iterator var3 = this.projektid.iterator();

        while(var3.hasNext()) {
            Projekt obj = (Projekt)var3.next();
            JSONObject projekt = new JSONObject();
            projekt.put("Projekt", obj.getProjektiNimi());
            projekt.put("Tahtaeg", obj.getTahtaeg());
            projekt.put("Haldur", obj.getHaldur());
            JSONObject tooted = new JSONObject();
            int tooteLoendur = 0;
            Iterator var8 = obj.getTooted().iterator();

            while(var8.hasNext()) {
                Toode jupp = (Toode)var8.next();
                JSONObject toode = new JSONObject();
                toode.put("Kood", jupp.getKood());
                toode.put("Kogus", String.valueOf(jupp.getKogus()));
                toode.put("Kuupaev", jupp.getSaabumiseKuupaev());
                toode.put("Kohal", jupp.getKohal());
                toode.put("Makse tahtaeg", jupp.getMakseTahtaeg());
                toode.put("Makstud", jupp.getMakstud());
                toode.put("Hind", String.valueOf(jupp.getHind()));
                tooted.put(String.valueOf(tooteLoendur++), toode);
            }

            projekt.put("Tooted", tooted);
            JSONArray tootajad = new JSONArray();
            Iterator var18 = obj.getTootajad().iterator();

            while(var18.hasNext()) {
                Tootaja jupp = (Tootaja)var18.next();
                JSONObject tootaja = new JSONObject();
                JSONObject tunnid = new JSONObject();
                int tootajaLoendur = 0;
                Iterator var14 = jupp.getTund().iterator();

                while(var14.hasNext()) {
                    Tund tund = (Tund)var14.next();
                    JSONObject abiTund = new JSONObject();
                    abiTund.put("Tehtud tunnid", String.valueOf(tund.getTund()));
                    abiTund.put("Kuupaev", tund.getKuupaev());
                    tunnid.put(String.valueOf(tootajaLoendur++), abiTund);
                }

                tootaja.put("Tootaja", jupp.getNimi());
                tootaja.put("Tunnid", tunnid);
                tootajad.add(tootaja);
            }

            projekt.put("Tootajad", tootajad);
            JSONObject logid = new JSONObject();
            int logiLoendur = 0;
            Iterator var22 = obj.getLogid().iterator();

            while(var22.hasNext()) {
                Logi jupp = (Logi)var22.next();
                JSONObject log = new JSONObject();
                log.put("Logi", jupp.getLogi());
                log.put("Kuupaev", jupp.getKuupaev());
                logid.put(String.valueOf(logiLoendur++), log);
            }

            projekt.put("Logid", logid);
            kluster.put(String.valueOf(loendur++), projekt);
        }

        System.out.println(kluster);
        Files.write(Paths.get("Andmed.json"), kluster.toJSONString().getBytes(), new OpenOption[0]);
    }

    public void loe() throws ParseException {
        FileReader lugeja = null;

        try {
            lugeja = new FileReader("Andmed.json");
            JSONParser parser = new JSONParser();
            JSONObject andmed = (JSONObject)parser.parse(lugeja);
            System.out.println(andmed);

            for(int i = 0; i < andmed.size(); ++i) {
                JSONObject projekt = (JSONObject)andmed.get(String.valueOf(i));
                JSONArray tootajad = (JSONArray)projekt.get("Tootajad");
                JSONObject tooted = (JSONObject)projekt.get("Tooted");
                JSONObject logid = (JSONObject)projekt.get("Logid");
                System.out.println(tootajad);
                Projekt uusProjekt = new Projekt(projekt.get("Projekt").toString(), projekt.get("Tahtaeg").toString(), projekt.get("Haldur").toString());
                List<Toode> uusTooted = uusProjekt.getTooted();

                for(int j = 0; j < tooted.size(); ++j) {
                    JSONObject toode = (JSONObject)tooted.get(String.valueOf(j));
                    Toode lisa = new Toode(toode.get("Kood").toString(), Integer.parseInt(toode.get("Kogus").toString())
                            , toode.get("Kuupaev").toString(), toode.get("Kohal").toString()
                            , toode.get("Makse tahtaeg").toString(), toode.get("Makstud").toString()
                            , Double.parseDouble(toode.get("Hind").toString()));
                    uusTooted.add(lisa);
                }

                uusProjekt.setTooted(uusTooted);
                List<Tootaja> uusTootajad = uusProjekt.getTootajad();

                JSONObject logi;
                for(int j = 0; j < tootajad.size(); ++j) {
                    JSONObject tootaja = (JSONObject)tootajad.get(j);
                    logi = (JSONObject)tootaja.get("Tunnid");
                    List<Tund> lisaTund = new ArrayList();

                    for(int k = 0; k < logi.size(); ++k) {
                        JSONObject tund = (JSONObject)logi.get(String.valueOf(k));
                        Tund lisa = new Tund(Integer.parseInt(tund.get("Tehtud tunnid").toString()), tund.get("Kuupaev").toString());
                        lisaTund.add(lisa);
                    }

                    Tootaja lisa = new Tootaja(tootaja.get("Tootaja").toString(), lisaTund);
                    uusTootajad.add(lisa);
                }

                uusProjekt.setTootajad(uusTootajad);
                List<Logi> uusLogid = uusProjekt.getLogid();

                for(int j = 0; j < logid.size(); ++j) {
                    logi = (JSONObject)logid.get(String.valueOf(j));
                    Logi lisa = new Logi(logi.get("Logi").toString(), logi.get("Kuupaev").toString());
                    uusLogid.add(lisa);
                }

                uusProjekt.setLogid(uusLogid);
                this.projektid.add(uusProjekt);
            }
        } catch (FileNotFoundException var19) {
            System.out.println("Eelnevalt salvestatud andmeid ei leitud!");
        } catch (IOException var20) {
            var20.printStackTrace();
        }

    }
}

