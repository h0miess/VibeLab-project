package ru.vibelab.taskmanager.services.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vibelab.taskmanager.exceptions.*;
import ru.vibelab.taskmanager.mappers.ColumnMapper;
import ru.vibelab.taskmanager.models.Board;
import ru.vibelab.taskmanager.models.ColumnEntity;
import ru.vibelab.taskmanager.models.requests.ColumnRequest;
import ru.vibelab.taskmanager.models.responses.ColumnResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateColumnRequest;
import ru.vibelab.taskmanager.repositories.BoardRepository;
import ru.vibelab.taskmanager.repositories.ColumnRepository;
import ru.vibelab.taskmanager.services.api.ColumnService;
import ru.vibelab.taskmanager.util.Validator;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final BoardAccessServiceImpl boardAccessService;
    private final ColumnMapper columnMapper;

    @Override
    @Transactional
    public ColumnResponse createColumn(@NotNull ColumnRequest request, @NotNull Long board_id, Principal principal) {

        Board board = boardRepository.findById(board_id).orElseThrow(() -> new BoardNotFoundException(board_id));

        if (!boardAccessService.checkAccessToBoard(board, principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        ColumnEntity column = columnMapper.requestToModel(request);
        column.setBoard(board);

        if (column.getBoard().getColumns() != null) {
            validateColumnTitle(request.getTitle());
        }

        if(column.getBoard().getColumns() != null) {
            Integer countOfColumns = column.getBoard().getColumns().size();
            column.setNumber(countOfColumns + 1);
        } else {
            column.setNumber(1);
        }

        columnRepository.save(column);
        updateBoardLastUpdateTime(board);

        return columnMapper.modelToResponse(column);
    }

    @Override
    @Transactional
    public ColumnResponse updateColumn(@NotNull Long column_id, @Nullable UpdateColumnRequest updateRequest, Principal principal) {

        ColumnEntity column = columnRepository.findById(column_id).orElseThrow(() -> new ColumnNotFoundException(column_id));

        Board board = column.getBoard();
        if (!boardAccessService.checkAccessToBoard(board, principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        if (updateRequest == null) {
            return columnMapper.modelToResponse(column);
        }

        if(updateRequest.getTitle() != null) {
            validateColumnTitle(updateRequest.getTitle());
        }

        if (updateRequest.getNumber() != null) {

            Integer countOfColumns = board.getColumns().size();
            Integer newNumber = updateRequest.getNumber();
            Integer currentNumber = column.getNumber();


            if (newNumber <= 0 || newNumber > countOfColumns) {

                throw new InvalidColumnNumberException();
            }

            updateNumberOtherColumns(currentNumber, newNumber, board.getColumns());

            column.setNumber(newNumber);
        }

        columnMapper.updateRequestToModel(column, updateRequest);

        columnRepository.save(column);
        updateBoardLastUpdateTime(board);

        return columnMapper.modelToResponse(column);
    }

    @Override
    @Transactional
    public void deleteColumn(@NotNull Long column_id, Principal principal) {

        ColumnEntity column = columnRepository.findById(column_id)
                .orElseThrow(() -> new ColumnNotFoundException(column_id));

        if (!boardAccessService.checkAccessToBoard(column.getBoard(), principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        if (!isLastColumnNumber(column)) {
            decrementNumberOfOtherColumnsOnDelete(column);
        }

        columnRepository.deleteById(column_id);
        updateBoardLastUpdateTime(column.getBoard());
    }

    private boolean isLastColumnNumber(ColumnEntity column) {

        if (column.getNumber() == column.getBoard().getColumns().size()) {
            return true;
        }

        return false;
    }

    private void decrementNumberOfOtherColumnsOnDelete(ColumnEntity column) {

        List<ColumnEntity> columns = column.getBoard().getColumns();
        columns.sort(Comparator.comparingInt(ColumnEntity::getNumber));

        for (ColumnEntity anotherColumn : columns) {
            if (anotherColumn.getNumber() > column.getNumber()) {
                anotherColumn.setNumber(anotherColumn.getNumber() - 1);
            }
        }

        columnRepository.saveAll(columns);
    }

    private void updateNumberOtherColumns(Integer currentNumber,
                                          Integer newNumber,
                                          List<ColumnEntity> columns) {

        columns.sort(Comparator.comparingInt(ColumnEntity::getNumber));

        if (newNumber > currentNumber) {
            for (int i = currentNumber; i < newNumber; i++) {
                columns.get(i).setNumber(columns.get(i).getNumber() - 1);
            }
        } else if (newNumber < currentNumber) {
            for (int i = newNumber - 1; i < currentNumber - 1; i++) {
                columns.get(i).setNumber(columns.get(i).getNumber() + 1);
            }
        } else return;

        columnRepository.saveAll(columns);
    }

    private void validateColumnTitle(String title) {
        if(!Validator.isTitleValid(title)) {
            throw new InvalidTitleException("Название столбца некорректное. Могут быть использованы только буквы " +
                    "российского алфавита, латиница, цифры, нижнее подчеркивание, дефис и пробел");
        }

        if(title.isBlank()) {
            throw new InvalidTitleException("Название столбца не может быть пустым");
        }
    }


    private void updateBoardLastUpdateTime(Board board) {
        board.setLastUpdateDate(ZonedDateTime.now());
        boardRepository.save(board);
    }
}
