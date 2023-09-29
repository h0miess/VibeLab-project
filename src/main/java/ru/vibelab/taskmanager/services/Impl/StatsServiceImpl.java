package ru.vibelab.taskmanager.services.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vibelab.taskmanager.exceptions.BoardNotFoundException;
import ru.vibelab.taskmanager.exceptions.InvalidPeriodException;
import ru.vibelab.taskmanager.exceptions.NoAccessException;
import ru.vibelab.taskmanager.exceptions.UserNotFoundException;
import ru.vibelab.taskmanager.mappers.HoursStatsMapper;
import ru.vibelab.taskmanager.models.*;
import ru.vibelab.taskmanager.models.requests.HoursStatsRequest;
import ru.vibelab.taskmanager.models.responses.HoursStatsResponse;
import ru.vibelab.taskmanager.repositories.BoardRepository;
import ru.vibelab.taskmanager.repositories.UserInBoardRepository;
import ru.vibelab.taskmanager.repositories.UserRepository;
import ru.vibelab.taskmanager.services.api.StatsService;
import ru.vibelab.taskmanager.config.PropertiesConfig;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {

    private final BoardRepository boardRepository;
    private final UserInBoardRepository userInBoardRepository;
    private final UserRepository userRepository;
    private final PropertiesConfig propertiesConfig;


    @Override
    @Transactional
    public HoursStatsResponse getHoursStatistics(HoursStatsRequest request) {

        List<Task> tasksInBoard;

        if (request.getUserEmails() == null) {
            Board board = boardRepository.findById(request.getBoardId())
                    .orElseThrow(() -> new BoardNotFoundException(request.getBoardId()));

            tasksInBoard = findAllTasksInBoard(board, request.getPeriod());

        } else {

            List<String> userEmails = request.getUserEmails();
            List<User> usersFromRequest = new ArrayList<>();
            for (String email : userEmails) {
                usersFromRequest.add(userRepository.findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException("Пользователь с почтой " + email + " не найден"))
                );
            }

            Board board = boardRepository.findById(request.getBoardId())
                    .orElseThrow(() -> new BoardNotFoundException(request.getBoardId()));

            checkIfAllUsersInBoard(usersFromRequest, board.getId());

            tasksInBoard = findAllTasksInBoard(board, request.getPeriod());

            filterByUsers(tasksInBoard, usersFromRequest);

        }

        if (tasksInBoard.isEmpty()) {
            return HoursStatsMapper.toResponse(0L, 0L);
        }

        Long estimatedTime = addEstimatedTime(tasksInBoard);
        Long spentTime = addSpentTime(tasksInBoard);

        return HoursStatsMapper.toResponse(convertToHours(estimatedTime), convertToHours(spentTime));


    }

    private boolean isBoardContainsTasks(Board board) {
        if (board.getColumns() == null) return false;

        List<ColumnEntity> columns = board.getColumns();

        int count = 0;
        for (ColumnEntity column : columns) {
            if (column.getTasks() != null) {
                count += column.getTasks().size();
            }
        }

        return count != 0;
    }

    private List<Task> getTasksInBoardWithPeriod(Board board, String period) {

        if (!isBoardContainsTasks(board)) return Collections.emptyList();

        LocalDate from; // начало периода
        LocalDate to; // конец периода

        try {

            String[] splitted = period.split(" - ");
            from = LocalDate.parse(splitted[0], DateTimeFormatter.ofPattern(propertiesConfig.getDatePattern()));
            to = LocalDate.parse(splitted[1], DateTimeFormatter.ofPattern(propertiesConfig.getDatePattern()));

        } catch (DateTimeParseException e) {
            throw new InvalidPeriodException("Некорректная строка периода");
        }

        if (to.isAfter(LocalDate.now()))
            throw new InvalidPeriodException("Правая граница периода не может быть больше, чем текущая дата");

        if (from.isAfter(to))
            throw new InvalidPeriodException("Левая граница периода не может быть больше, чем правая");

        List<ColumnEntity> columns = board.getColumns();
        List<Task> tasksInBoard = new ArrayList<>();

        for (ColumnEntity column : columns) {
            if (column.getTasks() != null) {
                for (Task task : column.getTasks()) {

                    LocalDate dateOfCreation = task.getCreatedAt().toLocalDate();

                    if ((dateOfCreation.isAfter(from) || dateOfCreation.isEqual(from))
                            && (dateOfCreation.isBefore(to) || dateOfCreation.isEqual(to))) {

                        tasksInBoard.add(task);
                    }
                }
            }
        }

        return tasksInBoard;
    }

    private List<Task> getTasksInBoard(Board board) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern(propertiesConfig.getDatePattern()));
        return getTasksInBoardWithPeriod(board, "01.01.2010 - " + today);
    }

    private List<Task> findAllTasksInBoard(Board board, String period) {
        List<Task> tasksInBoard;
        if (period == null) {
            tasksInBoard = getTasksInBoard(board);
        } else {
            tasksInBoard = getTasksInBoardWithPeriod(board, period);
        }

        return tasksInBoard;
    }

    private Long convertToHours(Long minutes) {
        return minutes / 60;
    }

    private void checkIfAllUsersInBoard(List<User> usersFromRequest, Long boardId) {
        List<User> usersInBoard = userInBoardRepository.findAllByBoardId(boardId).stream()
                .map(UserInBoard::getUser)
                .toList();

        for (User userFromRequest : usersFromRequest) {
            if (!usersInBoard.contains(userFromRequest)) {
                throw new NoAccessException("Пользователь с айди " + userFromRequest.getId() +
                        " не относится к этой доске");
            }
        }
    }

    private void filterByUsers(@NotNull List<Task> tasksInBoard, List<User> usersInBoard) {
        tasksInBoard.removeIf(task -> !usersInBoard.contains(task.getExecutor()));

    }

    private Long addEstimatedTime(@NotNull List<Task> tasksInBoard) {
        Long estimatedTime = 0L;
        for(Task task : tasksInBoard) {
            if(task.getEstimatedTime() != null) {
                estimatedTime += task.getEstimatedTime();
            }
        }

        return estimatedTime;
    }

    private Long addSpentTime(@NotNull List<Task> tasksInBoard) {
        Long spentTime = 0L;
        for(Task task : tasksInBoard) {
            if(task.getSpentTime() != null) {
                spentTime += task.getSpentTime();
            }
        }

        return spentTime;
    }
}
