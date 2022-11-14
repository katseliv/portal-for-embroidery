package ru.vsu.portalforembroidery.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonDeserialize(builder = PlacementPositionDto.PlacementPositionDtoBuilder.class)
public class PlacementPositionDto {

    @NotNull(message = "Name is null.")
    @NotBlank(message = "Name is blank.")
    private final String name;

    @NotNull(message = "Anchor is null.")
    @Positive(message = "Anchor is negative ot zero.")
    private final Integer anchor;

    @NotNull(message = "Top Margin Position is null.")
    @Positive(message = "Top Margin Position is negative ot zero.")
    private final BigDecimal topMarginPosition;

    @NotNull(message = "Bottom Margin Position is null.")
    @Positive(message = "Bottom Margin Position is negative ot zero.")
    private final BigDecimal bottomMarginPosition;

    @NotNull(message = "Left Margin Position is null.")
    @Positive(message = "Left Margin Position is negative ot zero.")
    private final BigDecimal leftMarginPosition;

    @NotNull(message = "Right Margin Position is null.")
    @Positive(message = "Right Margin Position is negative ot zero.")
    private final BigDecimal rightMarginPosition;

    @NotNull(message = "Height Percent is null.")
    @Positive(message = "Height Percent is negative ot zero.")
    private final BigDecimal heightPercent;

    @NotNull(message = "Width Percent is null.")
    @Positive(message = "Width Percent is negative ot zero.")
    private final BigDecimal widthPercent;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PlacementPositionDtoBuilder {

    }

}
