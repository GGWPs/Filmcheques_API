package nl.han.ise;

import nl.han.ise.DAO.RapportageDAO;
import nl.han.ise.Domain.Rapportage;
import nl.han.ise.Domain.RapportageLijst;
import nl.han.ise.Service.RapportageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RapportageServiceTest {

    @Mock
    RapportageDAO rapportageDAO;

    @InjectMocks
    RapportageService sut;


    @Test
    public void testGetRapportage(){
        //SETUP
        String rapportageString = "Rapportage";
        when(rapportageDAO.getRapportage(any())).thenReturn(rapportageString);

        //TEST
        Rapportage rapportage = sut.getRapportage("Rapportage");

        //VERIFY
        Assertions.assertEquals(rapportageString, rapportage.getRapportage());
    }

    @Test
    public void testGetRapportageLijst(){
        //SETUP
        RapportageLijst rapportageLijst = new RapportageLijst();
        rapportageLijst.getRapportageLijst().add("Rapportage");
        when(rapportageDAO.lijstRapportagesRedis()).thenReturn(rapportageLijst.getRapportageLijst());

        //TEST
        RapportageLijst rapportageLijst2 = sut.retrieveLijst();

        //VERIFY
        Assertions.assertEquals(rapportageLijst.getRapportageLijst(), rapportageLijst2.getRapportageLijst());
    }

}
