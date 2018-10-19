import java.io.Serializable;

// spline curve of format:
// S(x):= aj + bj(x-xj) + cj(x-xj)^2 + dj(x-xj)^3
public class CubicSplineInterval implements Serializable {
    private static final double eps = 0.000000001;
    private double a_j, b_j, c_j, d_j, xl, xr;
    private long j;

    public static double getEps() {
        return eps;
    }

    public double getAj() {
        return a_j;
    }

    public void setAj(double a_j) {
        this.a_j = a_j;
    }

    public double getBj() {
        return b_j;
    }

    public void setBj(double b_j) {
        this.b_j = b_j;
    }

    public double getCj() {
        return c_j;
    }

    public void setCj(double c_j) {
        this.c_j = c_j;
    }

    public double getDj() {
        return d_j;
    }

    public void setDj(double d_j) {
        this.d_j = d_j;
    }

    public double getXl() {
        return xl;
    }

    public void setXl(double xl) {
        this.xl = xl;
    }

    public double getXr() {
        return xr;
    }

    public void setXr(double xr) {
        this.xr = xr;
    }

    public long getJ() {
        return j;
    }

    public void setJ(long j) {
        this.j = j;
    }

    public CubicSplineInterval(long j, double a, double b, double c, double d, double xl, double xr) {
        this.j = j;
        this.a_j = a;
        this.b_j = b;
        this.c_j = c;
        this.d_j = d;
        this.xl = xl;
        this.xr = xr;
    }

    public CubicSplineInterval() {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    private double eval(double x) {
        return this.a_j + this.b_j * (x - this.xl)
                + this.c_j * Math.pow(x - this.xl, 2)
                + this.d_j * Math.pow(x - this.xl, 3);
    }

    public double evaluate(double x) throws IllegalArgumentException {
        if (!this.isInInterval(x)) throw new IllegalArgumentException();
        return this.eval(x);
    }

    public boolean isInInterval(double x) {
        return (x >= xl - eps) || (x <= xr + eps);
    }

    @Override
    public String toString() {
        return String.format("S_[%f, %f](x):= %f + %f(x-%f) + %f(x-%f)^2 + %f(x-%f)^3",
                this.xl, this.xr,
                this.a_j, this.b_j, this.xl, this.c_j, this.xl, this.d_j, this.xl);
    }
}
