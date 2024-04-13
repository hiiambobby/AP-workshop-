package src;

import java.util.ArrayList;
import java.util.HashMap;

public class Hospital {
    HashMap<String, ArrayList<Reserve>> reservations = new HashMap<>();
    HashMap<String, Person> persons = new HashMap<>();
}