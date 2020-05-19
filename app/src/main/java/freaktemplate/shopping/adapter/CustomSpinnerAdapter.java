package freaktemplate.shopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.CategoryGetSet;


public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<CategoryGetSet> catList;
    private LayoutInflater inflter;

    public CustomSpinnerAdapter(Context applicationContext, ArrayList<CategoryGetSet> catList) {
        this.context = applicationContext;
        this.catList = catList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.cell_spinner_items, null);
        CategoryGetSet categoryGetSet = catList.get(i);
        TextView txt_catName = view.findViewById(R.id.txt_catName);
        txt_catName.setText(categoryGetSet.getName());
        return view;
    }
}