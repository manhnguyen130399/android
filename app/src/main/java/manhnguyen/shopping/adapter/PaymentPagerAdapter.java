package manhnguyen.shopping.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

import manhnguyen.shopping.fragment.AddressFragment;
import manhnguyen.shopping.fragment.PaymentMethodFragment;


public class PaymentPagerAdapter extends FragmentStatePagerAdapter {

    public PaymentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return 2;
    }

    // Returns the fragment to display for that page
    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new AddressFragment();
        } else if (position == 1) {
            fragment = new PaymentMethodFragment();
        }
        return fragment;
    }

}

