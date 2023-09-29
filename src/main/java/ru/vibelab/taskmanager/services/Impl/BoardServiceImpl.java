package ru.vibelab.taskmanager.services.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vibelab.taskmanager.exceptions.*;
import ru.vibelab.taskmanager.mappers.BoardMapper;
import ru.vibelab.taskmanager.models.*;
import ru.vibelab.taskmanager.models.requests.BoardRequest;
import ru.vibelab.taskmanager.models.responses.BoardResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateBoardRequest;
import ru.vibelab.taskmanager.repositories.BoardRepository;
import ru.vibelab.taskmanager.repositories.PictureRepository;
import ru.vibelab.taskmanager.repositories.UserInBoardRepository;
import ru.vibelab.taskmanager.repositories.UserRepository;
import ru.vibelab.taskmanager.services.api.BoardService;
import ru.vibelab.taskmanager.util.Validator;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final UserInBoardRepository userInBoardRepository;
    private final PictureRepository pictureRepository;
    private final BoardAccessServiceImpl boardAccessService;
    private final NotificationServiceImpl notificationService;
    private final BoardMapper boardMapper;

    @Transactional
    @Override
    public BoardResponse createBoard(@NotNull BoardRequest request, Principal principal) {

        Board board = boardMapper.requestToModel(request, pictureRepository.findByLink(request.getPictureLink()).get());
        User creator = userRepository.findByEmail(principal.getName()).get();
        validateBoardName(request.getTitle());

        UserInBoard userInBoard = new UserInBoard(creator, board, BoardRoles.ADMIN);
        board.setUsers(Collections.singletonList(userInBoard));

        userInBoardRepository.save(userInBoard);
        boardRepository.save(board);

        notificationService.createNotification(NotificationTypes.NEW_BOARD,
                "Доска " + board.getTitle() + " успешно создана!", creator);

        return boardMapper.modelToResponse(board);
    }

    @Transactional
    @Override
    public BoardResponse updateBoard(@NotNull Long boardId,
                                     @Nullable UpdateBoardRequest updateRequest,
                                     Principal principal) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));

        if (!boardAccessService.checkAdminAccessToBoard(board, principal))
            throw new NoAccessException("Изменять доску может только администратор");

        if (updateRequest == null) {
            return boardMapper.modelToResponse(board);
        }

        if (updateRequest.getTitle() != null) {
            validateBoardName(updateRequest.getTitle());
        }

        boardMapper.updateRequestToModel(board, updateRequest, pictureRepository.findByLink(updateRequest.getPictureLink()).get());

        boardRepository.save(board);

        return boardMapper.modelToResponse(board);
    }

    @Transactional
    @Override
    public void deleteBoard(@NotNull Long boardId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));

        if (!boardAccessService.checkAdminAccessToBoard(board, principal))
            throw new NoAccessException("Удалить доску может только администратор");

        boardRepository.deleteById(boardId);
    }

    @Transactional
    @Override
    public List<BoardResponse> getAllBoards(Principal principal) {

        Long userId = userRepository.findByEmail(principal.getName()).get().getId();

        List<Board> boards = userInBoardRepository.findAllByUserId(userId).stream()
                .map(UserInBoard::getBoard)
                .toList();

        List<BoardResponse> boardResponses = boards.stream()
                .map(boardMapper::modelToResponse)
                .toList();

        return boardResponses;
    }

    @Override
    public BoardResponse getBoard(@NotNull Long boardId, Principal principal) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));

        if (!boardAccessService.checkAccessToBoard(board, principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        return boardMapper.modelToResponse(board);
    }

    @Transactional
    public BoardResponse addUserInBoard(Long boardId, Long userId, Principal principal) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));

        if (!boardAccessService.checkAdminAccessToBoard(board, principal))
            throw new NoAccessException("Вы не можете добавить пользователя в эту доску!");

        if(userId.equals(userRepository.findByEmail(principal.getName()).get().getId())) {
            throw new IncorrectUserIdException("Вы не можете добавить себя в доску.");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if(userInBoardRepository.findByUserIdAndBoardId(user.getId(), boardId).isPresent()) {
            throw new UserAlreadyInBoardException(user.getId());
        }


            var userInBoard = UserInBoard.builder()
                .board(board)
                .role(BoardRoles.USER)
                .user(user)
                .build();

        List<UserInBoard> users = board.getUsers();
        users.add(userInBoard);
        board.setUsers(users);

        userInBoardRepository.save(userInBoard);
        notificationService.createNotification(NotificationTypes.INVITATION,
                "Вас пригласили на Доску " + board.getTitle(), userInBoard.getUser());

        return boardMapper.modelToResponse(board);
    }

    @Transactional
    public BoardResponse deleteUserFromBoard(Long boardId, Long userId, Principal principal) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));

        if (!boardAccessService.checkAdminAccessToBoard(board, principal))
            throw new NoAccessException("Вы не можете удалить пользователя из этой доски!");

        if(userId.equals(userRepository.findByEmail(principal.getName()).get().getId())) {
            throw new IncorrectUserIdException("Вы не можете удалить себя из доски.");
        }

        UserInBoard userInBoard = userInBoardRepository.findByUserIdAndBoardId(userId, boardId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<UserInBoard> users = board.getUsers();
        users.remove(userInBoard);
        board.setUsers(users);

        userInBoardRepository.deleteByUserIdAndBoardId(userId, boardId);
        notificationService.createNotification(NotificationTypes.KICK,
                "У вас больше нет доступа к доске" + board.getTitle(), userInBoard.getUser());

        return boardMapper.modelToResponse(board);
    }

    private void validateBoardName(String title) {
        if (!Validator.isTitleValid(title)) {
            throw new InvalidTitleException("Некорректное название доски. Могут быть использованы только буквы " +
                    "российского алфавита, латиница, цифры, нижнее подчеркивание, дефис и пробел");
        }

        if (title.isBlank()) {
            throw new InvalidTitleException("Название доски не может быть пустым");
        }
    }

}
