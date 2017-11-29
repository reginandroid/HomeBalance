package com.example.buh.homebalance;




public class Balance_Item {
    private String date;
    private String item_name;
    private int sum;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Balance_Item(String date, String item_name, int sum) {
        this.date = date;
        this.item_name = item_name;
        this.sum = sum;
    }
    public Balance_Item(int id, String date, String item_name, int sum) {
        this.id = id;
        this.date = date;
        this.item_name = item_name;
        this.sum = sum;
    }
    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getSum() {
        return sum;
    }


    public void setSum(int sum) {
        this.sum = sum;
    }
}
