package manhnguyen.shopping.activity;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.GzipRequestInterceptor;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;

import manhnguyen.shopping.R;
import manhnguyen.shopping.getset.ReportSpam.ReportSpam;
import manhnguyen.shopping.utils.MyCheckBox;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;
import okhttp3.OkHttpClient;


public class ReportSpamActivity extends AppCompatActivity {
    private static final String TAG = "ReportSpamActivity";
    private Button btn_submit;
    private EditText editinfo;
    private ArrayList<String> reportList = new ArrayList<>();
    private String userid;
    private MyCheckBox chk_phonnumber;
    private MyCheckBox chk_address;
    private MyCheckBox chk_placeclosed;
    private MyCheckBox chk_menuisincorrect;
    private String report;
    private ReportSpamActivity context;
    private Gson gson;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_spam);
        context = ReportSpamActivity.this;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder
                .registerTypeAdapterFactory(UtilHelper.UNRELIABLE_INTEGER_FACTORY)
                .create();
        okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new GzipRequestInterceptor())
                .build();
        userid = SPmanager.getPreference(ReportSpamActivity.this, "userID");
        init();
        clickevnts();
    }

    private void clickevnts() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (report != null) {
                        getReportspamFromServer();
                    } else {
                        Toast.makeText(ReportSpamActivity.this, getString(R.string.select_optionfirst), Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void ShowDialog(String title, String msg) {
        Dialog dialog;
        MaterialButton rele_close, rele_submit;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rele_submit = dialog.findViewById(R.id.rele_submit);
        rele_close = dialog.findViewById(R.id.rele_close);
        TextView txt_settingtitle = dialog.findViewById(R.id.txt_settingtitle);
        TextView txt_desc = dialog.findViewById(R.id.txt_desc);
        dialog.show();
        txt_settingtitle.setText(title);
        txt_desc.setText(msg);
        rele_close.setText(getString(R.string.cancle));
        rele_submit.setVisibility(View.GONE);
        rele_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (title.equals("Success")) {
                    onBackPressed();
                }
            }
        });

    }

    private void getReportspamFromServer() {
        UtilHelper.showdialog(context);
        final String moreInfo = editinfo.getText().toString().length()>0? editinfo.getText().toString():"Report";
        String url = getString(R.string.link) + "addcomplain?user_id=" + userid + "&description=" + moreInfo + "&complain_type=" + report;
        Log.e(TAG, "getReportspamFromServer: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);

                        try {
                            ReportSpam reportSpam = gson.fromJson(String.valueOf(response), ReportSpam.class);
                            if (reportSpam.getData().getStatus().equals(1)) {
                                UtilHelper.hidedialog();
                                ShowDialog("Success", reportSpam.getData().getMsg());
                            } else {
                                ShowDialog("Error", reportSpam.getData().getMsg());
                                UtilHelper.hidedialog();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            UtilHelper.hidedialog();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError.getErrorDetail());
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                        UtilHelper.hidedialog();
                    }
                });
    }

    private void init() {
        chk_phonnumber = findViewById(R.id.chk_phonnumber);
        chk_address = findViewById(R.id.chk_address);
        chk_placeclosed = findViewById(R.id.chk_placeclosed);
        editinfo = findViewById(R.id.edt_info);
        editinfo.clearFocus();

        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        chk_menuisincorrect = findViewById(R.id.chk_menuisincorrect);
        btn_submit = findViewById(R.id.btn_submit);

        chk_phonnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_phonnumber.isChecked()) {
                    reportList.add(chk_phonnumber.getText().toString());
                    report = TextUtils.join(",", reportList);
                    Log.e(TAG, "onClick: " + report);
                } else {
                    reportList.remove(chk_phonnumber.getText().toString());
                    report = TextUtils.join(",", reportList);
                    Log.e(TAG, "onClick: " + report);
                }
            }
        });
        chk_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_address.isChecked()) {
                    reportList.add(chk_address.getText().toString());
                    report = TextUtils.join(",", reportList);
                    Log.e(TAG, "onClick: " + report);
                } else {
                    reportList.remove(chk_address.getText().toString());
                    report = TextUtils.join(",", reportList);
                    Log.e(TAG, "onClick: " + report);
                }
            }
        });
        chk_menuisincorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_menuisincorrect.isChecked()) {
                    reportList.add(chk_menuisincorrect.getText().toString());
                    report = TextUtils.join(",", reportList);
                    Log.e(TAG, "onClick: " + report);
                } else {
                    reportList.remove(chk_menuisincorrect.getText().toString());
                    report = TextUtils.join(",", reportList);
                    Log.e(TAG, "onClick: " + report);
                }
            }
        });
        chk_placeclosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_placeclosed.isChecked()) {
                    reportList.add(chk_placeclosed.getText().toString());
                    report = TextUtils.join(",", reportList);
                    Log.e(TAG, "onClick: " + report);
                } else {
                    reportList.remove(chk_placeclosed.getText().toString());
                    report = TextUtils.join(",", reportList);
                    Log.e(TAG, "onClick: " + report);
                }
            }
        });
    }
}

