package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

    
} 
