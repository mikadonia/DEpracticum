package sample;

import javafx.scene.chart.XYChart;

public class Exact {
    private double x0, y0, X, h;
    private double [] Xi;
    private static double [] Yi;
    private int n;
    //XYChart<X, Y> is responsible for drawing the two axes and the plot content.
    //It contains a list of all content in the plot and implementations of XYChart
    //can add nodes to this list that need to be rendered.
    //XYChart.Series<X,Y> == A named series of data items
    private XYChart.Series <Number, Number> exactSeries;

    public static double valueYi(int i) {
        return Yi[i];
    }

    //Create constructor of sample.Exact
    Exact (double x0, double y0, double X, int n){
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.n = n;

        //Compute number of points h = (b-a)/n where a=x0, b = X and n id=s number of points on [a,b]
        h = (X - x0) / n;

        //Create array of x values and fill in with Xi = x0 + ih
        Xi = new double[n + 1]; //x0...Xi
        for (int i = 0; i < n + 1; i++) {
            Xi[i] = x0 + i * h;
        }

        //Create array of y values
        Yi = new double[n + 1]; //y0...y(Xi)

        //sample.Exact series
        exactSeries= new XYChart.Series <Number, Number>();
        exactSeries.getData().clear();

        //Mark series as "sample.Exact"
        exactSeries.setName("sample.Exact");

        //end of constructor
    }

    //Method for solving sample.Exact equation
    XYChart.Series <Number, Number> exactSolution() {
        //Compute constant: Co = (Yo - Xo*sinXo)/Xo
        double c = ( y0 - x0 * Math.sin(x0))/x0;
        //Compute value: Yi = x*sinx + c*x
        for (int i = 0; i < Xi.length; i++) {
            Yi[i] = Xi[i] * Math.sin(Xi[i])+ c * Xi[i];

            //Add Xi and Yi to sample.Exact series
            exactSeries.getData().add(new XYChart.Data <Number, Number>(Xi[i], Yi[i]));
        }
        return exactSeries;
    }
}
