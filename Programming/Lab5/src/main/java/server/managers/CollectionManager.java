package server.managers;

import common.mainpart.Person;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class CollectionManager {
    private List<Person> collection = new Stack<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private String filename;

    public CollectionManager(String filename){
        this.filename = filename;
        collection = DumpManager.read(filename);
        lastInitTime = LocalDateTime.now();
        lastSaveTime = LocalDateTime.now();
    }

    public CollectionManager(){
        lastInitTime = LocalDateTime.now();
        lastSaveTime = LocalDateTime.now();
    }

    public List<Person> getCollection() {
        return collection;
    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public String getFilename() {
        return filename;
    }

    public int size(){
        return collection.size();
    }

    public void add(Person p){
        collection.add(p);
    }

    public void updateById(int id, Person p){
        for (Person person : collection) {
            if (person.getId() == id){
                person.update(p);
                return;
            }
        }
    }

    public void removeById(int id){
        for (Person person : collection) {
            if (person.getId() == id){
                collection.remove(person);
                return;
            }
        }
    }

    public void clear(){
        collection.clear();
    }

    public void save(){
        DumpManager.write(filename, collection);
    }

    public Person getMax(){
        if (collection.isEmpty()){
            return null;
        }
        Person mxPerson = collection.get(0);
        for (Person person : collection) {
            if (person.getId() > mxPerson.getId()){
                mxPerson = person;
            }
        }
        return mxPerson;
    }

    public void shuffle(){
        Collections.shuffle(collection);
    }

    public void reorder(){
        Collections.reverse(collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection, lastInitTime, lastSaveTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || o.getClass() != this.getClass()) { return false; }
        CollectionManager manager = (CollectionManager) o;
        return Objects.equals(collection, manager.collection)
                && Objects.equals(lastInitTime, manager.lastInitTime)
                && Objects.equals(lastSaveTime, manager.lastSaveTime);
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (Person p : collection){
            answer.append(p.toString()).append('\n');
        }
        return answer.toString();
    }
}
