package ru.vsu.portalforembroidery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vsu.portalforembroidery.exception.EntityCreationException;
import ru.vsu.portalforembroidery.exception.EntityNotFoundException;
import ru.vsu.portalforembroidery.mapper.CommentMapper;
import ru.vsu.portalforembroidery.model.dto.CommentDto;
import ru.vsu.portalforembroidery.model.dto.view.CommentViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.model.entity.CommentEntity;
import ru.vsu.portalforembroidery.model.entity.PostEntity;
import ru.vsu.portalforembroidery.model.entity.UserEntity;
import ru.vsu.portalforembroidery.repository.CommentRepository;
import ru.vsu.portalforembroidery.repository.PostRepository;
import ru.vsu.portalforembroidery.repository.UserRepository;
import ru.vsu.portalforembroidery.utils.ParseUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService, PaginationService<CommentViewDto> {

    @Value("${pagination.defaultPageNumber}")
    private int defaultPageNumber;
    @Value("${pagination.defaultPageSize}")
    private int defaultPageSize;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public int createComment(CommentDto commentDto) {
        final Optional<PostEntity> postEntity = postRepository.findById(commentDto.getPostId());
        postEntity.ifPresentOrElse(
                (post) -> log.info("Post has been found."),
                () -> {
                    log.warn("Post hasn't been found.");
                    throw new EntityNotFoundException("Post not found!");
                }
        );
        final Optional<UserEntity> userEntity = userRepository.findById(commentDto.getUserId());
        userEntity.ifPresentOrElse(
                (user) -> log.info("User has been found."),
                () -> {
                    log.warn("User hasn't been found.");
                    throw new EntityNotFoundException("User not found!");
                }
        );
        final CommentEntity commentEntity = Optional.of(commentDto)
                .map(commentMapper::commentDtoToCommentEntity)
                .map(comment -> {
                    comment.setCreationDatetime(LocalDateTime.now());
                    return comment;
                })
                .map(commentRepository::save)
                .orElseThrow(() -> new EntityCreationException("Comment hasn't been created!"));
        log.info("Comment with id = {} has been created.", commentEntity.getId());
        return commentEntity.getId();
    }

    @Override
    public CommentViewDto getCommentViewById(int id) {
        final Optional<CommentEntity> commentEntity = commentRepository.findById(id);
        commentEntity.ifPresentOrElse(
                (comment) -> log.info("Comment with id = {} has been found.", comment.getId()),
                () -> log.warn("Comment hasn't been found."));
        return commentEntity.map(commentMapper::commentEntityToCommentViewDto)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found!"));
    }

    @Override
    public void updateCommentById(int id, CommentDto commentDto) {
        final Optional<CommentEntity> commentEntity = commentRepository.findById(id);
        commentEntity.ifPresentOrElse(
                (post) -> log.info("Comment with id = {} has been found.", post.getId()),
                () -> {
                    log.warn("Comment hasn't been found.");
                    throw new EntityNotFoundException("Comment not found!");
                });
        Optional.of(commentDto)
                .map(commentMapper::commentDtoToCommentEntity)
                .map((comment) -> {
                    comment.setId(id);
                    return commentRepository.save(comment);
                });
        log.info("Comment with id = {} has been updated.", id);
    }

    @Override
    public void deleteCommentById(int id) {
        final Optional<CommentEntity> commentEntity = commentRepository.findById(id);
        commentEntity.ifPresentOrElse(
                (comment) -> log.info("Comment with id = {} has been found.", comment.getId()),
                () -> {
                    log.warn("Comment hasn't been found.");
                    throw new EntityNotFoundException("Comment not found!");
                });
        commentRepository.deleteById(id);
        log.info("Comment with id = {} has been deleted.", id);
    }

    @Override
    public ViewListPage<CommentViewDto> getViewListPage(String page, String size) {
        final int pageNumber = Optional.ofNullable(page).map(ParseUtils::parsePositiveInteger).orElse(defaultPageNumber);
        final int pageSize = Optional.ofNullable(size).map(ParseUtils::parsePositiveInteger).orElse(defaultPageSize);

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final List<CommentViewDto> listComments = listComments(pageable);
        final int totalAmount = numberOfComments();

        return getViewListPage(totalAmount, pageSize, pageNumber, listComments);
    }

    @Override
    public List<CommentViewDto> listComments(Pageable pageable) {
        final List<CommentEntity> commentEntities = commentRepository.findAll(pageable).getContent();
        log.info("There have been found {} comments.", commentEntities.size());
        return commentMapper.commentEntitiesToCommentViewDtoList(commentEntities);
    }

    @Override
    public int numberOfComments() {
        final long numberOfComments = commentRepository.count();
        log.info("There have been found {} comments.", numberOfComments);
        return (int) numberOfComments;
    }

}