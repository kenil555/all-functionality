package com.hassle.video.players.Databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


import com.hassle.video.players.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class DBHelper extends SQLiteOpenHelper {

    static String DB_NAME = "onlinemp3.db";

    static String TAG_PLAYLIST_ID = "id";
    static String TAG_PLAYLIST_NAME = "name";

    static String TAG_ID = "id";
    static String TAG_SID = "sid";
    static String TAG_PID = "pid";
    static String TAG_TITLE = "title";
    static String TAG_DESC = "description";
    static String TAG_LYRICS = "lyrics";
    static String TAG_ARTIST = "artist";
    static String TAG_DURATION = "duration";
    static String TAG_URL = "url";
    static String TAG_IMAGE = "image";
    static String TAG_AVG_RATE = "avg_rate";
    static String TAG_VIEWS = "views";
    static String TAG_DOWNLOADS = "downloads";
    static final String TAG_TEMP_NAME = "tempid";
    SQLiteDatabase db;
    final Context context;

    // Table Name
    private static final String TABLE_ABOUT = "about";
    private static final String TABLE_PLAYLIST = "playlist";
    private static final String TABLE_PLAYLIST_OFFLINE = "playlist_offline";
    private static final String TABLE_PLAYLIST_SONG = "playlistsong";
    private static final String TABLE_PLAYLIST_SONG_OFFLINE = "playlistsong_offline";
    private static final String TABLE_RECENT = "recent";
    private static final String TABLE_RECENT_OFFLINE = "recent_off";
    private static final String TABLE_DOWNLOAD_SONG = "download";

    // Table columns_quotes

    private static final String TAG_ABOUT_NAME = "name";
    private static final String TAG_ABOUT_LOGO = "logo";
    private static final String TAG_ABOUT_VERSION = "version";
    private static final String TAG_ABOUT_AUTHOR = "author";
    private static final String TAG_ABOUT_CONTACT = "contact";
    private static final String TAG_ABOUT_EMAIL = "email";
    private static final String TAG_ABOUT_WEBSITE = "website";
    private static final String TAG_ABOUT_DESC = "description";
    private static final String TAG_ABOUT_PUB_ID = "ad_pub";
    private static final String TAG_ABOUT_BANNER_ID = "ad_banner";
    private static final String TAG_ABOUT_INTER_ID = "ad_inter";
    private static final String TAG_ABOUT_NATIVE_ID = "ad_native";
    private static final String TAG_ABOUT_IS_BANNER = "isbanner";
    private static final String TAG_ABOUT_IS_INTER = "isinter";
    private static final String TAG_ABOUT_IS_NATIVE = "isNative";
    private static final String TAG_ABOUT_START_APP_ID = "start_app_id";
    private static final String TAG_ABOUT_WORTISE_APP_ID = "wortise_app_id";
    private static final String TAG_ABOUT_CLICK = "click";
    private static final String TAG_ABOUT_IS_DOWNLOAD = "isdownload";

    String[] columns_song = new String[]{TAG_ID, TAG_SID, TAG_TITLE, TAG_DESC, TAG_ARTIST, TAG_DURATION, TAG_LYRICS,
            TAG_URL, TAG_IMAGE, TAG_AVG_RATE, TAG_VIEWS, TAG_DOWNLOADS};

    String[] columns_playlist_song = new String[]{TAG_ID, TAG_SID, TAG_TITLE, TAG_DESC, TAG_ARTIST, TAG_DURATION,
            TAG_LYRICS, TAG_URL, TAG_IMAGE, TAG_AVG_RATE, TAG_VIEWS, TAG_DOWNLOADS};

    String[] columns_download_song = new String[]{TAG_ID, TAG_SID, TAG_TITLE, TAG_DESC, TAG_ARTIST, TAG_DURATION,
            TAG_LYRICS, TAG_URL, TAG_IMAGE, TAG_AVG_RATE, TAG_VIEWS, TAG_DOWNLOADS, TAG_TEMP_NAME};

    String[] columns_playlist = new String[]{TAG_PLAYLIST_ID, TAG_PLAYLIST_NAME};

    String[] columns_about = new String[]{TAG_ABOUT_NAME, TAG_ABOUT_LOGO, TAG_ABOUT_VERSION, TAG_ABOUT_AUTHOR, TAG_ABOUT_CONTACT,
            TAG_ABOUT_EMAIL, TAG_ABOUT_WEBSITE, TAG_ABOUT_DESC, TAG_ABOUT_PUB_ID, TAG_ABOUT_BANNER_ID,
            TAG_ABOUT_INTER_ID, TAG_ABOUT_NATIVE_ID, TAG_ABOUT_IS_BANNER, TAG_ABOUT_IS_INTER, TAG_ABOUT_IS_NATIVE,
            TAG_ABOUT_START_APP_ID, TAG_ABOUT_WORTISE_APP_ID, TAG_ABOUT_CLICK, TAG_ABOUT_IS_DOWNLOAD};

    // Creating table playlist
    private static final String CREATE_TABLE_PLAYLIST = "create table " + TABLE_PLAYLIST + "(" + TAG_PLAYLIST_ID
            + " integer PRIMARY KEY AUTOINCREMENT, " + TAG_PLAYLIST_NAME + " TEXT);";
    // Creating table playlist offline
    private static final String CREATE_TABLE_PLAYLIST_OFFLINE = "create table " + TABLE_PLAYLIST_OFFLINE + "(" + TAG_PLAYLIST_ID
            + " integer PRIMARY KEY AUTOINCREMENT, " + TAG_PLAYLIST_NAME + " TEXT);";

    // Creating table about
    private static final String CREATE_TABLE_ABOUT = "create table " + TABLE_ABOUT + "(" +
            TAG_ABOUT_NAME + " TEXT, " +
            TAG_ABOUT_LOGO + " TEXT, " +
            TAG_ABOUT_VERSION + " TEXT, " +
            TAG_ABOUT_AUTHOR + " TEXT, " +
            TAG_ABOUT_CONTACT + " TEXT, " +
            TAG_ABOUT_EMAIL + " TEXT, " +
            TAG_ABOUT_WEBSITE + " TEXT, " +
            TAG_ABOUT_DESC + " TEXT, " +
            TAG_ABOUT_PUB_ID + " TEXT, " +
            TAG_ABOUT_BANNER_ID + " TEXT, " +
            TAG_ABOUT_INTER_ID + " TEXT, " +
            TAG_ABOUT_NATIVE_ID + " TEXT, " +
            TAG_ABOUT_IS_BANNER + " TEXT, " +
            TAG_ABOUT_IS_INTER + " TEXT, " +
            TAG_ABOUT_IS_NATIVE + " TEXT, " +
            TAG_ABOUT_START_APP_ID + " TEXT, " +
            TAG_ABOUT_WORTISE_APP_ID + " TEXT, " +
            TAG_ABOUT_CLICK + " TEXT, " +
            TAG_ABOUT_IS_DOWNLOAD + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_PLAYLIST_SONG = "create table " + TABLE_PLAYLIST_SONG + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESC + " TEXT," +
            TAG_LYRICS + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_PID + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_PLAYLIST_SONG_OFFLINE = "create table " + TABLE_PLAYLIST_SONG_OFFLINE + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESC + " TEXT," +
            TAG_LYRICS + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_PID + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_RECENT = "create table " + TABLE_RECENT + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESC + " TEXT," +
            TAG_LYRICS + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_RECENT_OFFLINE = "create table " + TABLE_RECENT_OFFLINE + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESC + " TEXT," +
            TAG_LYRICS + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT);";

    // Creating table query
    private static final String CREATE_TABLE_DOWNLOAD = "create table " + TABLE_DOWNLOAD_SONG + "(" +
            TAG_ID + " integer PRIMARY KEY AUTOINCREMENT," +
            TAG_SID + " TEXT," +
            TAG_TITLE + " TEXT," +
            TAG_DESC + " TEXT," +
            TAG_LYRICS + " TEXT," +
            TAG_ARTIST + " TEXT," +
            TAG_DURATION + " TEXT," +
            TAG_URL + " TEXT," +
            TAG_IMAGE + " TEXT," +
            TAG_AVG_RATE + " TEXT," +
            TAG_VIEWS + " TEXT," +
            TAG_DOWNLOADS + " TEXT," +
            TAG_TEMP_NAME + " TEXT);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_DOWNLOAD);
            db.execSQL(CREATE_TABLE_ABOUT);
            db.execSQL(CREATE_TABLE_PLAYLIST);
            addPlayListMyPlay(db, context.getString(R.string.playlists), true);
            db.execSQL(CREATE_TABLE_PLAYLIST_OFFLINE);
            db.execSQL(CREATE_TABLE_PLAYLIST_SONG);
            db.execSQL(CREATE_TABLE_PLAYLIST_SONG_OFFLINE);
            db.execSQL(CREATE_TABLE_RECENT);
            db.execSQL(CREATE_TABLE_RECENT_OFFLINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPlayListMyPlay(SQLiteDatabase db, String playlist, Boolean isOnline) {
        String table;
        if (isOnline) {
            table = TABLE_PLAYLIST;
        } else {
            table = TABLE_PLAYLIST_OFFLINE;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_PLAYLIST_NAME, playlist);
        db.insert(table, null, contentValues);

//        loadPlayList(isOnline);
    }

//    public ArrayList<ItemMyPlayList> addPlayList(String playlist, Boolean isOnline) {
//        String table;
//        if (isOnline) {
//            table = TABLE_PLAYLIST;
//        } else {
//            table = TABLE_PLAYLIST_OFFLINE;
//        }
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TAG_PLAYLIST_NAME, playlist);
//        db.insert(table, null, contentValues);
//
//        return loadPlayList(isOnline);
//    }

