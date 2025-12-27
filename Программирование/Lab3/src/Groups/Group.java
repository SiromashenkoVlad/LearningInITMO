package Groups;

import Enums.Status;
import Other.Car;
import Peoples.People;

import java.util.ArrayList;
import java.util.Random;

abstract class Group <T extends People>{
    private ArrayList<T> members = new ArrayList<>();
    private ArrayList<Car> vehicles = new ArrayList<>();

    abstract boolean hasActiveMembers();

//    protected void addMembers(ArrayList<T> people){
//        for (People man : people){
//            members.add((T) man);
//        }
//    }

    protected Car pickRandomVehicle(){
        Random random = new Random();
        Car randomVehicle = vehicles.get(random.nextInt(vehicles.size()));
        return randomVehicle;
    }

    protected People pickRandomMember(){
        Random random = new Random();
        People random_member = members.get(random.nextInt(members.size()));
        return random_member;
    }

    protected ArrayList<People> getListActiveMembers(){
        ArrayList<People> activeMembers = new ArrayList<>();
        for (People man : members){
            if (man.getStatus() == Status.ALIVE){
                activeMembers.add(man);
            }
        }
        return activeMembers;
    }

    protected People pickActiveMember(){
        ArrayList<People> activeMembers = getListActiveMembers();
        Random random = new Random();
        System.out.println(activeMembers.size());
        People activeMan = activeMembers.get(random.nextInt(activeMembers.size()));
        return activeMan;
    }
}
