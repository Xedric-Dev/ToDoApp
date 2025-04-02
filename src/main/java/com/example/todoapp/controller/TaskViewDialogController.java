package com.example.todoapp.controller;

import com.example.todoapp.dto.TaskDTO;
import com.example.todoapp.managers.TaskList;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class TaskViewDialogController {
    public MFXTextField taskTitleField;
    public MFXTextField descriptionField;
    public MFXComboBox statusComboBox;
    public MFXTextField commentField;
    public VBox lastCommentLabel;

    private TaskDTO task;
    private TaskCardController taskCardController;

    public void initialize(){

        taskCardController = new TaskCardController();

    }

    public void setTaskDetails(TaskDTO task, TaskCardController taskCardController){

        this.task = task;
        this.taskCardController = taskCardController;

        taskTitleField.setText(task.getTitle());
        descriptionField.setText(task.getDescription());

        statusComboBox.getItems().clear();
        statusComboBox.getItems().addAll("ToDo","InProgress","Completed");

        Platform.runLater(()->{
            statusComboBox.setValue(task.getStatus());
        });

        task.getComments().forEach(c->{
            displayComments(c);
        });

    }

    private void displayComments(String comment){

        Text commentLabel = new Text(comment);
        commentLabel.setStyle("-fx-padding : 3px;");
        lastCommentLabel.getChildren().add(commentLabel);

    }

    public void handleAddComment(ActionEvent actionEvent) {

        String comment = commentField.getText();
        if(!comment.isEmpty()){
            task.addComment(comment);
            displayComments(comment);
            commentField.clear();
        }

    }

    public void handleCancel(ActionEvent actionEvent) {

        closeDialog();

    }

    private void closeDialog(){

        Stage stage = (Stage) taskTitleField.getScene().getWindow();
        stage.close();

    }

    public void handleDeleteTask(ActionEvent actionEvent) {

        taskCardController.deleteTask(task);
        closeDialog();

    }

    public void handleUpdateTask(ActionEvent actionEvent) {

        task.setTitle(taskTitleField.getText());
        task.setDescription(descriptionField.getText());
        task.setStatus((String)statusComboBox.getValue());

        taskCardController.updateTask(task);
        closeDialog();

    }
}
