package model;

public interface ICalculator {
    default void prepareToBinaryOperation() {

    }

    void readkey(String key);
}
