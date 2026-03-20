package view.command;

import controller.Controller;
import exception.StatementExecutionException;

public class RunExample extends Command{
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller=controller;
    }

    @Override
    public void execute() {
        try{
            controller.allStep();
        } catch (StatementExecutionException e) {
            System.out.println("Execution Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Critical Error: " + e.getMessage());
        }
    }
}
