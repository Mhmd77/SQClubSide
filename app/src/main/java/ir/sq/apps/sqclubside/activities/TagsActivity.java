package ir.sq.apps.sqclubside.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.type_text_view)
    TextView typeTextView;
    @BindView(R.id.submit_tags_button)
    Button submitTagsButton;
    private String[] typeSans = {"فوتبال", "shena", "فوتسال", "بسکتبال", "بیسبال", "والیبال", "کبدی", "شطرنج"};
    private String[] typeHour = {"فوتبال", "فوتسال", "بسکتبال", "بیسبال", "والیبال", "کبدی", "شطرنج"};
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        ButterKnife.bind(this);
        setFonts();
        addTagsType(tagsGroupTypeHour, typeHour);
        addTagsType(tagsGroupTypeSans, typeSans);
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

    private void addTagsType(MultiSelectToggleGroup mstg, String[] types) {
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
        typeHourRadioButton.setTypeface(TypeFaceHandler.getInstance(this).getFa_light());
        typeSansRadioButton.setTypeface(TypeFaceHandler.getInstance(this).getFa_light());
        typeTextView.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
    }

    @OnClick(R.id.submit_tags_button)
    public void onViewClicked() {
        List<String> tags = getTagsList();

    }

    public List<String> getTagsList() {
        List<String> tagsList = new ArrayList<>();
        MultiSelectToggleGroup group = type == 0 ? tagsGroupTypeHour : tagsGroupTypeSans;
        String[] tags = type == 0 ? typeHour : typeSans;
        int subtractor = type == 0 ? tagsGroupTypeHour.getChildAt(0).getId() : tagsGroupTypeSans.getChildAt(0).getId();
        for (int i : group.getCheckedIds()) {
            tagsList.add(tags[i - subtractor]);
        }
        Log.i(TagsActivity.class.getSimpleName(), tagsList.toString());
        return tagsList;
    }
}
