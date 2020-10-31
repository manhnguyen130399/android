package manhnguyen.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import manhnguyen.shopping.R;
import manhnguyen.shopping.adapter.OrderlistAdapter;
import manhnguyen.shopping.getset.CatPojjo;
import manhnguyen.shopping.getset.OrderSubItemGetSet;
import manhnguyen.shopping.getset.Orderlist.Order;
import manhnguyen.shopping.getset.Orderlist.OrderListpojjo;
import manhnguyen.shopping.getset.Orderlist.SubList;
import manhnguyen.shopping.utils.AdManager;
import manhnguyen.shopping.utils.SPmanager;
import manhnguyen.shopping.utils.UtilHelper;

public class OrderList extends AppCompatActivity {

    private static final String TAG = "OrderList";
    private ListView recycle_order;
    private LinkedHashMap<CatPojjo, List<SubList>> map;
    private ShimmerRecyclerView order_recycle;
    private OrderList context;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        context = OrderList.this;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder
                .registerTypeAdapterFactory(UtilHelper.UNRELIABLE_INTEGER_FACTORY)
                .create();
        inite();
    }

    private void inite() {
        map = new LinkedHashMap<>();
        ArrayList<OrderSubItemGetSet> itemlist = new ArrayList<>();
        order_recycle = findViewById(R.id.order_recycle);
        recycle_order = findViewById(R.id.recycle_order);
        ImageView ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getList();
    }


    private void getList() {
        order_recycle.showShimmer();
        String userID = SPmanager.getPreference(context, "userID");
        String url = getString(R.string.link) + "order_history/" + userID;
        Log.e(TAG, "getList: " + url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            OrderListpojjo orderListpojjo = gson.fromJson(String.valueOf(response), OrderListpojjo.class);
                            Integer status = orderListpojjo.getData().getStatus();
                            if (status.equals(1)) {
                                List<Order> orders = orderListpojjo.getData().getOrders();
                                for (int i = 0; i < orders.size(); i++) {
                                    CatPojjo order = new CatPojjo();
                                    order.setName(orders.get(i).getName());

                                    List<SubList> sublists = orders.get(i).getSubList();
                                    List<SubList> subData = new ArrayList<>();
                                    for (int j = 0; j < sublists.size(); j++) {
                                        SubList subList = sublists.get(j);
                                        subData.add(subList);
                                    }
                                    if (subData.size() != 0) {
                                        map.put(order, subData);
                                    }
                                }
                                final ArrayList list = new ArrayList();
                                for (CatPojjo getSet : map.keySet()) {
                                    list.add(getSet);
                                    list.addAll(map.get(getSet));
                                }
                                order_recycle.hideShimmer();
                                setorderList(list);
                            } else {
                                order_recycle.hideShimmer();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            order_recycle.hideShimmer();
                            Log.e(TAG, "onResponse: " + e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        order_recycle.hideShimmer();
                        Log.e(TAG, "onError: " + anError.getErrorBody());
                    }
                });
    }

    private void setorderList(ArrayList list) {
        order_recycle.setVisibility(View.GONE);
        OrderlistAdapter adapter = new OrderlistAdapter(OrderList.this, list);
        recycle_order.setAdapter(adapter);
        recycle_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubList c = (SubList) adapter.getItem(position);
                Intent intent = new Intent(OrderList.this, MyOrderActivity.class);
                intent.putExtra("order_id", String.valueOf(c.getOrderNo()));
                intent.putExtra("key", "my_order");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                AdManager.increaseCount(context);
                AdManager.showInterstial(context);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdManager.setUpInterstial(context);
    }
}