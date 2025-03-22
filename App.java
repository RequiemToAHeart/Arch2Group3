public class App {
    public static void main(String[] args) {
        // Create Model, View, and Controller
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);

        // Display the GUI
        view.setVisible(true);
    }
}