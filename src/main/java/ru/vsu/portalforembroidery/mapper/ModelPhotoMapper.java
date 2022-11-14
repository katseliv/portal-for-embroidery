package ru.vsu.portalforembroidery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.vsu.portalforembroidery.model.dto.ModelPhotoDto;
import ru.vsu.portalforembroidery.model.dto.view.ModelPhotoViewDto;
import ru.vsu.portalforembroidery.model.entity.ModelPhotoEntity;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelPhotoMapper {

    @Mapping(target = "base64StringFile", source = "file", qualifiedByName = "bytesArrayFile")
    @Mapping(target = "designId", source = "design.id")
    @Mapping(target = "placementPositionId", source = "placementPosition.id")
    ModelPhotoDto modelPhotoEntityToModelPhotoDto(ModelPhotoEntity entity);

    @Mapping(target = "base64StringFile", source = "file", qualifiedByName = "bytesArrayFile")
    @Mapping(target = "designName", source = "design.name")
    @Mapping(target = "designBase64StringFile", source = "design.file")
    @Mapping(target = "placementPositionHeightPercent", source = "placementPosition.heightPercent")
    @Mapping(target = "placementPositionWidthPercent", source = "placementPosition.widthPercent")
    ModelPhotoViewDto modelPhotoEntityToModelPhotoViewDto(ModelPhotoEntity entity);

    @Named(value = "bytesArrayFile")
    default String mapBytesArrayFile(final byte[] file) {
        return Base64.getEncoder().encodeToString(file);
    }

    @Mapping(target = "file", source = "base64StringFile", qualifiedByName = "base64StringFile")
    @Mapping(target = "design.id", source = "designId")
    @Mapping(target = "placementPosition.id", source = "placementPositionId")
    ModelPhotoEntity modelPhotoDtoToModelPhotoEntity(ModelPhotoDto dto);

    @Mapping(target = "file", source = "base64StringFile", qualifiedByName = "base64StringFile")
    @Mapping(target = "design.name", source = "designName")
    @Mapping(target = "design.file", source = "designBase64StringFile")
    @Mapping(target = "placementPosition.heightPercent", source = "placementPositionHeightPercent")
    @Mapping(target = "placementPosition.widthPercent", source = "placementPositionWidthPercent")
    ModelPhotoEntity modelPhotoViewDtoToModelPhotoEntity(ModelPhotoViewDto dto);

    @Named(value = "base64StringFile")
    default byte[] mapBase64StringFile(final String base64StringFile) {
        return Base64.getDecoder().decode(base64StringFile);
    }

    List<ModelPhotoViewDto> modelPhotoEntitiesToModelPhotoViewDtoList(Iterable<ModelPhotoEntity> entities);

}