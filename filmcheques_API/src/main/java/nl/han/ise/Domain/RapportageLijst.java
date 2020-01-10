package nl.han.ise.Domain;

import java.util.ArrayList;
import java.util.List;

public class RapportageLijst {

    private List<String> rapportageLijst = new ArrayList<>();

    public RapportageLijst(){}

    public List<String> getRapportageLijst(){ return rapportageLijst;}

    public void setRapportageLijst(List<String> rapportageLijst) {
        this.rapportageLijst = rapportageLijst;
    }

}
