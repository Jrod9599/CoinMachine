package com;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MachineController {


    @RequestMapping("/requestMoneyAmount")
    public String requestMoneyAmount() {
        return SnackMachine.getMoneyAmount();
    }

    @RequestMapping("/requestCoinAmount")
    public String requestCoinAmount() {
        return SnackMachine.getCoinAmount();
    }

    @RequestMapping("/changeLocation")
    public void changeLocation(@RequestParam(value = "newLocation") String location) {
        SnackMachine.changeLocation(location);

    }

    @RequestMapping("/getLocation")
    public void getLocation(@RequestParam(value = "newLocation") String location) {
        SnackMachine.changeLocation(location);

    }

/*    @RequestMapping("/pullLogFile")
    public String pullLogFile(){
        return abstractMachine.getLogFile();
    }*/

}


