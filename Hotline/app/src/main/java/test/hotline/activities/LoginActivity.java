package test.hotline.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.freshdesk.hotline.Hotline;
import com.freshdesk.hotline.HotlineConfig;
import com.freshdesk.hotline.HotlineUser;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import test.hotline.R;
import test.hotline.helper.Utilities;

/**
 * Created by tanmay on 22/07/17.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText editText_full_name,editText_email,editText_contact,editText_username;
    private Button button_register;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    FirebaseApp firebaseApp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseApp.initializeApp(getApplicationContext());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        editText_full_name = (EditText)findViewById(R.id.edittext_full_name);
        editText_email = (EditText)findViewById(R.id.edittext_email);
        editText_contact = (EditText)findViewById(R.id.edittext_contact);
        editText_username = (EditText)findViewById(R.id.edittext_username);
        button_register = (Button)findViewById(R.id.button_register);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_full_name.getText().toString().equals("") || editText_email.getText().toString().equals("")
                        || editText_contact.getText().toString().equals("") || editText_username.getText().toString().equals("")) {
                    Utilities.toast(getApplicationContext(),"Please enter all the fields");
                }else {
                    initializeAndRegister(editText_full_name.getText().toString().trim(),editText_email.getText().toString().trim(),
                                editText_username.getText().toString().trim(),editText_contact.getText().toString().trim());
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void initializeAndRegister(String fullName,String email,String username,String contact){

        HotlineConfig hotlineConfig=new HotlineConfig("b3cdbcb9-f8fe-4323-8c0f-2c8f2cee96f4","4d35a5d8-57b2-489d-a4a8-c8857b0ab01a");
        hotlineConfig.setVoiceMessagingEnabled(true);
        hotlineConfig.setPictureMessagingEnabled(true);
        hotlineConfig.setAgentAvatarEnabled(true);

        Hotline.getInstance(getApplicationContext()).init(hotlineConfig);

        HotlineUser hotlineUser=Hotline.getInstance(getApplicationContext()).getUser();
        hotlineUser.setName(fullName);
        hotlineUser.setEmail(email);
        hotlineUser.setExternalId(username);
        hotlineUser.setPhone("+91", contact);

        editor.putString("login","true");
        editor.apply();

        Hotline.getInstance(getApplicationContext()).updateUser(hotlineUser);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Hotline.getInstance(this).updateGcmRegistrationToken(refreshedToken);
    }
}
