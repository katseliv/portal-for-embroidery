package ru.vsu.portalforembroidery.model.dto.view;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelPhotoViewDto {

    private final Integer id;
    private final String base64StringFile;
    private final String designName;
    private final BigDecimal placementPositionHeightRelativeSize;
    private final BigDecimal placementPositionWidthRelativeSize;

}
