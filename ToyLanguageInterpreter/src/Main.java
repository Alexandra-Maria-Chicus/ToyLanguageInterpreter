import controller.Controller;
import model.state.MyList;
import model.state.PrgState;
import repository.MyRepository;
import view.MainView;

void main() {
    List<PrgState> states=new ArrayList<>();
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter logFile name: ");
    String logFileName=sc.nextLine();
    MyRepository repo=new MyRepository(states,logFileName);
    Controller controller=new Controller(repo);
    MainView mainView=new MainView(repo,controller);
    mainView.run();
    sc.close();
}