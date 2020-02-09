package ee.erp.haldur.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import ee.erp.haldur.DAO.projektideHaldusDAO;
import ee.erp.haldur.Data.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


public class lisaSisuController implements Initializable {
    private projektideHaldusDAO dao;
    @FXML
    private ComboBox valiProjektCombo;
    @FXML
    private ComboBox valiToodeCombo;
    @FXML
    private ComboBox valiTootajaCombo;
    @FXML
    private Button lisaToodeButton;
    @FXML
    private Button muudaToodeButton;
    @FXML
    private Button lisaTootajaButton;
    @FXML
    private Button lisaTunnidButton;
    @FXML
    private Button lisaLogiButton;
    @FXML
    private TextField tooteKoodLText;
    @FXML
    private TextField kogusLText;
    @FXML
    private TextField saabumineLText;
    @FXML
    private TextField kohalLText;
    @FXML
    private TextField makseTahtaegLText;
    @FXML
    private TextField makstudLText;
    @FXML
    private TextField hindLText;
    @FXML
    private TextField tooteKoodMText;
    @FXML
    private TextField kogusMText;
    @FXML
    private TextField saabumineMText;
    @FXML
    private TextField kohalMText;
    @FXML
    private TextField makseTahtaegMText;
    @FXML
    private TextField makstudMText;
    @FXML
    private TextField hindMText;
    @FXML
    private TextField tootajaNimiText;
    @FXML
    private TextField tooTunnidText;
    @FXML
    private TextField tooKuupaevText;
    @FXML
    private TextArea logiText;

