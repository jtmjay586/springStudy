package com.example.demo.board.infra;

import com.example.demo.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Optional<Board> findByDeletedAndId(boolean deleted, int id);
    Optional<Board> findByDeletedOrId(boolean deleted, int id);

}
