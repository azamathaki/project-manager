package com.portfolio.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.demo.model.Task;
import com.portfolio.demo.model.User;
import com.portfolio.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    //creating a user
    @PostMapping("/users")
    public User creatingUser(@RequestBody User user){
        return service.createUser(user);
    }

    // get a user
    @GetMapping("/users/{username}")
    public User gettingUser(@PathVariable("username") String username){
        return service.findUser(username);
    }

    // get all tasks of an user
    @GetMapping("/users/{username}/tasks")
    public List<Task> gettingTasks(@PathVariable("username") String username){
        return service.getTasksOf(username);
    }


    // giving a task to an employee
    @PutMapping("/users/{fromUser}/tasks/to/{toUser}")
    public String creatingTask(@PathVariable("fromUser") String fromUser,
                            @PathVariable("toUser") String toUser,
                            @RequestBody Task task)
    {
        return service.createTaskForEmployee(fromUser, toUser, task);
    }

    // giving a reply post to given task
    @PutMapping("/users/{username}/tasks/{givenTaskId}/posts")
    public User creatingPost(@PathVariable("username") String username,
    @PathVariable("givenTaskId") Long givenTaskId,
    @RequestBody Task task){
        
        return service.createPostForEmployer(username, givenTaskId, task);
    }


    
    @DeleteMapping("/users/{username}/tasks/{taskId}/delete")
    public String deletingTask(@PathVariable("username") String username,
    @PathVariable("taskId") Long taskId){
        return service.deleteTask(username, taskId);
    }

}
