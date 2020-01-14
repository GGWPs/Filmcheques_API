package nl.han.ise.Service;

import nl.han.ise.DAO.RapportageDAO;
import nl.han.ise.Domain.Rapportage;
import nl.han.ise.Domain.RapportageLijst;

import javax.inject.Inject;

public class RapportageService {

    @Inject
    private RapportageDAO rapportageDAO;

    public RapportageLijst retrieveLijst(){
        RapportageLijst rapportageLijst = new RapportageLijst();
        rapportageLijst.setRapportageLijst(rapportageDAO.lijstRapportagesRedis());
        return rapportageLijst;
    }

    public Rapportage getRapportage(String rapportageNaam){
        Rapportage rapportage = new Rapportage();
        rapportage.setRapportage(rapportageDAO.getRapportage(rapportageNaam));
        return rapportage;
    }

    public RapportageLijst updateRapportages(){
        rapportageDAO.addAndUpdateAllRapportages();
        RapportageLijst rapportageLijst = new RapportageLijst();
        rapportageLijst.setRapportageLijst(rapportageDAO.lijstRapportagesRedis());
        return rapportageLijst;
    }
}
