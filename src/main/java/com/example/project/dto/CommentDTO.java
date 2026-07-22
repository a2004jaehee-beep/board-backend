package com.example.project.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long commentId;

    // 댓글이 속한 게시글 번호
    private Long boardId;

    // 댓글 내용
    private String content;

    // 작성자
    private String writer;

    // 등록일
    private LocalDateTime regDate;
}
