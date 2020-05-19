package freaktemplate.shopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import freaktemplate.shopping.MyApplication;
import freaktemplate.shopping.R;
import freaktemplate.shopping.adapter.GridAdapter;
import freaktemplate.shopping.getset.Detail.Attribute;
import freaktemplate.shopping.getset.Detail.Attributename;
import freaktemplate.shopping.getset.HeaderGetSet;


public class DetailInformation extends Fragment {
    private RecyclerView recycle_table;
    private View rootview;
    private MyApplication myApp;
    private List<Attribute> attributeList;
    private List<Attributename> attributenameList;
    private ArrayList<HeaderGetSet> headerlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_detail_information, container, false);
        myApp = MyApplication.getInstance();
        inite();
        return rootview;
    }

    private void inite() {
        recycle_table = rootview.findViewById(R.id.recycle_table);
        attributeList = new ArrayList<>();
        attributenameList = new ArrayList<>();
        headerlist = new ArrayList<>();
        getList();
    }


    private void getList() {
        attributeList = myApp.getAttributesList();
        Log.d("ArrayName", "" + attributeList);
        HeaderGetSet getSet = new HeaderGetSet();
        getSet.setName(getString(R.string.specifications));
        getSet.setDpec(getString(R.string.features));
        getSet.setBrand(getString(R.string.brand));
        headerlist.add(getSet);

        if (attributeList.size() != 0) {
            for (int i = 0; i < attributeList.size(); i++) {
                String str_name = attributeList.get(i).getSet();
                attributenameList = attributeList.get(i).getAttributename();
                for (int j = 0; j < attributenameList.size(); j++) {
                    Log.d("ANYTHING_NAME", "" + str_name + " " + attributenameList.get(j).getName() + " " + attributenameList.get(j).getValues());
                    HeaderGetSet getSet1 = new HeaderGetSet();
                    if (j == 0) {
                        getSet1.setName(str_name);
                    }
                    getSet1.setBrand("" + attributenameList.get(j).getName());
                    List<String> valueList = attributenameList.get(j).getValues();
                    for (int k = 0; k < valueList.size(); k++) {

                        getSet1.setDpec(valueList.get(k));
                    }
                    getSet1.setAttributenameList(attributenameList);
                    headerlist.add(getSet1);
                }
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(rootview.getContext());
            recycle_table.setLayoutManager(layoutManager);
            GridAdapter adapter = new GridAdapter(rootview.getContext(), headerlist);
            recycle_table.setAdapter(adapter);
        }
        Log.d("HEADER", "" + headerlist);
    }
}