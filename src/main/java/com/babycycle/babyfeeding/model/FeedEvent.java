package com.babycycle.babyfeeding.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: babycycle
 * Date: 4/25/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable(tableName = "feed_event")
public class FeedEvent {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = true, columnName = "start_time")
    private Date startTime;

    @DatabaseField(canBeNull = true, columnName = "finish_time")
    private Date finishTime;

    @DatabaseField(canBeNull = true, columnName = "left_breast")
    private boolean leftBreast;

    @DatabaseField(canBeNull = true, columnName = "right_breast")
    private boolean rightBreast;

    @DatabaseField(canBeNull = true, columnName = "milk_amount")
    private int milkAmount;

    public FeedEvent() {
    }

    public FeedEvent(Date startTime, int milk_amount, boolean rightBreast, boolean leftBreast, Date finishTime) {
        this.startTime = startTime;
        this.milkAmount = milk_amount;
        this.rightBreast = rightBreast;
        this.leftBreast = leftBreast;
        this.finishTime = finishTime;
    }

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
        if(finishTime == null) {
            return startTime;
        }
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

    public int getMilkAmount() {
        return milkAmount;
    }

    public void setMilkAmount(int milkAmount) {
        this.milkAmount = milkAmount;
    }

    public boolean odd;

}
