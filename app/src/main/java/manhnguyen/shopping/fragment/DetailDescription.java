package manhnguyen.shopping.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import manhnguyen.shopping.MyApplication;
import manhnguyen.shopping.R;
import manhnguyen.shopping.adapter.SpinnerSizeAdapter;
import manhnguyen.shopping.getset.Detail.Option;
import manhnguyen.shopping.getset.Detail.Optionvalue;
import me.wcy.htmltext.HtmlImageLoader;
import me.wcy.htmltext.HtmlText;
import me.wcy.htmltext.OnTagClickListener;

public class DetailDescription extends Fragment {


    private Spinner spin_color;
    private Spinner spin_size;
    private Spinner spin_rom;
    private RelativeLayout rel_spinnerromsize;
    private RelativeLayout rel_spinnersize;
    private RelativeLayout rel_spinnercolor;
    private MyApplication myApp;
    private TextView txt_option;
    private TextView txt_option2;
    private TextView txt_option1;
    private View rootview;
    private String strcolor;
    private String strsize;
    private String sku, cat, tag;
    private String desc;
    private String strsize1;
    private String strcolorprice;
    private String strramprice;
    private String strromprice;
    private String price;
    private Context context;

    public DetailDescription() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_detail_description, container, false);
        context = rootview.getContext();
        myApp = MyApplication.getInstance();
        getData();
        inite();
        getList();
        return rootview;
    }

    private void getData() {
        sku = myApp.getSku();
        cat = myApp.getCategory();
        tag = myApp.getTag();
        desc = myApp.getDesc();
        price = myApp.getProduct_price();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        inite();
        getList();
    }

    private void getList() {
        List<Option> list = new ArrayList<>();
        List<Optionvalue> colorlist = new ArrayList<>();
        List<Optionvalue> romlist = new ArrayList<>();
        List<Optionvalue> storagelist = new ArrayList<>();
        colorlist.clear();
        romlist.clear();
        storagelist.clear();
        if (myApp.getOptionlist() != null) {
            list = myApp.getOptionlist();
            try {
                romlist = list.get(0).getOptionvalues();
                myApp.setOptionvalue1(list.get(0).getOptionname());
                txt_option1.setText(list.get(0).getOptionname());
            } catch (Exception e) {
                e.printStackTrace();
                myApp.setOptionvalue1(null);
                myApp.setRam(null);
            }
            try {
                storagelist = list.get(1).getOptionvalues();
                myApp.setOptionvalue2(list.get(1).getOptionname());
                txt_option2.setText(list.get(1).getOptionname());
            } catch (Exception e) {
                e.printStackTrace();
                myApp.setOptionvalue2(null);
                myApp.setRom(null);
            }
            try {
                colorlist = list.get(2).getOptionvalues();
                myApp.setOptionvalue3(list.get(2).getOptionname());
                txt_option.setText(list.get(2).getOptionname());
            } catch (Exception e) {
                e.printStackTrace();
                myApp.setOptionvalue3(null);
                myApp.setColor(null);
            }
        }
        if (colorlist.size() != 0) {

            List<Optionvalue> colorlist1 = new ArrayList<>();
            for (int i = 0; i < colorlist.size(); i++) {

                if (i == 0) {
                    Optionvalue optionvalue = new Optionvalue();
                    optionvalue.setLabel(getString(R.string.select));
                    optionvalue.setPrice(colorlist.get(i).getPrice());
                    colorlist1.add(optionvalue);
                }
                Optionvalue optionvalue = new Optionvalue();
                optionvalue.setLabel(colorlist.get(i).getLabel());
                optionvalue.setPrice(colorlist.get(i).getPrice());
                colorlist1.add(optionvalue);


            }
            rel_spinnercolor.setVisibility(View.VISIBLE);
            SpinnerSizeAdapter adapter = new SpinnerSizeAdapter(rootview.getContext(), colorlist1);
            spin_color.setPrompt("Select ");
            spin_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        strcolor = colorlist1.get(position).getLabel();
                        strcolorprice = colorlist1.get(position).getPrice();
                        if (!strcolorprice.equals("")) {
                            if (position > 0) {
                                double total = Double.parseDouble(price) + Double.parseDouble(strcolorprice);
                                DetailFragment.txt_price.setText(getString(R.string.dolar) + "" + total);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (strcolor.equals(getString(R.string.select))) {
                        strcolor = null;
                    }
                    myApp.setColor(strcolor);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            rel_spinnercolor.setVisibility(View.GONE);
        }
        if (romlist.size() != 0) {
            List<Optionvalue> colorlist1 = new ArrayList<>();
            for (int i = 0; i < romlist.size(); i++) {

                if (i == 0) {
                    Optionvalue optionvalue = new Optionvalue();
                    optionvalue.setLabel(getString(R.string.select));
                    optionvalue.setPrice(null);
                    colorlist1.add(optionvalue);
                }
                Optionvalue optionvalue = new Optionvalue();
                optionvalue.setLabel(romlist.get(i).getLabel());
                optionvalue.setPrice(romlist.get(i).getPrice());
                colorlist1.add(optionvalue);
            }
            rel_spinnersize.setVisibility(View.VISIBLE);
            SpinnerSizeAdapter adapter = new SpinnerSizeAdapter(rootview.getContext(), colorlist1);
            spin_size.setAdapter(adapter);
            spin_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        strsize1 = colorlist1.get(position).getLabel();
                        strramprice = colorlist1.get(position).getPrice();
                        gettotaql(price, strromprice, strramprice);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (strsize1.equals(getString(R.string.select))) {
                        strsize1 = null;
                    }
                    myApp.setRam(strsize1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            rel_spinnersize.setVisibility(View.GONE);
        }
        if (storagelist.size() != 0) {
            List<Optionvalue> colorlist1 = new ArrayList<>();
            for (int i = 0; i < storagelist.size(); i++) {

                if (i == 0) {
                    Optionvalue optionvalue = new Optionvalue();
                    optionvalue.setLabel(getString(R.string.select));
                    optionvalue.setPrice(null);
                    colorlist1.add(optionvalue);
                }
                Optionvalue optionvalue = new Optionvalue();
                optionvalue.setLabel(storagelist.get(i).getLabel());
                optionvalue.setPrice(storagelist.get(i).getPrice());
                colorlist1.add(optionvalue);


            }
            rel_spinnerromsize.setVisibility(View.VISIBLE);
            SpinnerSizeAdapter adapter = new SpinnerSizeAdapter(rootview.getContext(), colorlist1);
            spin_rom.setAdapter(adapter);
            spin_rom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        strsize = colorlist1.get(position).getLabel();
                        strromprice = colorlist1.get(position).getPrice();
                        gettotaql(price, strromprice, strramprice);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (strsize.equals(getString(R.string.select))) {
                        strsize = null;
                    }
                    myApp.setRom(strsize);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            rel_spinnerromsize.setVisibility(View.GONE);
        }

    }

    private void gettotaql(String price, String strromprice, String strramprice) {
        if (strramprice == null || strramprice.equals(getString(R.string.select)) || strramprice.equals("")) {
            strramprice = "0.00";
        }
        if (strromprice == null || strromprice.equals(getString(R.string.select)) || strromprice.equals("")) {
            strromprice = "0.00";
        }
        Double total = Double.parseDouble(price) + Double.parseDouble(strromprice) + Double.parseDouble(strramprice);
        DetailFragment.txt_price.setText(getString(R.string.dolar) + "" + total.intValue());
    }

    private void inite() {
        spin_color = rootview.findViewById(R.id.spin_color);
        rel_spinnercolor = rootview.findViewById(R.id.rel_spinnercolor);
        rel_spinnersize = rootview.findViewById(R.id.rel_spinnersize);
        rel_spinnerromsize = rootview.findViewById(R.id.rel_spinnerromsize);
        TextView txt_text = rootview.findViewById(R.id.txt_text);
        spin_size = rootview.findViewById(R.id.spin_size);
        spin_rom = rootview.findViewById(R.id.spin_rom);
        TextView sku_text = rootview.findViewById(R.id.sku_text);
        TextView tags = rootview.findViewById(R.id.tags);
        TextView category = rootview.findViewById(R.id.category);
        spin_size = rootview.findViewById(R.id.spin_size);
        txt_option1 = rootview.findViewById(R.id.txt_option1);
        txt_option2 = rootview.findViewById(R.id.txt_option2);
        txt_option = rootview.findViewById(R.id.txt_option);

        if (sku != null) {
            sku_text.setText(sku);
        }
        if (cat != null) {
            category.setText(cat);
        }
        if (tag != null) {
            if (tag.equals("null")) {
                tags.setText(" ");
            } else {
                tags.setText(tag);
            }
        }
        if (desc != null) {
           /* if (Build.VERSION.SDK_INT >= 24) {
                txt_text.setText(Html.fromHtml(desc, Html.FROM_HTML_MODE_LEGACY));
            } else {
                txt_text.setText(Html.fromHtml(desc));
            }*/
            HtmlText.from(desc)
                    .setImageLoader(new HtmlImageLoader() {
                        @Override
                        public void loadImage(String url, final Callback callback) {
                            // Glide sample, you can also use other image loader

                            Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    callback.onLoadComplete(resource);
                                }
                            });
/*
                            Glide.with(context)
                                    .load(url)
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            callback.onLoadComplete(resource);
                                        }

                                    });
*/
                        }

                        @Override
                        public Drawable getDefaultDrawable() {
                            return null;
                        }

                        @Override
                        public Drawable getErrorDrawable() {
                            return null;
                        }

                        @Override
                        public int getMaxWidth() {
                            return 0;
                        }

                        @Override
                        public boolean fitWidth() {
                            return false;
                        }


                    })
                    .setOnTagClickListener(new OnTagClickListener() {
                        @Override
                        public void onImageClick(Context context, List<String> imageUrlList, int position) {
                            // image click
                        }

                        @Override
                        public void onLinkClick(Context context, String url) {
                            // link click
                        }
                    })
                    .into(txt_text);
        }
    }

}
