package ee.erp.haldur.ui.controllers;

import javafx.scene.control.Alert;

public class Dialoog {
    private Alert alert;

    public Dialoog(Alert.AlertType tuup) {
        this.alert = new Alert(tuup);
    }

    public void MakeDialog(String title, String header, String message){
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
