public class User {
    public static final int CUSTOMER = 0;
    public static final int CASHIER = 1;
    public static final int MANAGER = 2;
    public static final int ADMIN = 3;


    public String username, password, fullname;
    public int userType, usrCustomerID;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append("\"").append(username).append("\"").append(",");
        sb.append("\"").append(password).append("\"").append(",");
        sb.append(userType).append(")");
        return sb.toString();
    }

}
