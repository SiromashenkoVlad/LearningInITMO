package common.Exceptions;

public class WrongDateType extends Exception{
    public  WrongDateType(String conditions, String data){
        super("Условия на данные: " + conditions + "\nДанные, которые были переданы: "+ data);
    }
}
