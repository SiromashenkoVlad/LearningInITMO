package common.requests;

import common.Enums.Commands;
import common.Model.Location;

public class CountByLocationRequest extends Request {
    private final Location location;
    public CountByLocationRequest(Location location){
        super(Commands.COUNT_BY_LOCATION);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
