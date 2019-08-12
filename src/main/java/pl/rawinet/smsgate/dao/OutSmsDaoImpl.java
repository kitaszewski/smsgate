package pl.rawinet.smsgate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.rawinet.smsgate.model.OutSms;

import java.util.List;

@Transactional
@Repository
public class OutSmsDaoImpl implements OutSmsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void sendSms(OutSms sms) {
        String query = "INSERT INTO outbox (DestinationNumber, TextDecoded, CreatorID, Coding) VALUES (?, ?, ?, 'Default_No_Compression');";
//        String query = "INSERT INTO outbox_test (DestinationNumber, TextDecoded, CreatorID, Coding) VALUES (?, ?, ?, 'Default_No_Compression');";
        jdbcTemplate.update(query, sms.getNumber(), sms.getMessage(), sms.getSender());
    }

    public List<OutSms> selectAllSentitems(){
        String query = "SELECT * FROM sentitems;";
        return jdbcTemplate.query(query, (resultSet, i) -> new OutSms(
                resultSet.getString("DESTINATIONNUMBER"),
                resultSet.getString("TEXTDECODED"),
                resultSet.getString("CREATORID"))
        );
    }

    public List<OutSms> selectAllInbox(){
        String query = "SELECT * FROM inbox;";
        return jdbcTemplate.query(query, (resultSet, i) -> new OutSms(
                resultSet.getString("SenderNumber"),
                resultSet.getString("TEXTDECODED"),
                resultSet.getString("ReceivingDateTime"))
        );
    }

    public Integer countSendedAlarms(){
        String query = "SELECT count(*) FROM sentitems WHERE CreatorID like 'Gammu%';";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public void deleteAlarms(){
        String query = "DELETE FROM sentitems WHERE CreatorID like 'Gammu%';";
        jdbcTemplate.update(query);
    }
}
