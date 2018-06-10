package ir.sq.apps.sqclubside.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;

public class TempActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        ButterKnife.bind(this);
        text.setTypeface(TypeFaceHandler.getInstance(this).getFa_light());
    }
}
