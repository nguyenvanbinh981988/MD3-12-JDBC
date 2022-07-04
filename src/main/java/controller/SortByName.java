package controller;

import model.User;

import java.util.Comparator;

public class SortByName implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if (o1.getName().compareTo(o2.getName())>0) {
            return 1;
        } else {
            return -1;
        }
    }
}