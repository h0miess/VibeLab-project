package ru.vibelab.taskmanager.mappers;

import ru.vibelab.taskmanager.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vibelab.taskmanager.config.PropertiesConfig;
import ru.vibelab.taskmanager.models.Board;
import ru.vibelab.taskmanager.models.ColumnEntity;
import ru.vibelab.taskmanager.models.UserInBoard;
import ru.vibelab.taskmanager.models.requests.BoardRequest;
import ru.vibelab.taskmanager.models.responses.BoardResponse;
import ru.vibelab.taskmanager.models.responses.ColumnResponse;
import ru.vibelab.taskmanager.models.responses.UserInBoardResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateBoardRequest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardMapper {

    private final ColumnMapper columnMapper;
    private final PropertiesConfig propertiesConfig;

    public Board requestToModel(BoardRequest request, Picture picture) {
        return Board.builder()
                .title(request.getTitle().strip())
                .picture(picture)
                .lastUpdateDate(ZonedDateTime.now())
                .build();
    }

    public BoardResponse modelToResponse(Board board) {

        List<ColumnEntity> columns = board.getColumns();
        List<ColumnResponse> columnResponses = new ArrayList<>();
        if(columns != null) {
            for(ColumnEntity column : columns) {
                columnResponses.add(columnMapper.modelToResponse(column));
            }
        }

        columnResponses.sort(Comparator.comparingInt(ColumnResponse::getNumber));

        List<UserInBoard> users = board.getUsers();
        List<UserInBoardResponse> userInBoardResponses = new ArrayList<>();
        for(UserInBoard userInBoard : users) {
            userInBoardResponses.add(UserInBoardMapper.toResponse(userInBoard.getUser(), userInBoard.getRole()));
        }


        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .pictureLink(board.getPicture().getLink())
                .lastUpdateDate(board.getLastUpdateDate().
                        format(DateTimeFormatter.ofPattern(propertiesConfig.getDateTimePattern())))
                .columns(columnResponses)
                .users(userInBoardResponses)
                .build();
    }

    public void updateRequestToModel(Board board, UpdateBoardRequest updateRequest, Picture picture) {
        if(updateRequest.getTitle() != null) {
            board.setTitle(updateRequest.getTitle().strip());
        }

        if(updateRequest.getPictureLink() != null) {
            board.setPicture(picture);
        }

        board.setLastUpdateDate(ZonedDateTime.now());

    }
}
