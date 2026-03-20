package view;

import controller.Controller;
import exception.InvalidTypeException;
import model.expression.*;
import model.state.MyDictionary;
import model.state.PrgState;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.MyRepository;
import view.command.ExitCommand;
import view.command.RunExample;

import java.util.ArrayList;
import java.util.List;

class Interpreter {
    public static void main(String[] args) {

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt ex1 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt(new ValueExp(new IntValue(2)), "v"), new PrintStmt(new
                        VarExp("v"))));
        try {
            ex1.typecheck(new MyDictionary<String, Type>());
            PrgState prg1 = new PrgState(ex1);
            IRepository repo1 = new MyRepository(new ArrayList<>(List.of(prg1)), "log1.txt");
            Controller ctr1 = new Controller(repo1);

            menu.addCommand(new RunExample("1", "example 1", ctr1));
        } catch (Exception e) {
            System.out.println("Type check error: ex10 - "+ e.getMessage());
        }

        IStmt ex2 = new CompStmt(new VarDeclStmt(new IntType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "b"),
                        new CompStmt(new AssignStmt(new ArithmeticExp(new ValueExp(new IntValue(2)), new
                                ArithmeticExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), "*"), "+"), "a"),
                                new CompStmt(new AssignStmt(new ArithmeticExp(new VarExp("a"), new ValueExp(new
                                        IntValue(1)), "+"), "b"), new PrintStmt(new VarExp("b"))))));
        try{
            ex2.typecheck(new MyDictionary<>());
            PrgState prg2 = new PrgState(ex2);
            IRepository repo2 = new MyRepository(new ArrayList<>(List.of(prg2)), "log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", "example 2", ctr2));
        } catch (Exception e) {
            System.out.println("Type check error: ex10 - "+ e.getMessage());
        }

        IStmt ex3 = new CompStmt(new VarDeclStmt(new BooleanType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "v"),
                        new CompStmt(new AssignStmt(new ValueExp(new BoolValue(true)), "a"),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt(new ValueExp(new
                                        IntValue(2)), "v"), new AssignStmt(new ValueExp(new IntValue(3)), "v")), new PrintStmt(new
                                        VarExp("v"))))));

        try{
            ex3.typecheck(new MyDictionary<>());
            PrgState prg3 = new PrgState(ex3);
            IRepository repo3 = new MyRepository(new ArrayList<>(List.of(prg3)), "log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", "example 3", ctr3));
        } catch (Exception e) {
            System.out.println("Type check error: ex10 - "+ e.getMessage());
        }

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

        try{
            ex4.typecheck(new MyDictionary<>());
            PrgState prg4 = new PrgState(ex4);
            IRepository repo4 = new MyRepository(new ArrayList<>(List.of(prg4)), "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", "example 4", ctr4));
        } catch (Exception e) {
            System.out.println("Type check error: ex10 - "+ e.getMessage());
        }

        IStmt ex5 =new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));

        try{
            ex5.typecheck(new MyDictionary<>());
            PrgState prg5 = new PrgState(ex5);
            IRepository repo5 = new MyRepository(new ArrayList<>(List.of(prg5)), "log5.txt");
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", "example 5", ctr5));

        } catch (Exception e) {
            System.out.println("Type check error: ex10 - "+ e.getMessage());
        }

        IStmt ex6 = new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                                new PrintStmt(new ArithmeticExp(
                                                                new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5)),"+")))))));

        try{
            ex6.typecheck(new MyDictionary<>());
            PrgState prg6 = new PrgState(ex6);
            IRepository repo6 = new MyRepository(new ArrayList<>(List.of(prg6)), "log6.txt");
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", "example 6", ctr6));

        } catch (Exception e) {
            System.out.println("Type check error: ex10 - "+ e.getMessage());
        }

        IStmt ex7 = new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithmeticExp(new rH(new VarExp("v")),
                                                        new ValueExp(new IntValue(5)),"+"))))));

        try{
            ex7.typecheck(new MyDictionary<>());
            PrgState prg7 = new PrgState(ex7);
            IRepository repo7 = new MyRepository(new ArrayList<>(List.of(prg7)), "log7.txt");
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", "example 7", ctr7));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        IStmt ex8 = new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                        new PrintStmt(new rH(new rH(new VarExp("a")))))))));

        try{
            ex8.typecheck(new MyDictionary<>());
            PrgState prg8 = new PrgState(ex8);
            IRepository repo8 = new MyRepository(new ArrayList<>(List.of(prg8)), "log8.txt");
            Controller ctr8 = new Controller(repo8);
            menu.addCommand(new RunExample("8", "example 8", ctr8));

        } catch (Exception e) {
            System.out.println("Type check error: ex10 - "+ e.getMessage());
        }

        IStmt ex9 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt( new ValueExp(new IntValue(4)),"v"),
                        new CompStmt(new WhileStmt(new RelationalExp( new VarExp("v"), new ValueExp(new IntValue(0)),">"),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new AssignStmt(new ArithmeticExp( new VarExp("v"), new ValueExp(new IntValue(1)),"-"),"v"))),
                                new PrintStmt(new VarExp("v")))));

        try{
            ex9.typecheck(new MyDictionary<>());
            PrgState prg9 = new PrgState(ex9);
            IRepository repo9 = new MyRepository(new ArrayList<>(List.of(prg9)), "log9.txt");
            Controller ctr9 = new Controller(repo9);
            menu.addCommand(new RunExample("9", "example 9", ctr9));

        } catch (Exception e) {
            System.out.println("Type check error: ex10 - "+ e.getMessage());
        }

        IStmt ex10 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new VarDeclStmt(new RefType(new IntType()), "a"),
                        new CompStmt(new AssignStmt(new ValueExp(new IntValue(10)), "v"),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(new AssignStmt(new ValueExp(new IntValue(32)),"v"),
                                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new rH(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));

        try{
            ex10.typecheck(new MyDictionary<>());
            PrgState prg10 = new PrgState(ex10);
            IRepository repo10 = new MyRepository(new ArrayList<>(List.of(prg10)), "log10.txt");
            Controller ctr10 = new Controller(repo10);
            menu.addCommand(new RunExample("10", "example 10", ctr10));

        } catch (Exception e) {
            System.out.println("Type check error: ex10 - "+ e.getMessage());
        }


        IStmt ex11 = new CompStmt(new VarDeclStmt(new BooleanType(), "b"),
                new AssignStmt(new ValueExp(new IntValue(6)), "b"));

        try{
            ex11.typecheck(new MyDictionary<>());
            PrgState prg11 = new PrgState(ex11);
            IRepository repo11 = new MyRepository(new ArrayList<>(List.of(prg11)), "log11.txt");
            Controller ctr11 = new Controller(repo11);
            menu.addCommand(new RunExample("11", "example 11", ctr11));
        } catch (Exception e) {
            System.out.println("Type check error: ex11 - "+ e.getMessage());
        }

        menu.show();
    }
}
