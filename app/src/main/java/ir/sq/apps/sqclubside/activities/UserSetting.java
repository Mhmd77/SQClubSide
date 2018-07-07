package ir.sq.apps.sqclubside.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sq.apps.sqclubside.R;

public class UserSetting extends AppCompatActivity {

//    @BindView(R.id.exit_Button)
//    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ButterKnife.bind(this);
    }

//    @OnClick(R.id.exit_Button)
//    public void onViewClicked() {
//        startActivity(new Intent(UserSetting.this,TempActivity.class));
//    }
}