//    public void addToDownloads(ItemSong itemSong) {
//        String name = itemSong.getTitle().replace("'", "%27");
//        String description = DatabaseUtils.sqlEscapeString(itemSong.getDescription());
//        String lyrics = DatabaseUtils.sqlEscapeString(itemSong.getLyrics());
//
//        String imageBig = encryptData.encrypt(itemSong.getImageBig());
//        String url = encryptData.encrypt(itemSong.getUrl());
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TAG_SID, itemSong.getId());
//        contentValues.put(TAG_TITLE, name);
//        contentValues.put(TAG_DESC, description);
//        contentValues.put(TAG_LYRICS, lyrics);
//        contentValues.put(TAG_ARTIST, itemSong.getArtist());
//        contentValues.put(TAG_URL, url);
//        contentValues.put(TAG_IMAGE, imageBig);
//        contentValues.put(TAG_AVG_RATE, itemSong.getAverageRating());
//        contentValues.put(TAG_VIEWS, itemSong.getViews());
//        contentValues.put(TAG_DOWNLOADS, itemSong.getDownloads());
//        contentValues.put(TAG_TEMP_NAME, itemSong.getTempName());
//
//        db.insert(TABLE_DOWNLOAD_SONG, null, contentValues);
//    }

    @SuppressLint("Range")
