package com.example.Board.Service;

import com.example.Board.Dto.BoardDto;
import com.example.Board.Dto.PageRequestDTO;
import com.example.Board.Dto.PageResultDTO;
import com.example.Board.Entity.Board;
import com.example.Board.Entity.QBoard;
import com.example.Board.Repository.BoardRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDto dto) {

        Board entity = dtoToEntity(dto);

        boardRepository.save(entity);
        return entity.getBno();
    }

    @Override
    public PageResultDTO<BoardDto, Board> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO);  //검색 조건을 이용하여 레포지토리 접근
        Page<Board> result = boardRepository.findAll(booleanBuilder, pageable);

        Function<Board, BoardDto> fn = (entity -> EntityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    //게시글 상세조회
    @Override
    public BoardDto read(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);

        return result.isPresent() ? EntityToDto(result.get()) : null;
    }

    //게시글 삭제
    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    //게시글 수정
    @Override
    public void modify(BoardDto dto) {
        Optional<Board> result = boardRepository.findById(dto.getBno());

        if(result.isPresent()) {
            Board entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            entity.changeCategory(dto.getCategory());

            boardRepository.save(entity);
        }
    }
    //추가
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();

         QBoard qBoard = QBoard.board;

        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qBoard.bno.gt(0L);
        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")) {
            conditionBuilder.or(qBoard.title.contains(keyword));
        }
        if(type.contains("c")) {
            conditionBuilder.or(qBoard.content.contains(keyword));
        }
        if(type.contains("w")) {
            conditionBuilder.or(qBoard.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

//    //조회수
//    @Transactional
//    @Override
//    public void increaseViews(Long bno) {
//        Board board = boardRepository.findById(bno)
//                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다: " + bno));
//
//        board.increaseViews(); // 게시글 엔터티에서 조회수 증가 메서드 호출
//
//        // 이후 필요에 따라 다른 로직 수행
//
//        boardRepository.save(board); // 변경된 내용 저장
//    }

    @Transactional
    @Override
    public Long updateViewCount(Long bno) {
        Optional<Board> optionalBoard = boardRepository.findById(bno);
        optionalBoard.ifPresent(board -> {
            board.increaseViewCount(); // Board 엔티티에 increaseViewCount 메서드를 구현하세요
            boardRepository.save(board);
        });
        return bno;
    }

}