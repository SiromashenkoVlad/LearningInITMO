package Peoples;

import Enums.Clothes;
import Enums.Role;
import Enums.Status;
import Other.Car;
import Other.Suitcase;

public class Policeman extends People{
    public Policeman(String name, Role role, double accuracy, Clothes clothes,
                     Status status){
        super(name, role, accuracy, clothes, status);
    }

    public boolean performASearch(Car <? extends People> target, Suitcase suitcase) {
        boolean haveCase = false;
        for(People man: target.getPassengers()){
            haveCase = this.performASearch(man, suitcase);
            if (haveCase){
                break;
            }
        }
        return haveCase;
    }

    public boolean performASearch(People target, Suitcase suitcase) {
        return target.hasSuitcase(suitcase);
    }

    @Override
    public String toString(){
        return super.toString() + " работает: полицейским";
    }
}