//    public void addToRecent(ItemSong itemSong, Boolean isOnline) {
//        Cursor cursor_delete = db.query(TABLE_RECENT, columns_song, null, null, null, null, null);
//        if (cursor_delete != null && cursor_delete.getCount() > 20) {
//            cursor_delete.moveToFirst();
//            db.delete(TABLE_RECENT, TAG_SID + "=" + cursor_delete.getString(cursor_delete.getColumnIndex(TAG_SID)), null);
//        }
//        if (cursor_delete != null) {
//            cursor_delete.close();
//        }
//
//        String table, imageBig, url;
//        if (isOnline) {
//            table = TABLE_RECENT;
//        } else {
//            table = TABLE_RECENT_OFFLINE;
//        }
//        if (checkRecent(itemSong.getId(), isOnline)) {
//            db.delete(table, TAG_SID + "=" + itemSong.getId(), null);
//        }
//
//        imageBig = encryptData.encrypt(itemSong.getImageBig().replace(" ", "%20"));
//        url = encryptData.encrypt(itemSong.getUrl());
//
//        String description = DatabaseUtils.sqlEscapeString(itemSong.getDescription());
//        String lyrics = DatabaseUtils.sqlEscapeString(itemSong.getLyrics());
//        String name = itemSong.getTitle().replace("'", "%27");
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TAG_SID, itemSong.getId());
//        contentValues.put(TAG_TITLE, name);
//        contentValues.put(TAG_DESC, description);
//        contentValues.put(TAG_LYRICS, lyrics);
//        contentValues.put(TAG_ARTIST, itemSong.getArtist());
//        contentValues.put(TAG_URL, url);
//        contentValues.put(TAG_IMAGE, imageBig);
//        contentValues.put(TAG_AVG_RATE, itemSong.getAverageRating());
//        contentValues.put(TAG_VIEWS, itemSong.getViews());
//        contentValues.put(TAG_DOWNLOADS, itemSong.getDownloads());
//
//        db.insert(table, null, contentValues);
//    }


