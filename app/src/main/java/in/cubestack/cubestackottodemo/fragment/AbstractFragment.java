package in.cubestack.cubestackottodemo.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class AbstractFragment extends Fragment {

    public String getTabName(){ return ""; }

    private String title;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void update() {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
