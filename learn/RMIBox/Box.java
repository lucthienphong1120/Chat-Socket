package learn.RMIBox;

import java.io.Serializable;

public class Box implements Serializable {

    private double w, d, h;

    private static final long serialVersionUID = 712203765183510254L;

    public void updateSize(double deltaW, double deltaD, double deltaH) {
        w += deltaW;
        h += deltaH;
        d += deltaD;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

}
