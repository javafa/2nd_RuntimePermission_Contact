package com.veryworks.android.runtimepermission;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by pc on 2/1/2017.
 */

public class DataLoader {

    private ArrayList<Contact> datas = new ArrayList<>();
    private Context context;

    public DataLoader(Context context){
        this.context = context;
    }


    public ArrayList<Contact> get(){
        return datas;
    }

    public void load(){
        // 1. 주소록에 접근하기위해 ContentResolver 를 불러온다.
        ContentResolver resolver = context.getContentResolver();

        // 2. 주소록에서 가져올 데이터 컬럼명을 정의한다.
        String projections[] = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,   // 데이터의 아이디
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, // 이름
                ContactsContract.CommonDataKinds.Phone.NUMBER        // 전화번호
        };

        // 3. Content Resolver 로 쿼리한 데이터를 커서에 담는다.
        // 전화번호 URI : ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 주소록 URI : ContactsContract.Contacts.CONTENT_URI
        //             HAS_PHONE_NUMBER : 전화번호가 있는지 확인하는 상수
        Cursor cursor = resolver.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI  // 데이터의 주소
                        , projections            // 가져올 데이터 컬럼명 배열
                        , null                   // 조건절에 들어가는 컬럼명들 지정
                        , null                   // 지정된 컬럼명과 매핑되는 실제 조건 값
                        , null                   // 정렬
        );

        if(cursor != null) {
            // 4. 커서에 넘어온 데이터가 있다면 반복문을 돌면서 datas 에 담아준다
            while( cursor.moveToNext() ){
                Contact contact = new Contact();
                // 5. 커서의 컬럼 인덱스를 가져온후
                int idx = cursor.getColumnIndex(projections[0]);
                // 5.1 컬럼인덱스에 해당하는 타입에 맞게 값을 꺼내서 세팅한다
                contact.setId(cursor.getInt(idx));

                idx = cursor.getColumnIndex(projections[1]);
                contact.setName(cursor.getString(idx));
                idx = cursor.getColumnIndex(projections[2]);
                contact.addTel(cursor.getString(idx));

                datas.add(contact);
            }

            // * 중요 : 사용 후 close 를 호출하지 않으면 메모리 누수가 발생할 수 있다.
            cursor.close();
        }
    }
}
