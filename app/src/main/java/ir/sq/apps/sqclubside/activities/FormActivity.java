package ir.sq.apps.sqclubside.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sq.apps.sqclubside.uiControllers.ConnectionUi;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;
import ir.sq.apps.sqclubside.controllers.Connection;
import ir.sq.apps.sqclubside.controllers.UrlHandler;
import ir.sq.apps.sqclubside.controllers.UserHandler;

public class FormActivity extends AppCompatActivity {

    @BindView(R.id.club_name_textview)
    TextView clubNameTextview;
    @BindView(R.id.club_name_edittext)
    EditText clubNameEdittext;
    @BindView(R.id.club_owner_textview)
    TextView clubOwnerTextview;
    @BindView(R.id.club_owner_edittext)
    EditText clubOwnerEdittext;
    @BindView(R.id.club_telephonenumber_textview)
    TextView clubTelephonenumberTextview;
    @BindView(R.id.club_telephonenumber_edittext)
    EditText clubTelephonenumberEdittext;
    @BindView(R.id.club_cellphonenumber_textview)
    TextView clubCellphonenumberTextview;
    @BindView(R.id.club_cellphonenumber_edittext)
    EditText clubCellphonenumberEdittext;
    @BindView(R.id.club_address_textview)
    TextView clubAddressTextview;
    @BindView(R.id.club_address_edittext)
    EditText clubAddressEdittext;
    @BindView(R.id.club_location_button)
    Button clubLocationButton;
    @BindView(R.id.submit_information_button)
    Button submitInformationButton;
    @BindView(R.id.club_name_input)
    TextInputLayout clubNameInput;
    @BindView(R.id.club_owner_input)
    TextInputLayout clubOwnerInput;
    @BindView(R.id.club_telephonenumber_input)
    TextInputLayout clubTelephonenumberInput;
    @BindView(R.id.club_cellphonenumber_input)
    TextInputLayout clubCellphonenumberInput;
    @BindView(R.id.club_address_input)
    TextInputLayout clubAddressInput;

    private EditText[] allEditTexts;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);
        setFonts();
        setViews();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void setViews() {
        allEditTexts = new EditText[5];
        allEditTexts[0] = clubNameEdittext;
        allEditTexts[1] = clubOwnerEdittext;
        allEditTexts[2] = clubTelephonenumberEdittext;
        allEditTexts[3] = clubCellphonenumberEdittext;
        allEditTexts[4] = clubAddressEdittext;
    }

    private void setFonts() {
        clubNameTextview.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubNameEdittext.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubOwnerTextview.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubOwnerEdittext.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubTelephonenumberTextview.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubTelephonenumberEdittext.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubCellphonenumberTextview.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubCellphonenumberEdittext.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubAddressTextview.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubAddressEdittext.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubLocationButton.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        submitInformationButton.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubNameInput.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubTelephonenumberInput.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubCellphonenumberInput.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubAddressInput.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        clubOwnerInput.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
    }


    @OnClick({R.id.club_location_button, R.id.submit_information_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.club_location_button:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(FormActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.submit_information_button:
                if (checkEmptyFields()) {
                    sendUserToServer();
                }
                startActivity(new Intent(FormActivity.this, ImageActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);
                Log.i("LOCATION", stBuilder.toString());
            }
        }
    }

    private void sendUserToServer() {
        UserHandler.getInstance().createClub(allEditTexts[0].getText().toString(), clubOwnerEdittext.getText().toString(),
                clubTelephonenumberEdittext.getText().toString(), clubCellphonenumberEdittext.getText().toString(), clubAddressEdittext.getText().toString());
//        Connection connection = new Connection(UrlHandler.createUserURL.toString(), UserHandler.getInstance().getmClub().formToJson(), "POST", ConnectionUi.getDefault(this)) {
//            @Override
//            protected void onResult(String result) {
//                if (result.length() == 0) {
//                    Log.e("ERROR", "FAILED");
//                }
//                Log.i("RESULT", result);
//            }
//        };
//        connection.execute();
    }

    private Boolean checkEmptyFields() {
        Boolean flag = true;
        for (EditText e : allEditTexts) {
            if (e.getText().toString().length() == 0) {
                e.setError(getString(R.string.empty_field_error_meesage));
                flag = false;
            }
        }
        return flag;
    }
}
