package com.example.thinkinjava;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

class Glyph {
    void draw() {
        System.out.println("Glyph.draw()");
    }
    Glyph() {
        System.out.println("Glyph() before draw()");
        draw();
        System.out.println("Glyph() after draw()");
    }
}

class RoundGlyph extends Glyph {
    private int radius = 1;

    RoundGlyph(int r) {
        radius = r;
        System.out.println("RoundGlyph.RoundGlyph(). radius = " + radius);
    }

    void draw() {
        System.out.println("RoundGlyph.draw(). radius = " + radius);
    }
}

public class PolyConstructors {

    public static void main(String[] args) {
        new RoundGlyph(5);

    }

}
