package ir.sq.apps.sqclubside.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.uiControllers.LabelToggle;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;

public class TagsActivity extends AppCompatActivity {

    @BindView(R.id.type_sans_radio_button)
    RadioButton typeSansRadioButton;
    @BindView(R.id.type_hour_radio_button)
    RadioButton typeHourRadioButton;
    @BindView(R.id.type_selector_radio_group)
    RadioGroup typeSelectorRadioGroup;
    @BindView(R.id.tags_group_type_sans)
    MultiSelectToggleGroup tagsGroupTypeSans;
    @BindView(R.id.tags_group_type_hour)
    MultiSelectToggleGroup tagsGroupTypeHour;
    private String[] type1 = {"فوتبال", "shena", "فوتسال", "بسکتبال", "بیسبال", "والیبال", "کبدی", "شطرنج"};
    private String[] type2 = {"فوتبال", "فوتسال", "بسکتبال", "بیسبال", "والیبال", "کبدی", "شطرنج"};
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        ButterKnife.bind(this);
        setFonts();
        addTagsTypeOne(tagsGroupTypeSans, type1);
        addTagsTypeOne(tagsGroupTypeHour, type2);
        setRadioGroup();
    }

    private void setRadioGroup() {
        tagsGroupTypeHour.setVisibility(View.VISIBLE);
        typeSelectorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.type_sans_radio_button:
                        tagsGroupTypeSans.setVisibility(View.VISIBLE);
                        tagsGroupTypeHour.setVisibility(View.GONE);
                        type = 1;
                        break;
                    case R.id.type_hour_radio_button:
                        tagsGroupTypeSans.setVisibility(View.GONE);
                        tagsGroupTypeHour.setVisibility(View.VISIBLE);
                        type = 0;
                        break;
                }
            }
        });
    }

    private void addTagsTypeOne(MultiSelectToggleGroup mstg, String[] types) {
        for (String s : types) {
            LabelToggle labelToggle = new LabelToggle(this);
            labelToggle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            labelToggle.setLayoutParams(params);
            labelToggle.setMarkerColor(ContextCompat.getColor(this, R.color.colorAccent));
            labelToggle.setText(s);
            labelToggle.setTypeFace(TypeFaceHandler.getInstance(this).getFa_light());
            mstg.addView(labelToggle);
        }
    }

    private void setFonts() {
        typeHourRadioButton.setTypeface(TypeFaceHandler.getInstance(this).getEn_light());
        typeSansRadioButton.setTypeface(TypeFaceHandler.getInstance(this).getEn_light());
    }
}
