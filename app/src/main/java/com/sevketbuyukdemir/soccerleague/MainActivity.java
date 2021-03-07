package com.sevketbuyukdemir.soccerleague;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sevketbuyukdemir.soccerleague.utils.GlobalVariables;
import com.sevketbuyukdemir.soccerleague.utils.InternetConnControlService;
import com.sevketbuyukdemir.soccerleague.utils.SharedPrefsController;
import com.sevketbuyukdemir.soccerleague.view_adapters.MainActivityRecyclerViewAdapter;
import com.sevketbuyukdemir.soccerleague.viewmodels.MainActivityViewModel;
import static com.sevketbuyukdemir.soccerleague.utils.GlobalVariables.INT_CONN_CONTROL_BROADCAST_STR_FOR_ACTION;
import static com.sevketbuyukdemir.soccerleague.utils.GlobalVariables.fixture_by_ids;
import static com.sevketbuyukdemir.soccerleague.utils.GlobalVariables.soccerTeamList;

/**
 * MainActivity contains UI components, MainActivityViewModel related initializations and
 * Network controller related processes.
 * This Activity provide one RecyclerView for showing Soccer Teams which are received from
 * remote webservice with Retrofit2 API.
 * This activity provide two floating action button. First one is settings action button
 * this button change theme dark - light and save last selection as shared preferences which have
 * manage icon
 * Second floating action button start fixture activity which have application icon.
 * Read comments which is in activity for detailed information.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final Context context = this;
    /**
     * SharedPrefsController for save theme selection as shared preferences.
     */
    private SharedPrefsController sharedPrefsController;
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
    private ImageView iv_network_image_main_activity;
    private TextView tv_network_image_main_activity;
    /**
     * RecyclerView for show Soccer Teams list
     * Show one image (icon of team) and one text (name of team) view for each Soccer Team
     */
    private RecyclerView soccerTeamRecyclerview;
    /**
     * Adapter object for soccerTeamRecyclerView
     * Look at the MainActivityRecyclerViewAdapter class for detailed information
     */
    private MainActivityRecyclerViewAdapter mainActivityRecyclerViewAdapter;
    /**
     * Floating action button for direct user to FixtureActivity
     */
    private FloatingActionButton fixtureFloatingActionButton;
    /**
     * Floating action button for theme setting (dark - light)
     */
    private FloatingActionButton settingsFloatingActionButton;
    /**
     * View model for send HTTP request to remote webservice
     * Look at the MainActivityViewModel class for detailed information
     */
    private MainActivityViewModel mainActivityViewModel;

    /**
     * init() function called in onCreate lifecycle override function
     * 1- Make theme settings from shared prefs
     * 2- Bind UI components by id
     * 3- Set recyclerview for ready to work (layout manager, adapter, etc.)
     * 4- Initialize and set ViewModel for ready to work. (observer, getTeams, etc.)
     * 5- Initialize network control and do first control.
     */
    private void init(){
        // Theme settings
        sharedPrefsController = new SharedPrefsController(context);
        String theme = sharedPrefsController.get("theme");
        switch (theme) {
            case "":
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }

        // UI processes
        soccerTeamRecyclerview = findViewById(R.id.soccer_team_recyclerview);
        fixtureFloatingActionButton = findViewById(R.id.fixture_floating_action_button);
        settingsFloatingActionButton = findViewById(R.id.settings_floating_action_button);
        iv_network_image_main_activity = findViewById(R.id.iv_network_image_main_activity);
        tv_network_image_main_activity = findViewById(R.id.tv_network_image_main_activity);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        soccerTeamRecyclerview.setLayoutManager(llm);
        mainActivityRecyclerViewAdapter = new MainActivityRecyclerViewAdapter(this, soccerTeamList);
        soccerTeamRecyclerview.setAdapter(mainActivityRecyclerViewAdapter);
        fixtureFloatingActionButton.setOnClickListener(this);
        settingsFloatingActionButton.setOnClickListener(this);
        // ViewModel initialization
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getSoccerTeams().observe(this, soccerTeams -> {
            if(soccerTeams != null) {
                // Assign teams to global teams list
                soccerTeamList = soccerTeams;
                // Prepare fixture when teams ready
                Thread prepFixture = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalVariables.createFixtureByID();
                    }
                });
                prepFixture.start();
                mainActivityRecyclerViewAdapter.setTeamList(soccerTeams);
            }
        });
        mainActivityViewModel.getTeams();
        // Network control initialization
        networkControlIntentFilter = new IntentFilter();
        networkControlIntentFilter.addAction(INT_CONN_CONTROL_BROADCAST_STR_FOR_ACTION);
        Intent serviceIntent = new Intent(context, InternetConnControlService.class);
        startService(serviceIntent);

        iv_network_image_main_activity.setVisibility(View.GONE);
        tv_network_image_main_activity.setVisibility(View.GONE);
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
        setContentView(R.layout.activity_main);
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
     * OnClick method trigger when user press our UI clickable components.
     * For detailed information look at the View.OnClickListener interface
     * @param v View -> represent pressed components
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.fixture_floating_action_button){
            // Explicit intent to Fixture Activity
            if((soccerTeamList != null) && (fixture_by_ids != null)){
                Intent fixture_intent = new Intent(this, FixtureActivity.class);
                fixture_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                fixture_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(fixture_intent);
            } else {
                Toast.makeText(context, R.string.soccer_team_list_null, Toast.LENGTH_LONG).show();
            }
        } else if(id == R.id.settings_floating_action_button){
            // Theme setting control button
            if (isChecked) {
                isChecked = false;
                sharedPrefsController.save("theme", "light");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                isChecked = true;
                sharedPrefsController.save("theme", "dark");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
    }

    /**
     * This override function prevent mistake from user.
     * If user press back by mistake, ask for "want to exit?" with AlertDialog.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.back_pressed_title);
        builder.setMessage(R.string.back_pressed_message);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.app_icon);
        builder.setPositiveButton(R.string.back_pressed_positive, (dialog, which) -> MainActivity.super.onBackPressed());
        builder.setNegativeButton(R.string.back_pressed_negative, null);
        builder.create();
        builder.show();
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
        iv_network_image_main_activity.setVisibility(View.GONE);
        tv_network_image_main_activity.setVisibility(View.GONE);
        if(soccerTeamList == null){
            mainActivityViewModel.getTeams();
        }
    }

    /**
     * This function triggered when state being fail.
     * Write what do you want in this time
     * 1- Set warning views as visible and warn user
     */
    public void networkConnectionFail(){
        iv_network_image_main_activity.setImageResource(R.drawable.network_fail_icon);
        tv_network_image_main_activity.setText(R.string.tv_network_text);
        iv_network_image_main_activity.setVisibility(View.VISIBLE);
        tv_network_image_main_activity.setVisibility(View.VISIBLE);
    }
}