package org.example;

import java.util.Objects;

public class Utils {

public boolean choose(String choice) {
    if (Objects.equals(choice, "1")|| Objects.equals(choice, "2")){
        return true;
    }else return false;

}
public boolean checksize(int choice) {
    if (choice >= 8 &&  choice <= 26) {
        return true;
    }else return false;

}
}