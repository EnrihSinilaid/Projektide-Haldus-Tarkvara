package ee.erp.haldur.ui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import ee.erp.haldur.DAO.projektideHaldusDAO;
import ee.erp.haldur.Data.Projekt;
import ee.erp.haldur.Data.Toode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class uusProjectController implements Initializable {
    private projektideHaldusDAO dao;
    @FXML
    private TextField nimiField;
    @FXML
    private TextField tahtaegField;
    @FXML
    private TextField haldurField;
    @FXML
    private TextField nimiMField;
    @FXML
    private TextField tahtaegMField;
    @FXML
    private TextField haldurMField;
    @FXML
    private Button uusProjektButton;
    @FXML
    private Button salvestaButton;
    @FXML
    private Button araSalvestaButton;
    @FXML
    private Button muudaProjektiButton;
    @FXML
    private ComboBox valiProjektCombo;

    public uusProjectController(projektideHaldusDAO dao) {
        this.dao = dao;
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.disableFields(2);
        this.valiProjektCombo.showingProperty().addListener((observable, wasShowing, isNowShowing) -> {
            this.populeeriProjektid();
        });
        this.valiProjektCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                taidaValjad();
            }
        });
    }

    @FXML
    protected void uusProjektButtonClicked() {
        this.nimiField.setDisable(false);
        this.tahtaegField.setDisable(false);
        this.haldurField.setDisable(false);
    }

    @FXML
    protected void salvestaButtonClicked() {
        Projekt uusProjekt = new Projekt(this.nimiField.getText(), this.tahtaegField.getText(), this.haldurField.getText());
        this.dao.getProjektid().add(uusProjekt);
        this.resetFields(0);
        this.disableFields(0);
    }

    @FXML
    protected void araSalvestaButtonClicked() {
        this.resetFields(2);
        this.disableFields(2);
    }

    @FXML
    protected void muudaProjektiButtonClicked() {
        Projekt valitud = this.valitudProjekt();
        valitud.setProjektiNimi(nimiMField.getText());
        valitud.setTahtaeg(tahtaegMField.getText());
        valitud.setHaldur(haldurMField.getText());
        this.resetFields(1);
        this.disableFields(1);
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

    public void taidaValjad() {
        Projekt valitud = this.valitudProjekt();
        this.nimiMField.setText(valitud.getProjektiNimi());
        this.tahtaegMField.setText(valitud.getTahtaeg());
        this.haldurMField.setText(valitud.getHaldur());
        this.nimiMField.setDisable(false);
        this.tahtaegMField.setDisable(false);
        this.haldurMField.setDisable(false);
    }

    private void resetFields(int tuup) {
        if(tuup == 0){
            this.nimiField.setText("");
            this.tahtaegField.setText("");
            this.haldurField.setText("");
        }
        else if(tuup == 1){
            this.nimiMField.setText("");
            this.tahtaegMField.setText("");
            this.haldurMField.setText("");
        }
        else {
            this.nimiField.setText("");
            this.tahtaegField.setText("");
            this.haldurField.setText("");
            this.nimiMField.setText("");
            this.tahtaegMField.setText("");
            this.haldurMField.setText("");
        }
    }

    private void disableFields(int tuup) {
        if(tuup == 0){
            this.nimiField.setDisable(true);
            this.tahtaegField.setDisable(true);
            this.haldurField.setDisable(true);
        }
        else if(tuup == 1){
            this.nimiMField.setDisable(true);
            this.tahtaegMField.setDisable(true);
            this.haldurMField.setDisable(true);
        }
        else {
            this.nimiField.setDisable(true);
            this.tahtaegField.setDisable(true);
            this.haldurField.setDisable(true);
            this.nimiMField.setDisable(true);
            this.tahtaegMField.setDisable(true);
            this.haldurMField.setDisable(true);
        }
    }
}

