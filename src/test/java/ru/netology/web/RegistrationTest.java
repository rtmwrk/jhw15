package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {
    // Метод будем использовать для динамическог оформирования даты -
    // добавляем к текущей дате заданное число дней и форматирует вывод
    String setupDate (int dayAdd, String pattern) {
        return LocalDate.now().plusDays(dayAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    // Проведем функциональное позитивное тестирование работы сервиса заказа дебетовой карты
    @Test
    void shouldRegisterDebetCard() {
        // Подготовим данные для работы с элементами формы заказа дебетовой карты
        // Формируем значения поля "Город"
        String cityRegistration = "Краснодар";
        // Формируем значение поля "Дата" - по условию дата должна быть не раньше чем текущая + 3 дня
        String dateVisit = setupDate(1, "dd.MM.yyyy");
        // Формируем значение поля "Фамилия и Имя клиента"
        String nameUser = "Иванов Петр";
        // Формируем значение поля "Телефон клиента"
        String phoneUser = "+79181234567";
        // Формируем имена кнопок и текстов сообщений для последующего анализа
        String submitButtonName = "Забронировать";
        String successWindow = "Успешно";

        // Вызываем форму доставки карты
        open("http://localhost:9999");
        // Находим на странице элемент с классом "form"
        SelenideElement form = $(".form");
        // Находим элемент "Город", устанавливаем значение для дочернего input
        form.$("[data-test-id=city] input").setValue(cityRegistration);
        // Находим элемент "Дата", устанавливаем значение для дочернего input
        // Очищаем вручную значение поля "по умолчанию"
        form.$("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        // Устанавливаем нужное значение даты
        form.$("[data-test-id=date] input").setValue(dateVisit);
        // Находим элемент "Имя клиента", устанавливаем значение для дочернего input
        form.$("[data-test-id=name] input").setValue(nameUser);
        // Находим элемент "Телефон клиента", устанавливаем значение для дочернего input
        form.$("[data-test-id=phone] input").setValue(phoneUser);
        // Нажимаем чекбокс "Я соглашаюсь"
        form.$("[data-test-id=agreement]").click();
        // Нажимаем кнопку "Забронировать"
        $$("button").find(exactText(submitButtonName)).click();
        // Ожидаем появление элемента с текстом "Успешно", что подтверждает правильность регистрации
        $(withText(successWindow)).shouldBe(visible, Duration.ofMillis(11000));
    }
}

