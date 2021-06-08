package com.example.sellall.AdapterData;

import java.util.ArrayList;

public class UserSellData {
    public UserSellData(){}
    String itemname,quantity,contactno,address,pincode,personname;
    ArrayList<String> a=new ArrayList<>();

    public UserSellData(String itemname, String quantity, String contactno, String address, String pincode) {
        this.itemname = itemname;
        this.quantity = quantity;
        this.contactno = contactno;
        this.address = address;
        this.pincode = pincode;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public UserSellData(String itemname, String quantity, String contactno, String address, String pincode, ArrayList<String> a) {
        this.itemname = itemname;
        this.quantity = quantity;
        this.contactno = contactno;
        this.address = address;
        this.pincode = pincode;
        this.a = a;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setA(ArrayList<String> a) {
        this.a = a;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getContactno() {
        return contactno;
    }

    public String getAddress() {
        return address;
    }

    public String getPincode() {
        return pincode;
    }

    public ArrayList<String> getA() {
        return a;
    }
}
