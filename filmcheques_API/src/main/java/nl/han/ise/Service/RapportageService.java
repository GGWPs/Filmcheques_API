package nl.han.ise.Service;

import nl.han.ise.DAO.RapportageDAO;
import nl.han.ise.Domain.Rapportage;

import javax.inject.Inject;

public class RapportageService {

    @Inject
    private RapportageDAO rapportageDAO;


    public Rapportage rapportage(){
        Rapportage rapportage = new Rapportage();

        rapportage.setRapportage(rapportageDAO.test());
        return rapportage;
    }
}
