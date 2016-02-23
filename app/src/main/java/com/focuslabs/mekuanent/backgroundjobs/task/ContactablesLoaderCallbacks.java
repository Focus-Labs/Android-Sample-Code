package com.focuslabs.mekuanent.backgroundjobs.task;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mekuanent on 2/19/16.
 */
public class ContactablesLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "LoaderCallbacks";
    private final Activity mContext;
    private TextView textView;

    public ContactablesLoaderCallbacks(Activity context,TextView textView) {
        mContext = context;
        this.textView = textView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String sortBy = ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME;

        return new CursorLoader(
                mContext,
                uri,
                null,
                null,
                null,
                sortBy);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {



        int emailColumnIndex = cursor.getColumnIndex(CommonDataKinds.Email.ADDRESS);
        int nameColumnIndex = cursor.getColumnIndex(CommonDataKinds.Contactables.DISPLAY_NAME);
        int lookupColumnIndex = cursor.getColumnIndex(CommonDataKinds.Contactables.LOOKUP_KEY);
        int typeColumnIndex = cursor.getColumnIndex(CommonDataKinds.Contactables.MIMETYPE);
        int imageColumnIndex = cursor.getColumnIndex(CommonDataKinds.Contactables.PHOTO_URI);
        int hasPhoneNumberIndex = cursor.getColumnIndex(CommonDataKinds.Contactables.HAS_PHONE_NUMBER);
        int contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);


        cursor.moveToFirst();

        ArrayList<String> phoneNums = new ArrayList<>();

        ContentResolver contentResolver = mContext.getContentResolver();

        StringBuilder builder = new StringBuilder();

        do{
            phoneNums.clear();

            if(cursor.getInt(hasPhoneNumberIndex) == 1) {

                Cursor pCur = contentResolver.query(CommonDataKinds.Phone.CONTENT_URI,null,
                        CommonDataKinds.Phone.CONTACT_ID + " = " + cursor.getString(contactIdIndex), null, null);
                int phoneColumnIndex = pCur.getColumnIndex(CommonDataKinds.Phone.NUMBER);
                ArrayList<String> phones = new ArrayList<>();
                try {
                    while(pCur.moveToNext()) {
                        phones.add(pCur.getString(phoneColumnIndex));
                    }

                }catch (Exception e){

                }


                builder.append(cursor.getString(nameColumnIndex) + "  " + (phones.size() > 0? phones.get(0) : "-") + "\n");

            }

        }while(cursor.moveToNext());


        textView.setText(builder.toString());

        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}