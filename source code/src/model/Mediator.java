package model;

import java.util.ArrayList;

public interface Mediator {
    void Register(Individual individual, int index);
    ArrayList<Integer> SuggestInteraction(int index);
}
