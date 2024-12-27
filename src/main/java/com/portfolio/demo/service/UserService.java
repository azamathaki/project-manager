package com.portfolio.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.demo.model.Task;
import com.portfolio.demo.model.User;
import com.portfolio.demo.repository.TaskRepository;
import com.portfolio.demo.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    public User createUser(User user){
        if (user == null){
            throw new RuntimeException("user = null (UserService.java)");
        }
        return userRepository.save(user);
    }

    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }

    // this method only can be used by owner
    public String createTaskForEmployee(String fromUser, String toUser, Task task) {
        User owner = userRepository.findByUsername(fromUser);
        User user = userRepository.findByUsername(toUser);

        if (owner.getRole().toString().equals("EMPLOYEE")){
            return fromUser + " cannot create task for " + toUser;
        }

        if (user == null){
            return "user = null (UserService.java)";
        }

        if (task == null){
            return "task = null (UserService.java)";
        }
        

        owner.addTask(task, user);
        user.addTaskForUser(task, owner);

        taskRepository.save(task);
        userRepository.save(user);
        userRepository.save(owner);

        return "task created for " + toUser;
        
    }

    public User createPostForEmployer(String username, Long givenTaskId, Task postToGivenTask) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }
    
        if (postToGivenTask == null) {
            throw new RuntimeException("Post cannot be null.");
        }
    
        Task givenTask = user.findGivenPostById(user, givenTaskId);
        if (givenTask == null) {
            throw new RuntimeException("Task with ID " + givenTaskId + " not found for user: " + username);
        }
    
        if (!givenTask.getId().equals(givenTaskId)) {
            throw new RuntimeException("Task ID mismatch: Expected " + givenTaskId + ", Found " + givenTask.getId());
        }
    
        User owner = givenTask.getCreatedBy();
        if (owner == null) {
            throw new RuntimeException("Task does not have a valid owner.");
        }
    
        postToGivenTask.setCreatedBy(owner);
        postToGivenTask.setAssignedTo(user);
        user.addPost(postToGivenTask, owner);
    
        taskRepository.save(postToGivenTask);
    
        return user;
    }
    

    public List<Task> getTasksOf(String username){
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new RuntimeException("user = null (UserService.java)");
        }

        return user.getTasks();

    }


    public String deleteTask(String username, Long taskId){


        
        User user = userRepository.findByUsername(username);

        if (user == null){
            return "user == null (deleteTask(...))";
        }

        Task task = user.findGivenTaskById(user,taskId);

        if (task == null){
            return username + " cannot delete this task!";
        }

        user.removeTask(task);
        taskRepository.delete(task);
        userRepository.save(user);
        return "task id: " + taskId + " is deleted successfully";

    }


}
