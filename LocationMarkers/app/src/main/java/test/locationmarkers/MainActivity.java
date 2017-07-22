package test.locationmarkers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;

import test.locationmarkers.custom.DatabaseHandler;
import test.locationmarkers.custom.Utilities;
import test.locationmarkers.pojo.LocationDetails;

public class MainActivity extends AppCompatActivity {
    private EditText editText_name,editText_latitude,editText_longitude,editText_description;
    private Button button_add;
    private TextView textView_get_latlng;
    private DatabaseHandler db;
    int PLACE_PICKER_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        editText_name = (EditText)findViewById(R.id.editText_name);
        editText_latitude = (EditText)findViewById(R.id.editText_latitude);
        editText_longitude = (EditText)findViewById(R.id.editText_longitude);
        editText_description = (EditText)findViewById(R.id.editText_description);
        button_add = (Button)findViewById(R.id.button_add);
        textView_get_latlng = (TextView)findViewById(R.id.textView_get_latlng);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_name.getText().toString().trim().equals("")||editText_latitude.getText().toString().trim().equals("") || editText_longitude.getText().toString().trim().equals("") || editText_description.getText().toString().trim().equals("")){
                    Utilities.toast(getApplicationContext(),"Please enter all the fields");
                }else {
                    String name = editText_name.getText().toString().trim();
                    double latitude = Double.parseDouble(editText_latitude.getText().toString().trim());
                    double longitude = Double.parseDouble(editText_longitude.getText().toString().trim());
                    String description = editText_description.getText().toString().trim();

                    db.addLocation(new LocationDetails(name, latitude, longitude, description),getApplicationContext());

                    List<LocationDetails> locationDetailsList = db.getAllContacts();

                    for (LocationDetails details : locationDetailsList) {
                        String log = "Id: " + details.getId() + " ,Name: " + details.getName() + " ,Latitude: " + details.getLatitude()
                                + " ,Longitude: " + details.getLongitude() + " ,Description: " + details.getDescription();
                        Log.d("DETAILS: ", log);
                    }
                    editText_name.setText("");
                    editText_latitude.setText("");
                    editText_longitude.setText("");
                    editText_description.setText("");
                }
            }
        });

        textView_get_latlng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_map){
            Intent intent = new Intent(MainActivity.this,ShowLocationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                Double latitude = place.getLatLng().latitude;
                Double longitude = place.getLatLng().longitude;
                editText_latitude.setText(String.valueOf(latitude));
                editText_longitude.setText(String.valueOf(longitude));
                Utilities.toast(getApplicationContext(),String.format("Place: ", place.getName()));
            }
        }
    }
}