package com.github.mikesafonov.specification.builder.starter.base.cars;

import com.github.mikesafonov.specification.builder.starter.annotations.Like;
import com.github.mikesafonov.specification.builder.starter.annotations.Name;

/**
 *
 * @author MikeSafonov
 */
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

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getAroundName() {
        return aroundName;
    }

    public void setAroundName(String aroundName) {
        this.aroundName = aroundName;
    }
}
