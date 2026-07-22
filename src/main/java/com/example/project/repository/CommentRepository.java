package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 게시글 번호(boardId)로 댓글 목록 조회
    List<Comment> findByBoard_BoardId(Long boardId);
    // Comment -> Board -> boardId로 조회하기 위한 JPA 메소드 

}
