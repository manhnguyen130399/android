package manhnguyen.shopping.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import manhnguyen.shopping.R;

public class UtilHelper {
    public static final TypeAdapter<Number> UNRELIABLE_INTEGER = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            JsonToken jsonToken = in.peek();
            switch (jsonToken) {
                case NUMBER:
                case STRING:
                    String s = in.nextString();
                    try {
                        return Integer.parseInt(s);
                    } catch (NumberFormatException ignored) {
                    }
                    try {
                        return (int) Double.parseDouble(s);
                    } catch (NumberFormatException ignored) {
                    }
                    return null;
                case NULL:
                    in.nextNull();
                    return null;
                case BOOLEAN:
                    in.nextBoolean();
                    return null;
                default:
                    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
            }
        }

    };
    public static final TypeAdapterFactory UNRELIABLE_INTEGER_FACTORY = TypeAdapters.newFactory(int.class, Integer.class, UNRELIABLE_INTEGER);
    private static Dialog dialog_main;
    private static Dialog dialogs;

    public static void showdialog(Context context) {
        dialog_main = new Dialog(context);
        dialog_main.setContentView(R.layout.progress);

        dialog_main.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_main.show();
    }

    public static String setValue(Integer value) {
        String price;
        if (value == null) {
            price = "0";
        } else {
            price = String.valueOf(value);
        }
        return price;
    }

    public static void hidedialog() {
        dialog_main.dismiss();
    }

    public static void showLikedialog(Context context) {
        dialogs = new Dialog(context);
        dialogs.setContentView(R.layout.likedialoge);

        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogs.show();
    }

    public static void hideLikedialog() {
        dialogs.dismiss();
    }

}
