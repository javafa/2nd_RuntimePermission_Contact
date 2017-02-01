package com.veryworks.android.runtimepermission;

import java.util.ArrayList;

/**
 * Created by pc on 2/1/2017.
 */

public class DataLoader {

    private ArrayList<Contact> datas = new ArrayList<>();

    public ArrayList<Contact> get(){
        return datas;
    }

    public void load(){
        for(int i=0 ; i<100 ; i++){
            Contact contact = new Contact();
            contact.setId(i+1);
            contact.setName("홍길동 " + contact.getId());
            String temp = String.format("%02d",i);
            contact.addTel("010-1234-56" + temp);

            datas.add(contact);
        }
    }
}
