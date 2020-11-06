package sample;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Computational practicum");
        primaryStage.setScene(new Scene(root, 700, 1300));
        primaryStage.show();
    }

    // Function for y' =  y/x + x cos(x)
    public static double F(double x, double y) {
        return y/x + x * Math.cos(x);
    }

    public static void main(String[] args) {
        launch(args);
    }
}