package com.example.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.BoardDTO;
import com.example.project.security.CustomUserDetails;
import com.example.project.service.BoardService;

import lombok.RequiredArgsConstructor;

// client Side Rendering 에서 백엔드 Controller는
// 모두 RestController로 구성한다.
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity <List<BoardDTO>> getBoards(){
        return ResponseEntity.ok(boardService.getBoards());
    
    }

    // 메롱ㅇㅇㅇ
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long boardId){
        return ResponseEntity.ok(boardService.getBoardById(boardId));
    }

    // 인증된 사용자만 글 작성이 가능하도록 권한 설정
    @PreAuthorize("isAuthenticated()")
    // @PreAuthorize("hasRole('ADMIN')") : ADMIN 사용자만 접근 가능 
    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(
        @RequestBody BoardDTO boardDTO,
        @AuthenticationPrincipal CustomUserDetails userDetails){
        // @AuthenticationPrincipal : 인증된 사용자 정보 객체 참조

        boardDTO.setWriter(userDetails.getAccount().getUsername());
        BoardDTO result = boardService.saveBoard(boardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardDTO> updateBoard(
        @PathVariable Long boardId,
        @RequestBody BoardDTO boardDTO
    ){
        return ResponseEntity
            .ok(boardService.updateBoard(boardId, boardDTO));

    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
        @PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
        }
    

}
