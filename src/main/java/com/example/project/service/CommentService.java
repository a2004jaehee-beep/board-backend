package com.example.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.dto.CommentDTO;
import com.example.project.entity.Board;
import com.example.project.entity.Comment;
import com.example.project.repository.BoardRepository;
import com.example.project.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 게시글별 댓글 조회
    public List<CommentDTO> getComments(Long boardId) {

        List<Comment> comments = commentRepository.findByBoard_BoardId(boardId);

        return comments.stream()
                .map(comment -> CommentDTO.builder()
                        .commentId(comment.getCommentId())
                        .boardId(comment.getBoard().getBoardId())
                        .content(comment.getContent())
                        .writer(comment.getWriter())
                        .regDate(comment.getRegDate())
                        .build())
                .toList();
    }

    // 댓글 등록
    public CommentDTO saveComment(CommentDTO commentDTO) {

        Board board = boardRepository.findById(commentDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .board(board)
                .content(commentDTO.getContent())
                .writer(commentDTO.getWriter())
                .build();

        Comment savedComment = commentRepository.save(comment);

        return CommentDTO.builder()
                .commentId(savedComment.getCommentId())
                .boardId(savedComment.getBoard().getBoardId())
                .content(savedComment.getContent())
                .writer(savedComment.getWriter())
                .regDate(savedComment.getRegDate())
                .build();
    }

    // 댓글 수정
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        comment.setContent(commentDTO.getContent());
        comment.setWriter(commentDTO.getWriter());

        Comment updatedComment = commentRepository.save(comment);

        return CommentDTO.builder()
                .commentId(updatedComment.getCommentId())
                .boardId(updatedComment.getBoard().getBoardId())
                .content(updatedComment.getContent())
                .writer(updatedComment.getWriter())
                .regDate(updatedComment.getRegDate())
                .build();
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {

        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }

        commentRepository.deleteById(commentId);
    }
}