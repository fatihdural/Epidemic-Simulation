package controller;

import model.Model;
import view.AlertView;
import view.SimulationView;

public class MainController {
    private final Model model;

    public MainController(Model model){
        SimulationView simulationView = new SimulationView(this);
        this.model = model;
        this.model.addObserver(simulationView);
    }

    public void addPersonHandler(String s, String m, String d, String c, String hm){
        Double speed;
        Double mask;
        Double distance;
        Double collide;
        Integer howMany;

        // check if string is not empty
        if( s.equals("") || m.equals("") || d.equals("") || c.equals("") || hm.equals("")){
            new AlertView();
            return;
        }

        // check if string is numeric
        try {
            speed = Double.parseDouble(s);
            mask = Double.parseDouble(m);
            distance = Double.parseDouble(d);
            collide = Double.parseDouble(c);
            howMany = Integer.parseInt(hm);

        } catch (NumberFormatException e) {
            new AlertView();
            return;
        }

        // check numbers is in range
        if( !(speed >= 1 && speed <= 500) ){
            new AlertView();
            return;
        }
        if( !(mask == 0.2 || mask == 1.0) ){
            new AlertView();
            return;
        }
        if( !(distance >= 0 && distance <= 9) ){
            new AlertView();
            return;
        }
        if( !(collide >= 1 && collide <= 5) ){
            new AlertView();
            return;
        }
        if( (howMany < 1) ){
            new AlertView();
            return;
        }
        model.addNewPerson(speed, mask, distance, collide, howMany);
    }

    public void pauseAllHandler() {
        model.pauseAll();
    }

    public void resumeAllHandler() {
        model.resumeAll();
    }
}