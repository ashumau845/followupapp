package com.example.followupapp.DatabaseHelper;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;


import net.sqlcipher.database.SQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private AppDatabase appDatabase;

    public DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "user_database").build();
    }



    public List<User> getAllUsers() throws ExecutionException, InterruptedException {
        return new GetUserAsyncTask().execute().get();
    }

    private class GetUserAsyncTask extends AsyncTask<Void, Void,List<User>>{

        @Override
        protected List<User> doInBackground(Void... voids) {
            return appDatabase.userDao().getAll();
        }
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }


    public void close () {
        if (appDatabase != null) {
            appDatabase.close();

        }
    }


}