package Groups;

import Enums.Locations;
import Enums.Status;
import Other.Car;
import Other.Suitcase;
import Peoples.People;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Crowd <T extends People> extends Group{
    private final String name;
    private List<T> members = new ArrayList<>();
    private List<Car<? extends People>> vehicles = new ArrayList<>();
    private Locations location;
    public Crowd(String name, List<T> members, List<Car<? extends People>> vehicles, Locations location){
        this.name = name;
        this.members = members;
        this.vehicles = vehicles;
        this.location = location;
    }

    public void attack(Crowd<? extends People> target) {
        if (!this.getListActiveMembers().isEmpty()) {
            Random random = new Random();
            double check = random.nextDouble();
            People man = pickActiveMember();
            if (check < 0.5) {
                man.shootAt(target.pickActiveMember());
            } else {
                man.shootAt(target.pickRandomVehicle());
            }
        }
    }


    public void addMembers(ArrayList<T> people){
        members.addAll(people);
    }

    @Override
    public Car<? extends People> pickRandomVehicle(){
        Random random = new Random();
        Car<? extends People> randomVehicle = vehicles.get(random.nextInt(vehicles.size()));
        return randomVehicle;
    }

    @Override
    public People pickRandomMember(){
        Random random = new Random();
        People random_member = members.get(random.nextInt(members.size()));
        return random_member;
    }

    @Override
    public ArrayList<People> getListActiveMembers(){
        ArrayList<People> activeMembers = new ArrayList<>();
        for (People man : members){
            if (man.getStatus() == Status.ALIVE){
                activeMembers.add(man);
            }
        }
        //System.out.println(activeMembers);
        return activeMembers;
    }

    public ArrayList<People> getListAliveMembers(){
        ArrayList<People> activeMembers = new ArrayList<>();
        for (People man : members){
            if (man.getStatus() != Status.DIED){
                activeMembers.add(man);
            }
        }
        //System.out.println(activeMembers);
        return activeMembers;
    }

    @Override
    public People pickActiveMember(){
        ArrayList<People> activeMembers = getListActiveMembers();
        Random random = new Random();
        People activeMan = activeMembers.get(random.nextInt(activeMembers.size()));
        return activeMan;
    }

    public boolean isCarryingSuitcase(Suitcase suitcase){
        boolean isCarrying = false;
        for (People man : members){
            if (man.hasSuitcase(suitcase)){
                isCarrying = true;
                break;
            }
        }
        return isCarrying;
    }

    public Integer getCntMembers(){
        return this.members.size();
    }

    public List<T> getMembers() {
        return members;
    }

    public Integer getCntVehicles(){
        return this.vehicles.size();
    }

    public boolean hasActiveMembers(){
        boolean hasActiveMembers = false;
        for (T man : members){
            if (man.getStatus() == Status.ALIVE){
                hasActiveMembers = true;
                break;
            }
        }
        return hasActiveMembers;
    }

    public boolean hasAliveMembers(){
        boolean hasAliveMembers = false;
        for (People man : members){
            if (man.getStatus() != Status.DIED){
                hasAliveMembers = true;
                break;
            }
        }
        return hasAliveMembers;
    }

    public String getName() {
        return name;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    @Override
    public String toString(){
        String result = "Группа с названием " + this.getName() + ", члены: ";
        for (T man : this.getMembers()){
            result += man.getName() + ", ";
        }
        return result;
    }


    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) return false;

        People other = (People) o;
        return other.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, members, vehicles, location);
    }
}
