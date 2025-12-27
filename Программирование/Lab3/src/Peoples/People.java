package Peoples;

import Enums.Clothes;
import Enums.Role;
import Enums.Status;
import Interfaces.Shootable;
import Interfaces.Shooter;
import Other.ArmoredCar;
import Other.Car;
import Other.Suitcase;

import java.util.Objects;
import java.util.Random;

public class People implements Shootable, Shooter {
    private String name;
    private Role role;
    private double accuracy;
    private Clothes clothes;
    private Suitcase heldCase = new Suitcase(-1);
    private Status status;

    public People(String name, Role role, double accuracy, Clothes clothes,
                  Status status){
        this.name = name;
        this.role = role;
        this.accuracy = accuracy;
        this.clothes = clothes;
        this.status = status;
    }

    public boolean hasSuitcase(Suitcase suitcase){
        if (this.heldCase == suitcase){return true;}
        else {return false;}
    }

    public void takeSuitase(Suitcase suitcase){
        this.heldCase = suitcase;
    }

    public void dropSuitcase(){
        this.heldCase = new Suitcase(-1);
    }


    @Override
    public void changeStatus(Status status){
        this.status = status;
        //System.out.println("Статус " + this.name +" изменен на: " + this.status);
    }

    @Override
    public void takeDamage(){
        this.accuracy /= 2.0;
    }

    @Override
    public void shootAt(People target){
        Random random = new Random();
        double check = random.nextDouble();
        if (3 * check < this.getAccuracy()){
            target.changeStatus(Status.DIED);
        }
        else if (check < 2 * this.getAccuracy()){
            target.takeDamage();
        }
    }

    @Override
    public void shootAt(Car target) {
        Random random = new Random();
        double check = random.nextDouble();
        if (4 * check < this.getAccuracy()){
            target.changeStatus(Status.DIED);
            System.out.println(this.name + " вывел машину из строя");
        }
        else if (2 * check < this.getAccuracy()){
            target.takeDamage();
        }
    }

    @Override
    public void shootAt(ArmoredCar target) {
        System.out.println(name + " принимает жалкие попытки в одиночку уничтожить бронированный автомобиль.");
    }

    public String getName(){
        return this.name;
    }

    public Role getRole(){
        return this.role;
    }

    public double getAccuracy() {
        return this.accuracy;
    }

    public Clothes getClothes() {
        return this.clothes;
    }

    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Suitcase getHeldCase() {
        return heldCase;
    }

    @Override
    public String toString(){
        return "Человек по имени: " + this.getName() + " со статусом: " + this.getStatus();
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
        return Objects.hash(name, role, accuracy, clothes, heldCase, status);
    }
}
