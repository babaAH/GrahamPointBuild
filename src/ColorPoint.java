import java.awt.*;

class ColorPoint {
    private int x;
    private int y;
    private int size;
    private Color color;
    public int index;
    public double polarAngle;
    //    public ColorPoint()
//    {
//        x = 0;
//        y = 0;
//    }
//
    public ColorPoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.size = 10;
        this.color = Color.black;
    }


    public ColorPoint(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.size = 10;
        this.color = Color.black;
        this.index = index;
    }

    public ColorPoint(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

}

