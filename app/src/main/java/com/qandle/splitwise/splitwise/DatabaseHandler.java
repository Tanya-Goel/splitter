package com.qandle.splitwise.splitwise;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactsManager";

    private static final String PEOPLES = "peoples";
    private static final String TABLE_ALL_BILLS = "all_bills";
    private static final String TABLE_BILLS = "bills";
    private static final String TABLE_PAID_BY = "paid_by";
    private static final String TABLE_SHARED_BY = "shared_by";


    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    private static final String KEY_DATE = "date";
    private static final String KEY_NAMES = "names";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_DESCRIPTION = "description";
    private static final String BILL_ID = "bill_id";
    private static final String PERSON_ID = "person_id";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + PEOPLES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_TRAVELS_TABLE = "CREATE TABLE " + TABLE_ALL_BILLS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_NAMES + " TEXT," + KEY_AMOUNT + " NUMBER" + ")";
        db.execSQL(CREATE_TRAVELS_TABLE);
//
//        String CREATE_BILLS_TABLE = "CREATE TABLE " + TABLE_BILLS + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
//                + KEY_DESCRIPTION + " TEXT,"+KEY_AMOUNT+" TEXT" + ")";
//        db.execSQL(CREATE_BILLS_TABLE);

        String CREATE_PAID_BY_TABLE = "CREATE TABLE " + TABLE_PAID_BY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + PERSON_ID + " INTEGER,"
                + BILL_ID + " INTEGER," + KEY_AMOUNT + " NUMBER" + ")";
        db.execSQL(CREATE_PAID_BY_TABLE);

        String CREATE_SHARED_BY_TABLE = "CREATE TABLE " + TABLE_SHARED_BY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + BILL_ID + " INTEGER," + PERSON_ID + " INTEGER," + KEY_AMOUNT + " NUMBER" + ")";
        db.execSQL(CREATE_SHARED_BY_TABLE);


        autoAddContact(db, new People("pradeep"));
        autoAddContact(db, new People("rajender"));
        autoAddContact(db, new People("gaurav"));
        autoAddContact(db, new People("anmol"));
        autoAddContact(db, new People("naman"));
        autoAddContact(db, new People("rahul"));
        autoAddContact(db, new People("rajeev ranjan"));

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PEOPLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_BILLS);
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(People contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // People Name
//        values.put(KEY_PH_NO, contact.getAmount()); // People Phone

        // Inserting Row
        db.insert(PEOPLES, null, values);
        db.close(); // Closing database connection
    }

    public static long autoAddContact(SQLiteDatabase db, People contact) {
        ContentValues values = new  ContentValues();
        values.put(KEY_NAME, contact.getName()); // People Name
//        values.put(KEY_PH_NO, contact.getAmount()); // People Phone

        // Inserting Row
        return db.insert(PEOPLES, null, values);
    }

    // Getting single contact
    People getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PEOPLES, new String[]{KEY_ID,
                        KEY_NAME, KEY_PH_NO}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        People contact = new People(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<People> getAllContacts() {
        List<People> contactList = new ArrayList<People>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PEOPLES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                People contact = new People();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
//                contact.setAmount(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(People contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getAmount());

        // updating row
        return db.update(PEOPLES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
    }

    // Deleting single contact
    public void deleteContact(People contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PEOPLES, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + PEOPLES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    // Adding new contact
    long addTravels(Bills bills) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMES, bills.getTitle()); // People Name
        values.put(KEY_AMOUNT, bills.getAmount()); // People Phone
        values.put(KEY_DATE, bills.getDate()); // People Phone

        // Inserting Row
        long d = db.insert(TABLE_ALL_BILLS, null, values);
        db.close(); // Closing database connection
        return d;
    }

    long addper_person(int person_id, int key_amount, long bill_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERSON_ID, person_id);
        values.put(KEY_AMOUNT, key_amount);
        values.put(BILL_ID, bill_id);
        long a = db.insert(TABLE_SHARED_BY, null, values);
        db.close();
        return a;

    }

    long paid_by_data(int person_id, int key_amount, long bill_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERSON_ID, person_id);
        values.put(KEY_AMOUNT, key_amount);
        values.put(BILL_ID, bill_id);
        long p = db.insert(TABLE_PAID_BY, null, values);
        db.close();
        return p;

    }

    // Getting All Contacts
    public List<Bills> getAllTravels() {
        List<Bills> contactList = new ArrayList<Bills>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ALL_BILLS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Bills contact = new Bills();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setDate(cursor.getString(1));
                contact.setTitle(cursor.getString(2));
                contact.setAmount(cursor.getString(3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public HashMap<String,AmountTransaction> query() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + TABLE_SHARED_BY + "." + PERSON_ID + " as payee, " + TABLE_SHARED_BY + "." + KEY_AMOUNT + " as amount, " + TABLE_PAID_BY + "." + PERSON_ID +
                " as receiver from " + TABLE_SHARED_BY  + " LEFT JOIN " + TABLE_PAID_BY + " ON " + TABLE_SHARED_BY + "." + BILL_ID + " = " + TABLE_PAID_BY + "." + BILL_ID, null);

        HashMap<String,AmountTransaction> data = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                int payee = Integer.parseInt(cursor.getString(0));
                int receiver = Integer.parseInt(cursor.getString(2));
                double amount = Double.parseDouble(cursor.getString(1));

                String map = payee+"->"+receiver;
                if(data.containsKey(map))
                {
                    data.get(map).addAmount(amount);
                }
                else
                {
                    data.put(map,new AmountTransaction(payee,receiver,amount));
                }

                Log.i("Myapp", "Payee*" + cursor.getString(0) + " Reciver*" + cursor.getString(2) + " Amount*" + cursor.getString(1));
            }
            while (cursor.moveToNext());

        }
        return data;
    }
}
