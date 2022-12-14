package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.vsu.portalforembroidery.model.dto.CommentDto;
import ru.vsu.portalforembroidery.model.dto.view.CommentViewDto;
import ru.vsu.portalforembroidery.model.entity.CommentEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "userId", source = "user.id")
    CommentDto commentEntityToCommentDto(CommentEntity entity);

    @Mapping(target = "userFirstName", source = "user.firstName")
    @Mapping(target = "userLastName", source = "user.lastName")
    CommentViewDto commentEntityToCommentViewDto(CommentEntity entity);

    @Mapping(target = "post.id", source = "postId")
    @Mapping(target = "user.id", source = "userId")
    CommentEntity commentDtoToCommentEntity(CommentDto dto);

    @Mapping(target = "user.firstName", source = "userFirstName")
    @Mapping(target = "user.lastName", source = "userLastName")
    CommentEntity commentViewDtoToCommentEntity(CommentViewDto dto);

    void mergeCommentEntityAndCommentDto(@MappingTarget CommentEntity entity, CommentDto dto);

    List<CommentViewDto> commentEntitiesToCommentViewDtoList(Iterable<CommentEntity> entities);

}
