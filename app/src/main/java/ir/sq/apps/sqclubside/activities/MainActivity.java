package ir.sq.apps.sqclubside.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.fragments.PlanFragment;
import ir.sq.apps.sqclubside.fragments.ProfileFragment;
import ir.sq.apps.sqclubside.fragments.ReceiptsFragment;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ProfileFragment.OnFragmentInteractionListener,
        PlanFragment.OnFragmentInteractionListener ,ReceiptsFragment.OnFragmentInteractionListener{

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_PLAN = "plan";
    private static final String TAG_RECEIPT = "receipt";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_SETTINGS = "setting";
    private static String CURRENT_TAG = TAG_PLAN;
    private String titles[] = {"Calendar", "Receipts", "Profile", "Settings"};
    // toolbar titles respected to selected nav menu item

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private ResideMenu resideMenu;
    private Handler mHandler;
    private List<ResideMenuItem> resideMenuItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setUpToolbar();
        mHandler = new Handler();
        setUpMenu();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_PLAN;
            loadHomeFragment();
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setToolbarTitle();
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    private void loadHomeFragment() {
        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            resideMenu.closeMenu();
            return;
        }
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.container, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        // If mPendingRunnable is not null, then add to the message queue
        mHandler.post(mPendingRunnable);
        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void setToolbarTitle() {
        toolbarTitle.setTypeface(TypeFaceHandler.getInstance(this).getFa_light());
        toolbarTitle.setText(titles[navItemIndex]);
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return PlanFragment.newInstance();
            // plan
            case 1:
                return ReceiptsFragment.newInstance();
            // receipts
            case 2:
                // profile
                return ProfileFragment.newInstance();
            case 3:
                // settings
                return ProfileFragment.newInstance();
            default:
                return ProfileFragment.newInstance();
        }

    }


    private void setUpMenu() {
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.bg_image);
        resideMenu.attachToActivity(this);
        resideMenuItemList = new ArrayList<>();
        int icon[] = {R.drawable.ic_date_range_white_36dp, R.drawable.ic_receipt_white_36dp,
                R.drawable.ic_account_box_white_36dp, R.drawable.ic_settings_white_36dp};

        for (int i = 0; i < titles.length; i++) {
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT);
            resideMenuItemList.add(item);
        }
        resideMenu.setMenuListener(new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {

            }

            @Override
            public void closeMenu() {

            }
        });
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
    }

    @Override
    public void onBackPressed() {
        if (resideMenu.isOpened()) {
            resideMenu.closeMenu();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_PLAN;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        String tags[] = {TAG_PLAN, TAG_RECEIPT, TAG_PROFILE, TAG_SETTINGS};
        for (int i = 0; i < resideMenuItemList.size(); i++)
            if (view == resideMenuItemList.get(i)) {
                CURRENT_TAG = tags[i];
                navItemIndex = i;
                break;
            }
        resideMenu.closeMenu();
        loadHomeFragment();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
