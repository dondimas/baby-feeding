package com.babycycle.babyfeeding.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.babycycle.babyfeeding.model.FeedEvent;
import com.babycycle.babyfeeding.model.Reminder;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.*;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static DatabaseHelper instance;

    public static final int DATABASE_VERSION = 20;

    private static final String DATABASE_NAME = "babyfeeding3.db";

    public static final Class[] ENTITIES = {
            FeedEvent.class,
    };

    public static final Class[] ENTITIES_NEW = {
            FeedEvent.class, Reminder.class,
    };

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null) {
            instance = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

//        createTablesForEntities(connectionSource, ENTITIES);
        createTablesForEntities(connectionSource, ENTITIES_NEW);
        createFeedEventForPersistingStartedFeeding();
        createReminder();

    }

    private void createFeedEventForPersistingStartedFeeding() {

        RuntimeExceptionDao feedEventRepo = getRuntimeExceptionDao(FeedEvent.class);
        FeedEvent feedEvent = new FeedEvent();
        feedEvent.setStartTime(new Date());
        feedEvent.setFinishTime(new Date());
        feedEventRepo.createOrUpdate(feedEvent);

    }

    private void createReminder() {
        RuntimeExceptionDao reminderRepo = getRuntimeExceptionDao(Reminder.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,10);

        if(calendar.get(Calendar.DAY_OF_MONTH) >1)
            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) -1);
        else if (calendar.get(Calendar.MONTH) >0)
            calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH) -1);

        Reminder reminder = new Reminder("Czy ty dalas dziecku vitaminki?", calendar.getTime());
        reminderRepo.createOrUpdate(reminder);

//        Reminder reminder2 = new Reminder("reminder2", calendar.getTime());
//        reminderRepo.createOrUpdate(reminder2);
//        Reminder reminder3 = new Reminder("reminder3", calendar.getTime());
//        reminderRepo.createOrUpdate(reminder3);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

//        dropTablesForEntities(connectionSource, ENTITIES);
        dropTablesForEntities(connectionSource, ENTITIES_NEW);
        onCreate(sqLiteDatabase, connectionSource);
    }

    private void createTablesForEntities(ConnectionSource connectionSource, Class... entities) {
        for (Class entity : entities) {
            try {
                TableUtils.createTable(connectionSource, entity);
            } catch (SQLException e) {
                throw new RuntimeException("error while creating table for class " + entity, e);
            }
        }
    }

    private void dropTablesForEntities(ConnectionSource connectionSource, Class... entities) {
        for (Class entity : entities) {
            try {
                TableUtils.dropTable(connectionSource, entity, false);
            } catch (SQLException e) {
                throw new RuntimeException("error while dropping table for class " + entity, e);
            }
        }
    }


    private RuntimeExceptionDao<FeedEvent, String> simpleRuntimeDao = null;

    public RuntimeExceptionDao<FeedEvent, String> getSimpleDataDao() {
        if (simpleRuntimeDao == null) {
            simpleRuntimeDao = getRuntimeExceptionDao(FeedEvent.class);
        }
        return simpleRuntimeDao;
    }

    public void saveFeedEvent(FeedEvent feedEvent) {
        RuntimeExceptionDao feedEventRepo = getRuntimeExceptionDao(FeedEvent.class);
        feedEventRepo.createOrUpdate(feedEvent);
    }

    public List<FeedEvent> getFeedEvents(Context context)
    {
        DatabaseHelper helper = getHelper(context);
        RuntimeExceptionDao<FeedEvent, String> simpleDao = helper.getSimpleDataDao();
        List<FeedEvent> list = simpleDao.queryForAll();
        try {
            list = simpleDao.queryBuilder().orderBy("id", false) .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveReminder(Reminder reminder) {
        RuntimeExceptionDao feedEventRepo = getRuntimeExceptionDao(Reminder.class);
        feedEventRepo.createOrUpdate(reminder);
    }

    public List<Reminder> getReminders(Context context) {
        DatabaseHelper helper = getHelper(context);
        RuntimeExceptionDao<Reminder, String> simpleDao = helper.getRuntimeExceptionDao(Reminder.class);
        List<Reminder> list = simpleDao.queryForAll();
        return list;
    }

    public FeedEvent getFeedEventForId(Context context,String id) {
        DatabaseHelper helper = DatabaseHelper.getHelper(context);
        RuntimeExceptionDao<FeedEvent, String> simpleDao = helper.getRuntimeExceptionDao(FeedEvent.class);
        return simpleDao.queryForId(id);
    }
}
