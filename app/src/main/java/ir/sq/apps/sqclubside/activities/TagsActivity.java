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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.controllers.OnResponse;
import ir.sq.apps.sqclubside.controllers.RequestsHandler;
import ir.sq.apps.sqclubside.controllers.UrlHandler;
import ir.sq.apps.sqclubside.controllers.UserHandler;
import ir.sq.apps.sqclubside.uiControllers.LabelToggle;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;
import ir.sq.apps.sqclubside.utils.Constants;

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
    private List<String> typeSans = new ArrayList<>();
    private List<String> typeHour = new ArrayList<>();
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        ButterKnife.bind(this);
        setFonts();
        getTagsFromServer();
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
                        type = Constants.TYPE_SANS;
                        break;
                    case R.id.type_hour_radio_button:
                        tagsGroupTypeSans.setVisibility(View.GONE);
                        tagsGroupTypeHour.setVisibility(View.VISIBLE);
                        type = Constants.TYPE_HOUR;
                        break;
                }
            }
        });
    }

    private void addTagsType(MultiSelectToggleGroup mstg, List<String> types) {
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
        submitTagsButton.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
    }

    @OnClick(R.id.submit_tags_button)
    public void onViewClicked() {
        List<String> tags = getTagsList();
        UserHandler.getInstance().getmClub().addTags(tags);
        RequestsHandler.updateClubTo(UserHandler.getInstance().getmClub(), UrlHandler.createUserURL.toString());
    }

    public List<String> getTagsList() {
        List<String> tagsList = new ArrayList<>();
        MultiSelectToggleGroup group = type == Constants.TYPE_HOUR ? tagsGroupTypeHour : tagsGroupTypeSans;
        List<String> tags = type == Constants.TYPE_HOUR ? typeHour : typeSans;
        int subtractor = type == Constants.TYPE_HOUR ? tagsGroupTypeHour.getChildAt(0).getId() : tagsGroupTypeSans.getChildAt(0).getId();
        for (int i : group.getCheckedIds()) {
            tagsList.add(tags.get(i - subtractor));
        }
        return tagsList;
    }

    public void getTagsFromServer() {
        RequestsHandler.getTagsFrom(UrlHandler.getTagsURL.toString(), new OnResponse() {
            @Override
            public void onTagsRecieved(JSONArray tags) {
                parseTags(tags);
                addTagsType(tagsGroupTypeHour, typeHour);
                addTagsType(tagsGroupTypeSans, typeSans);
            }
        });
    }

    private void parseTags(JSONArray tags) {
        try {
            JSONArray type_one = tags.getJSONArray(0);
            JSONArray type_two = tags.getJSONArray(1);
            if (type_one != null) {
                typeHour = new ArrayList<>();
                for (int i = 0; i < type_one.length(); i++) {
                    typeHour.add(type_one.getString(i));
                }
            } else {
                Log.e("EXCEPTION", "type one is null");
            }
            if (type_two != null) {
                typeSans = new ArrayList<>();
                for (int i = 0; i < type_two.length(); i++) {
                    typeSans.add(type_two.getString(i));
                }
            } else {
                Log.e("EXCEPTION", "type two is null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
