package org.easycloud.las.analyzer.recommender;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-7-30
 * Time: 上午10:02
 */
public class RecommendedItem implements Serializable {

    private String itemId;
    private double prefValue;

    public RecommendedItem() {
    }

    public RecommendedItem(String itemId, double prefValue) {
        this.itemId = itemId;
        this.prefValue = prefValue;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getPrefValue() {
        return prefValue;
    }

    public void setPrefValue(double prefValue) {
        this.prefValue = prefValue;
    }

    @Override
    public String toString() {
        return "RecommendedItem{" +
                "itemId='" + itemId + '\'' +
                ", prefValue=" + prefValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecommendedItem that = (RecommendedItem) o;

        if (Double.compare(that.prefValue, prefValue) != 0) return false;
        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = itemId != null ? itemId.hashCode() : 0;
        temp = Double.doubleToLongBits(prefValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
