package ee.erp.haldur.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ee.erp.haldur.DAO.projektideHaldusDAO;
import ee.erp.haldur.Data.Projekt;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class uusProjectController implements Initializable {
    private projektideHaldusDAO dao;
    @FXML
    private TextField nimiField;
    @FXML
    private TextField tahtaegField;
    @FXML
    private TextField midagiField;
    @FXML
    private Button uusProjektButton;
    @FXML
    private Button salvestaButton;
    @FXML
    private Button araSalvestaButton;

    public uusProjectController(projektideHaldusDAO dao) {
        this.dao = dao;
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.disableFields();
    }

    @FXML
    protected void uusProjektButtonClicked() {
        this.nimiField.setDisable(false);
        this.tahtaegField.setDisable(false);
        this.midagiField.setDisable(false);
    }

    @FXML
    protected void salvestaButtonClicked() {
        Projekt uusProjekt = new Projekt(this.nimiField.getText(), this.tahtaegField.getText());
        this.dao.getProjektid().add(uusProjekt);
        this.resetFields();
        this.disableFields();
    }

    @FXML
    protected void araSalvestaButtonClicked() {
        this.resetFields();
        this.disableFields();
    }

    private void resetFields() {
        this.nimiField.setText("");
        this.tahtaegField.setText("");
        this.midagiField.setText("");
    }

    private void disableFields() {
        this.nimiField.setDisable(true);
        this.tahtaegField.setDisable(true);
        this.midagiField.setDisable(true);
    }
}

