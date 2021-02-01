package model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.util.*;

public class Model extends Observable {
    private Map<Integer, Individual> individuals;
    private Map<Integer, Timeline> timers;
    private ArrayList<String> statistics;
    private IndividualMediator mediator;
    private ArrayList<Integer> keys;
    private int keyValue;
    private Hospital hospital;
    private Double constR, constZ;
    private boolean pause;

    public Model(){
        individuals = new HashMap<>();
        keys = new ArrayList<>();
        timers = new HashMap<>();
        statistics = new ArrayList<>();
        hospital = new Hospital();
        mediator = new IndividualMediator(individuals, timers);
        pause = false;
        keyValue = 0;

        this.addObserver(hospital);

        // statistics array must have 5 elements
        for(int i = 0; i < 5; i++){
            statistics.add("0");
        }

        Timeline generalTimer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int infected = 0, healthy = 0, hospitalized = 0, dead = 0;

                for(Individual individual : individuals.values()){
                    if( individual.getSituation() == 'I' ){
                        infected++;
                    }
                    else if( individual.getSituation() == 'H' || individual.getSituation() == 'W' ){
                        healthy++;
                    }
                    else if( individual.getSituation() == 'U' ){
                        hospitalized++;
                    }
                    statistics.set(0, String.valueOf(individuals.size()));
                    statistics.set(1, String.valueOf(infected));
                    statistics.set(2, String.valueOf(healthy));
                    statistics.set(3, String.valueOf(hospitalized));
                }
                setChanged();
                notifyObservers(-1); // -1 means update all elements(not only individuals)
            }
        }));

        generalTimer.setCycleCount(Animation.INDEFINITE);
        generalTimer.play();
        timers.put(0, generalTimer);
    }

    public void addConstants(Double constR, Double constZ){
        this.constR = constR;
        this.constZ = constZ;
    }

    public void addNewPerson(Double speed, Double mask, Double distance, Double collide, Integer howMany){
        int infectedPerson = -1;
        if( individuals.size() == 0){
            // random infection
            Random rand = new Random();
            infectedPerson = rand.nextInt(howMany);
        }

        for(int i = 0; i < howMany; i++){
            Individual individual = new Individual.Builder().
                    speed(speed).
                    mask(mask).
                    distance(distance).
                    collide(collide).
                    build();

            // random position
            Random rand = new Random();
            individual.setLayoutX((double) rand.nextInt(1000));
            individual.setLayoutY((double) rand.nextInt(600));

            // random direction
            int newRand =  rand.nextInt(4);
            individual.setDirection(newRand);

            // add to list
            keyValue++;
            int index = keyValue;
            individuals.put(index, individual);

            // total population set to array
            statistics.set(0, String.valueOf(index));

            // register to mediator
            mediator.Register(individual, index);

            // initial situation
            individual.setSituation('N');

            // create first time
            setChanged();
            notifyObservers(index);

            // if infected
            if( i == infectedPerson ){
                individual.setSituation('I');
                addNewInfected(index);
            }
            else{
                individual.setSituation('H');
            }

            // set the timer
            setTimer(individual, index);
        }
    }

    private void setTimer(Individual individual, int index){

        // if a person move 5 pixel in 1 seconds, it move 1 pixel every 1000/5 ms
        Timeline person = new Timeline(new KeyFrame(Duration.millis(1000/individual.getSpeed()), new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent t) {

                if( individual.getSituation() == 'H' || individual.getSituation() == 'I'){
                    double oldLayoutX = individual.getLayoutX();
                    double oldLayoutY = individual.getLayoutY();

                    double speed = 1;

                    double newLayoutX = 0;
                    double newLayoutY = 0;

                    // top left
                    if( individual.getDirection() == 0 ){
                        newLayoutX = oldLayoutX - speed;
                        newLayoutY = oldLayoutY - speed;
                    }

                    // top right
                    else if( individual.getDirection() == 1 ){
                        newLayoutX = oldLayoutX + speed;
                        newLayoutY = oldLayoutY - speed;
                    }

                    // bottom left
                    else if( individual.getDirection() == 2 ){
                        newLayoutX = oldLayoutX - speed;
                        newLayoutY = oldLayoutY + speed;
                    }

                    // bottom right
                    else if( individual.getDirection() == 3 ){
                        newLayoutX = oldLayoutX + speed;
                        newLayoutY = oldLayoutY + speed;
                    }

                    if( newLayoutX >= 995 - speed ){
                        int newRand = 0;
                        while(true){
                            Random rand = new Random();
                            newRand =  rand.nextInt(4);
                            if( newRand != individual.getDirection() && newRand != 1 && newRand != 3 ){
                                break;
                            }
                        }
                        individual.setDirection(newRand);
                    }
                    else if( newLayoutX <= speed ){
                        int newRand = 0;
                        while(true){
                            Random rand = new Random();
                            newRand =  rand.nextInt(4);
                            if( newRand != individual.getDirection() && newRand != 0 && newRand != 2 ){
                                break;
                            }
                        }
                        individual.setDirection(newRand);
                    }
                    else if( newLayoutY >= 595 - speed ){
                        int newRand = 0;
                        while(true){
                            Random rand = new Random();
                            newRand =  rand.nextInt(4);
                            if( newRand != individual.getDirection() && newRand != 2 && newRand != 3 ){
                                break;
                            }
                        }
                        individual.setDirection(newRand);
                    }
                    else if( newLayoutY <= speed ){
                        int newRand = 0;
                        while(true){
                            Random rand = new Random();
                            newRand =  rand.nextInt(4);
                            if( newRand != individual.getDirection() && newRand != 0 && newRand != 1 ){
                                break;
                            }
                        }
                        individual.setDirection(newRand);
                    }

                    individual.setLayoutX( newLayoutX);
                    individual.setLayoutY( newLayoutY);

                    ArrayList<Integer> returnedIndexes = mediator.SuggestInteraction(index); //?

                    if( returnedIndexes != null && returnedIndexes.size() == 2 ){
                        Integer controlledKey = returnedIndexes.get(0);
                        Integer tempKey = returnedIndexes.get(1);
                        Individual controlledPerson = individuals.get(controlledKey);
                        Individual tempPerson = individuals.get(tempKey);

                        char oldControlledStat = controlledPerson.getSituation();
                        char oldTempStat = tempPerson.getSituation();

                        controlledPerson.setSituation('W');
                        tempPerson.setSituation('W');

                        timers.get(controlledKey).stop();
                        timers.get(tempKey).stop();
                        controlledPerson.setLastCollideIndex(tempKey);
                        tempPerson.setLastCollideIndex(controlledKey);

                        // stay together
                        double distanceMin = Math.min(controlledPerson.getSocDistance(), tempPerson.getSocDistance());
                        double collideMax = Math.max(controlledPerson.getCollide(), tempPerson.getCollide());
                        Timeline collide = new Timeline(new KeyFrame(Duration.millis(collideMax * 1000), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if( oldControlledStat == 'I' && oldTempStat == 'I' ){
                                    controlledPerson.setSituation(oldControlledStat);
                                    tempPerson.setSituation(oldTempStat);
                                }

                                else if( oldControlledStat == 'I' || oldTempStat == 'I' ){

                                    double infectProbability = Math.min((constR * (1+collideMax/10)*controlledPerson.getMask()*tempPerson.getMask()* (1-distanceMin/10)), 1);

                                    if( new Random().nextDouble() <= infectProbability ) {  // probability
                                        if( oldControlledStat == 'I' ){
                                            controlledPerson.setSituation('I');
                                            tempPerson.setSituation('I');
                                            addNewInfected(tempKey);
                                        }
                                        else{
                                            controlledPerson.setSituation('I');
                                            tempPerson.setSituation('I');
                                            addNewInfected(controlledKey);
                                        }
                                    }
                                    else{
                                        controlledPerson.setSituation(oldControlledStat);
                                        tempPerson.setSituation(oldTempStat);
                                    }
                                }
                                else{
                                    controlledPerson.setSituation(oldControlledStat);
                                    tempPerson.setSituation(oldTempStat);
                                }

                                if( !pause ){
                                    timers.get(controlledKey).play();
                                    timers.get(tempKey).play();
                                }
                            }
                        }));

                        collide.setCycleCount(1);
                        collide.play();
                        timers.put(createUniqKey(), collide);
                    }
                    setChanged();
                    notifyObservers(index);
                }
            }
        }));
        person.setCycleCount(Timeline.INDEFINITE);
        person.play();
        timers.put(index, person);
    }

    public void hospitalInteraction(Integer key){
        setChanged();
        notifyObservers(key);
    }

    private void addNewInfected(Integer key){
        Individual individual = individuals.get(key);

        // go to hospital
        Timeline sendHospitalTimer = new Timeline(new KeyFrame(Duration.millis(25 * 1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if( individual.getSituation() == 'I' ){
                    hospital.addHospital(individual, key);
                }
            }
        }));
        sendHospitalTimer.setCycleCount(1);
        sendHospitalTimer.play();
        timers.put(createUniqKey(), sendHospitalTimer);

        // deadline
        Timeline dieTimer = new Timeline(new KeyFrame(Duration.millis((( 100 * (1 - constZ) ) * 1000)), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if( individual.getSituation() == 'I' || individual.getSituation() == 'W'){
                    individual.setSituation('D');
                    individuals.remove(key);

                    timers.remove(key);

                    setChanged();
                    notifyObservers(key);

                    int deadNum = Integer.parseInt(statistics.get(4));
                    deadNum++;
                    statistics.set(4, String.valueOf(deadNum));
                }
            }
        }));

        dieTimer.setCycleCount(1);
        dieTimer.play();

        timers.put(createUniqKey(), dieTimer);
    }

    public Map<Integer, Individual> getIndividuals() {
        return individuals;
    }

    private int createUniqKey(){
        int index = 0;
        while(true){
            index++;
            if( !(timers.containsKey(index)) && !(individuals.containsKey(index)) ){
                break;
            }
        }
        return index;
    }

    public ArrayList<String> getStatistics(){return statistics;}

    public void pauseAll() {
        pause = true;
        for(Timeline timeline : timers.values()){
            timeline.pause();
        }
    }

    public void resumeAll() {
        pause = false;
        for (Timeline timeline : timers.values()){
            timeline.play();
        }
    }
}
