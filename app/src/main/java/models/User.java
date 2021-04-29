package models;

public class User {

    String firstNameEdt, lastNameEdt, addressEdt, phoneNumberEdt, emailEdtSignUp, passwordEdtSignUp;

    public User() {
    }

    public User(String firstNameEdt, String lastNameEdt, String addressEdt, String phoneNumberEdt, String emailEdtSignUp, String passwordEdtSignUp) {
        this.firstNameEdt = firstNameEdt;
        this.lastNameEdt = lastNameEdt;
        this.addressEdt = addressEdt;
        this.phoneNumberEdt = phoneNumberEdt;
        this.emailEdtSignUp = emailEdtSignUp;
        this.passwordEdtSignUp = passwordEdtSignUp;
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

    public String getPasswordEdtSignUp() {
        return passwordEdtSignUp;
    }

    public void setPasswordEdtSignUp(String passwordEdtSignUp) {
        this.passwordEdtSignUp = passwordEdtSignUp;
    }
}
