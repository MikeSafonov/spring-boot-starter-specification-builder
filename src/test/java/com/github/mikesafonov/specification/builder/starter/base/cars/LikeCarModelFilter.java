package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;
import lombok.Data;

/**
 *
 * @author MikeSafonov
 */
@Data
public class LikeCarModelFilter {
    @Name("name")
    @Like(direction = Like.DIRECTION.LEFT)
    private String leftName;

    @Name("name")
    @Like(direction = Like.DIRECTION.RIGHT)
    private String rightName;

    @Name("name")
    @Like(direction = Like.DIRECTION.AROUND)
    private String aroundName;
}
