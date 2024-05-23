public class MyGenericClass<Thing extends Number> {
    Thing x;

    MyGenericClass(Thing x) {
        this.x = x;

    }

    public Thing getValue() {
        return x;
    }

}