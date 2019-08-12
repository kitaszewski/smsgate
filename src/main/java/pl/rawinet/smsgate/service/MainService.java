package pl.rawinet.smsgate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rawinet.smsgate.dao.OutSmsDaoImpl;
import pl.rawinet.smsgate.model.OutSms;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainService {

    @Autowired
    private OutSmsDaoImpl smsDao;

    public MainService(){
    }

    public void addOutSms(OutSms s) {
        if (s.getNumber().length() == 9){
            s.setNumber("0048"+s.getNumber());
        }
        smsDao.sendSms(s);
    }

    public List<OutSms> filterUsersSentitems(String user){
        return smsDao.selectAllSentitems()
                .stream()
                .filter(e -> e.getSender().contains("_"+user))
                .map( e -> new OutSms(e.getNumber().substring(4, e.getNumber().length()), e.getMessage(), e.getSender()))
                .collect(Collectors.toList());
    }

    public List<OutSms> getInbox(){
        return smsDao.selectAllInbox()
                .stream()
                .map(e -> new OutSms(e.getNumber().substring(3, e.getNumber().length()), e.getMessage(), e.getSender()))
                .collect(Collectors.toList());
    }

    public Integer countSendedAlarms(){
        return smsDao.countSendedAlarms();
    }

    public void removeAlarms(){
        smsDao.deleteAlarms();
    }
}
