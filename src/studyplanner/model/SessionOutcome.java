package studyplanner.model;

import studyplanner.model.*;

import java.util.ArrayList;
import java.util.List;

public class SessionOutcome{
    private final OutcomeType[] types;

    public SessionOutcome(OutcomeType[] types) throws IllegalArgumentException {
        if (types == null){
            throw new IllegalArgumentException("Types can't be null!");
        }
        if (types.length == 0){
            throw new IllegalArgumentException("Types must contain at least one OutcomeType!");
        }
        for (OutcomeType t : types){
            if (t == null){
                throw new IllegalArgumentException("Types must not contain null element!");
            }
        }
        this.types = new OutcomeType[types.length];
        for (int i = 0; i < types.length; i++){
            this.types[i] = types[i];
        }
    }

    public OutcomeType[] getTypes(){
        OutcomeType[] copy = new OutcomeType[this.types.length];
        for (int i = 0; i < this.types.length; i++){
            copy[i] = this.types[i];
        }
        return copy;
    }
}