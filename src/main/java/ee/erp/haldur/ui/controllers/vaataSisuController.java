package ee.erp.haldur.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import ee.erp.haldur.DAO.projektideHaldusDAO;
import ee.erp.haldur.Data.*;
import ee.erp.haldur.ui.controllers.helper.ActionButtonTableCell;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableColumn<Tund, Tund> TEemalda = new TableColumn<>("Eemalda");
    @FXML
    private TableColumn<Toode, Toode> TEemaldaHind = new TableColumn<>("Eemalda");

    List<Tund> tunnid = new ArrayList<>();
    Tootaja valitudTootaja;
    Projekt valitudProjekt;

    public vaataSisuController(projektideHaldusDAO dao) {
        this.dao = dao;
    }

    public void initialize(URL location, ResourceBundle resources) {

        TEemalda.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        TEemalda.setCellFactory(param -> new TableCell<Tund, Tund>(){
            private final Button deleteButton1 = new Button("X");

            @Override
            protected void updateItem(Tund tund, boolean empty) {
                super.updateItem(tund, empty);

                if (tund == null) {
                    setGraphic(null);
                    return;
                }

                valitudProjekt = valitudProjekt();

                setGraphic(deleteButton1);
                deleteButton1.setOnAction(event -> {
                    valitudTootaja.getTund().remove(tund);
                    taidaTunnid(valitudTootaja.getTund());
                    TabTootajad.refresh();
                    try {
                        dao.salvesta();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        });

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
                valitudTootaja = (Tootaja)this.TabTootajad.getSelectionModel().getSelectedItem();
                tunnid = ((Tootaja)this.TabTootajad.getSelectionModel().getSelectedItem()).getTund();
                this.taidaTunnid(tunnid);
            }

        });
        this.TabLogid.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                String[] logi = new String[]{this.TabLogid.getSelectionModel().getSelectedItem().getLogi(), this.TabLogid.getSelectionModel().getSelectedItem().getKuupaev()};
                this.kuvaLogi(logi);
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

    public void taidaTabel() {
        Projekt valitud = this.valitudProjekt();
        taidaTooted(valitud.getTooted());
        taidaTootajad(valitud.getTootajad());
        taidaLogid(valitud.getLogid());
    }

    @FXML
    public void taidaTooted(List<Toode> tooted){
        this.TabTooted.setItems(FXCollections.observableArrayList(tooted));
        this.TabTooted.refresh();
    }

    @FXML
    public void taidaTootajad(List<Tootaja> tootajad){
        this.TabTootajad.setItems(FXCollections.observableArrayList(tootajad));
        this.TabTootajad.refresh();
    }

    @FXML
    public void taidaLogid(List<Logi> logid){
        this.TabLogid.setItems(FXCollections.observableArrayList(logid));
        this.TabLogid.refresh();
    }

    @FXML
    public void taidaTunnid(List<Tund> tund) {
        this.TabTunnid.setItems(FXCollections.observableArrayList(tund));
        this.TabTunnid.refresh();
    }

    private void kuvaLogi(String[] logi) {
        Dialoog kuva = new Dialoog(Alert.AlertType.INFORMATION);
        kuva.MakeDialog("Logi", "Logi esitatud: " + logi[1], logi[0]);
    }
}

