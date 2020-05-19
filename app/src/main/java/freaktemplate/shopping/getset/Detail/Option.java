package freaktemplate.shopping.getset.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Option {

    @SerializedName("optionname")
    @Expose
    private String optionname;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("required")
    @Expose
    private String required;
    @SerializedName("optionvalues")
    @Expose
    private List<Optionvalue> optionvalues = null;

    public String getOptionname() {
        return optionname;
    }

    public void setOptionname(String optionname) {
        this.optionname = optionname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public List<Optionvalue> getOptionvalues() {
        return optionvalues;
    }

    public void setOptionvalues(List<Optionvalue> optionvalues) {
        this.optionvalues = optionvalues;
    }

}
