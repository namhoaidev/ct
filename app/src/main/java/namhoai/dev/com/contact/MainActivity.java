package namhoai.dev.com.contact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import namhoai.dev.com.contact.model.Contact;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listView) ListView listView;
    List<Contact> contacts;
    List<String> displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayName);
        listView.setAdapter(arrayAdapter);
    }

    private void initData() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID, ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY},
                null, null, null);
        contacts = new ArrayList<>();

        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.set_id(cursor.getLong(cursor.getColumnIndex(ContactsContract.RawContacts._ID)));
                    contact.setDisplayName(cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY)));

                    contacts.add(contact);
                    displayName.add(cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY)));
                }while (cursor.moveToNext());
            }
        }
    }
}
