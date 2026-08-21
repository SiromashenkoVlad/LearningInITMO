package server.managers;

import common.Mainpart.Person;
import server.db.DumpManager;
import server.db.dao.PersonDao;
import server.db.dao.UserDao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class CollectionManager {
    private List<Person> collection = new Stack<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;

    public CollectionManager() throws SQLException {
        collection = DumpManager.read();
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

    public void add(Person p) throws SQLException {
        PersonDao.getInstance().save(p);
        collection = DumpManager.read();

    }

    public void updateById(int id, Person p) throws SQLException{
        PersonDao.getInstance().update(id, p);
        collection = DumpManager.read();
    }

    public void removeById(int id) throws SQLException {
        System.out.println("collection manager");
        PersonDao.getInstance().remove(id);
        collection = DumpManager.read();
    }

    public void clear(String maker) throws SQLException{
        PersonDao personDao = PersonDao.getInstance();
        personDao.clear(maker);
        collection = DumpManager.read();
    }

    public void clear() throws SQLException{
        PersonDao personDao = PersonDao.getInstance();
        personDao.clear();
        collection = DumpManager.read();
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
        List<Person> tmp = new Stack<>();
        tmp.addAll(collection);
        Collections.sort(tmp);
        StringBuilder answer = new StringBuilder();

        for (Person p : tmp){
            answer.append(p.toString()).append('\n');
        }
        return answer.toString();
    }
}