//    public void addToPlayList(ItemSong itemSong, String pid, Boolean isOnline) {
//        String tableName;
//        if (isOnline) {
//            tableName = TABLE_PLAYLIST_SONG;
//        } else {
//            tableName = TABLE_PLAYLIST_SONG_OFFLINE;
//
//        }
//        if (checkPlaylist(itemSong.getId(), isOnline)) {
//            db.delete(tableName, TAG_SID + "=" + itemSong.getId(), null);
//        }
//        String description = DatabaseUtils.sqlEscapeString(itemSong.getDescription());
//        String lyrics = DatabaseUtils.sqlEscapeString(itemSong.getLyrics());
//        String name = itemSong.getTitle().replace("'", "%27");
//
//        String imageBig = encryptData.encrypt(itemSong.getImageBig().replace(" ", "%20"));
//        String url = encryptData.encrypt(itemSong.getUrl());
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TAG_SID, itemSong.getId());
//        contentValues.put(TAG_PID, pid);
//        contentValues.put(TAG_TITLE, name);
//        contentValues.put(TAG_DESC, description);
//        contentValues.put(TAG_LYRICS, lyrics);
//        contentValues.put(TAG_ARTIST, itemSong.getArtist());
//        contentValues.put(TAG_URL, url);
//        contentValues.put(TAG_IMAGE, imageBig);
//        contentValues.put(TAG_AVG_RATE, itemSong.getAverageRating());
//        contentValues.put(TAG_VIEWS, itemSong.getViews());
//        contentValues.put(TAG_DOWNLOADS, itemSong.getDownloads());
//
//        db.insert(tableName, null, contentValues);
//    }
//
//    @SuppressLint("Range")
//    public ArrayList<ItemMyPlayList> loadPlayList(Boolean isOnline) {
//        ArrayList<ItemMyPlayList> arrayList = new ArrayList<>();
//
//        String tableName;
//        if (isOnline) {
//            tableName = TABLE_PLAYLIST;
//        } else {
//            tableName = TABLE_PLAYLIST_OFFLINE;
//        }
//
//        try {
//            Cursor cursor = db.query(tableName, columns_playlist, null, null, null, null, TAG_PLAYLIST_NAME + " ASC");
//            if (cursor != null && cursor.moveToFirst()) {
//                for (int i = 0; i < cursor.getCount(); i++) {
//                    String id = cursor.getString(cursor.getColumnIndex(TAG_PLAYLIST_ID));
//                    String name = cursor.getString(cursor.getColumnIndex(TAG_PLAYLIST_NAME));
//
//                    ItemMyPlayList objItem = new ItemMyPlayList(id, name, loadPlaylistImages(id, isOnline));
//                    arrayList.add(objItem);
//
//                    cursor.moveToNext();
//                }
//                cursor.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return arrayList;
//    }

    public void removeFromDownload(String id) {
        db.delete(TABLE_DOWNLOAD_SONG, TAG_SID + "=" + id, null);
    }

    public void removeFromPlayList(String id, Boolean isOnline) {
        String table;
        if (isOnline) {
            table = TABLE_PLAYLIST_SONG;
        } else {
            table = TABLE_PLAYLIST_SONG_OFFLINE;
        }
        db.delete(table, TAG_SID + "=" + id, null);
    }

    public void removePlayList(String pid, Boolean isOnline) {
        String tableName;
        if (isOnline) {
            tableName = TABLE_PLAYLIST;
        } else {
            tableName = TABLE_PLAYLIST_OFFLINE;
        }

        db.delete(tableName, TAG_ID + "=" + pid, null);
        removePlayListAllSongs(pid, isOnline);
    }

    private void removePlayListAllSongs(String pid, Boolean isOnline) {
        String tableName;
        if (isOnline) {
            tableName = TABLE_PLAYLIST_SONG;
        } else {
            tableName = TABLE_PLAYLIST_SONG_OFFLINE;
        }

        db.delete(tableName, TAG_PID + "=" + pid, null);
    }

    private Boolean checkRecent(String id, Boolean isOnline) {
        String table;
        if (isOnline) {
            table = TABLE_RECENT;
        } else {
            table = TABLE_RECENT_OFFLINE;
        }
        Cursor cursor = db.query(table, columns_song, TAG_SID + "=" + id, null, null, null, null);
        Boolean isFav = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return isFav;
    }

    private Boolean checkPlaylist(String id, Boolean isOnline) {
        String table;
        if (isOnline) {
            table = TABLE_PLAYLIST_SONG;
        } else {
            table = TABLE_PLAYLIST_SONG_OFFLINE;
        }

        Cursor cursor = db.query(table, columns_playlist_song, TAG_SID + "=" + id, null, null, null, null);
        Boolean isFav = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return isFav;
    }

    @SuppressLint("Range")
    public Boolean checkDownload(String id) {
        File root = new File(context.getExternalFilesDir("").getAbsolutePath() + "/temp");

        Cursor cursor = db.query(TABLE_DOWNLOAD_SONG, columns_download_song, TAG_SID + "=" + id, null, null, null, null);
        boolean isDownloaded;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String filename = cursor.getString(cursor.getColumnIndex(TAG_TEMP_NAME));
            File file = new File(root, filename + ".mp3");
            isDownloaded = file.exists();
            cursor.close();
        } else {
            isDownloaded = false;
        }
        return isDownloaded;
    }

