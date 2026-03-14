package client.readArguments;

import common.enums.Color;
import common.enums.Country;
import common.mainpart.Person;
import common.model.Coordinates;
import common.model.Location;

import java.util.HashMap;
import java.util.Map;

public class ValueFactory {

    private final Map<Class<?>, Reader> readers = new HashMap<>();

    public ValueFactory(){
        readers.put(String.class, new StringRead());
        readers.put(Integer.class, new IdRead());
        readers.put(Person.class, new PersonRead());
        readers.put(Color.class, new ColorRead());
        readers.put(Coordinates.class, new CoordinateRead());
        readers.put(Country.class, new CountryRead());
        readers.put(Location.class, new LocationRead());
    }

    public Reader getReader(Class<?> type){
        return readers.get(type);
    }
}
