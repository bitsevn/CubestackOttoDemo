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

public class ServerFragment extends AbstractFragment {

    protected static final String TAG = ServerFragment.class.getSimpleName();

    public static final String FRAG_TAG = "FRAG_TAG";

    TextView clientToServerCalls;
    Button btnCallClient;
    boolean toggleButton = true;

    public static ServerFragment newInstance(int position) {
        ServerFragment f = new ServerFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        f.setTitle("SERVER");
        return f;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_server, container, false);
        clientToServerCalls = (TextView) rootView.findViewById(R.id.clientToServerCalls);
        btnCallClient = (Button) rootView.findViewById(R.id.btnCallClient);
        btnCallClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new ServerCallEvent());
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Subscribe
    public void onClientEvent(ClientCallEvent event) {
        int calls = Integer.parseInt(clientToServerCalls.getText().toString());
        clientToServerCalls.setText(String.valueOf(calls + 1));
    }

    @Subscribe
    public void onActivityEvent(ActivityEvent event) {
        if(toggleButton) {
            btnCallClient.setBackgroundColor(getResources().getColor(R.color.color_primary));
            btnCallClient.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            btnCallClient.setBackgroundColor(getResources().getColor(R.color.btn_color));
            btnCallClient.setTextColor(getResources().getColor(R.color.text_primary));
        }
        toggleButton = !toggleButton;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
