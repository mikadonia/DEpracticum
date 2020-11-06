package sample;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane highField;

    @FXML
    private AnchorPane mainField;

    @FXML
    private Button computation;


    @FXML
    private TextField maxX;

    @FXML
    private TextField y0;

    @FXML
    private TextField x0;

    @FXML
    private TextField N;

    @FXML
    private LineChart<Number, Number> approximationChart;

    @FXML
    private NumberAxis yChartm;

    @FXML
    private NumberAxis xChartm;
    @FXML

    private NumberAxis yChartl;

    @FXML
    private NumberAxis xChartl;

    @FXML
    private NumberAxis yChartg;

    @FXML
    private NumberAxis xChartg;

    @FXML
    private LineChart<Number, Number> errorsChart;

    @FXML
    private LineChart<Number, Number> methodsChart;

    @FXML
    private CheckBox ExactCheck;

    @FXML
    private CheckBox EulerCheck;

    @FXML
    private CheckBox ImprovedEulerCheck;

    @FXML
    private CheckBox Runge_KuttaCheck;

    @FXML
    private TextField iP;

    @FXML
    private TextField Fp;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert xChartm != null : "fx:id=\"Chart\" was not injected: check your FXML file 'sample.fxml'.";
        assert yChartm != null : "fx:id=\"Chart\" was not injected: check your FXML file 'sample.fxml'.";
        assert xChartl != null : "fx:id=\"Chart\" was not injected: check your FXML file 'sample.fxml'.";
        assert yChartl != null : "fx:id=\"Chart\" was not injected: check your FXML file 'sample.fxml'.";
        assert xChartg != null : "fx:id=\"Chart\" was not injected: check your FXML file 'sample.fxml'.";
        assert yChartg != null : "fx:id=\"Chart\" was not injected: check your FXML file 'sample.fxml'.";
        assert highField != null : "fx:id=\"highField\" was not injected: check your FXML file 'sample.fxml'.";
        assert mainField != null : "fx:id=\"mainField\" was not injected: check your FXML file 'sample.fxml'.";
        assert computation != null : "fx:id=\"start\" was not injected: check your FXML file 'sample.fxml'.";
        assert maxX != null : "fx:id=\"X\" was not injected: check your FXML file 'sample.fxml'.";
        assert y0 != null : "fx:id=\"y0\" was not injected: check your FXML file 'sample.fxml'.";
        assert x0 != null : "fx:id=\"x0\" was not injected: check your FXML file 'sample.fxml'.";
        assert N != null : "fx:id=\"N\" was not injected: check your FXML file 'sample.fxml'.";
        assert approximationChart != null : "fx:id=\"approximationChart\" was not injected: check your FXML file 'sample.fxml'.";
        assert errorsChart != null : "fx:id=\"errorsChart\" was not injected: check your FXML file 'sample.fxml'.";
        assert methodsChart != null : "fx:id=\"methodsChart\" was not injected: check your FXML file 'sample.fxml'.";
        assert ExactCheck != null : "fx:id=\"ExactCheck\" was not injected: check your FXML file 'sample.fxml'.";
        assert EulerCheck != null : "fx:id=\"EulerCheck\" was not injected: check your FXML file 'sample.fxml'.";
        assert ImprovedEulerCheck != null : "fx:id=\"ImprovedEulerCheck\" was not injected: check your FXML file 'sample.fxml'.";
        assert Runge_KuttaCheck != null : "fx:id=\"Runge_KuttaCheck\" was not injected: check your FXML file 'sample.fxml'.";
        assert iP != null : "fx:id=\"iP\" was not injected: check your FXML file 'sample.fxml'.";
        assert Fp != null : "fx:id=\"Fp\" was not injected: check your FXML file 'sample.fxml'.";

    }


    @FXML
    void computation() {
        // Charts must be empty before start
        methodsChart.getData().clear();
        errorsChart.getData().clear();
        approximationChart.getData().clear();

        // Take values from a text fields
        double x = Double.parseDouble(x0.getCharacters().toString());
        double y = Double.parseDouble(y0.getCharacters().toString());
        double X = Double.parseDouble(maxX.getCharacters().toString());
        int n = Integer.parseInt(N.getCharacters().toString());
        int p0 = Integer.parseInt(iP.getCharacters().toString());
        int pN = Integer.parseInt(Fp.getCharacters().toString());

        // Plot Exact chart
        if (ExactCheck.isSelected()) { //Just Exact CheckBox
            //Exact object
            Exact exС = new Exact(x, y, X, n);
            //Compute by exactSolution(), get series with this solution and add series to the chart of methods
            System.out.println("data" + methodsChart.getData() + " " + exС.exactSolution().getData()); //just my checker
            methodsChart.getData().add(exС.exactSolution());
        }
        if (EulerCheck.isSelected()) { //Just euler CheckBox
            //Euler object
            Euler euC = new Euler(x, y, X, n);
            //Compute by eulerSolution(), get series with this solution and add series to the chart of methods
            methodsChart.getData().add(euC.eulerSolution());
            if (ExactCheck.isSelected()) { //Euler + Exact Checkboxes
                //Compute by eulerLTE(), get series with this errors and add this error series to the LTEchart
                errorsChart.getData().add(euC.eulerLTE());
                //Compute by eulerGTE(..), get series with this approximation and add this approximation series to the GTEchart
                approximationChart.getData().add(euC.eulerGTE(p0, pN));
            }
        }

        if (ImprovedEulerCheck.isSelected()) { //Just Improved Euler CheckBox
            //Improved Euler object
            ImprovedEuler imprv = new ImprovedEuler(x, y, X, n);
            //Compute by improvedSolution(), get series with this solution and add series to the chart of methods
            methodsChart.getData().add(imprv.improvedSolution());
            if (ExactCheck.isSelected()) { //Improved Euler + Exact CheckBoxes
                //Compute by improvedLTE(), get series with this errors and add series to the LTEchart
                errorsChart.getData().add(imprv.improvedLTE());
                //Compute by improvedGTE(...), get series with this approximation and add series to the GTEchart
                approximationChart.getData().add(imprv.improvedGTE(p0, pN));
            }
        }

        if (Runge_KuttaCheck.isSelected()) {//Just Runge-Kutta CheckBox
            //Runge-Kutta object
            RungeKutta rk = new RungeKutta(x, y, X, n);
            //Compute by rkSolution(), get series with this solution and add series to the chart of methods
            methodsChart.getData().add(rk.rkSolution());
            if (ExactCheck.isSelected()) { //Runge-Kutta + Exact CheckBoxes
                //Compute by rkLTE(), get series with this errors and add series to the LTEchart
                errorsChart.getData().add(rk.rkLTE());
                //Compute by rkGTE(...), get series with this approximation and add series to the GTEchart
                approximationChart.getData().add(rk.rkGTE(p0, pN));
            }

        }
    }
}