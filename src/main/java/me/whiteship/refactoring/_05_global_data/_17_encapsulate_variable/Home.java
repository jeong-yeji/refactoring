package me.whiteship.refactoring._05_global_data._17_encapsulate_variable;

public class Home {

    public static void main(String[] args) {
        System.out.println(Thermostats.getTargetTemperature());
        Thermostats.setTargetTemperature(60);
        Thermostats.setReadInFahrenheit(false);
    }
}
