package Other;

import Enums.Status;
import Exceptions.OverflowMachine;
import Interfaces.Shootable;
import Peoples.People;
import Peoples.Policeman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Car <T extends People> implements Shootable {
    private Integer maxCountPassengers;
    private List<T> passengers = new ArrayList<>();
    private Status status;
    public Car(Integer cntPassengers, List<? extends People> lstPassengers){
        maxCountPassengers = cntPassengers;

        if (maxCountPassengers < lstPassengers.size()){
            throw new OverflowMachine("Недостаточно мест в машине. Доступно: ", maxCountPassengers);
        }
        for (Integer id = 0; id < lstPassengers.size(); ++id){
            passengers.add((T) lstPassengers.get(id));
        }
        status = Status.ALIVE;
    }

    public List<T> getPassengers(){
        return passengers;
    }

    public void addPassenger(T passenger){
        if (this.passengers.size() == maxCountPassengers){
            throw new OverflowMachine("Недостаточно мест в машине. Доступно: ", maxCountPassengers);
        }
        else {
            passengers.add(passenger);
        }
    }

    public void addPassengers(ArrayList<T> passengers){
        if (this.passengers.size() + passengers.size() > maxCountPassengers){
            throw new OverflowMachine("Недостаточно мест в машине. Доступно: ", maxCountPassengers);
        }
        else{
            for (T man : passengers){
                this.passengers.add(man);
            }
        }
    }

    public void removePassenger(People passenger){
        passengers.remove(passenger);
    }

    public Integer getMaxCountPassengers() {
        return maxCountPassengers;
    }

    public Status getStatus() {
        return status;
    }

    public void clear(){
        passengers.clear();
    }

    @Override
    public void changeStatus(Status status) {
        this.status = status;
        if (status != Status.ALIVE){
            for (People people : passengers){
                if (people.getStatus() != Status.DIED){
                    people.changeStatus(Status.CAPTURED);
                }
            }
        }
    }

    @Override
    public void takeDamage(){
        Integer random_person = new Random().nextInt(this.passengers.size());
        passengers.get(random_person).changeStatus(Status.DIED);
        boolean haveAlivePassengers = true;
        for (People people : passengers){
            if (people.getStatus() == Status.DIED){
                haveAlivePassengers = false;
                break;
            }
        }
        if (!haveAlivePassengers){
            this.changeStatus(Status.CAPTURED);
        }
    }
}
