package Groups;

import Enums.Clothes;
import Enums.Locations;
import Enums.Role;
import Enums.Status;
import Exceptions.GetAwayFromPersecution;
import Other.ArmoredCar;
import Other.Car;
import Other.Suitcase;
import Peoples.People;
import Peoples.Policeman;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PoliceConvoy  extends Crowd{
    List<ArmoredCar<? extends People>> armoredCars;
    public PoliceConvoy(String name, List<? extends People> members, List<Car<? extends People>> vehicles,
                        List<ArmoredCar<? extends People>> armoredCars, Locations location){
        super(name, members, vehicles, location);
        this.armoredCars = armoredCars;
    }

    public ArmoredCar<? extends People> pickRandomArmoredCar(){
        Random random = new Random();
        ArmoredCar<? extends People> randomArmoredCar = armoredCars.get(random.nextInt(this.getCntVehicles()));
        return randomArmoredCar;
    }

    public boolean chasing(BanditGang target) throws GetAwayFromPersecution {
        System.out.println("Группа " + this.getName() + " ведёт преследование подозреваемых: " + target.getName());

        boolean isfight = target.beenСhasing(this);

        if (isfight && target.getCntMembers() > this.getCntMembers()){
            throw new GetAwayFromPersecution(target.getName() + " ушли от преследования");
        }

        else if (isfight){
            System.out.println("Группа " + target.getName() + " атакует группу " + this.getName() + ":");
            while (this.hasActiveMembers() && target.hasActiveMembers()){
                if (this.hasActiveMembers()){
                    target.attack(this);
                }
                if (target.hasActiveMembers()) {
                    this.attack(target);
                }
                if (this.getListActiveMembers().size() < 3){
                    callForBackUp();
                }
            }
            if (this.hasActiveMembers()){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            System.out.println("Полиция продолжила преследование, но не нашала ничего подозрительного");
            return false;
        }
    }

    public void callForBackUp(){
        Policeman f = new Policeman("Ф.", Role.POLICEMAN, 1.0, Clothes.POLICE, Status.ALIVE);
        Policeman c = new Policeman("С.", Role.POLICEMAN, 1.0, Clothes.POLICE, Status.ALIVE);
        ArrayList<Policeman> passengers = new ArrayList<Policeman>();
        passengers.add(f);
        passengers.add(c);
        ArmoredCar car = new ArmoredCar(4, passengers);
        System.out.println("Полиция вызвала подкрепление");

        armoredCars.add(car);
        this.addMembers(passengers);

    }

    @Override
    public Policeman pickActiveMember(){
        return (Policeman) super.pickActiveMember();
    }

    public boolean checkForSuitcse(BanditGang target, Suitcase suitcase){
        Policeman search_policeman = pickActiveMember();
        System.out.println("Обыск проводит " + search_policeman.getName());
        boolean hasCase = false;
        for (People man : target.getMembers()){
            if (search_policeman.performASearch(man, suitcase)){
                hasCase = true;
                break;
            }
        }
        return hasCase;
    }

    @Override
    public String toString(){
        return "Отряд полицейских. " + super.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), armoredCars);
    }
}
