package lenart.piotr.plugins.towerdefencelib.exceptions;

public class TowerDefenceException extends Exception {
    String message;
    public String getMessage() {
        return message;
    }
    public TowerDefenceException(String message) {
        this.message = message;
    }
}
