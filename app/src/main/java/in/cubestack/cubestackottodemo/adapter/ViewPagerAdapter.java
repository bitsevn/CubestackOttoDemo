package in.cubestack.cubestackottodemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.cubestack.cubestackottodemo.fragment.AbstractFragment;

/**
 * Created by arunk on 7/9/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<AbstractFragment> items = new ArrayList<AbstractFragment>(0);

    public ViewPagerAdapter(FragmentManager fm, List<AbstractFragment> items) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getTitle();
    }

    public void updateFragment(int pos) {
        if (pos > -1 && items.size() > 0) {
            AbstractFragment f = items.get(pos);
            if (f != null) f.update();
        }
    }
}
