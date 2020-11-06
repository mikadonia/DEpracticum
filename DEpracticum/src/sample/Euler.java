package sample;

import javafx.scene.chart.XYChart;

import static sample.Main.F;

public class Euler {
    private double x0, y0, X, h;
    private double [] Xi;
    private double [] Yi;

    //XYChart<X, Y> is responsible for drawing the two axes and the plot content.
    //It contains a list of all content in the plot and implementations of XYChart
    //can add nodes to this list that need to be rendered.
    //XYChart.Series<X,Y> == A named series of data items
    private XYChart.Series <Number, Number> eulerSeries;
    private XYChart.Series <Number, Number> eulerApproximation;
    private XYChart.Series <Number, Number> eulerErrors;

    //Create constructor of sample.Euler method
    Euler(double x0, double y0, double X, int n){
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;

        //Compute number of points h = (b-a)/n where a=x0, b = X and n id=s number of points on [a,b]
        h = (X - x0) / n;

        //Create array of x values and fill in with Xi = x0 + ih
        Xi = new double[n + 1]; //x0...Xi
        for (int i = 0; i < n + 1; ++i) {
            Xi[i] = x0 + i * h;
        }

        //Create array of y values
        Yi = new double[n + 1]; //y0...y(Xi)
        Yi[0] = y0; //y(x0) = y0

        //sample.Euler Series
        eulerSeries = new XYChart.Series <Number, Number>();
        //getData() == Gets the value of the property data.
        eulerSeries.getData().clear();

        eulerApproximation = new XYChart.Series <Number, Number>();
        eulerApproximation.getData().clear();

        eulerErrors = new XYChart.Series <Number, Number>();
        eulerErrors.getData().clear();

        //Mark series as "sample.Euler"
        eulerSeries.setName("sample.Euler");
        eulerApproximation.setName("sample.Euler");
        eulerErrors.setName("sample.Euler");

        //end of constructor
    }

    XYChart.Series <Number, Number> eulerSolution() {
        //add 1st point (x0, y0)
        eulerSeries.getData().add(new XYChart.Data <Number, Number>(x0, y0));
        //Compute Yi by general formula: Y[i+1] = Yi + h*F(Xi, Yi)
        //rewrite general formula for Yi
        for (int i = 1; i < Xi.length; ++i) {
            Yi[i] = Yi[i - 1] + h * F(Xi[i - 1], Yi[i - 1]);
            //Add Xi and Yi to euler series
            eulerSeries.getData().add(new XYChart.Data <Number, Number>(Xi[i], Yi[i]));
        }
        return eulerSeries;
    }

    //sample.Euler errors
    XYChart.Series <Number, Number> eulerLTE() { //LTE
        //Error for i-th point
        for (int i = 0; i < Yi.length; ++i) {
            //add error to euler errors series (Xi[i] and |Exact Yi[i] - Yi[i]|
            eulerErrors.getData().add(new XYChart.Data <Number, Number> (Xi[i], Math.abs(Exact.valueYi(i) - Yi[i])));
        }
        return eulerErrors;
    }

    XYChart.Series <Number, Number> eulerGTE(int p0, int pN) { //GTE
        for (int i = p0; i <= pN; ++i) {
            //For getting approximation we should solve exact equation
            Exact ex = new Exact(x0, y0, X, i);
            ex.exactSolution();
            //Euler object to compute total approximation error
            Euler eu = new Euler(x0, y0, X, i);
            eu.eulerSolution();
            //Search for max error
            double max = 0;
            for (int j = 0; j < eu.Yi.length; j++) {
                if (max <= Math.abs(ex.valueYi(j) - eu.Yi[j])) {
                    max = Math.abs(ex.valueYi(j) - eu.Yi[j]);
                }
            }
            //Add error to the euler approximation series
            eulerApproximation.getData().add(new XYChart.Data<Number, Number> (i, max));
        }
        return eulerApproximation;
    }





}
