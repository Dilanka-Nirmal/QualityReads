package models;

public class User {

    String UID, firstNameEdt, lastNameEdt, addressEdt, phoneNumberEdt, emailEdtSignUp;
    int userType;

    public User() {
    }

    public User(String UID, String firstNameEdt, String lastNameEdt, String addressEdt, String phoneNumberEdt, String emailEdtSignUp, int userType) {
        this.UID = UID;
        this.firstNameEdt = firstNameEdt;
        this.lastNameEdt = lastNameEdt;
        this.addressEdt = addressEdt;
        this.phoneNumberEdt = phoneNumberEdt;
        this.emailEdtSignUp = emailEdtSignUp;
        this.userType = userType;
    }

    public User(String firstNameEdt, String lastNameEdt, String addressEdt, String phoneNumberEdt, String emailEdtSignUp) {
        this.firstNameEdt = firstNameEdt;
        this.lastNameEdt = lastNameEdt;
        this.addressEdt = addressEdt;
        this.phoneNumberEdt = phoneNumberEdt;
        this.emailEdtSignUp = emailEdtSignUp;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getFirstNameEdt() {
        return firstNameEdt;
    }

    public void setFirstNameEdt(String firstNameEdt) {
        this.firstNameEdt = firstNameEdt;
    }

    public String getLastNameEdt() {
        return lastNameEdt;
    }

    public void setLastNameEdt(String lastNameEdt) {
        this.lastNameEdt = lastNameEdt;
    }

    public String getAddressEdt() {
        return addressEdt;
    }

    public void setAddressEdt(String addressEdt) {
        this.addressEdt = addressEdt;
    }

    public String getPhoneNumberEdt() {
        return phoneNumberEdt;
    }

    public void setPhoneNumberEdt(String phoneNumberEdt) {
        this.phoneNumberEdt = phoneNumberEdt;
    }

    public String getEmailEdtSignUp() {
        return emailEdtSignUp;
    }

    public void setEmailEdtSignUp(String emailEdtSignUp) {
        this.emailEdtSignUp = emailEdtSignUp;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
