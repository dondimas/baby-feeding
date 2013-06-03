package com.babycycle.babyfeeding.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 6/2/13
 * Time: 9:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedDay {

    private Date day;
    private int chemical;
    private int natural;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getChemical() {
        return chemical;
    }

    public void setChemical(int chemical) {
        this.chemical = chemical;
    }

    public int getNatural() {
        return natural;
    }

    public void setNatural(int natural) {
        this.natural = natural;
    }
}
