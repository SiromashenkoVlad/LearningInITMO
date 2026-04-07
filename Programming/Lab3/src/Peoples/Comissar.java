package Peoples;

import Enums.Clothes;
import Enums.Role;
import Enums.Status;

public class Comissar extends Policeman {
    public Comissar(String name, Role role, double accuracy, Clothes clothes,
                    Status status){
        super(name, role, accuracy, clothes, status);
    }

    public void InterrogationPeople(Bandit target){
        System.out.println(target.beenOnInterrogation());
    }
}
