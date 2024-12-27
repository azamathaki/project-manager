package com.portfolio.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "createdBy", fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.EAGER)
    private List<Task> posts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getPosts() {
        return posts;
    }

    public void setPosts(List<Task> posts) {
        this.posts = posts;
    }


    public void addTask(Task task, User user){
        this.tasks.add(task);
        task.setCreatedBy(this);
        task.setAssignedTo(user);
    }

    public void addTaskForUser(Task task, User createdByUser){
        this.posts.add(task);
        task.setCreatedBy(createdByUser);
        task.setAssignedTo(this);
    }

    public void removeTask(Task task){
        if (task != null){
            task.setCreatedBy(null);
            task.setAssignedTo(null);
            tasks.remove(task);
        }
    }

    public void removePost(Task post){
        if (post != null){
            post.setCreatedBy(null);
            post.setAssignedTo(null);
            posts.remove(post);
        }
    }
    
    public void addPost(Task task, User belongUser){
        this.posts.add(task);
        task.setCreatedBy(this);
        task.setAssignedTo(belongUser);
    }

    public Task findGivenTaskById(User user, Long id){
        for (Task task: user.getTasks()){
            if (task.getId().equals(id)){
                return task;
            }
        }
        return null;
    }

    public Task findGivenPostById(User user, Long id){
        for (Task post: user.getPosts()){
            if (post.getId().equals(id)){
                return post;
            }
        }
        return null;
    }

}
