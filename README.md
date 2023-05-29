# Данное приложение проводит тестирование сайта 'https://dodopizza.ru/'.
Сценарий тестирования описан в файле "Task.txt", в корне репозитория.

Работа с тестами проводилась в среде разработки: IntelliJ IDEA. 

Код тестов написан на Java.

Результаты тестирования отображены в файле "BagReport.xls"


# 1. Очищение от предыдущих тестов:
а) В ручную, удаление всех файлов из директории '/allure-results/' .
б) В командной строке. Удаление директории сборки:
```
mvn clean
```


# 2. Запуск тестов:
В командной строке, все тесты сразу:
```
mvn test
```
Или 

Вручную, по отдельности. Запустив в коде выбранные классы или методы. Нажать на зеленый треугольник слева названия класса или метода.


# 3. Формирование Allure отчета:
а) Сгенерировать отчет, а предыдущие удалить
```
allure generate --clean
```
или
```
mvn allure:serve
```


# 4. Открыть отчет:
Вручную. Открыть файл по адресу '/allure-report/index.html'. В правом верхнем углу экрана нажать на значок браузера. Откроется отчет в браузере.
Или

В командной строке:
```
allure open
```
Откроется отчет в браузере.
Что бы остановить, в командной строке нажимаем <Ctrl+C>

