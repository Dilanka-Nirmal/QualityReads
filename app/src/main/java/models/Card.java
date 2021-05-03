package models;

public class Card {

    String cardHolderNameEdt, cardNumberEdt, expiryDateEdt;

    public Card(){

    }

    public Card(String cardHolderNameEdt, String cardNumberEdt, String expiryDateEdt) {

        this.cardHolderNameEdt = cardHolderNameEdt;
        this.cardNumberEdt = cardNumberEdt;
        this.expiryDateEdt = expiryDateEdt;
    }

    public String getCardHolderNameEdt() {
        return cardHolderNameEdt;
    }

    public void setCardHolderNameEdt(String cardHolderNameEdt) {
        this.cardHolderNameEdt = cardHolderNameEdt;
    }

    public String getCardNumberEdt() {
        return cardNumberEdt;
    }

    public void setCardNumberEdt(String cardNumberEdt) {
        this.cardNumberEdt = cardNumberEdt;
    }

    public String getExpiryDateEdt() {
        return expiryDateEdt;
    }

    public void setExpiryDateEdt(String expiryDateEdt) {
        this.expiryDateEdt = expiryDateEdt;
    }
}
