package com.babycycle.babyfeeding.ui.controller;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/3/13
 * Time: 1:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeedingButtonViewController {

    public interface FeedingRunner {
        void runFeeding();
        void finalizeFeeding();
    }
}
