package classes;

import java.awt.*;

public class _SOLUTION {
    public static void main(String[] args) {
        /*
        Picture r = new Picture("juju.png"); r.allRed();
        Picture g = new Picture("juju.png"); g.allGreen();
        Picture b = new Picture("juju.png"); b.allBlue();

        Picture.overlay(r, g, b).explore();

        Picture.grayscale(new Picture("juju.png")).explore();

        Picture n = new Picture("juju.png"); n.negative(true, true, false);
                n.explore();

        Picture gree = new Picture("juju.png");
        gree.allGreen();
        Picture neg = new Picture("juju.png");
        neg.negative(true,true,true);
        Picture shf = new Picture("juju.png"); shf.colorShift();
        Picture.overlay(shf,gree,neg).explore();
        */
        Picture p = new Picture("moon-surface.jpg"); p.mirrorDiagonal(); p.explore();

    }


}
