package client.readArguments;

import common.enums.Color;
import common.enums.Country;
import common.Mainpart.Person;
import common.model.Coordinates;
import common.model.Location;

import java.util.HashMap;
import java.util.Map;

public final class ValueFactory {

    private static final Map<Class<?>, Reader> readers = new HashMap<>();

    static {
        readers.put(String.class, new StringRead());
        readers.put(Integer.class, new IdRead());
        readers.put(Person.class, new PersonRead());
        readers.put(Color.class, new ColorRead());
        readers.put(Coordinates.class, new CoordinateRead());
        readers.put(Country.class, new CountryRead());
        readers.put(Location.class, new LocationRead());
    }

    private ValueFactory(){}

    public static Reader getReader(Class<?> type){
        return readers.get(type);
    }
}
