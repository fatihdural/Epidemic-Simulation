package model;

import javafx.animation.Timeline;
import java.util.ArrayList;
import java.util.Map;

public class IndividualMediator implements Mediator {
    final Map<Integer, Individual> individuals;
    final Map<Integer, Timeline> timers;

    IndividualMediator(Map<Integer, Individual> individuals, Map<Integer, Timeline> timers){
        this.individuals = individuals;
        this.timers = timers;
    }

    @Override
    public void Register(Individual individual, int index) {
        individuals.put(index, individual);
    }

    @Override
    public ArrayList<Integer> SuggestInteraction(int index) {
        Individual controlledPerson = individuals.get(index);

            synchronized (individuals) {
                if( controlledPerson != null ){
                    if(controlledPerson.getSituation() == 'H' || controlledPerson.getSituation() == 'I' ) {
                        double controlledX = controlledPerson.getLayoutX();
                        double controlledY = controlledPerson.getLayoutY();
                        double socDistance = controlledPerson.getSocDistance();

                        double controlledXmax = controlledX + socDistance+5;
                        double controlledXmin = controlledX - socDistance-5;

                        double controlledYmax = controlledY + socDistance+5;
                        double controlledYmin = controlledY - socDistance-5;

                        for (Integer key : individuals.keySet()) {
                            Individual tempPerson = individuals.get(key);
                            double tempX = tempPerson.getLayoutX();
                            double tempY = tempPerson.getLayoutY();

                            if (((tempX <= controlledXmax) && (tempX >= controlledXmin))
                                    && ((tempY <= controlledYmax) && (tempY >= controlledYmin))
                                    && key != index
                                    && tempPerson.getSituation() != 'W'
                                    && tempPerson.getSituation() != 'U'
                                    && controlledPerson.getLastCollideIndex() != key
                            ) {
                                ArrayList<Integer> returnedIndexes = new ArrayList<>();
                                returnedIndexes.add(index);
                                returnedIndexes.add(key);
                                return returnedIndexes;
                            }
                        }
                    }
                }
            }
            return null;
    }
}
