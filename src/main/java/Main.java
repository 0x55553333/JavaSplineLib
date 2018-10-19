public class Main {

    public double[] y, x;

    public Main(int n, double delta, double x0) {
        y = new double[n];
        x = new double[n];
        for (int i = 0; i < n; ++i, x0 += delta) {
            x[i] = x0;
            y[i] = f(x0);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Generating data: n=100, d=0.1, x0=1");
        Main data = new Main(100, 0.1, 1);
        CubicSplineFitter fitter = new CubicSplineFitter(data.x, data.y);
        System.out.println("Interpolating spline");
        fitter.interpolate();
        System.out.println("Retrieving curve");
        CubicSplineInterval[] curve = fitter.getCurve();
        System.out.println("Spline pieces: " + curve.length);
        for (int i = 0; i < curve.length; ++i) {
            System.out.println("Spline" + i + ": " + curve[i]);
        }
        for (int i = 0; i < curve.length; ++i) {
            System.out.printf("F(%f)=%f; S(%f)=%f\n", data.x[i], data.y[i], data.x[i], curve[i].evaluate(data.x[i]));
        }
    }

    public double f(double x) {
        return Math.pow(x - 3, 3) + Math.pow(x + 2, 2) - x + 9;
    }
}
