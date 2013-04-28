package com.dima.babyfeeding.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static DatabaseHelper instance;

    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "babyfeeding3.db";

    public static final Class[] ENTITIES = {
            FeedEvent.class,
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

        createTablesForEntities(connectionSource, ENTITIES);

//        createOfferShoppingList();
//        createOfferShoppingList2();

    }

    private void createOfferShoppingList() {
        RuntimeExceptionDao personRepo = getRuntimeExceptionDao(FeedEvent.class);
        FeedEvent person = new FeedEvent();
        person.setStartTime(Calendar.getInstance().getTime());
        personRepo.createOrUpdate(person);

    }


//    private void createOfferShoppingList2() {
//    RuntimeExceptionDao personRepo = getRuntimeExceptionDao(Person.class);
//    Person person = new Person("AAAA");
//    personRepo.createOrUpdate(person);
//
//    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        dropTablesForEntities(connectionSource, ENTITIES);

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

//    public List<Person> GetData(Context context)
//    {
//        DatabaseHelper helper = getHelper(context);
//        RuntimeExceptionDao<Person, String> simpleDao = getRuntimeExceptionDao(Person.class);
//        List<Person> list = simpleDao.queryForAll();
//        return list;
//    }

    public void saveFeedEvent(FeedEvent feedEvent) {
        RuntimeExceptionDao feedEventRepo = getRuntimeExceptionDao(FeedEvent.class);
        feedEventRepo.createOrUpdate(feedEvent);
    }

    public List<FeedEvent> getFeedEvents(Context context)
    {
        DatabaseHelper helper = getHelper(context);
        RuntimeExceptionDao<FeedEvent, String> simpleDao = helper.getSimpleDataDao();
        List<FeedEvent> list = simpleDao.queryForAll();
        return list;
    }
}
