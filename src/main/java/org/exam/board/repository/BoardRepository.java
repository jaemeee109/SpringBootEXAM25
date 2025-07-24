package org.exam.board.repository;

import jakarta.persistence.Entity;
import org.exam.board.domain.Board;
import org.exam.board.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> , BoardSearch {


    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);

    @Query("select b from Board b where  b.title like concat('%',:keyword,'%')")
    Page<Board> findKeyword(String keyword, Pageable pageable);

    @Query(value ="select now()", nativeQuery = true)
    String getTime();

    @EntityGraph(attributePaths = {"imageSet"}) // 지연로딩이지만 같이 로딩되게 하는 값
    @Query("select b from Board b where b.bno = :bno")
    Optional<Board> findByIdWithImage(Long bno);


} // BoardRepository 종료
