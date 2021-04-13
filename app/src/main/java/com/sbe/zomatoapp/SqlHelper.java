package com.sbe.zomatoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class SqlHelper  extends SQLiteOpenHelper {
    public Context cob;
    public static final String KEY_ID="_id";
    public static final String NAME="_name";
    public static final String CATEGORY="_category";

    private static final String DB_NAME="fav.db";
    private static final String TAB_NAME="favorate";
    private static final int DB_VER=1;
    private static final  String CREATE_QUERY = "create table " + TAB_NAME +
            " ( " + KEY_ID +
            " integer primary key autoincrement, " +
            NAME + " text not null ,"+CATEGORY +" text not null);";
    public SqlHelper(Context context) {
        super(context,DB_NAME,null,DB_VER);
        this.cob=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TAB_NAME);

        onCreate(sqLiteDatabase);
    }
    public void DeleteUser(SqlHelper mob,String name,String category){
        String selectoion_clause=NAME + " LIKE ? AND " + CATEGORY + " LIKE ? ";
        String parameter[]={name,category};
        SQLiteDatabase sql=mob.getWritableDatabase();

        //sqLiteDatabse.delete(TABLENAME,WEHERCLAUSE,PARAMETER);
        sql.delete(TAB_NAME,selectoion_clause,parameter);
    }
    public void putInfo(String name,String category)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(CATEGORY,category);
        long result=sqLiteDatabase.insert(TAB_NAME,null,contentValues);
        if (result==-1){
            Toast.makeText(cob, "Failed!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(cob, "Succefully Inserted!", Toast.LENGTH_SHORT).show();
            }
    }
    Cursor getInfo(){
        String ret_query="SELECT * FROM "+TAB_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor=null;
        if (sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(ret_query,null);
        }
        return cursor;
    }

}
