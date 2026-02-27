package common.requests;

import common.Enums.Commands;
import common.Model.Location;

public class CountGreaterThanLocationRequest extends Request{
    private final Location location;
    public CountGreaterThanLocationRequest(Location location){
        super(Commands.COUNT_GREATER_THAN_LOCATION);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
