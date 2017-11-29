package com.example.buh.homebalance;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


      public class Balance_DBHelper  {
    Cursor c;

BalanceDataBase openHelper;
    Balance_Item item;
    private SQLiteDatabase db;
    public Balance_DBHelper(Context context){
        openHelper = new BalanceDataBase(context);
db = openHelper.getWritableDatabase();


    }
    public void close(){
c.close();
        db.close();
    }
    public void saveArticleItem(String tablename, Balance_Item item){


            ContentValues cv = new ContentValues();
            cv.put(Banance_Contract.COLUMN_DATE, item.getDate());
            cv.put(Banance_Contract.COLUMN_NAME, item.getItem_name());
            cv.put(Banance_Contract.COLUMN_SUM, item.getSum());

           db.insert(tablename, null, cv);

        }
    public void saveBalanceSum(String tablename, int sum){
        ContentValues cv = new ContentValues();
        cv.put(Banance_Contract.COLUMN_SUM, sum);
        db.insert(tablename, null, cv);
    }
        public Cursor queryDB(String tablename){
            Cursor c = db.rawQuery("SELECT*FROM "+tablename,null);
            return  c;

        }
        public void remove(String tablename, int orderInList){
            List<Integer> database_ids = new ArrayList<Integer>();
            Cursor c = db.query(tablename,null,null,null,null,null,Banance_Contract.COLUMN_DATE + " DESC");
            while(c.moveToNext()){
                database_ids.add(Integer.parseInt(c.getString(0)));
            }
            db.delete(tablename,Banance_Contract.COLUMN_ID + " =?",new String[]{String.valueOf(database_ids.get(orderInList))});
            c.close();
        }

          public void update(String tablename, int orderInList, String date, String name, int sum){
              List<Integer> database_ids = new ArrayList<Integer>();
              ContentValues cv = new ContentValues();
              Cursor c = db.query(tablename,null,null,null,null,null,Banance_Contract.COLUMN_DATE + " DESC");
              while(c.moveToNext()){
                  database_ids.add(Integer.parseInt(c.getString(0)));
              }
              db.update(tablename, cv, Banance_Contract.COLUMN_ID + " =?",new String[]{String.valueOf(database_ids.get(orderInList))});

              c.close();

          }
    public ArrayList<Balance_Item> showDataByDate(String tablename, String from, String to){
        ArrayList<Balance_Item> list = new ArrayList<>();
        String[] columns = new  String [] {Banance_Contract.COLUMN_DATE, Banance_Contract.COLUMN_NAME, Banance_Contract.COLUMN_SUM};
        Cursor c = db.query(tablename, columns, Banance_Contract.COLUMN_DATE + " BETWEEN '" + from +  "' AND '" + to + "' ",null ,null,null,
                Banance_Contract.COLUMN_DATE + " DESC");
        if (c!=null){
        if (c.moveToFirst()){
                    do {

                            String date = c.getString(c.getColumnIndex(Banance_Contract.COLUMN_DATE));
                        String name = c.getString(c.getColumnIndex(Banance_Contract.COLUMN_NAME));
                        int sum = Integer.parseInt(c.getString(c.getColumnIndex(Banance_Contract.COLUMN_SUM)));
                       list.add(new Balance_Item(date, name,sum));
                       for (Balance_Item item: list){
                           Log. d("my", item.getDate() + " " + item.getSum());

                       }

                    }
                 while (c.moveToNext());
                }}
c.close();
        return list;
    }
    public Balance_Item showData(String tablename, String from, String to){

        String[] columns = new  String [] { Banance_Contract.COLUMN_DATE, Banance_Contract.COLUMN_NAME, Banance_Contract.COLUMN_SUM};

        Cursor c = db.query(tablename, columns, Banance_Contract.COLUMN_DATE + " BETWEEN '" + from +  "' AND '" + to + "' ",null ,null,null,
                Banance_Contract.COLUMN_DATE + " DESC");
        if (c!=null){
            if (c.moveToFirst()){
                do {

                    String date = c.getString(c.getColumnIndex(Banance_Contract.COLUMN_DATE));
                    String name = c.getString(c.getColumnIndex(Banance_Contract.COLUMN_NAME));
                    int sum = Integer.parseInt(c.getString(c.getColumnIndex(Banance_Contract.COLUMN_SUM)));
                    item = new Balance_Item(date, name,sum);}
                while (c.moveToNext());
        c.close();

    }}
        return item;
    }
    static class BalanceDataBase extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "balance.db";

    private BalanceDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

            final String SQL_CREATE_INCOMES_TABLE = "CREATE TABLE " + Banance_Contract.TABLE_NAME_INCOMES + " (" +

                    Banance_Contract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    Banance_Contract.COLUMN_DATE + " TEXT NOT NULL, " +
                    Banance_Contract.COLUMN_NAME + " TEXT NOT NULL, " +
                    Banance_Contract.COLUMN_SUM + " INTEGER NOT NULL " + ");" ;

        final String SQL_CREATE_CHARGES_TABLE = "CREATE TABLE " + Banance_Contract.TABLE_NAME_CHARGES + " (" +

                Banance_Contract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                Banance_Contract.COLUMN_DATE + " TEXT NOT NULL, " +
                Banance_Contract.COLUMN_NAME + " TEXT NOT NULL, " +
                Banance_Contract.COLUMN_SUM + " INTEGER NOT NULL " + ");" ;

        final String SQL_CREATE_BALANCE_TABLE = "CREATE TABLE " + Banance_Contract.TABLE_NAME_BALANCE + " (" +

                Banance_Contract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Banance_Contract.COLUMN_SUM + " INTEGER NOT NULL " + ");" ;
        sqLiteDatabase.execSQL(SQL_CREATE_INCOMES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CHARGES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_BALANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Banance_Contract.TABLE_NAME_CHARGES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Banance_Contract.TABLE_NAME_CHARGES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Banance_Contract.TABLE_NAME_BALANCE);
        onCreate(sqLiteDatabase);
    }

}
}