import Enums.Clothes;
import Enums.Locations;
import Enums.Role;
import Enums.Status;
import Groups.BanditGang;
import Groups.PoliceConvoy;
import Other.ArmoredCar;
import Other.Car;
import Other.Suitcase;
import Peoples.Bandit;
import Peoples.Comissar;
import Peoples.People;
import Peoples.Policeman;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Suitcase searchChemodan = new Suitcase(100);

        Policeman Rei = new Policeman("Rei", Role.POLICEMAN, 0.2, Clothes.POLICE, Status.ALIVE);
        Policeman Pars = new Policeman("Pars", Role.POLICEMAN, 0.2, Clothes.POLICE, Status.ALIVE);
        Policeman Ruta = new Policeman("Ruta", Role.POLICEMAN, 0.2, Clothes.POLICE, Status.ALIVE);
        List<Policeman> policeGroup = new ArrayList<>();
        policeGroup.add(Pars);
        policeGroup.add(Rei);
        policeGroup.add(Ruta);

        Car<Policeman> policeCar = new Car<>(4, policeGroup);
        List<Car<? extends People>> policeCars = new ArrayList<>();
        policeCars.add(policeCar);
        List<ArmoredCar<? extends People>> policeArmCars = new ArrayList<>();
        PoliceConvoy policeConvoy = new PoliceConvoy("ФСБ",  policeGroup, policeCars, policeArmCars, Locations.STREET);

        Bandit Serii = new Bandit("Серый", Role.BANDIT, 0.8, 50, Clothes.BANDIT, Status.ALIVE);
        Bandit Valera = new Bandit("Валерон", Role.BANDIT, 0.4, 60, Clothes.BANDIT, Status.ALIVE);
        Bandit Fotic = new Bandit("Фотик", Role.BANDIT, 0.6, 35, Clothes.BANDIT, Status.ALIVE);


        List<Bandit> banditGroup = new ArrayList<>();
        banditGroup.add(Serii);
        banditGroup.add(Valera);
        banditGroup.add(Fotic);



        Car<? extends People> banditCar = new Car<>(4, banditGroup);
        List<Car<? extends People>> banditCars = new ArrayList<>();
        banditCars.add(banditCar);
        BanditGang banditGang = new BanditGang("Волки АУФ", banditGroup, banditCars, Locations.STREET);


        boolean isSuccessfullChaise = policeConvoy.chasing(banditGang);
        if (isSuccessfullChaise){
            if (policeConvoy.checkForSuitcse(banditGang, searchChemodan)){
                System.out.println("Чемодан найден");
            }
            else {
                System.out.println("При обыске чемодан не найден");
            }


            Comissar comissar = new Comissar("Mr. Success", Role.COMMISSIONER, 1.0, Clothes.POLICE, Status.ALIVE);
            for (Bandit man: banditGang.getListAliveMembers()){
                comissar.InterrogationPeople(man);
            }

        }
        else{
            System.out.println("Бандиты оторвались");
        }
    }
}


class Constant{
    static final double P = 3.14;
    Constant single;
    private Constant(){
    };
    public Constant get(){
        if (this == null){
            single = new Constant();
        }
        return single;
    }
}