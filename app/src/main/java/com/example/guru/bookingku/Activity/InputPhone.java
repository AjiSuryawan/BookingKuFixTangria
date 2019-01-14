package com.example.guru.bookingku.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.guru.bookingku.Activity.Main.MainActivity;
import com.example.guru.bookingku.Model.BookingResponse;
import com.example.guru.bookingku.Network.BookingClient;
import com.example.guru.bookingku.Network.BookingService;
import com.example.guru.bookingku.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputPhone extends Activity {

    @BindView(R.id.inputPhone)
    EditText inputPhone;
    @BindView(R.id.submitBtn)
    Button submitBtn;
    SharedPreferences preferences;
    String Name;
    String phoneNo;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private void getContactInfo(Intent data) {
// Check the SDK version and whether the permission is already granted or not.


        // TODO Auto-generated method stub

        ContentResolver cr = getContentResolver();


        Cursor cursor = managedQuery(data.getData(), null, null, null, null);
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            Name = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

            String hasPhone = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            Cursor emailCur = cr.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                    new String[]{contactId}, null);


            emailCur.close();


            if (hasPhone.equalsIgnoreCase("1"))
                hasPhone = "true";
            else
                hasPhone = "false";


            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    phoneNo = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }

            Log.d("hasil1", "getContactInfo: " + Name);
            //
            Log.d("hasil2", "getContactInfo: " + phoneNo);
            inputPhone.setText(phoneNo);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_phone);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }

        inputPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (inputPhone.getRight() - inputPhone.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        //Toast.makeText(getApplicationContext(),"makan",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Intent.ACTION_PICK,
                                ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(i, 1);
                        return true;
                    }
                }
                return false;
            }
        });

        preferences = getSharedPreferences("login", MODE_PRIVATE);
        final int userId= preferences.getInt("userid", 0);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = inputPhone.getText().toString().trim();
                if(phone.equals(null)){
                    inputPhone.setError("Tolong jangan di kosongin ya");
                } else {
                    BookingService service = BookingClient.getRetrofit().create(BookingService.class);
                    Call<BookingResponse> call = service.insertPhone(userId, phone);
                    call.enqueue(new Callback<BookingResponse>() {
                        @Override
                        public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                            try{
                                boolean success = response.body().getSuccess();
                                if(success){
                                    Intent intent = new Intent(InputPhone.this, MainActivity.class);
                                    finish();
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(InputPhone.this, "Something wrong is happen", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){}
                        }

                        @Override
                        public void onFailure(Call<BookingResponse> call, Throwable t) {
                            Toast.makeText(InputPhone.this, "Can't connect to server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                getContactInfo(data);

            }
        }
    }
}
