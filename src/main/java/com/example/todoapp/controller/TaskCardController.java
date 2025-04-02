package com.example.todoapp.controller;

import com.example.todoapp.dto.TaskDTO;
import com.example.todoapp.managers.TaskList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskCardController {

    public Label taskStatus;

    public Label taskTimeStamp;

    public Label taskName;

    public String taskId;
    private TaskList taskList;
    private TodoController tdController;


    public void handleViewTask(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todoapp/task_view_dialog.fxml"));
            AnchorPane viewTaskDialog = loader.load();
            TaskViewDialogController tvdController = loader.getController();

            String taskDialogCss = this.getClass().getResource("/com/example/todoapp/viewtaskstyles.css").toExternalForm();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(taskName.getText());
            dialogStage.initModality(Modality.APPLICATION_MODAL); // blocca l'app sotto la pop up window
            Scene viewTaskDialogScene = new Scene(viewTaskDialog);

            tvdController.setTaskDetails(taskList.loadTaskById(taskId),this);
            viewTaskDialogScene.getStylesheets().add(taskDialogCss);
            dialogStage.setScene(viewTaskDialogScene);
            dialogStage.setResizable(false);
            dialogStage.showAndWait(); //aspetta le interazioni

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void setTaskDetails (String name, LocalDateTime timeStamp, String status, String taskId, TaskList taskList, TodoController tdController){

        taskName.setText(name);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a, dd.MM.yyyy");
        taskTimeStamp.setText(timeStamp.format(formatter));

        taskStatus.setText(status);
        applyStatusColor(status);

        this.taskId = taskId;
        this.taskList = taskList;

        this.tdController = tdController;

    }

    public void applyStatusColor (String status){

        switch (status.toLowerCase()) {
            case "todo":
                taskStatus.setStyle("-fx-text-fill : gray;");
                break;
            case "inprogress":
                taskStatus.setStyle("-fx-text-fill : orange;");
                break;
            case "completed":
                taskStatus.setStyle("-fx-text-fill : green;");
                break;
            default:
                taskStatus.setStyle("-fx-text-fill : black;");
        }

    }

    public void setTaskList(TaskList taskList) {

        this.taskList = taskList;

    }

    public void updateTask (TaskDTO task){

        taskList.updateTask(task);
        taskName.setText(task.getTitle());
        taskStatus.setText(task.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a, dd.MM.yyyy");
        taskTimeStamp.setText(task.getDateTime().format(formatter));
        applyStatusColor(task.getStatus());

        tdController.redrawTasksList();
        tdController.saveList();

    }

    protected void deleteTask(TaskDTO task){

        taskList.removeTask(task);
        tdController.redrawTasksList();
        tdController.saveList();

    }


}
