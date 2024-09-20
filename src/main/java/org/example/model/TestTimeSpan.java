package org.example.model;

import org.example.TimeSpan;

public class TestTimeSpan {
    public static void main(String[] args) {
        try {
            // Створюємо об'єкт класу TimeSpan
            TimeSpan timeSpan1 = new TimeSpan(2, 30);
            System.out.println("TimeSpan1: " + timeSpan1.getTotalHours() + " hours");

            // Використовуємо конструктори та методи
            TimeSpan timeSpan2 = new TimeSpan();  // 0 годин, 0 хвилин
            System.out.println("TimeSpan2: " + timeSpan2.getTotalHours() + " hours");

            TimeSpan timeSpan3 = new TimeSpan(40);
            System.out.println("TimeSpan3: " + timeSpan3.getTotalHours() + " hours");

            timeSpan1.add(1, 65);  // Помилка: некоректні години та хвилини
            System.out.println("TimeSpan1 after addition: " + timeSpan1.getTotalHours() + " hours");

            timeSpan2.add(-45);  // Помилка: некоректні хвилини
            System.out.println("TimeSpan2 after addition: " + timeSpan2.getTotalHours() + " hours");

            timeSpan3.subtract(timeSpan1);  // Помилка: неможливо відняти більший TimeSpan
            System.out.println("TimeSpan3 after subtraction: " + timeSpan3.getTotalHours() + " hours");

            timeSpan1.scale(0);  // Помилка: множник повинен бути більший за нуль
            System.out.println("TimeSpan1 after scaling: " + timeSpan1.getTotalHours() + " hours");
        } catch (NegativeTimeSpanException | IllegalInputTimeSpanException | IllegalScaleFactorException e) {
            // Обробка виняткових ситуацій
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}


