package nl.han.ise.Service;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class TimerService {

    @Inject
    private RapportageService rapportageService;

    //Tijd waarop de rapportages worden geupdated.
    @Schedule(second="*/00", minute="*/00",hour="*/08", persistent=false)
    public void startUpdateRapportage(){
        rapportageService.updateRapportages();
    }
}
