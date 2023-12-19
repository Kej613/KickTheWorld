package com.example.Board.Service;

import com.example.Board.Dto.BoardDto;
import com.example.Board.Dto.PageRequestDTO;
import com.example.Board.Dto.PageResultDTO;
import com.example.Board.Entity.Board;

import java.util.List;

public interface BoardService {

    Long register(BoardDto dto);

    PageResultDTO<BoardDto, Board> getList(PageRequestDTO requestDTO);

    //게시글 상세조회
    BoardDto read(Long bno);

    //아래 매퍼(dto->entity) 메서드 추가
    default Board dtoToEntity(BoardDto dto) {

        if (dto == null) {  //null값이 존재할때
            return null;
        }

        Board entity = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .category(dto.getCategory())
                .build();
        return entity;
    }

    default BoardDto EntityToDto(Board entity) {
        if (entity == null) {
            return null;
        }

        BoardDto dto = BoardDto.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .category(entity.getCategory())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    void remove(Long bno);
    void modify(BoardDto dto);


    //조회수
//    @Transactional
//    void increaseViews(Long bno);

    Long updateViewCount(Long bno);

}
