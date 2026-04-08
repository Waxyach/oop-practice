<div align="center">

# Практика з ООП

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![JavaFX](https://img.shields.io/badge/JavaFX-23-0078D4?style=flat-square&logo=javafx&logoColor=white)](https://openjfx.io/)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-02303A?style=flat-square&logo=gradle&logoColor=white)](https://gradle.org/)
[![JUnit](https://img.shields.io/badge/JUnit-5.12-25A162?style=flat-square&logo=junit5&logoColor=white)](https://junit.org/junit5/)

</div>

---

## Виконавець

**Студент:** Рябуха Максим 

**Група:** 34 

**Керівник:** Анастасія Подунова

---

## Стек

| Інструмент | Для чого |
|---|---|
| **JavaFX** | Графічний інтерфейс (GUI) |
| **Lombok** | Скорочення шаблонного коду |
| **Axion** | Автоматичне версіонування через Git-теги |
| **Spotless** | Форматування коду у стилі Palantir |
| **JUnit 5** | Модульне тестування |
| **TestFX** | Автоматизоване тестування GUI |

---

---

## Структура

```
├── build-logic/     — спільні конвенції збірки
├── task1/           — розрахунок струмів у паралельних опорах
├── task2/           — колекції та Factory Method
├── task3/           — поліморфізм і форматований вивід
├── task4/           — Singleton та Command
├── task5/           — паралельне виконання, Worker Thread
└── task6/           — графічний інтерфейс на JavaFX
```

---

## Завдання

#### [1 — Розрахунок струмів](./task1/)
Обчислення струму через чотири паралельні опори. Серіалізація об'єктів із `transient`-полями.

#### [2 — Спадкування та колекції](./task2/)
Factory Method, ієрархія `View` / `Viewable`, `ArrayList`, лямбда-вирази.

#### [3 — Поліморфізм і таблиці](./task3/)
Перевизначення `viewHeader` / `viewBody` / `viewFooter`, форматований вивід через `Formatter` та `printf`.

#### [4 — Singleton та Command](./task4/)
Команди View, Generate, Save, Restore як окремі об'єкти. Макрокоманда з підтримкою `undo`.

#### [5 — Worker Thread](./task5/)
Черга завдань (`CommandQueue`), фоновий `Worker`, команди `MaxCommand` / `AvgCommand` / `MinMaxCommand`, синхронізація через `wait` / `notify`.

#### [6 — Графічний інтерфейс](./task6/)
JavaFX + CSS, Reflection API з `@Displayable`, `LineChart`, стек `undo`, `LogManager`, UI-тести через TestFX.

---

## Запуск

```bash
git clone https://github.com/Waxyach/opp-practice.git && cd practice

./gradlew spotlessApply   # форматування
./gradlew clean build     # збірка + тести
./gradlew :task6:run      # запуск GUI
```
