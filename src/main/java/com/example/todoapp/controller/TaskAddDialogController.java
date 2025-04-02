package com.example.todoapp.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskAddDialogController {
    public MFXTextField taskTitleField;
    public TextArea taskDescriptionField;

    private TodoController mainController;

    public void setMainController(TodoController mainController){
        this.mainController = mainController;
    }

    public void handleCancel(ActionEvent actionEvent) {

        closeDialog();
    }

    public void handleSubmit(ActionEvent actionEvent) {

        if(!taskTitleField.getText().isEmpty()){
            mainController.addTaskFromDialog(taskTitleField.getText(), taskDescriptionField.getText());
            closeDialog();
        }else {
            taskTitleField.requestFocus();
            taskTitleField.setPromptText("Title is required before submit");
            taskTitleField.setStyle(" -fx-border-color : red;");
        }

    }

    private void closeDialog(){

        Stage stage = (Stage) taskDescriptionField.getScene().getWindow();
        stage.close();
    }


}
