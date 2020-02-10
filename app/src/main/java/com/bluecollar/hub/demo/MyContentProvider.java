package com.bluecollar.hub.demo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    private final String TABLE_NAME = "Book";
    MyDatabaseHelper helper;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        helper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 1);
        db = helper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return db.query(TABLE_NAME, projection, selection, selectionArgs, null,
                null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = db.insert(TABLE_NAME, null, values);
        if (rowId > 0) {
            // 插入成功则返回新的把id添加进去形成新的uri
            Uri newUri = ContentUris.withAppendedId(uri, rowId);
            // 通知数据已更新
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return db.delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return db.update(TABLE_NAME, values, selection, selectionArgs);
    }
}
