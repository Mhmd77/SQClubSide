package ir.sq.apps.sqclubside.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);
        setFonts();
        setViews();
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
                startActivity(new Intent(FormActivity.this, ClubLocationActivity.class));
                break;
            case R.id.submit_information_button:
                if (checkEmptyFields()) {
                    finish();
                }
                break;
        }
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
