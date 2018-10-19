import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;

public class CubicSplineFitter {

    private double[] x, a;
    private int n;
    private CubicSplineInterval[] curve;

    public CubicSplineFitter(double[] x, double[] a) throws InvalidArgumentException {
        if (x.length != a.length)
            throw new InvalidArgumentException(
                    new String[]{"Error: Mismatch of domain and range"});
        this.x = x;
        this.a = a;
        this.n = x.length - 1;
        this.curve = new CubicSplineInterval[n + 1];
        for (int i = 0; i < this.curve.length; ++i) {
            this.curve[i] = new CubicSplineInterval();
        }
    }

    void interpolate() {
        double[] h = new double[n + 1], alpha = new double[n + 1],
                l = new double[n + 1], mu = new double[n + 1], z = new double[n + 1];
        h[0] = x[1] - x[0];
        l[0] = 1;
        mu[0] = 0;
        z[0] = 0;
        for (int i = 1; i < n; ++i) {
            h[i] = x[i + 1] - x[i];
            alpha[i] = ((a[i + 1] - a[i]) * 3 / h[i]) - ((a[i] - a[i - 1]) * 3 / h[i - 1]);
            l[i] = 2 * (x[i + 1] - x[i - 1]) - h[i - 1] * mu[i - 1];
            mu[i] = h[i] / l[i];
            z[i] = (alpha[i] - h[i - 1] * z[i - 1]) / l[i];
        }
        l[n] = 1;
        z[n] = 0;
        this.curve[n].setCj(0);
        this.curve[n].setAj(a[n]);
        for (int j = n - 1; j >= 0; --j) {
            this.curve[j].setXl(x[j]);
            this.curve[j].setXr(x[j+1]);
            this.curve[j].setCj(z[j] - mu[j] * this.curve[j + 1].getCj());
            this.curve[j].setBj((a[j + 1] - a[j]) / h[j] - h[j]
                    * (this.curve[j + 1].getCj() - 2 * this.curve[j].getCj()) / 3);
            this.curve[j].setDj((this.curve[j + 1].getCj() - this.curve[j].getCj()) / (3 * h[j]));
            this.curve[j].setAj(a[j]);
        }
    }


    public CubicSplineInterval[] getCurve() {
        return this.curve;
    }
}
