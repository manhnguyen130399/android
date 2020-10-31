package manhnguyen.shopping.interfaces;

import android.widget.ImageView;

/**
 * Created by Redixbit 2 on 12-10-2016.
 */
public interface WishlistButtonListener {
    void onAddClicked(int position, ImageView like, ImageView unlike);

    void onremovelist(int position, Integer id);

}
