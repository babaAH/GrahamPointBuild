public class Singleton {
    private Singleton(){

    }

    private static volatile Singleton instanse;

    public static Singleton getInstance()
    {
        if(instanse == null){
            synchronized (Singleton.class) {
                instanse = new Singleton();
            }
        }

        return instanse;
    }
}
