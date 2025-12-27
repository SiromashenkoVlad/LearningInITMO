package Other;

import Enums.Status;
import Interfaces.Shooter;
import Peoples.People;

import java.util.ArrayList;
import java.util.Random;

public class ArmoredCar <T extends People> extends Car implements Shooter {
    public ArmoredCar(Integer cntPassengers, ArrayList<T> lstPassengers){
        super(cntPassengers, lstPassengers);
    }

    @Override
    public void shootAt(People target){
        target.changeStatus(Status.DIED);
    }

    @Override
    public void shootAt(Car target) {
        target.changeStatus(Status.CAPTURED);
    }

    @Override
    public void shootAt(ArmoredCar target) {
        Random random = new Random();
        double check = random.nextDouble();
        if (check < 0.5){
            target.changeStatus(Status.DIED);
        }
        else {
            target.takeDamage();
        }
    }
}
