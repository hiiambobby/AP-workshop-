import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        // generic classes

        MyGenericClass<Integer> myInt = new MyGenericClass<>(1);
        MyGenericClass<Double> myDouble = new MyGenericClass<>(2.5);
        //MyGenericClass<String> myString = new MyGenericClass<>("Sab");
        ArrayList<String> myFrineds = new ArrayList<>();
        System.out.println(myDouble.getValue());
    }

    // in arraylist we are actually using a generic class because we can save all
    // the things

    //we learnt how to set bounderies
}
