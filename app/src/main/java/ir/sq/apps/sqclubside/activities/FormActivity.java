package ir.sq.apps.sqclubside.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sq.apps.sqclubside.ClubLocationActivity;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.TypeFaceHandler;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);
        setFonts();
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
    }


    @OnClick({R.id.club_location_button, R.id.submit_information_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.club_location_button:
                break;
            case R.id.submit_information_button:
                startActivity(new Intent(FormActivity.this, ClubLocationActivity.class));
                break;
        }
    }
}
