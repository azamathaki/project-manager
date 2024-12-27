package com.portfolio.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.demo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

    void deleteById(long id);
}
