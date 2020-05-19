package freaktemplate.shopping.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import freaktemplate.shopping.MyApplication;
import freaktemplate.shopping.R;
import freaktemplate.shopping.adapter.ReviewAdapter;
import freaktemplate.shopping.getset.Detail.Detailpojo;
import freaktemplate.shopping.getset.Detail.Offers;
import freaktemplate.shopping.getset.Detail.Review;
import freaktemplate.shopping.getset.Review.Data;
import freaktemplate.shopping.getset.Review.ReviewPojo;
import freaktemplate.shopping.utils.CustomGridLayoutManager;
import freaktemplate.shopping.utils.SPmanager;
import freaktemplate.shopping.utils.UtilHelper;
import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

public class DetailReview extends Fragment {
    private static final String TAG = "DetailReview";
    private View rootview;
    private TextView btn_add;
    private EditText edt_review;
    private String userrate;
    private String usercomment;
    private String productid;
    private Gson gson;
    private String userid;
    private RecyclerView recycle_review;
    private List<Review> reviewlist;
    private Context context;
    private TextView txt_1;
    private TextView txt_2;
    private TextView txt_3;
    private TextView txt_4;
    private TextView txt_5;
    private TextView total_reviews;
    private TextView avg_star;
    private ZzHorizontalProgressBar progressbar5;
    private ZzHorizontalProgressBar progressbar4;
    private ZzHorizontalProgressBar progressbar3;
    private ZzHorizontalProgressBar progressbar2;
    private ZzHorizontalProgressBar progressbar1;
    private MyApplication myApp;

