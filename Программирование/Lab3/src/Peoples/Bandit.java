package Peoples;

import Enums.Clothes;
import Enums.Locations;
import Enums.Role;
import Enums.Status;
import Other.Car;

import java.util.Objects;
import java.util.Random;

public class Bandit extends People {
    Integer brave;
    public Bandit(String name, Role role, double accuracy, Integer brave, Clothes clothes,
                  Status status){
        super(name, role, accuracy, clothes, status);
        this.brave = brave;
    }

    @Override
    public void shootAt(People target){
        Random random = new Random();
        double check = random.nextDouble();
        if (check < this.getAccuracy()){
            target.changeStatus(Status.DIED);
            brave = Math.max(brave + 10, 100);
        }
        else if (check < 2 * this.getAccuracy()){
            target.takeDamage();
            brave = Math.max(brave + 5, 100);
        }
    }

    @Override
    public void shootAt(Car target) {
        Random random = new Random();
        double check = random.nextDouble();
        if (4 * check < this.getAccuracy()){
            target.changeStatus(Status.DIED);
            brave = 100;
        }
        else if (2 * check < this.getAccuracy()){
            target.takeDamage();
            brave = Math.min(brave + 30, 100);
        }
    }

    public String denyCrime(){
        String[] answers = {"Ты на кого бычишь?",
        "Никакого чемодана я не видел", "Никакого банка не грабил и не думал даже грабить",
        "Я вообще осуждаю, то в чём вы меня обвиняете", "Осуждаю"};
        return answers[new Random().nextInt(answers.length)];
    }

    public String explainMistake(){
        String[] answers = {"Мы не знали, что нас преследуют полицейские, а, наоборот, думали, что за нами гонятся бандиты",
                "Нынешнего полицейского не отличишь от бандита, так как полицейские часто действуют заодно с бандитами",
                "Ну, не разглядели. Поймите и простите."};
        return answers[new Random().nextInt(answers.length)];
    }

    public String admitCrime(){
        String[] answers = {"Да, это были мы", "В главных ролях: Серый, Валерон и Фотик"};
        return answers[new Random().nextInt(answers.length)];
    }

    public String beenOnInterrogation(){
        if (this.getStatus() == Status.DIED){
            return "Чел, ты труп допрашиваешь. Тебе бы выходной взять";
        }
        brave = Math.max(brave - 10, 0);
        if (brave < 30){
            return admitCrime();
        }
        else if (brave < 60){
            return explainMistake();
        }
        return denyCrime();
    }

    @Override
    public void changeStatus(Status status){
        this.setStatus(status);
        if (this.getStatus() == Status.CAPTURED){
            brave = Math.max(0, brave - 40);
        }
        System.out.println("Статус " + this.getName() +" изменен на: " + this.getStatus());
    }

    @Override
    public String toString(){
        return super.toString() + " работает: бандитом";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brave);
    }
}
