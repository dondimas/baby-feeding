package com.dima.babyfeeding.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 4/25/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable(tableName = "feed_event")
public class FeedEvent {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = true)
    private Date startTime;

    @DatabaseField(canBeNull = true)
    private Date finishTime;

    @DatabaseField(canBeNull = true)
    private boolean leftBreast;

    public boolean isRightBreast() {
        return rightBreast;
    }

    public void setRightBreast(boolean rightBreast) {
        this.rightBreast = rightBreast;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isLeftBreast() {
        return leftBreast;
    }

    public void setLeftBreast(boolean leftBreast) {
        this.leftBreast = leftBreast;
    }

    @DatabaseField(canBeNull = true)
    private boolean rightBreast;

    public boolean odd;

}