//    public void addtoAbout() {
//        try {
//            db.delete(TABLE_ABOUT, null, null);
//
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(TAG_ABOUT_NAME, Constant.itemAbout.getAppName());
//            contentValues.put(TAG_ABOUT_LOGO, Constant.itemAbout.getAppLogo());
//            contentValues.put(TAG_ABOUT_VERSION, Constant.itemAbout.getAppVersion());
//            contentValues.put(TAG_ABOUT_AUTHOR, Constant.itemAbout.getAuthor());
//            contentValues.put(TAG_ABOUT_CONTACT, Constant.itemAbout.getContact());
//            contentValues.put(TAG_ABOUT_EMAIL, Constant.itemAbout.getEmail());
//            contentValues.put(TAG_ABOUT_WEBSITE, Constant.itemAbout.getWebsite());
//            contentValues.put(TAG_ABOUT_DESC, Constant.itemAbout.getAppDesc());
//            contentValues.put(TAG_ABOUT_PUB_ID, Constant.publisherAdID);
//            contentValues.put(TAG_ABOUT_BANNER_ID, Constant.bannerAdID);
//            contentValues.put(TAG_ABOUT_INTER_ID, Constant.interstitialAdID);
//            contentValues.put(TAG_ABOUT_NATIVE_ID, Constant.nativeAdID);
//            contentValues.put(TAG_ABOUT_IS_BANNER, Constant.isBannerAd);
//            contentValues.put(TAG_ABOUT_IS_INTER, Constant.isInterAd);
//            contentValues.put(TAG_ABOUT_IS_NATIVE, Constant.isNativeAd);
//            contentValues.put(TAG_ABOUT_START_APP_ID, Constant.startappAppId);
//            contentValues.put(TAG_ABOUT_WORTISE_APP_ID, Constant.wortiseAppId);
//            contentValues.put(TAG_ABOUT_CLICK, Constant.interstitialAdShow);
//            contentValues.put(TAG_ABOUT_IS_DOWNLOAD, String.valueOf(Constant.isSongDownload));
//
//            db.insert(TABLE_ABOUT, null, contentValues);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @SuppressLint("Range")
//    public Boolean getAbout() {
//        Cursor c = db.query(TABLE_ABOUT, columns_about, null, null, null, null, null);
//
//        if (c != null && c.getCount() > 0) {
//            c.moveToFirst();
//            for (int i = 0; i < c.getCount(); i++) {
//                String appname = c.getString(c.getColumnIndex(TAG_ABOUT_NAME));
//                String applogo = c.getString(c.getColumnIndex(TAG_ABOUT_LOGO));
//                String desc = c.getString(c.getColumnIndex(TAG_ABOUT_DESC));
//                String appversion = c.getString(c.getColumnIndex(TAG_ABOUT_VERSION));
//                String appauthor = c.getString(c.getColumnIndex(TAG_ABOUT_AUTHOR));
//                String appcontact = c.getString(c.getColumnIndex(TAG_ABOUT_CONTACT));
//                String email = c.getString(c.getColumnIndex(TAG_ABOUT_EMAIL));
//                String website = c.getString(c.getColumnIndex(TAG_ABOUT_WEBSITE));
//
//                Constant.bannerAdID = c.getString(c.getColumnIndex(TAG_ABOUT_BANNER_ID));
//                Constant.interstitialAdID = c.getString(c.getColumnIndex(TAG_ABOUT_INTER_ID));
//                Constant.nativeAdID = c.getString(c.getColumnIndex(TAG_ABOUT_NATIVE_ID));
//                Constant.isBannerAd = Boolean.parseBoolean(c.getString(c.getColumnIndex(TAG_ABOUT_IS_BANNER)));
//                Constant.isInterAd = Boolean.parseBoolean(c.getString(c.getColumnIndex(TAG_ABOUT_IS_INTER)));
//                Constant.isNativeAd = Boolean.parseBoolean(c.getString(c.getColumnIndex(TAG_ABOUT_IS_NATIVE)));
//                Constant.publisherAdID = c.getString(c.getColumnIndex(TAG_ABOUT_PUB_ID));
//                Constant.startappAppId = c.getString(c.getColumnIndex(TAG_ABOUT_START_APP_ID));
//                Constant.wortiseAppId = c.getString(c.getColumnIndex(TAG_ABOUT_WORTISE_APP_ID));
//                Constant.interstitialAdShow = Integer.parseInt(c.getString(c.getColumnIndex(TAG_ABOUT_CLICK)));
//                Constant.isSongDownload = Boolean.parseBoolean(c.getString(c.getColumnIndex(TAG_ABOUT_IS_DOWNLOAD)));
//
//                Constant.itemAbout = new ItemAbout(appname, applogo, desc, appversion, appauthor, appcontact, email, website);
//            }
//            c.close();
//            return true;
//        } else {
//            if (c != null) {
//                c.close();
//            }
//            return false;
//        }
//    }

    @SuppressLint("Range")
    public String getRecentNewsIDs(String limit) {
        String newsIDs = "";

        Cursor cursor = db.query(TABLE_RECENT, new String[]{TAG_SID}, null, null, null, null, TAG_ID + " DESC", limit);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            newsIDs = cursor.getString(cursor.getColumnIndex(TAG_SID));
            cursor.moveToNext();
            for (int i = 1; i < cursor.getCount(); i++) {
                newsIDs = newsIDs.concat(",").concat(cursor.getString(cursor.getColumnIndex(TAG_SID)));

                cursor.moveToNext();
            }
            cursor.close();
        }
        return newsIDs;
    }

