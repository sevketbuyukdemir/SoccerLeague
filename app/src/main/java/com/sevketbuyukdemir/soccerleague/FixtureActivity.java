package com.sevketbuyukdemir.soccerleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import com.sevketbuyukdemir.soccerleague.utils.InternetConnControlService;
import com.sevketbuyukdemir.soccerleague.fragments.FixtureFragment;
import com.sevketbuyukdemir.soccerleague.view_adapters.FixtureActivityViewPagerAdapter;
import java.util.ArrayList;
import static com.sevketbuyukdemir.soccerleague.utils.GlobalVariables.INT_CONN_CONTROL_BROADCAST_STR_FOR_ACTION;
import static com.sevketbuyukdemir.soccerleague.utils.GlobalVariables.fixture_by_ids;

/**
 * This activity show Fixture of matches which will play between Soccer Teams.
 * Contains one tabLayout and one ViewPager for showing matches week by week.
 * Read comments which is in activity for detailed information.
 */
public class FixtureActivity extends AppCompatActivity {
    private Context context = this;
    /**
     * IntentFilter for network control
     */
    private IntentFilter networkControlIntentFilter;
    private boolean isChecked = false;
    /**
     * UI components for warn user when network connection state is fail
     * image view show network fail icon
     * text view says network is fail :D
     */
    private ImageView iv_network_image_fixture_activity;
    private TextView tv_network_image_fixture_activity;
    /**
     * TabLayout for show week number and order (Week - 1 ... Week - n)
     */
    private TabLayout fixtureTabLayout;
    /**
     * ViewPager for show FixtureFragments as Swipe-able UI.
     */
    private ViewPager fixtureViewPager;
    /**
     * ViewPagerAdapter for fixtureViewPager
     * Look at the FixtureActivityViewPagerAdapter class for detailed information.
     */
    private FixtureActivityViewPagerAdapter fixtureActivityViewPagerAdapter;
    /**
     * Hold weeks name ("Week - 1"...) as String in the ArrayList
     */
    private ArrayList<String> titleList;
    /**
     * Hold fixtureFragments in the arraylist
     */
    private ArrayList<Fragment> fragmentsList;

    /**
     * init() function called in onCreate lifecycle override function
     * 1- Bind UI components by id
     * 3- Create titleList for tabLayout
     * 4- Create fragments and assign fragmentsList for Shown in ViewPager
     * 4- Initialize and set ViewPager for ready to work. (adapter, setup with tabLayout, etc.)
     * 5- Initialize network control and do first control.
     */
    private void init(){
        // Initialize UI components
        iv_network_image_fixture_activity = findViewById(R.id.iv_network_image_fixture_activity);
        tv_network_image_fixture_activity = findViewById(R.id.tv_network_image_fixture_activity);
        fixtureTabLayout = findViewById(R.id.fixture_tab_layout);
        fixtureViewPager = findViewById(R.id.fixture_viewpager);
        // Add titles
        titleList = new ArrayList<>();
        for(int i = 0; i < fixture_by_ids.size(); i++){
            titleList.add("Week " + (i+1));
        }
        // Add fragments
        fragmentsList = new ArrayList<>();
        for(int i = 0; i < fixture_by_ids.size(); i++){
            fragmentsList.add(new FixtureFragment(i));
        }
        // Set adapter to ViewPager
        fixtureActivityViewPagerAdapter = new FixtureActivityViewPagerAdapter(titleList, fragmentsList,
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fixtureViewPager.setAdapter(fixtureActivityViewPagerAdapter);
        // Setup tabLayout with ViewPager
        fixtureTabLayout.setupWithViewPager(fixtureViewPager);
        // Network control initialization
        networkControlIntentFilter = new IntentFilter();
        networkControlIntentFilter.addAction(INT_CONN_CONTROL_BROADCAST_STR_FOR_ACTION);
        Intent serviceIntent = new Intent(context, InternetConnControlService.class);
        startService(serviceIntent);

        iv_network_image_fixture_activity.setVisibility(View.GONE);
        tv_network_image_fixture_activity.setVisibility(View.GONE);
        if(isNetworkON(getApplicationContext())){
            networkConnectionSuccess();
        } else {
            networkConnectionFail();
        }
    }

    /**
     * Android lifecycle function which is triggered first for launch activity.
     * 1- Call init() function
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);
        init();
    }

    /**
     * Android lifecycle function.
     * Added for register receiver in right time.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(networkReceiver, networkControlIntentFilter);
    }

    /**
     * Android lifecycle function.
     * Added for unregister receiver in right time.
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }

    /**
     * Android lifecycle function.
     * Added for register receiver in right time.
     */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkReceiver, networkControlIntentFilter);
    }

    /**
     * BroadcastReceiver for internet control, this receiver catch broadcast which is send by
     * InternetConnControlService service runnable. For detail look at the this service class.
     */
    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(INT_CONN_CONTROL_BROADCAST_STR_FOR_ACTION)){
                if(intent.getStringExtra("network_status").equals("true")){
                    networkConnectionSuccess();
                } else {
                    networkConnectionFail();
                }
            }
        }
    };

    /**
     * Function for control internet connection state. If state is successful return true, else
     * return false.
     * @param context Context
     * @return boolean
     */
    public boolean isNetworkON(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * This function triggered when state being successful.
     * Write what do you want in this time
     *
     * 1- Set warning views as invisible
     * 2- if soccer teams can not get when network state is fail send second GET request in state
     * is success.
     */
    public void networkConnectionSuccess(){
        iv_network_image_fixture_activity.setVisibility(View.GONE);
        tv_network_image_fixture_activity.setVisibility(View.GONE);
    }

    /**
     * This function triggered when state being fail.
     * Write what do you want in this time
     * 1- Set warning views as visible and warn user
     */
    public void networkConnectionFail(){
        iv_network_image_fixture_activity.setImageResource(R.drawable.network_fail_icon);
        tv_network_image_fixture_activity.setText(R.string.tv_network_text);
        iv_network_image_fixture_activity.setVisibility(View.VISIBLE);
        tv_network_image_fixture_activity.setVisibility(View.VISIBLE);
    }
}