    public DetailReview() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_detail_review, container, false);
        myApp = MyApplication.getInstance();
        context = rootview.getContext();
        userid = SPmanager.getPreference(context, "userID");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        getData();
        if (userid == null) {
            userid = "0";
        }
        initie();
        getList();
        return rootview;
    }


    private void getData() {
        productid = myApp.getProduct_id();
    }

    private void initie() {
        reviewlist = new ArrayList<>();
        recycle_review = rootview.findViewById(R.id.recycle_review);
        progressbar1 = rootview.findViewById(R.id.progressbar1);
        progressbar2 = rootview.findViewById(R.id.progressbar2);
        progressbar3 = rootview.findViewById(R.id.progressbar3);
        progressbar4 = rootview.findViewById(R.id.progressbar4);
        progressbar5 = rootview.findViewById(R.id.progressbar5);
        txt_1 = rootview.findViewById(R.id.txt_1);
        txt_2 = rootview.findViewById(R.id.txt_2);
        txt_3 = rootview.findViewById(R.id.txt_3);
        txt_4 = rootview.findViewById(R.id.txt_4);
        txt_5 = rootview.findViewById(R.id.txt_5);
        avg_star = rootview.findViewById(R.id.avg_star);
        total_reviews = rootview.findViewById(R.id.total_reviews);
        btn_add = rootview.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
                btn_add.startAnimation(buttonClick);
                String userid = SPmanager.getPreference(context, "userID");
                if (userid != null) {
                    ShowReviewDialog();
                } else {
                    showdialog();
                }
            }
        });
    }

    private void showdialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        MaterialButton rele_close = dialog.findViewById(R.id.rele_close);

        rele_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                Loginfrag(fragment);
                dialog.dismiss();
            }
        });
    }

    private void ShowReviewDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.reviewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        edt_review = dialog.findViewById(R.id.txt_description);
        final ScaleRatingBar rat = dialog.findViewById(R.id.rate1234);
        String rb = String.valueOf(rat);

        rat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userrate = String.valueOf(rat.getRating());
            }
        });
        MaterialButton btn_submit = dialog.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    usercomment = edt_review.getText().toString().replace(" ", "%20");
                    userrate = String.valueOf(rat.getRating());

                } catch (NullPointerException e) {
                    // TODO: handle exception
                }
                Log.e("comment", "" + usercomment);
                Log.e("rate", "" + userrate);
                if (usercomment.equals("")) {
                    edt_review.setError(getString(R.string.error_review));
                } else {
                    dialog.dismiss();
                    postReview();
                }
            }
        });

        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
    }

    private void postReview() {
        UtilHelper.showdialog(rootview.getContext());
        String url = getString(R.string.link) + "addreview";
        AndroidNetworking.post(url)
                .addBodyParameter("product_id", productid)
                .addBodyParameter("user_id", userid)
                .addBodyParameter("review", usercomment)
                .addBodyParameter("ratting", userrate)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse:review " + response);
                        ReviewPojo reviewPojo = gson.fromJson(String.valueOf(response), ReviewPojo.class);
                        Data data = reviewPojo.getData();
                        if (data.getStatus().equals(1)) {
                            Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                            getList();
                            UtilHelper.hidedialog();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        UtilHelper.hidedialog();
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Log.e(TAG, "onError: " + anError.getMessage());
                    }
                });

    }


    @Override
    public void onResume() {
        super.onResume();
        initie();
        getList();

    }

    private void Loginfrag(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void getList() {

        reviewlist.clear();
        String url = getString(R.string.link) + "viewproduct/" + productid + "/" + userid;
        Log.e(TAG, "registerUser: " + url);
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse:reviewlist " + response);

                        try {
                            Detailpojo detailpojo = gson.fromJson(String.valueOf(response), Detailpojo.class);
                            if (detailpojo.getStatus().equals(1)) {
                                Offers offers = detailpojo.getOffers();
                                String avgStar = String.valueOf(offers.getAvgStar());
                                avg_star.setText(avgStar);
                                reviewlist = offers.getReview();
                                setAdapter();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), R.string.sorry_try, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        Toast.makeText(getActivity(), R.string.sorry_try, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setAdapter() {
        CustomGridLayoutManager linearLayoutManager = new CustomGridLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recycle_review.setLayoutManager(linearLayoutManager);
        ReviewAdapter adapter = new ReviewAdapter(rootview.getContext(), reviewlist);
        recycle_review.setAdapter(adapter);
        Log.e(TAG, "onResponse:fafasfaf " + reviewlist.size());
        int y = 0;
        int z = 0;
        int q = 0;
        int k = 0;
        int m = 0;
        for (int i = 0; i < reviewlist.size(); i++) {
            String ratting = reviewlist.get(i).getRatting();

            if (Float.parseFloat(ratting) > 4.0) {
                y += 1;

            }
            if (Float.parseFloat(ratting) > 3.0 && Float.parseFloat(ratting) <= 4.0) {
                z += 1;

            }
            if (Float.parseFloat(ratting) > 2.0 && Float.parseFloat(ratting) <= 3.0) {
                q += 1;

            }
            if (Float.parseFloat(ratting) > 1.0 && Float.parseFloat(ratting) <= 2.0) {
                m += 1;

            }
            if (Float.parseFloat(ratting) > 0.0 && Float.parseFloat(ratting) <= 1.0) {
                k += 1;

            }
        }
        Log.e(TAG, "onResponse:teswtertyre " + y);
        txt_5.setText(String.valueOf(y));
        txt_4.setText((String.valueOf(z)));
        txt_3.setText(String.valueOf(q));
        txt_2.setText(String.valueOf(m));
        txt_1.setText(String.valueOf(k));

        float text5 = (float) y * 100f / (float) reviewlist.size();
        float text4 = (float) z * 100f / (float) reviewlist.size();
        float text3 = (float) q * 100f / (float) reviewlist.size();
        float text2 = (float) m * 100f / (float) reviewlist.size();
        float text1 = (float) k * 100f / (float) reviewlist.size();

        int total = y + z + q + m + k;
        total_reviews.setText(total + " " + getString(R.string.reviews));
        progressbar5.setProgress((int) text5);
        progressbar4.setProgress((int) text4);
        progressbar3.setProgress((int) text3);
        progressbar2.setProgress((int) text2);
        progressbar1.setProgress((int) text1);
    }

}
