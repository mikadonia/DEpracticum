package sample;
import javafx.scene.chart.XYChart;
import static sample.Main.F;

public class ImprovedEuler {
        private double x0, y0, X, h;
        private double [] Xi;
        private double [] Yi;
        private XYChart.Series <Number, Number> improvedSeries; //method chart
        private XYChart.Series <Number, Number> improvedErrors; //LTE
        private XYChart.Series <Number, Number> improvedApproximation; //GTE series

        // Create constructor of improved Euler method
        ImprovedEuler (double x0, double y0, double X, int n) {
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

            //Create Improved Euler Series
            improvedSeries = new XYChart.Series <Number, Number>();
            improvedSeries.getData().clear();
            improvedErrors = new XYChart.Series <Number, Number>();
            improvedErrors.getData().clear();
            improvedApproximation = new XYChart.Series <Number, Number>();
            improvedApproximation.getData().clear();

            //Mark series as "Improved Euler"
            improvedSeries.setName("Improved Euler");
            improvedErrors.setName("Improved Euler");
            improvedApproximation.setName("Improved Euler");

            //end of constructor
        }

        XYChart.Series <Number, Number> improvedSolution() {
            //add 1st point (x0, y0)
            improvedSeries.getData().add(new XYChart.Data<Number, Number>(x0,y0));
            //Compute Improved Euler method by formulas in comments below
            for (int i = 1; i < Xi.length; ++i) {
                double k1 = Main.F(Xi[i - 1], Yi[i - 1]); //K1i =F(Xi, Yi)
                double k2 = Main.F(Xi[i], Yi[i - 1] + h * k1); //K2i = F(Xi, Yi + h*K1i)
                Yi[i] = Yi[i - 1] + (h / 2) * (k1 + k2); //Yi+1 = Yi + h/2 (K1i + K2i)
                //Add Xi and Yi to Improved Euler series
                improvedSeries.getData().add(new XYChart.Data <Number, Number> (Xi[i], Yi[i]));
            }
            return improvedSeries;
        }

        XYChart.Series <Number, Number> improvedLTE() {
            //Error for i-th point
            for (int i = 0; i < Yi.length; ++i) {
                improvedErrors.getData().add(new XYChart.Data<Number, Number>(Xi[i], Math.abs(Exact.valueYi(i) - Yi[i])));
            }
            return improvedErrors;
        }

        XYChart.Series <Number, Number> improvedGTE(int p0, int pN) {
            for (int i = p0; i <= pN; ++i) {
                //For getting approximation we should solve exact equation
                Exact ex = new Exact(x0, y0, X, i);
                ex.exactSolution();
                //Create euler object to compute GTE
                ImprovedEuler impr = new ImprovedEuler(x0, y0, X, i);
                impr.improvedSolution();
                //Search for max error
                double max = 0;
                for (int j = 0; j < impr.Yi.length; ++j) {
                    if (max < Math.abs(ex.valueYi(j) - impr.Yi[j]))
                        max = Math.abs(ex.valueYi(j) - impr.Yi[j]);
                }
                //Add error to the Improved Euler Approximation Series
                improvedApproximation.getData().add(new XYChart.Data<Number, Number> (i, max));
            }
            return improvedApproximation;
        }
}
