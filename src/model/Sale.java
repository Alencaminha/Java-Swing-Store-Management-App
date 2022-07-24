package model;

public class Sale {
    int id;
    float totalCost;
    String sellerUsername, date;

    public Sale(int id, float totalCost, String sellerUsername, String date) {
        this.id = id;
        this.totalCost = totalCost;
        this.sellerUsername = sellerUsername;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public String getDate() {
        return date;
    }
}
