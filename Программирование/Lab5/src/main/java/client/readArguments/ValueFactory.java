package client.readArguments;

import common.Enums.Color;
import common.Enums.Country;
import common.Mainpart.Person;
import common.Model.Coordinates;
import common.Model.Location;

import java.util.HashMap;
import java.util.Map;

public class ValueFactory {

    private final Map<Class<?>, Reader> readers = new HashMap<>();

    public ValueFactory(){
        readers.put(String.class, new StringReader());
        readers.put(Integer.class, new IdReader());
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
