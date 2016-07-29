package com.example.thanh.mobilemonitoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import Models.Contact;

public class ContactView extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList1;
    int i=0;
    Button btn;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        Bundle bundle = this.getIntent().getExtras();
        String ref = bundle.getString("ref") + "/Contacts/";
        listView = (ListView)findViewById(R.id.listViewContacts);
        Log.e("reff: ", ref);
        btn = (Button)findViewById(R.id.buttonViewContact);

        Firebase firebase = new Firebase(ref);
        final ArrayList<Contact> arrayList = new ArrayList<Contact>();

         arrayList1 = new ArrayList<String>();
//        arrayList1.add("hai linh");
//        arrayList1.add("Tien Huy");
//        arrayList1.add(arrayList.get(4).getName());

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Contact contact = snapshot.getValue(Contact.class);

                    arrayList1.add(contact.getName());
                    Log.d("name", arrayList1.get(i++));

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList2.add("hai linh");
        arrayList2.add("tien huy");
        adapter = new ArrayAdapter(ContactView.this,
                android.R.layout.simple_list_item_1,
                arrayList1);
//        listView.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(adapter);
            }
        });
    }
}
