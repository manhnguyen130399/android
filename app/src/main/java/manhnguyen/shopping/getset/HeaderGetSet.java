package manhnguyen.shopping.getset;

import java.util.List;

import manhnguyen.shopping.getset.Detail.Attributename;

public class HeaderGetSet {
    List<Attributename> attributenameList;
    private String name, brand, dpec;

    public List<Attributename> getAttributenameList() {
        return attributenameList;
    }

    public void setAttributenameList(List<Attributename> attributenameList) {
        this.attributenameList = attributenameList;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDpec() {
        return dpec;
    }

    public void setDpec(String dpec) {
        this.dpec = dpec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
