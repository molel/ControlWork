package com.example.controlwork;

public class StringStorage {
    final static String LINK = "http://abashin.ru/cgi-bin/ru/tests/burnout";
    final static String METHOD = "POST";
    final static String HOST_KEY = "Host";
    final static String HOST_VALUE = "abashin.ru";
    final static String CONNECTION_KEY = "Connection";
    final static String CONNECTION_VALUE = "close";
    final static String CACHE_KEY = "Cache-Control";
    final static String CACHE_VALUE = "max-age=0";
    final static String DNT_KEY = "DNT";
    final static String DNT_VALUE = "1";
    final static String UPGRADE_KEY = "Upgrade-Insecure-Requests";
    final static String UPGRADE_VALUE = "1";
    final static String ACCEPT_KEY = "Accept";
    final static String ACCEPT_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
    final static String ENCODING_KEY = "Accept-Encoding";
    final static String ENCODING_VALUE = "deflate";
    final static String LANGUAGE_KEY = "Accept-Language";
    final static String LANGUAGE_VALUE = "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7";
    final static String TYPE_KEY = "Content-Type";
    final static String TYPE_VALUE = "application/x-www-form-urlencoded";
    final static String LENGTH_KEY = "Content-Type";
    final static boolean INPUT = true;
    final static boolean OUTPUT = true;

    final static String INCORRECT_INPUT = "Неверный ввод";
    final static String RESPONSE_PATTERN = "<.*?>";
    final static String INTENT_KEY = "result";

    final static String GOOD_RESULT = "Введенные значения соответствуют отсутствию переутомления.";
    final static String MEDIUM_RESULT = "Введенные значения соответствуют небольшому переутомлению. Рекомендуется снижение нагрузки.";
    final static String BAD_RESULT = "Введенные значения соответствуют высокому уровню переутомления. Рекомендуется снижение нагрузки или отпуск.";

}