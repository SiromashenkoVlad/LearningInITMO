package Interfaces;

import Other.ArmoredCar;
import Other.Car;
import Peoples.People;

public interface Shooter {
    void shootAt(People target);
    void shootAt(Car target);
    void shootAt(ArmoredCar target);
}
