package nl.han.ise;


import nl.han.ise.Controller.RapportageController;
import nl.han.ise.Domain.Rapportage;
import nl.han.ise.Domain.RapportageLijst;
import nl.han.ise.Service.RapportageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RapportageControllerTest {

    @InjectMocks
    private RapportageController sut;

    @Mock
    private RapportageService rapportageService;

    @Test
    public void testGetAllRapportages(){
        //SETUP
        RapportageLijst rapportageLijst = new RapportageLijst();
        List<String> rapportage = new ArrayList<>();
        rapportage.add("Rapportage");
        rapportageLijst.setRapportageLijst(rapportage);
        lenient().when(rapportageService.retrieveLijst()).thenReturn(rapportageLijst);

        //TEST
        Response rapportageResponse = sut.getAllRapportage();

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), rapportageResponse.getStatus());
        assertEquals(rapportageLijst, rapportageResponse.getEntity());
    }

    @Test
    public void testGetRapportage(){
        //SETUP
        Rapportage rapportage = new Rapportage();
        rapportage.setRapportage("Rapportage");
        when(rapportageService.getRapportage(any())).thenReturn(rapportage);

        //TEST
        Response rapportageResponse = sut.getRapportage("Rapportage");

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), rapportageResponse.getStatus());
        assertEquals(rapportage, rapportageResponse.getEntity());
    }

    @Test
    public void testGetRapportageWithIncorrectParameter(){
        //SETUP
        when(rapportageService.getRapportage(any())).thenReturn(null);

        //TEST
        Response rapportageResponse = sut.getRapportage("Rapportage");

        //VERIFY
        assertEquals(null, rapportageResponse.getEntity());
    }





}
