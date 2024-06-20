package com.ep.ai.hd.live.wallpaper.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ep.ai.hd.live.wallpaper.DataModel.FavouriteModel;

import java.util.ArrayList;

public class FavoriteDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Favorites.db";
    public static final String TABLE_NAME = "favorites";
    public static final String COLUMN_NAME_IMAGE = "image";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_HASHTAG = "hashtag";
    public static final String ID = "id";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_IMAGE + " TEXT," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_HASHTAG + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public FavoriteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertFavorite(FavouriteModel favoriteModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_IMAGE, favoriteModel.getvImage());
        values.put(COLUMN_NAME_NAME, favoriteModel.getvName());
        values.put(COLUMN_NAME_HASHTAG, favoriteModel.getvHashTage());
        db.insert(TABLE_NAME, null, values);

    }

    public ArrayList<FavouriteModel> getAllFavorites() {
        ArrayList<FavouriteModel> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ID,
                COLUMN_NAME_IMAGE,
                COLUMN_NAME_NAME,
                COLUMN_NAME_HASHTAG
        };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(ID));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_IMAGE));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_NAME));
            String hashtag = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_HASHTAG));

            FavouriteModel favoriteModel = new FavouriteModel();
            favoriteModel.setvImage(image);
            favoriteModel.setvName(name);
            favoriteModel.setvHashTage(hashtag);
            favoriteList.add(favoriteModel);
        }

        cursor.close();
        return favoriteList;
    }

    public boolean isImageExists(String vImage) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {ID};

        String selection = COLUMN_NAME_IMAGE + " = ?";
        String[] selectionArgs = {vImage};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void deleteFavoriteByImage(String vImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_NAME_IMAGE + " = ?";
        String[] selectionArgs = {vImage};
        db.delete(TABLE_NAME, selection, selectionArgs);

    }


}
