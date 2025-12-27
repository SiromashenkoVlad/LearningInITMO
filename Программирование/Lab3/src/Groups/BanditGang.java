package Groups;

import Enums.Locations;
import Other.Car;
import Peoples.Bandit;
import Peoples.People;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BanditGang extends Crowd{
    public BanditGang(String name, List<Bandit> members,
                      List<Car<? extends People>> vehicles, Locations location){
        super(name, members, vehicles, location);
    }

    public boolean beenСhasing(PoliceConvoy target){
        Random random = new Random();
        double check = random.nextDouble();
        if (check < 0.8){
        //if (check > 0){
            System.out.println("Бандиты заметили слежку");
            return true;
        }
        else {
            System.out.println("Бандиты не замечают слежку");
            return false;
        }
    }

    @Override
    public ArrayList<Bandit> getListActiveMembers(){
        ArrayList<Bandit> activeMembers = super.getListActiveMembers();
        return activeMembers;
    }

    @Override
    public ArrayList<Bandit> getListAliveMembers(){
        ArrayList<Bandit> aliveMembers = super.getListAliveMembers();
        return aliveMembers;
    }

    @Override
    public ArrayList<Bandit> getMembers() {
        return (ArrayList<Bandit>) super.getMembers();
    }

    @Override
    public String toString(){
        return "Отряд бандитов. " + super.toString();
    }
}
