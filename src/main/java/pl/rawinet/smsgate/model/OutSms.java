package pl.rawinet.smsgate.model;

public class OutSms {
    private String number;
    private String message;
    private String sender;

    public OutSms() {
    }

    public OutSms(String number, String message, String sender) {
        this.number = number;
        this.message = message;
        this.sender = sender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "OutSms{" +
                "number='" + number + '\'' +
                ", message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
