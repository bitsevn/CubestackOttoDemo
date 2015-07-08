package in.cubestack.cubestackottodemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import in.cubestack.cubestackottodemo.R;
import in.cubestack.cubestackottodemo.event.ActivityEvent;
import in.cubestack.cubestackottodemo.event.ClientCallEvent;
import in.cubestack.cubestackottodemo.event.ServerCallEvent;
import in.cubestack.cubestackottodemo.provider.BusProvider;

/**
 * Created by arunk on 6/30/2015.
 */
public class ClientFragment extends AbstractFragment {

    protected static final String TAG = ClientFragment.class.getSimpleName();

    public static final String FRAG_TAG = "FRAG_TAG";

    TextView serverToClientCalls;
    Button btnCallServer;
    boolean toggleButton = true;

    public static ClientFragment newInstance(int position) {
        ClientFragment f = new ClientFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        f.setTitle("CLIENT");
        return f;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_client, container, false);
        serverToClientCalls = (TextView) rootView.findViewById(R.id.serverToClientCalls);
        btnCallServer = (Button) rootView.findViewById(R.id.btnCallServer);
        btnCallServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new ClientCallEvent());
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Subscribe
    public void onServerEvent(ServerCallEvent event) {
        int calls = Integer.parseInt(serverToClientCalls.getText().toString());
        serverToClientCalls.setText(String.valueOf(calls + 1));
    }

    @Subscribe
    public void onActivityEvent(ActivityEvent event) {
        if(toggleButton) {
            btnCallServer.setBackgroundColor(getResources().getColor(R.color.color_primary));
            btnCallServer.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            btnCallServer.setBackgroundColor(getResources().getColor(R.color.btn_color));
            btnCallServer.setTextColor(getResources().getColor(R.color.text_primary));
        }
        toggleButton = !toggleButton;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register ourselves so that we can provide the initial value.
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Always unregister when an object no longer should be on the bus.
        BusProvider.getInstance().unregister(this);
    }
}
