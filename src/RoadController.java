import core.*;
//import core.Camera;

public class RoadController
{
    public static double passengerCarMaxWeight = 3500.0; // kg      Double passengerCarMaxWeight
    public static int passengerCarMaxHeight = 2000; // mm       Integer passengerCarMaxHeight
    public static int controllerMaxHeight = 3500; // mm         Integer controllerMaxHeight

    public static int passengerCarPrice = 100; // RUB           Integer passengerCarPrice
    public static int cargoCarPrice = 250; // RUB               Integer cargoCarPrice
    public static int vehicleAdditionalPrice = 200; // RUB      Integer vehicleAdditionalPrice

    public static int maxOncomingSpeed = 60; // km/h            Integer maxOncomingSpeed
    public static int speedFineGrade = 20; // km/h              Integer speedFineGrade
    public static int finePerGrade = 500; // RUB                Integer finePerGrade
    public static int criminalSpeed = 160; // km/h              Integer criminalSpeed

    public static void main(String[] args)
    {
        for(int i = 0; i < 10; i++)                             //Integer i счётчик цикла
        {
            Car car = Camera.getNextCar();
            System.out.println(car);
            System.out.println("Скорость: " + Camera.getCarSpeed(car) + " км/ч");



            /*
             * Проверка на наличие номера в списке номеров нарушителей
             */
            boolean policeCalled = false;                               //Boolean policeCalled
            for(String criminalNumber : Police.getCriminalNumbers())    //String criminalNumber
            {
                String carNumber = car.getNumber();                     //String carNumber
                if(carNumber.equals(criminalNumber)) {
                    Police.call("автомобиль нарушителя с номером " + carNumber);
                    blockWay("не двигайтесь с места! За вами уже выехали!");
                    break;
                }
            }

            /*
             * Пропускаем автомобили спецтранспорта
             **/
            if(car.isSpecial() && !Police.wasCalled()) {
                openWay();
                continue;
            }

            if(Police.wasCalled()) {
                continue;
            }

            /*  
             * Проверяем высоту и массу автомобиля, вычисляем стоимость проезда/
             */
            int carHeight = car.getHeight();                        //Integer carHeight
            int price = 0;                                          //Integer price
            if(carHeight > controllerMaxHeight) {
                blockWay("высота вашего ТС превышает высоту пропускного пункта!");
                continue;
            }
            else if(carHeight > passengerCarMaxHeight) {
                double weight = WeightMeter.getWeight(car);             //Double weight
                //Грузовой автомобиль
                if (weight > passengerCarMaxWeight) {
                    price = cargoCarPrice;
                    if (car.hasVehicle()) {
                        price = price + vehicleAdditionalPrice;
                    }
                }
                //Легковой автомобиль, но высота > passengerCarMaxHeight, а вес <= passengerCarMaxWeight
                else {
                    price = passengerCarPrice;
                }
            }
            //Легковой автомобиль
            else{
                price = passengerCarPrice;
            }

            /*
             * Проверка скорости подъезда и выставление штрафа
             */
            int carSpeed = Camera.getCarSpeed(car);               //Integer carSpeed
            if(carSpeed > criminalSpeed)
            {
                Police.call("cкорость автомобиля - " + carSpeed + " км/ч, номер - " + car.getNumber());
                blockWay("вы значительно превысили скорость. Ожидайте полицию!");
                continue;
            }
            else if(carSpeed > maxOncomingSpeed)
            {
                int overSpeed = carSpeed - maxOncomingSpeed;                            //Integer overSpeed
                int totalFine = finePerGrade * (1 + overSpeed / speedFineGrade);        //Integer totalFine
                System.out.println("Вы превысили скорость! Штраф: " + totalFine + " руб.");
                price = price + totalFine;
            }

            /*
             * Отображение суммы к оплате
             */
            System.out.println("Общая сумма к оплате: " + price + " руб.");
        }

    }

    /**
     * Открытие шлагбаума
     */
    public static void openWay()
    {
        System.out.println("Шлагбаум открывается... Счастливого пути!");
    }

    public static void blockWay(String reason)
    {
        System.out.println("Проезд невозможен: " + reason);
    }
}