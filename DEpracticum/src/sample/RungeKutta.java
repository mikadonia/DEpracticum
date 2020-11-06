package sample;
import javafx.scene.chart.XYChart;
import static sample.Main.F;

public class RungeKutta {
    private double x0, y0, X, h;
    private double [] Xi;
    private double [] Yi;
    private XYChart.Series <Number, Number> rkSeries;
    private XYChart.Series <Number, Number> rkErrors; //LTE
    private XYChart.Series <Number, Number> rkApproximation; //GTE

    //Create constructor of RungeKutta
    RungeKutta (double x0, double y0, double X, int n) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;

        //Compute number of points h = (b-a)/n where a=x0, b = X and n id=s number of points on [a,b]
        h = (X - x0) / n;

        //Create array of x values and fill in with Xi = x0 + ih
        Xi = new double[n + 1]; //x0...Xi
        for (int i = 0; i < n + 1; i++) {
            Xi[i] = x0 + i * h;
        }

        //Create array of y values
        Yi = new double[n + 1]; //y0...y(Xi)
        Yi[0] = y0; //y(x0) = y0

        //Create Runge Kutta Series
        rkSeries = new XYChart.Series <Number, Number>();
        rkSeries.getData().clear();
        rkErrors = new XYChart.Series <Number, Number>();
        rkErrors.getData().clear();
        rkApproximation = new XYChart.Series <Number, Number>();
        rkApproximation.getData().clear();

        //Mark series as Runge-Kutta
        rkSeries.setName("Runge-Kutta");
        rkErrors.setName("Runge-Kutta");
        rkApproximation.setName("Runge-Kutta");

        //end of constructor
    }

    XYChart.Series <Number, Number> rkSolution() {
        //add 1st point (x0, y0)
        rkSeries.getData().add(new XYChart.Data <Number, Number>(x0,y0));

        //Compute Runge-Kutte method by formulas in comments below
        for (int i = 1; i < Xi.length; ++i) {
            double k1 = Main.F(Xi[i - 1], Yi[i - 1]); //K1i = F(Xi, Yi)
            double k2 = Main.F(Xi[i - 1] + h / 2, Yi[i - 1] + k1 * (h / 2)); //K2i = F(Xi + h/2, Yi + h/2*K1i)
            double k3 = Main.F(Xi[i - 1] + h / 2, Yi[i - 1] + k2 * (h / 2)); //K3i = F(Xi + h/2, Yi + h/2*K2i)
            double k4 = Main.F(Xi[i - 1] + h, Yi[i - 1] + k3 * h); //K4i = F(Xi + h, Yi + h*K3i)
            Yi[i] = Yi[i - 1] + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4); //Yi+1 = Yi + h/6(K1i + 2K2i + 2K3i + K4i)
            //Add Xi and Yi to series
            rkSeries.getData().add(new XYChart.Data<Number, Number>(Xi[i], Yi[i]));
        }
        return rkSeries;
    }

    XYChart.Series <Number, Number> rkLTE() {
        //Error for i-th point
        for (int i = 0; i < Yi.length; ++i) {
            rkErrors.getData().add(new XYChart.Data<Number, Number>(Xi[i], Math.abs(Exact.valueYi(i) - Yi[i])));
        }
        return rkErrors;
    }

    XYChart.Series <Number, Number> rkGTE(int p0, int pN) {
        for (int i = p0; i <= pN; ++i) {
            //For getting approximation we should solve exact equation
            Exact ex = new Exact(x0, y0, X, i);
            ex.exactSolution();
            //RungeKutta object
            RungeKutta rung = new RungeKutta(x0, y0, X, i);
            rung.rkSolution();
            //Search max error
            double max = 0;
            for (int j = 0; j < rung.Yi.length; ++j) {
                if (max < Math.abs(ex.valueYi(j) - rung.Yi[j]))
                    max = Math.abs(ex.valueYi(j) - rung.Yi[j]);
            }
            //Add error to Runge-Kutta Series
            rkApproximation.getData().add(new XYChart.Data<Number, Number> (i, max));
        }
        return rkApproximation;
    }
}