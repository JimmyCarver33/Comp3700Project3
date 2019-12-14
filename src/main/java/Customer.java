public class Customer {
    public int customerID;
    public String lastName;
    public String firstName;
    public int phoneNum;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(customerID).append(",");
        sb.append("\"").append(lastName).append("\"").append(",");
        sb.append("\"").append(firstName).append("\"").append(",");
        sb.append(phoneNum).append(")");
        return sb.toString();

    }
}
