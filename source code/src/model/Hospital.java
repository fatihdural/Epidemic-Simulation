package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.util.*;

public class Hospital implements Observer {
    private Map<Integer, Individual> allPatients;
    private Queue<Integer> patients;    // producer - consumer
    private int ventilatorNum;
    private Model model;

    public Hospital(){
        patients = new LinkedList<>();
        allPatients = new HashMap<>();
    }

    public void addHospital(Individual individual, int key)  {
        allPatients.put(key, individual);
        patients.add(key);
    }

    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;
        this.model = model;

        int index = (Integer) arg;
        if( index == -1 ){
            int sizePopulation = model.getIndividuals().size();

            this.ventilatorNum = (sizePopulation / 100);  // --->  50/100 = 0.2 but 0, so adds 1

            if( patients.size() > 0 ){
                treatPeople();
            }
        }
    }

    private void treatPeople(){         // producer - consumer paradigm
        synchronized (this){        // only one thread access
            if(patients.size() < ventilatorNum){
                Integer individualKey = patients.poll();
                Individual individual = allPatients.get(individualKey);
                if( individual == null || individual.getSituation() != 'I' ){
                    allPatients.remove(individualKey);
                    return;
                }
                ventilatorNum--;

                individual.setSituation('U');
                model.hospitalInteraction(individualKey);

                // treatment time
                Timeline treatmentTime = new Timeline(new KeyFrame(Duration.millis( 10 * 1000), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if( individual.getSituation() == 'U' ){
                            individual.setSituation('H');

                            ventilatorNum++;

                            model.hospitalInteraction(individualKey);
                            allPatients.remove(individualKey);
                        }
                    }
                }));

                treatmentTime.setCycleCount(1);
                treatmentTime.play();
            }
        }
    }
}
