package main.java.DomainModel.Users;

public class Manager extends User {
    private static Manager instance;
    private String OTP; 

    //private contstructor
    private Manager(int id, String firstName, String lastName, String email, String password, String OTP) {
        super(id, firstName, lastName,  email, password);
        this.OTP = OTP;
    }

    public static Manager getInstance(int id, String firstName, String lastName, String email, String password, String OTP) {
        if (instance == null) {
            instance = new Manager(id, firstName, lastName, email, password, OTP);
        }
        return instance;
    }

    public String getOTP() {
        return OTP;
    }
    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
