package Interfaces;

import Enums.Status;

public interface Shootable {
    void takeDamage();

    void changeStatus(Status status);
}
