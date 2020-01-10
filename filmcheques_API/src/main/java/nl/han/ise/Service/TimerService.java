package nl.han.ise.Service;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class TimerService {

    @Inject
    RapportageService rapportageService;


    @Schedule(second="*/10", minute="*",hour="*", persistent=false)
    public void doWork(){
        rapportageService.updateRapportages();
    }
}