//    @SuppressLint("Range")
//    public ArrayList<ItemSong> loadDataRecent(Boolean isOnline, String limit) {
//        ArrayList<ItemSong> arrayList = new ArrayList<>();
//        String table;
//        if (isOnline) {
//            table = TABLE_RECENT;
//        } else {
//            table = TABLE_RECENT_OFFLINE;
//        }
//        Cursor cursor;
//        if (isOnline) {
//            cursor = db.query(table, columns_song, null, null, null, null, TAG_ID + " DESC", limit);
//        } else {
//            cursor = db.query(table, columns_song, null, null, null, null, TAG_ID + " DESC");
//        }
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            for (int i = 0; i < cursor.getCount(); i++) {
//
//                String id = cursor.getString(cursor.getColumnIndex(TAG_SID));
//                String artist = cursor.getString(cursor.getColumnIndex(TAG_ARTIST));
//                String name = cursor.getString(cursor.getColumnIndex(TAG_TITLE)).replace("%27", "'");
//
//                String desc = cursor.getString(cursor.getColumnIndex(TAG_DESC));
//                String lyrics = cursor.getString(cursor.getColumnIndex(TAG_LYRICS));
//
//                String url, imagebig;
//
//                url = encryptData.decrypt(cursor.getString(cursor.getColumnIndex(TAG_URL)));
//                imagebig = encryptData.decrypt(cursor.getString(cursor.getColumnIndex(TAG_IMAGE)));
//
//                String avg_rate = cursor.getString(cursor.getColumnIndex(TAG_AVG_RATE));
//                String views = cursor.getString(cursor.getColumnIndex(TAG_VIEWS));
//                String downloads = cursor.getString(cursor.getColumnIndex(TAG_DOWNLOADS));
//
//                ItemSong objItem = new ItemSong(id, artist, url, imagebig, name, desc, lyrics, avg_rate, views, downloads, false);
//                arrayList.add(objItem);
//
//                cursor.moveToNext();
//            }
//        }
//        if (cursor != null) {
//            cursor.close();
//        }
//        return arrayList;
//    }
//
//    @SuppressLint("Range")
//    public ArrayList<ItemSong> loadDataPlaylist(String pid, Boolean isOnline) {
//        String table;
//        if (isOnline) {
//            table = TABLE_PLAYLIST_SONG;
//        } else {
//            table = TABLE_PLAYLIST_SONG_OFFLINE;
//        }
//        ArrayList<ItemSong> arrayList = new ArrayList<>();
//
//        Cursor cursor = db.query(table, columns_playlist_song, TAG_PID + "=" + pid, null, null, null, "");
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            for (int i = 0; i < cursor.getCount(); i++) {
//
//                String id = cursor.getString(cursor.getColumnIndex(TAG_SID));
//                String artist = cursor.getString(cursor.getColumnIndex(TAG_ARTIST));
//                String name = cursor.getString(cursor.getColumnIndex(TAG_TITLE)).replace("%27", "'");
//
//                String desc = cursor.getString(cursor.getColumnIndex(TAG_DESC));
//                String lyrics = cursor.getString(cursor.getColumnIndex(TAG_LYRICS));
//
//                String url = encryptData.decrypt(cursor.getString(cursor.getColumnIndex(TAG_URL)));
//                String imagebig = encryptData.decrypt(cursor.getString(cursor.getColumnIndex(TAG_IMAGE)));
//
//                String avg_rate = cursor.getString(cursor.getColumnIndex(TAG_AVG_RATE));
//                String views = cursor.getString(cursor.getColumnIndex(TAG_VIEWS));
//                String downloads = cursor.getString(cursor.getColumnIndex(TAG_DOWNLOADS));
//
//                ItemSong objItem = new ItemSong(id, artist, url, imagebig, name, desc, lyrics, avg_rate, views, downloads, false);
//                arrayList.add(objItem);
//
//                cursor.moveToNext();
//            }
//        }
//        if (cursor != null) {
//            cursor.close();
//        }
//        return arrayList;
//    }
//
//
//
//    @SuppressLint("Range")
//    public ArrayList<String> loadPlaylistImages(String pid, Boolean isOnline) {
//        String table;
//        if (isOnline) {
//            table = TABLE_PLAYLIST_SONG;
//        } else {
//            table = TABLE_PLAYLIST_SONG_OFFLINE;
//        }
//        ArrayList<String> arrayList = new ArrayList<>();
//
//        Cursor cursor = db.query(table, new String[]{TAG_IMAGE}, TAG_PID + "=" + pid, null, null, null, "");
//
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            for (int i = 0; i < 4; i++) {
//
//                try {
//                    String imagesmall = encryptData.decrypt(cursor.getString(cursor.getColumnIndex(TAG_IMAGE)));
//                    arrayList.add(imagesmall);
//                    cursor.moveToNext();
//                } catch (Exception e) {
//                    cursor.moveToFirst();
//                    String imagesmall = encryptData.decrypt(cursor.getString(cursor.getColumnIndex(TAG_IMAGE)));
//                    arrayList.add(imagesmall);
//                }
//            }
//            Collections.reverse(arrayList);
//        } else {
//            arrayList.add("1");
//            arrayList.add("1");
//            arrayList.add("1");
//            arrayList.add("1");
//        }
//        if(cursor != null) {
//            cursor.close();
//        }
//        return arrayList;
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close () {
        if (db != null) {
            db.close();
            super.close();
        }
    }
}