package in.cubestack.cubestackottodemo.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Vector;

import in.cubestack.cubestackottodemo.R;
import in.cubestack.cubestackottodemo.adapter.ViewPagerAdapter;
import in.cubestack.cubestackottodemo.event.ActivityEvent;
import in.cubestack.cubestackottodemo.event.ClientCallEvent;
import in.cubestack.cubestackottodemo.event.ServerCallEvent;
import in.cubestack.cubestackottodemo.fragment.AbstractFragment;
import in.cubestack.cubestackottodemo.fragment.ClientFragment;
import in.cubestack.cubestackottodemo.fragment.ServerFragment;
import in.cubestack.cubestackottodemo.provider.BusProvider;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewpager;
    ViewPagerAdapter pagerAdapter;
    TextView clientEvents;
    TextView serverEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        clientEvents = (TextView) findViewById(R.id.clientEvents);
        serverEvents = (TextView) findViewById(R.id.serverEvents);
        initToolbar();
        initViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings) {
            BusProvider.getInstance().post(new ActivityEvent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onClientEvent(ClientCallEvent event) {
        int calls = Integer.parseInt(clientEvents.getText().toString());
        clientEvents.setText(String.valueOf(calls + 1));
    }

    @Subscribe
    public void onServerEvent(ServerCallEvent event) {
        int calls = Integer.parseInt(serverEvents.getText().toString());
        serverEvents.setText(String.valueOf(calls + 1));
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void initViewPager() {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getFragments());
        viewpager.setAdapter(pagerAdapter);
    }

    private List<AbstractFragment> getFragments() {
        List<AbstractFragment> fragments = new Vector<AbstractFragment>(2);
        fragments.add(ClientFragment.newInstance(0));
        fragments.add(ServerFragment.newInstance(1));
        return fragments;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register ourselves so that we can provide the initial value.
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Always unregister when an object no longer should be on the bus.
        BusProvider.getInstance().unregister(this);
    }
}
