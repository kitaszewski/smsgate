package pl.rawinet.smsgate.dao;

import pl.rawinet.smsgate.model.OutSms;

public interface OutSmsDao {
    public void sendSms(OutSms sms);
}
