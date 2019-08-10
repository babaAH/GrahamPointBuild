import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args)
    {
        LinkedList<ColorPoint> list = new LinkedList<>();
        list.add(new ColorPoint(2,6,1));//1
        list.add(new ColorPoint(3,3,2));//2
        list.add(new ColorPoint(1,1,3));//3
        list.add(new ColorPoint(5,4,4));//4
        list.add(new ColorPoint(4,1,5));//5
        //list.add(new ColorPoint(6,6,6));
//        list.add(new ColorPoint(3,2,6));//6
//        list.add(new ColorPoint(4,3,7));//7


        Singleton a = Singleton.getInstance();
        Singleton b = Singleton.getInstance();

        System.out.println(a == b);
//        Graham test = new Graham(list);
        ArrayList<ColorPoint> listTest = new ArrayList<ColorPoint>();
        listTest = (ArrayList<ColorPoint>) GrahamScan.getConvexHull(list);
//        test.sort(list);
        for(ColorPoint p : listTest )
        {
            System.out.println(p.index);
        }
//        try
//        {
//            LinkedList<ColorPoint> res = test.act();
//            for(ColorPoint p : res)
//            {
//                System.out.println(p.index);
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        System.out.println("Hello World!");
    }
}
