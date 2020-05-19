package freaktemplate.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.getset.Detail.Optionvalue;
import freaktemplate.shopping.interfaces.SizeClick;

public class SpinnerSizeAdapter extends BaseAdapter {
    Context context;
    private List<Optionvalue> colorlist;
    private LayoutInflater inflater;
    SizeClick SizeClick;

    public void setsizeClick(SizeClick sizeClick) {
        this.SizeClick = sizeClick;
    }

    public SpinnerSizeAdapter(Context context, List<Optionvalue> colorlist) {
        this.context = context;
        this.colorlist = colorlist;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return colorlist.size();
    }

    @Override
    public Object getItem(int position) {
        return colorlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.cell_size, parent, false);
        }
        TextView txt_size = vi.findViewById(R.id.txt_size);
        txt_size.setText(colorlist.get(position).getLabel());
      /*  if (position == 0) {
            try {
                txt_size.setText(colorlist.get(position).getLabel());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                txt_size.setText(colorlist.get(position).getLabel());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

      /*  if (colorlist.get(position) == null) {
            vi = inflater.inflate(R.layout.cell_sizes, parent, false);
            TextView txt_size = vi.findViewById(R.id.txt_size);

            try {
                txt_size.setText("Select");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.cell_size, parent, false);
            }
            TextView txt_size = vi.findViewById(R.id.txt_size);

            try {
                txt_size.setText(colorlist.get(position).getLabel());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/


        return vi;
    }
}
