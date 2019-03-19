package com.flatondemand.user.fod.App;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final String DATABASE_NAME = "fod";
    private static final int DATABASE_VERSION = 2;
    //table name
    private static final String TABLE_USER="user";
    private static final String TABLE_LOCATION = "location";
    private static final String TABLE_PROPERTY="property";
    //columns name for table USER
    private static final String TABLE_USER_ID = "id";
    private static final String TABLE_USER_NAME="name";
    private static final String TABLE_USER_MOBILE="mobile";
    private static final String TABLE_USER_EMAIL="email";
    //columns name for location
    private  static final String TABLE_LOCATION_ID="id";
    private  static final String TABLE_LOCATION_LOCATION="location";
    //property table Columns
    private static   final String PropertyId="property_id";
    private static   final String PropertyName="property_name";
    private static   final String PropertyCoverImage="property_cover_image";
    private static   final String PropertyPrice="property_price";
    private static   final String PropertyUid="property_uid";
    private static   final String PropertyAdd="property_address";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{

            String create_property_table="CREATE TABLE   "+TABLE_PROPERTY +" ("+PropertyId +" INTEGER PRIMARY KEY,"+ PropertyName +" TEXT, "+
                    PropertyCoverImage+" TEXT, "+PropertyPrice +" VARCHAR(256), "+PropertyUid+ " VARCHAR(256), "+PropertyAdd+" TEXT  )";
            Log.d(TAG+ "Property" , create_property_table);
            db.execSQL(create_property_table);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
        String CREATE_USER_TABLE="create table "+TABLE_USER +" ( "+TABLE_USER_ID  +" INTEGER PRIMARY KEY , "+ TABLE_USER_NAME+
                " VARCHAR (128) ,"+ TABLE_USER_MOBILE+" VARCHAR (12) ,"+ TABLE_USER_EMAIL+ " TEXT UNIQUE )";
        db.execSQL(CREATE_USER_TABLE);
            String CREATE_LOCATION_TABLE =  "CREATE TABLE " + TABLE_LOCATION + "("
                    + TABLE_LOCATION_ID + " INTEGER PRIMARY KEY," + TABLE_LOCATION_LOCATION + ")";
            db.execSQL(CREATE_LOCATION_TABLE);


        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG+"creattion" , ""+e.getLocalizedMessage());
        }



    }
    /* property details */
    public void addProperty(String propertyName , String propertyPrice , String propertyUid ,
                            String propertyCoverImage , String propertyAdd){

        SQLiteDatabase db;
        db=this.getReadableDatabase();
        String selectQuery="select * from "+TABLE_PROPERTY;
        long id=0;
        Cursor cursor=db.rawQuery(selectQuery , null);
        cursor.moveToFirst();
        if(cursor.getCount() >0){
            //update table else
            db=this.getWritableDatabase();
            try {
                db.execSQL("update "+TABLE_PROPERTY+" set "+PropertyName+"='"+propertyName+"' , "+PropertyAdd+"= '"+propertyAdd+"', "
                        +PropertyUid+"= '"+propertyUid+"', "+PropertyPrice+"= '"+propertyPrice+"' ,"+ PropertyCoverImage+"='"+propertyCoverImage+"' where "
                        +PropertyId+"=1");
            }catch (Exception e){
                Log.d(TAG, e.getLocalizedMessage());
            }
        }else{
            //insert
            db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(PropertyName , propertyName);
            contentValues.put(PropertyUid , propertyUid);
            contentValues.put(PropertyCoverImage , propertyCoverImage);
            contentValues.put(PropertyAdd , propertyAdd);
            contentValues.put(PropertyPrice,propertyPrice);
            try {
                id=db.insert(TABLE_PROPERTY ,null, contentValues);

            }catch (Exception e){
                Log.d(TAG , ""+e.getLocalizedMessage());
                Log.d(TAG, "New Property  inserted into sqlite: " + id);
            }
        }

    }
    /*
     * Storing user details in database
     * */
    public void addLocation(String location) {
        SQLiteDatabase db;
        String loc="'"+location+"'";
        String selectQuery="SELECT  * FROM "+TABLE_LOCATION;
        long id = 0;
        db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery , null);
        //move to first row
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            //map.put("location",cursor.getString(1));
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TABLE_LOCATION_LOCATION, location); // Name
            try {
                db.execSQL("update "+TABLE_LOCATION +" set "+TABLE_LOCATION_LOCATION+"="+loc+" where "+TABLE_LOCATION_ID+" =1");
                Log.d(TAG, "New location updated into sqlite: " + id);
            }catch (Exception e){
                Log.d(TAG , ""+e.getLocalizedMessage());
            }
        }else{
            // Inserting Row
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TABLE_LOCATION_LOCATION, location); // Name
            id = db.insert(TABLE_LOCATION, null, values);
            Log.d(TAG, "New location inserted into sqlite: " + id);
        }
        cursor.close();
        db.close();



    }
    public  void insertUser(String name , String email , String mobile){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TABLE_USER_NAME , name);
        contentValues.put(TABLE_USER_EMAIL , email);
        contentValues.put(TABLE_USER_MOBILE, mobile);
        long id = db.insert(TABLE_USER, null,contentValues);
        db.close(); // Closing database connection
        Log.d(TAG, "New user inserted into sqlite: " + id);

    }
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToLast();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("phone", cursor.getString(3));
            //user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }
    public HashMap<String , String>getLocation(){
        HashMap<String , String>map = new HashMap<String, String>();
        String selectQuery="SELECT  * FROM "+TABLE_LOCATION;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery , null);
        //move to first row
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            map.put("location",cursor.getString(1));
        }
        cursor.close();
        db.close();
        return map;
    }
    public  HashMap<String,String>getProperty(){
        HashMap<String , String>map = new HashMap<String, String>();
        String query="select * from "+TABLE_PROPERTY;
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            map.put("propertyName",cursor.getString(1));
            map.put("propertyCoverImage",cursor.getString(2));
            map.put("propertyPrice",cursor.getString(3));
            map.put("propertyUid",cursor.getString(4));
            map.put("propertyAdd",cursor.getString(5));
        }

        return  map;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY);
        onCreate(db);
    }
}
