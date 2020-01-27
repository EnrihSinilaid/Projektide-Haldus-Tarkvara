package ee.erp.haldur.ui.controllers;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

public class vaataSisuController implements Initializable {
    private projektideHaldusDAO dao;
    @FXML
    private ComboBox ProjektiValija;
    @FXML
    private TableView<Toode> TabTooted = new TableView();
    @FXML
    private TableView<Tootaja> TabTootajad;
    @FXML
    private TableView<Tund> TabTunnid;
    @FXML
    private TableView<Logi> TabLogid;

    public vaataSisuController(projektideHaldusDAO dao) {
        this.dao = dao;
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.ProjektiValija.showingProperty().addListener((observable, wasShowing, isNowShowing) -> {
            this.populeeriProjektid();
        });
        this.ProjektiValija.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                vaataSisuController.this.taidaTabel();
            }
        });
        this.TabTootajad.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                List<Tund> tund = ((Tootaja)this.TabTootajad.getSelectionModel().getSelectedItem()).getTund();
                this.taidaTunnid(tund);
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

        this.ProjektiValija.setItems(FXCollections.observableArrayList(projektiNimed));
    }

    public Projekt valitudProjekt() {
        Projekt valitud = null;
        Iterator var2 = this.dao.getProjektid().iterator();

        while(var2.hasNext()) {
            Projekt obj = (Projekt)var2.next();
            if (obj.getProjektiNimi().equals(this.ProjektiValija.getValue())) {
                valitud = obj;
                break;
            }
        }

        return valitud;
    }

    @FXML
    public void taidaTabel() {
        Projekt valitud = this.valitudProjekt();
        this.TabTooted.setItems(FXCollections.observableArrayList(valitud.getTooted()));
        this.TabTootajad.setItems(FXCollections.observableArrayList(valitud.getTootajad()));
        this.TabLogid.setItems(FXCollections.observableArrayList(valitud.getLogid()));
        this.TabTooted.refresh();
        this.TabTootajad.refresh();
        this.TabLogid.refresh();
    }

    @FXML
    public void taidaTunnid(List<Tund> tund) {
        this.TabTunnid.setItems(FXCollections.observableArrayList(tund));
        this.TabTunnid.refresh();
    }
}