    public lisaSisuController(projektideHaldusDAO dao) {
        this.dao = dao;
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.valiProjektCombo.showingProperty().addListener((observable, wasShowing, isNowShowing) -> {
            this.populeeriProjektid();
        });

        this.valiTootajaCombo.showingProperty().addListener((observable, wasShowing, isNowShowing) -> {
            this.populeeriTootajad();
        });
        this.valiToodeCombo.showingProperty().addListener((observable, wasShowing, isNowShowing) -> {
            this.populeeriTooted();
        });
        this.valiToodeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                lisaSisuController.this.taidaValjad();
            }
        });
    }

    @FXML
    public void populeeriProjektid() {
        List<String> projektiNimed = new ArrayList();
        Iterator var2 = this.dao.getProjektid().iterator();

        while(var2.hasNext()) {
            Projekt obj = (Projekt)var2.next();
            projektiNimed.add(obj.getProjektiNimi());
        }

        this.valiProjektCombo.setItems(FXCollections.observableArrayList(projektiNimed));
    }

    @FXML
    public void populeeriTootajad() {
        List<String> nimed = new ArrayList();
        Iterator var2 = this.valitudProjekt().getTootajad().iterator();

        while(var2.hasNext()) {
            Tootaja obj = (Tootaja)var2.next();
            nimed.add(obj.getNimi());
        }

        this.valiTootajaCombo.setItems(FXCollections.observableArrayList(nimed));
    }

    @FXML
    public void populeeriTooted() {
        List<String> tooted = new ArrayList();
        Iterator var2 = this.valitudProjekt().getTooted().iterator();

        while(var2.hasNext()) {
            Toode obj = (Toode)var2.next();
            tooted.add(obj.getKood());
        }

        this.valiToodeCombo.setItems(FXCollections.observableArrayList(tooted));
    }

    public Projekt valitudProjekt() {
        Projekt valitud = null;
        Iterator var2 = this.dao.getProjektid().iterator();

        while(var2.hasNext()) {
            Projekt obj = (Projekt)var2.next();
            if (obj.getProjektiNimi().equals(this.valiProjektCombo.getValue())) {
                valitud = obj;
                break;
            }
        }

        return valitud;
    }

    public Toode valitudToode() {
        Toode valitud = null;
        Iterator var2 = this.valitudProjekt().getTooted().iterator();

        while(var2.hasNext()) {
            Toode obj = (Toode)var2.next();
            if (obj.getKood().equals(this.valiToodeCombo.getValue())) {
                valitud = obj;
                break;
            }
        }

        return valitud;
    }

    public Tootaja valitudTootaja() {
        Tootaja valitud = null;
        Iterator var2 = this.valitudProjekt().getTootajad().iterator();

        while(var2.hasNext()) {
            Tootaja obj = (Tootaja)var2.next();
            if (obj.getNimi().equals(this.valiTootajaCombo.getValue())) {
                valitud = obj;
                break;
            }
        }

        return valitud;
    }

    public void taidaValjad() {
        Toode valitud = this.valitudToode();
        this.tooteKoodMText.setText(valitud.getKood());
        this.kogusMText.setText(String.valueOf(valitud.getKogus()));
        this.saabumineMText.setText(valitud.getSaabumiseKuupaev());
        this.kohalMText.setText(valitud.getKohal());
        this.makseTahtaegMText.setText(valitud.getMakseTahtaeg());
        this.makstudMText.setText(valitud.getMakstud());
        this.hindMText.setText(String.valueOf(valitud.getHind()));
    }

    @FXML
    public void lisaTootajaButtonClicked() {
        if(valitudProjekt() != null) {

            Projekt valitud = this.valitudProjekt();
            if(!this.tootajaNimiText.getText().replace(" ", "").isEmpty()) {
                valitud.addTootaja(new Tootaja(this.tootajaNimiText.getText()));
                this.tootajaNimiText.setText("");

                try {
                    this.dao.salvesta();
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
            }
            else{
                Dialoog alert = new Dialoog(Alert.AlertType.ERROR);
                alert.MakeDialog("Veateade!", "Viga tootaja lisamisel!", "Viga esines, sest sisestatud nimi puudus. Sisestage korrektne nimi ja proovige uuesti.");
            }
        }
        else{
            Dialoog alert = new Dialoog(Alert.AlertType.ERROR);
            alert.MakeDialog("Veateade!", "Viga tootaja lisamisel!", "Viga esines, sest projekt on valimata. Valige projekt ja proovige uuest.");
        }
    }

    @FXML
    public void lisaToodeButtonClicked() {
        Projekt valitud = this.valitudProjekt();
        valitud.addToode(new Toode(this.tooteKoodLText.getText(), Integer.parseInt(this.kogusLText.getText())
                                    , this.kohalLText.getText()
                                    , this.saabumineLText.getText()
                                    , this.makseTahtaegLText.getText()
                                    , this.makstudLText.getText()
                                    , Double.parseDouble(this.hindLText.getText())));

        lisaToodetValjadReset();

        try {
            this.dao.salvesta();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void muudaToodeButtonClicked() {
        if(valitudToode() != null) {
            Toode valitudToode = this.valitudToode();
            valitudToode.setKood(this.tooteKoodMText.getText());
            valitudToode.setKogus(Integer.parseInt(this.kogusMText.getText()));
            valitudToode.setSaabumiseKuupaev(this.saabumineMText.getText());
            valitudToode.setKohal(this.kohalMText.getText());
            valitudToode.setHind(Double.parseDouble(this.hindMText.getText()));
            muudaToodetValjadReset();
            try {
                this.dao.salvesta();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }
        else{
            Dialoog alert = new Dialoog(Alert.AlertType.ERROR);
            alert.MakeDialog("Veateade!", "Viga toote muutmisel", "Viga esines, sest toode on valimata. Valige toode ja proovige uuest.");
        }
    }

    @FXML
    public void muudaToodetValjadReset(){
        this.tooteKoodMText.setText("");
        this.kogusMText.setText("");
        this.saabumineMText.setText("");
        this.kohalMText.setText("");
        this.makseTahtaegMText.setText("");
        this.makstudMText.setText("");
        this.hindMText.setText("");
    }

    @FXML
    public void lisaToodetValjadReset(){
        this.tooteKoodLText.setText("");
        this.kogusLText.setText("");
        this.saabumineLText.setText("");
        this.kohalLText.setText("");
        this.makseTahtaegLText.setText("");
        this.makstudLText.setText("");
        this.hindLText.setText("");
    }


    @FXML
    public void lisaTunnidButtonClicked() {
        Tootaja valitud = this.valitudTootaja();
        valitud.addTunnid(new Tund(Integer.parseInt(this.tooTunnidText.getText()), this.tooKuupaevText.getText()));
        this.tooTunnidText.setText("");
        this.tooKuupaevText.setText("");

        try {
            this.dao.salvesta();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void lisaLogiButtonClicked() {
        Projekt valitud = this.valitudProjekt();
        valitud.addLogi(new Logi(this.logiText.getText()));
        this.logiText.setText("");

        try {
            this.dao.salvesta();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }
}
