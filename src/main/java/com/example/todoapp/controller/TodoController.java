package com.example.todoapp.controller;

import com.example.todoapp.dto.TaskDTO;
import com.example.todoapp.managers.TaskList;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TodoController {

    public VBox taskListVBox;
    public MFXComboBox statusComboBox;
    @FXML
    MFXTextField taskTitle;

    private TaskList taskList;

    public void initialize(){

        this.taskList = new TaskList();

        loadList();

        statusComboBox.getItems().addAll("All","ToDo", "InProgress", "Completed");

        statusComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                filterTasksByStatus(newValue);
            }

         }
        );

        displayOnInit(this.taskList);
    }

    private void displayOnInit(TaskList taskList){
        for (TaskDTO t :taskList.getTaskList()){
            displayTask(t);
        }
    }

    private void filterTasksByStatus(String status){

        taskListVBox.getChildren().clear();

        List<TaskDTO> filteredList;

        if("All".equals(status)){
            filteredList = taskList.getTaskList();
        }else{
            filteredList = taskList.getTaskList().stream().filter(
                    taskDTO -> taskDTO.getStatus().equals(status)).collect(Collectors.toList());
        }

        for (TaskDTO t : filteredList){
            displayTask(t);
        }

    }

    public void handleAddTask() {

        showAddTaskDialog();
    }

    private void showAddTaskDialog(){
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todoapp/task_add_dialog.fxml"));
            AnchorPane addTaskDialog = loader.load();
            TaskAddDialogController tadController = loader.getController();

            tadController.setMainController(this);

            String taskDialogCss = this.getClass().getResource("/com/example/todoapp/addtaskstyles.css").toExternalForm();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Task");
            dialogStage.initModality(Modality.APPLICATION_MODAL); // blocca l'app sotto la pop up window
            Scene addTaskDialogScene = new Scene(addTaskDialog);
            addTaskDialogScene.getStylesheets().add(taskDialogCss);
            dialogStage.setScene(addTaskDialogScene);
            dialogStage.setResizable(false);
            dialogStage.showAndWait(); //aspetta le interazioni


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void addTaskFromDialog(String title, String description){
        addTask(title,description,LocalDateTime.now(),"ToDo");
    }

    private void addTask(String title, String description, LocalDateTime dateTime, String status){

        TaskDTO newTask = new TaskDTO(title, description, dateTime, status);
        taskList.addTask(newTask);
        redrawTasksList();
        saveList();

    }

    private void displayTask (TaskDTO task){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todoapp/task-card.fxml"));
            HBox taskCard = loader.load();
            TaskCardController tcController = loader.getController();

            tcController.setTaskDetails(task.getTitle(), task.getDateTime(), task.getStatus(), task.getId(), this.taskList, this);

            taskListVBox.getChildren().add(taskCard);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void redrawTasksList(){

        taskListVBox.getChildren().clear();
        for (TaskDTO t : taskList.getTaskList()){

            displayTask(t);

        }

        statusComboBox.setValue("All");
    }

    protected void saveList(){

        try (FileOutputStream fop = new FileOutputStream("src/main/resources/com/example/todoapp/taskList.ser");
                ObjectOutputStream objos = new ObjectOutputStream(fop)){

            objos.writeObject(taskList);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void loadList(){

        try (FileInputStream fip = new FileInputStream("src/main/resources/com/example/todoapp/taskList.ser");
             ObjectInputStream objis = new ObjectInputStream(fip)){

            taskList = (TaskList) objis.readObject();


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
