package com.example.demo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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

    //게시판 리스트를 가져오는 것.
    /*
    @GetMapping("/boards")
    ResponseEntity boards(@Param("page") int page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.by("id").descending());
        Page<Board> boards = boardRepository.findAll(pageable);
        //List<Board> boards = boardRepository.findAll();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }
    */

    //param을 지정하지 않았지만 자체적으로 처리해주었다!
    @GetMapping("/boards")
    ResponseEntity boards(@PageableDefault(size = 3, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        //Page<Board> boards = boardRepository.findAll(pageable);
        return new ResponseEntity<>(boards, HttpStatus.OK);
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


        //Optional<Board> optionalBoard = boardRepository.findById(id);
        Optional<Board> optionalBoard = boardRepository.findByDeletedAndId(false, id);

        //람다 표현식 함수를 매개변수로 넣을 수 있다.
        optionalBoard.orElseThrow(() -> new BadRequestException("데이터가 없습니다"));
        //if(optionalBoard.isPresent()) {
            return new ResponseEntity<>(modelMapper.map(boardRepository.findById(id).get(), BoardDto.class), HttpStatus.OK);
        //}
        //예외 처리 로직
        //else {
        //    throw new BadRequestException("데이터가 없습니다");

            //ErrorResponse errorResponse = new ErrorResponse("잘못된 요청", "데이터를 찾을 수 없습니다.");
            //return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);


            //return new ResponseEntity<>("데이터가 없습니다.", HttpStatus.BAD_REQUEST);
            //throw new NoSuchElementException();
        //}
    }

    //포스트 방식
    /*
    @PostMapping("/board")
    ResponseEntity createBoard(@Valid @RequestBody Board board) {
        //boardRepository.save(board);
        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);
    }
    */

    @PostMapping("/board")
    ResponseEntity createBoard(@Valid @RequestBody Board board, BindingResult bindingResult) {
        //boardRepository.save(board);
        if(bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                builder.append("[");
                builder.append(fieldError.getField());
                builder.append("](은)는 ");
                builder.append(fieldError.getDefaultMessage());
                builder.append(" 입력된 값 : [");
                builder.append(fieldError.getRejectedValue());
                builder.append("]");
            }
            throw new BadRequestException(builder.toString());
            //throw new BadRequestException("요청된 데이터가 잘못되었습니다");
        }
        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);
    }

    //exception 종류 처리 핸들러
    //이벤트 핸들러
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity badRequestExceptionHandler(BadRequestException e) {
       ErrorResponse errorResponse = new ErrorResponse("잘못된 요청", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
