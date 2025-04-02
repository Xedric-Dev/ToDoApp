package com.example.todoapp.managers;

import com.example.todoapp.dto.TaskDTO;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<TaskDTO> taskList;

    public TaskList (){
        taskList = new ArrayList<>();
    }


    public List<TaskDTO> getTaskList() {
        return taskList;
    }

    public void addTask(TaskDTO task){
        this.taskList.add(task);
        sortTasksByStatus();
    }

    public void removeTask(TaskDTO task){
        this.taskList.removeIf(t -> t.equals(task));
    }

    public TaskDTO loadTaskById(String id){

        for (TaskDTO t : taskList){
            if (t.getId().equals(id)){
                return t;
            }
        }
        return null;
    }

    public void updateTask(TaskDTO task){

        for( int i = 0; i < taskList.size(); i++){
            TaskDTO currentTask = taskList.get(i);
            if (currentTask.getId().equals(task.getId())){

                currentTask.setTitle(task.getTitle());
                currentTask.setDescription(task.getDescription());
                currentTask.setDateTime(LocalDateTime.now());
                currentTask.setStatus(task.getStatus());

                currentTask.setComments(task.getComments());

                break;
            }
        }
        sortTasksByStatus();
    }

    private void sortTasksByStatus (){
        taskList.sort(Comparator.comparingInt(
                t -> {

                    switch (t.getStatus()){

                        case "ToDo":
                            return 1;
                        case "InProgress":
                            return 2;
                        case "Completed" :
                            return 3;
                        default:
                            return 4;

                    }
                }
        ));
    }

}
