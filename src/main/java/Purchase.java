public class Purchase {
    public int purchaseID;
    public int customerID;
    public int productID;
    public int quantity;
    private double total;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(purchaseID).append(",");
        sb.append(customerID).append(",");
        sb.append(productID).append(",");
        sb.append(quantity).append(",");
        sb.append(total).append(")");
        return sb.toString();
    }

    public void setTotal(double tot)
    {
        total = tot;
    }
}
