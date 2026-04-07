import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class Parent {
    private String parentPrivate;
    public int parentPublic;
}

class Child extends Parent {
    private String childPrivate;
    protected double childProtected;
    public static final int CONSTANT = 42;
}

public class FieldTest {
    public static void main(String[] args) {
        System.out.println("=== getDeclaredFields() ===");
        Field[] declaredFields = Child.class.getDeclaredFields();
        for (Field f : declaredFields) {
            System.out.println(f.getName() + " : " + Modifier.toString(f.getModifiers()));
        }

        System.out.println("\n=== getFields() ===");
        Field[] fields = Child.class.getFields();
        for (Field f : fields) {
            System.out.println(f.getName() + " : " + Modifier.toString(f.getModifiers()));
        }
    }
}