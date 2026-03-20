package view;

import controller.IController;
import exception.StatementExecutionException;
import model.expression.ArithmeticExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.state.*;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;

import java.util.Scanner;

public class MainView {
    public IRepository repo;
    public IController controller;

    public MainView(IRepository repo, IController controller) {
        this.repo = repo;
        this.controller = controller;
    }

    public void loadExample1(IController controller){
        IStmt ex1= new CompStmt(new VarDeclStmt( new IntType(),"v"),
                new CompStmt(new AssignStmt(new ValueExp(new IntValue(2)),"v"), new PrintStmt(new
                        VarExp("v"))));

        MyIStack<IStmt> exestack=new MyStack<>();
        MyIList<String> output=new MyList<>();
        MyIDictionary<String, Value> symTable=new MyDictionary<>();
        FileTable fileTable=new MapFileTable();
        PrgState current= new PrgState(exestack,symTable,output, ex1,fileTable);

        repo.add(current);
    }

    public void loadExample2(IController controller){
        IStmt ex2 = new CompStmt( new VarDeclStmt(new IntType(),"a"),
                new CompStmt(new VarDeclStmt(new IntType(),"b"),
                        new CompStmt(new AssignStmt( new ArithmeticExp(new ValueExp(new IntValue(2)),new
                                ArithmeticExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)),"*"),"+"),"a"),
                                new CompStmt(new AssignStmt(new ArithmeticExp(new VarExp("a"), new ValueExp(new
                                        IntValue(1)),"+"),"b"), new PrintStmt(new VarExp("b"))))));
        MyIStack<IStmt> exestack=new MyStack<>();
        MyIList<String> output=new MyList<>();
        MyIDictionary<String, Value> symTable=new MyDictionary<>();
        FileTable fileTable=new MapFileTable();
        PrgState current= new PrgState(exestack,symTable,output, ex2,fileTable);

        repo.add(current);
    }

    public void loadExample3(IController controller){
        IStmt ex3 = new CompStmt(new VarDeclStmt(new BooleanType(),"a"),
                new CompStmt(new VarDeclStmt(new IntType(),"v"),
                        new CompStmt(new AssignStmt( new ValueExp(new BoolValue(true)),"a"),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt(new ValueExp(new
                                        IntValue(2)),"v"), new AssignStmt( new ValueExp(new IntValue(3)),"v")), new PrintStmt(new
                                        VarExp("v"))))));
        MyIStack<IStmt> exestack=new MyStack<>();
        MyIList<String> output=new MyList<>();
        MyIDictionary<String, Value> symTable=new MyDictionary<>();
        FileTable fileTable=new MapFileTable();
        PrgState current= new PrgState(exestack,symTable,output, ex3,fileTable);

        repo.add(current);
    }

    public void loadExample4(IController controller){
        IStmt ex1_varf = new VarDeclStmt(new StringType(), "varf");
        IStmt ex2_assign = new AssignStmt(new ValueExp(new StringValue("test.in")), "varf");
        IStmt ex3_open = new OpenRFileStmt(new VarExp("varf"));
        IStmt ex4_varc = new VarDeclStmt(new IntType(), "varc");
        IStmt ex5_read_print = new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new PrintStmt(new VarExp("varc")));
        IStmt ex6_read_print = ex5_read_print;
        IStmt ex7_close = new CloseRFileStmt(new VarExp("varf"));

        IStmt finalExample = new CompStmt(ex1_varf,
                new CompStmt(ex2_assign,
                        new CompStmt(ex3_open,
                                new CompStmt(ex4_varc,
                                        new CompStmt(ex5_read_print,
                                                new CompStmt(ex6_read_print, ex7_close))))));
        MyIStack<IStmt> exestack=new MyStack<>();
        MyIList<String> output=new MyList<>();
        MyIDictionary<String, Value> symTable=new MyDictionary<>();
        FileTable fileTable=new MapFileTable();
        PrgState current= new PrgState(exestack,symTable,output, finalExample,fileTable);

        repo.add(current);
    }

    public void menu(){
        System.out.println("Menu");
        System.out.println("1. Example 1");
        System.out.println("2. Example 2");
        System.out.println("3. Example 3");
        System.out.println("4. Example 4");
        System.out.println("5. Execute all");
        System.out.println("6. Execute one step");
    }

    public void executeProgram(IController controller) throws StatementExecutionException {
        controller.allStep();
    }

    public void oneStep(IController controller, PrgState state) throws StatementExecutionException {
        controller.oneStep(state);
    }
    public void run(){
        menu();
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter your choice: ");
            int option=scanner.nextInt();
            switch (option){
                case 1: {
                    loadExample1(controller);
                    break;
                }
                case 2: {
                    loadExample2(controller);
                    break;
                }
                case 3:{
                    loadExample3(controller);
                    break;
                }
                case 4:{
                    loadExample4(controller);
                    break;
                }
                case 5:{
                    executeProgram(controller);
                    break;
                }
                case 6: {
                    PrgState state=repo.getCurrProg();
                    oneStep(controller,state);
                    break;
                }
                default: {
                System.out.println("Wrong choice");
                return;}
            }
            }
    }
}
