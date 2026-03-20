package view;

import controller.Controller;
import model.expression.ArithmeticExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.state.PrgState;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.MyRepository;
import view.command.ExitCommand;
import view.command.RunExample;

import java.util.ArrayList;
import java.util.List;

class Interpreter {
    public static void main(String[] args) {

        IStmt ex1 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt(new ValueExp(new IntValue(2)), "v"), new PrintStmt(new
                        VarExp("v"))));
        PrgState prg1 = new PrgState(ex1);
        IRepository repo1 = new MyRepository(new ArrayList<>(List.of(prg1)), "log1.txt");
        Controller ctr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt(new VarDeclStmt(new IntType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "b"),
                        new CompStmt(new AssignStmt(new ArithmeticExp(new ValueExp(new IntValue(2)), new
                                ArithmeticExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), "*"), "+"), "a"),
                                new CompStmt(new AssignStmt(new ArithmeticExp(new VarExp("a"), new ValueExp(new
                                        IntValue(1)), "+"), "b"), new PrintStmt(new VarExp("b"))))));
        PrgState prg2 = new PrgState(ex2);
        IRepository repo2 = new MyRepository(new ArrayList<>(List.of(prg2)), "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStmt ex3 = new CompStmt(new VarDeclStmt(new BooleanType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "v"),
                        new CompStmt(new AssignStmt(new ValueExp(new BoolValue(true)), "a"),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt(new ValueExp(new
                                        IntValue(2)), "v"), new AssignStmt(new ValueExp(new IntValue(3)), "v")), new PrintStmt(new
                                        VarExp("v"))))));
        PrgState prg3 = new PrgState(ex3);
        IRepository repo3 = new MyRepository(new ArrayList<>(List.of(prg3)), "log3.txt");
        Controller ctr3 = new Controller(repo3);

        IStmt ex1_varf = new VarDeclStmt(new StringType(), "varf");
        IStmt ex2_assign = new AssignStmt(new ValueExp(new StringValue("test.in")), "varf");
        IStmt ex3_open = new OpenRFileStmt(new VarExp("varf"));
        IStmt ex4_varc = new VarDeclStmt(new IntType(), "varc");
        IStmt ex5_read_print = new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new PrintStmt(new VarExp("varc")));
        IStmt ex6_read_print = ex5_read_print;
        IStmt ex7_close = new CloseRFileStmt(new VarExp("varf"));

        IStmt ex4 = new CompStmt(ex1_varf,
                new CompStmt(ex2_assign,
                        new CompStmt(ex3_open,
                                new CompStmt(ex4_varc,
                                        new CompStmt(ex5_read_print,
                                                new CompStmt(ex6_read_print, ex7_close))))));
        PrgState prg4 = new PrgState(ex4);
        IRepository repo4 = new MyRepository(new ArrayList<>(List.of(prg4)), "log4.txt");
        Controller ctr4 = new Controller(repo4);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", "example 1", ctr1));
        menu.addCommand(new RunExample("2", "example 2", ctr2));
        menu.addCommand(new RunExample("3", "example 3", ctr3));
        menu.addCommand(new RunExample("4", "example 4", ctr4));
        menu.show();
    }
}
