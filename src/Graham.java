import java.util.LinkedList;


public class Graham
{

    LinkedList<ColorPoint> points;
    //ColorPoint p0;


    public Graham(LinkedList points)
    {
        this.points = points;
        //this.p0 = findMinX(this.points);
    }

    @Deprecated
    private ColorPoint findMinY(ColorPoint[] points)
    {
        ColorPoint min;
        min = points[0];
        for(int i = 0; i < points.length; i++)
        {
            if(min.getY() > points[i].getY())
            {
                min = points[i];
            }
        }
        return min;
    }

    @Deprecated
    public static ColorPoint findMinY(LinkedList<ColorPoint> points)//worked
    {
        ColorPoint min;
        min = points.getFirst();
        int index = 0;
        for(int i = 0; i < points.size(); i++)
        {
            if(min.getY() > points.get(i).getY())
            {
                index = i;
                min = points.get(i);
            }
        }

        ColorPoint temp = points.getFirst();
        points.set(0, min);
        points.set(index, temp);
        //points.remove(min);
        return min;
    }

    public ColorPoint findMinX(LinkedList<ColorPoint> points)//untested
    {
        ColorPoint min;
        min = points.get(0);
        int index = 0;
        for(int i = 0; i < points.size(); i++)
        {
            if(min.getX() > points.get(i).getX())
            {
                index = i;
                min = points.get(i);
            }
        }
        ColorPoint temp = points.getFirst();
        points.set(0, min);
        points.set(index, temp);
        //points.remove(min);
        return min;
    }

    public int rotate(ColorPoint A, ColorPoint  B, ColorPoint C)//untested
    {
        int det = (B.getX() - A.getX()) * (C.getY() - B.getY()) - (B.getX() - A.getX()) * (C.getX() - B.getX());
        if(det > 0)
            return 1;
        if(det == 0)
            return 0;

        return -1;
    }

    public LinkedList<ColorPoint> act() throws Exception//untested
    {
        int arrSize = points.size();
        if(arrSize < 3)
        {
            throw new Exception("points length must been 3 or greate");
        }
        else
        {
            if((arrSize) == 3)
            {
                //points.add(p0);
                return points;
            }
            else
            {
                LinkedList<ColorPoint> pointsCopy = points;
                findMinY(pointsCopy);
                sort();
                LinkedList<ColorPoint> result = new LinkedList<>();
                result.add(pointsCopy.getFirst());
                result.add(pointsCopy.get(1));
                pointsCopy.remove(0);

                for(ColorPoint p : pointsCopy)
                {
                    if(rotate(result.get(result.size() - 2), result.getLast(), p) > 0)
                    {
                        result.add(p);
                    }
                    else
                    {
                        result.removeLast();
                    }
                }
//                points = result;
            }
            return  points;

        }
    }

    private void sort()//untested
    {
        //ColorPoint[] pointsArr = (ColorPoint[])points.toArray();
        for(int i = 2; i < points.size(); i++)
        {
            int j = i - 1;
            ColorPoint value = points.get(i);
            while(j >= 0 && rotate(points.getFirst(), points.get(j), value) < 0)///*pointsArr[j].polarAngle < value.polarAngle*/ )
            {
                ColorPoint temp = points.get(j + 1);
                points.set(j + 1, points.get(j));
                points.set(j, temp);
                j--;
            }
            points.set(j + 1, value);
        }
    }


    public void sort(LinkedList<ColorPoint> points)//untested
    {
        findMinY(points);
        //ColorPoint[] pointsArr = (ColorPoint[])points.toArray();
        for(int i = 2; i < points.size(); i++) {
            int j = i;
//            ColorPoint value = points.get(i);
            while (j > 1 &&
                    rotate(points.getFirst(), points.get(j - 1), points.get(i)) < 0)///*pointsArr[j].polarAngle < value.polarAngle*/ )
            {
                ColorPoint temp = points.get(j - 1);
                points.set(j - 1, points.get(j));
                points.set(j, temp);
                j--;
            }
//            points.set(j + 1, value);
        }
    }

}


