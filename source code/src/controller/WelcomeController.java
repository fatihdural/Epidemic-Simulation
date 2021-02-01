package controller;

import model.Model;
import view.AlertView;
import view.WelcomeView;

public class WelcomeController {
    private final Model model;
    private final WelcomeView welcomeView;

    public WelcomeController(Model model){
        welcomeView = new WelcomeView(this);
        this.model = model;
    }

    public void startSimulationHandler(String R, String Z ){
        Double constantRvalue;
        Double constantZvalue;


        // check if string is not empty
        if( R.equals("") || Z.equals("") ){
            new AlertView();
            return;
        }

        // check if string is numeric
        try {
            constantRvalue = Double.parseDouble(R);
            constantZvalue = Double.parseDouble(Z);
        } catch (NumberFormatException e) {
            new AlertView();
            return;
        }

        // check numbers is in range
        if( !(constantRvalue >= 0.5 && constantRvalue <= 1.0) ){
            new AlertView();
            return;
        }
        // check numbers is in range
        if( !(constantZvalue >= 0.1 && constantZvalue <= 0.9) ){
            new AlertView();
            return;
        }

        // if there is no problem
        welcomeView.close(); // close welcome window
        model.addConstants(constantRvalue, constantZvalue);
        new MainController(model);
    }

}
