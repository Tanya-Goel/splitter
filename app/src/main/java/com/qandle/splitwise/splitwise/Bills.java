package com.qandle.splitwise.splitwise;




public class Bills {

    //private variables
    int _id;
    String date;
    String title;
    String amount;

    // Empty constructor
    public Bills(){

    }
    // constructor
    public Bills(int id, String date, String title, String amount){
        this._id = id;
        this.title = title;
        this.amount = amount;
        this.date=date;
    }

    // constructor
    public Bills(String date, String name, String amount){
        this.title = name;
        this.date=date;
        this.amount = amount;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getTitle(){
        return this.title;
    }

    // setting name
    public void setTitle(String name){
        this.title = name;
    }

    // getting phone number
    public String getAmount(){
        return this.amount;
    }

    // setting phone number
    public void setAmount(String phone_number){
        this.amount = phone_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Date: "+ getDate() +"\nName: " + getTitle() + "\nAmount: " + getAmount();
    }
}
