package ru.vibelab.taskmanager.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vibelab.taskmanager.exceptions.*;
import ru.vibelab.taskmanager.models.Board;
import ru.vibelab.taskmanager.models.ColumnEntity;
import ru.vibelab.taskmanager.models.Task;
import ru.vibelab.taskmanager.models.requests.TaskAmountRequest;
import ru.vibelab.taskmanager.models.responses.TaskAmountResponse;
import ru.vibelab.taskmanager.repositories.BoardRepository;
import ru.vibelab.taskmanager.repositories.UserRepository;
import ru.vibelab.taskmanager.services.api.EfficiencyService;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EfficiencyServiceImpl implements EfficiencyService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardAccessServiceImpl boardAccessService;

    @Transactional
    public TaskAmountResponse getUsersTaskAmount(Principal principal, TaskAmountRequest request) {
        long amount = 0;
        Optional<Board> board = boardRepository.findById(request.getBoardId());

        if (board.isEmpty()) {
            throw new BoardNotFoundException(request.getBoardId());
        }

        if (!boardAccessService.checkAccessToBoard(board.get(), principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        Optional<ColumnEntity> column = board.get().getFinalColumn();

        if (column.isPresent())
            amount = ColumnTaskAmount(column.get(),request);

        return TaskAmountResponse.builder()
                .amount(amount)
                .build();
    }

    @Transactional
    public TaskAmountResponse getUserTaskAmount(Principal principal, TaskAmountRequest request) {
        Long userId = userRepository.findByEmail(principal.getName()).get().getId();

        long amount = 0;
        Optional<Board> board = boardRepository.findById(request.getBoardId());

        if (board.isEmpty()) {
            throw new BoardNotFoundException(request.getBoardId());
        }

        if (!boardAccessService.checkAccessToBoard(board.get(), principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        Optional<ColumnEntity> column = board.get().getFinalColumn();

        if (column.isPresent())
            amount = ColumnTaskAmount(column.get(),request,userId);

        return TaskAmountResponse.builder()
                .amount(amount)
                .build();
    }

    private long ColumnTaskAmount(ColumnEntity column, TaskAmountRequest request, Long userId){
        if(column.getTasks() == null)
            return 0;
        if(request.getTime() != null) {
            return column.getTasks().stream()
                    .filter(t -> Objects.equals(t.getExecutor().getId(), userId))
                    .filter(Task::isComplete)
                    .filter(t -> Duration.between(t.getCompletedDate(), LocalDateTime.now()).compareTo(request.getTime()) < 0)
                    .count();
        }
        else{
            return column.getTasks().stream()
                    .filter(t -> Objects.equals(t.getExecutor().getId(), userId))
                    .filter(Task::isComplete)
                    .count();
        }
    }
    private long ColumnTaskAmount(ColumnEntity column, TaskAmountRequest request){
        if(column.getTasks() == null)
            return 0;
        if(request.getTime()!=null) {
            return column.getTasks().stream()
                    .filter(Task::isComplete)
                    .filter(t -> Duration.between(t.getCompletedDate(), LocalDateTime.now()).compareTo(request.getTime()) < 0)
                    .count();
        }
        else {
            return column.getTasks().stream()
                    .filter(Task::isComplete)
                    .count();
        }
    }

}
