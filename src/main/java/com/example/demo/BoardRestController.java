package com.example.demo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

//controller와의 비교점
@RestController
public class BoardRestController {
/*

    @Autowired
    BoardRepository boardRepository;
*/
    final private BoardRepository boardRepository;
    final private ModelMapper modelMapper;

    @Autowired
    public BoardRestController(BoardRepository boardRepository, ModelMapper modelMapper) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }

    //가져올때
    //단일 게시판 select 로직
    @GetMapping("/board")
    ResponseEntity board(@Param("id") int id) {
        //Optional<Board> optionalBoard = boardRepository.findById(1); // optional 반환 객체
        //ModelMapper modelMapper = new ModelMapper();
        //optional 객체로 받아서 boardDto 객체형으로 받아서 보내준다.
        //즉 필요한 부분만 dto 객체로 만들어서 처리할 수 있다.
        //modelMapper.map(optionalBoard.get(), BoardDto.class);
        //optionalBoard.get();
        /*boardRepository.findAll();*/ //
        //return new ResponseEntity<>(modelMapper.map(optionalBoard.get(), BoardDto.class), HttpStatus.OK);


        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isPresent()) {
            return new ResponseEntity<>(modelMapper.map(boardRepository.findById(id).get(), BoardDto.class), HttpStatus.OK);
        }
        //예외 처리 로직
        else {
            ErrorResponse errorResponse = new ErrorResponse("잘못된 요청", "데이터를 찾을 수 없습니다.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            //return new ResponseEntity<>("데이터가 없습니다.", HttpStatus.BAD_REQUEST);
            //throw new NoSuchElementException();
        }
    }

    //포스트 방식
    @PostMapping("/board")
    ResponseEntity createBoard(@Valid @RequestBody Board board) {
        //boardRepository.save(board);
        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);
    }
}
