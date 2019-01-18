package core;
import java.text.DecimalFormat;

 public class Car {
    private String number;
    private Integer height;
    private Double weight;
    private Boolean hasVehicle;
    private Boolean isSpecial;
    DecimalFormat numberFormat = new DecimalFormat("#.000");    //Добавлен формат вывода массы с 3 знаками после запятой

    public Car(String carNumber, Integer carHeight, Double carWeight, Boolean carHasVehicle) {
        number = carNumber;
        height = carHeight;
        weight = carWeight;
        hasVehicle = carHasVehicle;
        isSpecial = false;
    }

    //=========================================================================

    public void setIsSpecial()
    {
        isSpecial = true;
    }

    //=========================================================================

    public String getNumber()
    {
        return number;
    }

    public Integer getHeight()
    {
        return height;
    }

    public Boolean isSpecial()
    {
        return isSpecial;
    }

    public Boolean hasVehicle()
    {
        return hasVehicle;
    }

    //=========================================================================

    Double getWeight()
    {
        return weight;
    }

    //=========================================================================

    public String toString()
    {
        String special = isSpecial ? "СПЕЦТРАНСПОРТ " : "";
        return "\n=========================================\n" +
            special + "Автомобиль с номером " + number +
            ":\n\tВысота: " + height + " мм\n\tМасса: " + numberFormat.format(weight) + " кг";
    }
}