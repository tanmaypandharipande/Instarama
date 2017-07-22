package test.hotline.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.freshdesk.hotline.FaqOptions;
import com.freshdesk.hotline.Hotline;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import test.hotline.R;
import test.hotline.adapter.GridAdapter;

public class MainActivity extends AppCompatActivity {
    FirebaseApp firebaseApp;
    GridView gridView;
    String[] text = {
            "Conversation",
            "FAQ's",
            "Conversation *",
            "FAQ's *",
            "Conversation *",
            "FAQ's *",
            "Conversation *",
            "FAQ's *",
            "Conversation *",
            "FAQ's *"

    } ;
    int[] images = {
            R.drawable.ic_chat_black_24dp,
            R.drawable.ic_info_black_24px,
            R.drawable.hotlineicon,
            R.drawable.hotlineicon,
            R.drawable.hotlineicon,
            R.drawable.hotlineicon,
            R.drawable.hotlineicon,
            R.drawable.hotlineicon,
            R.drawable.hotlineicon,
            R.drawable.hotlineicon,
    };
    int[] colors = {
            R.color.colorGreen,
            R.color.colorLightGreen,
            R.color.colorLightGreen,
            R.color.colorGreen,
            R.color.colorGreen,
            R.color.colorLightGreen,
            R.color.colorLightGreen,
            R.color.colorGreen,
            R.color.colorGreen,
            R.color.colorLightGreen
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseApp.initializeApp(getApplicationContext());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Hotline.getInstance(this).updateGcmRegistrationToken(refreshedToken);
        GridAdapter adapter = new GridAdapter(MainActivity.this, text, images,colors);
        gridView =(GridView)findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0){
                    Hotline.showConversations(getApplicationContext());
                }else if (position == 1){
                    FaqOptions faqOptions = new FaqOptions()
                            .showFaqCategoriesAsGrid(true)
                            .showContactUsOnAppBar(true)
                            .showContactUsOnFaqScreens(true)
                            .showContactUsOnFaqNotHelpful(true);

                    Hotline.showFAQs(MainActivity.this, faqOptions);
                }
            }
        });
    }
}
