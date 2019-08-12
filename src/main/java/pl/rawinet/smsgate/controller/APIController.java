package pl.rawinet.smsgate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rawinet.smsgate.model.OutSms;
import pl.rawinet.smsgate.model.Response;
import pl.rawinet.smsgate.service.MainService;

import java.util.List;

@CrossOrigin
@RestController
public class APIController {
    @Autowired
    MainService mainService;

    @Autowired
    Response response;

    @GetMapping("/")
    public Response hello(){
        response.setStatus("OK");
        response.setData("Hello");
        return response;
    }

    @GetMapping("/sec")
    public Response helloSec(){
        response.setStatus("OK");
        response.setData("Hello secured");
        return response;
    }

    @GetMapping(path="/sentitems/{user}", produces = "application/JSON")
    public List<OutSms> getUsersSentitems(@PathVariable(name = "user", required = true) String user){
        if(user.isEmpty()){return null;}
        return mainService.filterUsersSentitems(user);
    }

    @GetMapping(path="/inbox", produces = "application/JSON")
    public List<OutSms> getUsersSentitems(){
        return mainService.getInbox();
    }

    @GetMapping(path="/alarms", produces = "application/JSON")
    public Response countAlarms(){
        response.setStatus(mainService.countSendedAlarms().toString());
        response.setData("Sended alarms count.");
        return response;
    }

    @DeleteMapping(path="/clear", produces = "application/JSON")
    public Response removeAlarms(){
        mainService.removeAlarms();
        response.setStatus("Ok");
        response.setData("Alarms removed.");
        return response;
    }

    @PostMapping(path = "/new", consumes = "application/JSON", produces = "application/JSON")
    public Response newSms(@RequestBody OutSms sms){
        if(sms.getSender().contains("Detal_")){
            mainService.addOutSms(sms);
            response.setStatus("OK");
        } else {
            sms.setMessage("Wrong sender");
            response.setStatus("Crash");
        }

        response.setData(sms);
        return response;
    }


}
