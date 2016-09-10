package com.devlin.core.event;

/**
 * Created by Administrator on 9/7/2016.
 */
public class FavoriteStatusChangedEvent {
    private int[] indices;

    public FavoriteStatusChangedEvent(int[] indices) {
        this.indices = indices;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }
}
