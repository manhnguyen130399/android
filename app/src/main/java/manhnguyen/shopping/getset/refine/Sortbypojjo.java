package manhnguyen.shopping.getset.refine;

public class Sortbypojjo {

    boolean isSelected = false;
    int previousSelectedPosition = -1;
    private String name, id;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPreviousSelectedPosition() {
        return previousSelectedPosition;
    }

    public void setPreviousSelectedPosition(int previousSelectedPosition) {
        this.previousSelectedPosition = previousSelectedPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
