/*
https://anekdotbar.ru/pro-programmistov/
https://www.respectme.ru/anecdote/it
https://www.anekdot.ru/tags/%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%81%D1%82
 */
package com.interfaces;

import com.game.Util;

//шутник
public interface Jokable {
    String LABEL_JOKE = ":)";

    String[] JOKE_STORIES = {
            "Сколько нужно программистов, что бы вкрутить лампочку? Нисколько, это аппаратная задача. ",
            "Опытный разработчик всегда посмотрит направо и налево, даже если переходит улицу с односторонним движением.",
            "А вы знаете основные достоинства программистов? Конечно же, это высокомерие, нетерпимость и невероятная лень.",
            "Главная проблема при работе со штатом программистов: никогда не поймешь, чем заняты сотрудники, \nпока не окажется, что уже наступил дедлайн.",
            "Программа получилась плохой, а сроки горят, и заказчик ругается? Не волнуйтесь, смело выпускайте релиз. Просто назовите его версией 1.0.",
            "Если вы посмотрите на код, который вы писали более полугода назад, то, скорей всего, вам покажется, что автор – кто-то другой.",
            "Написание комментариев в коде чем-то похоже на мытье унитаза – крайне неприятно, совсем не хочется этого делать, \nно необходимо, чтобы не опозориться перед гостями.",
            "Если бы строители работали так же, как программисты кодят, то любая птица, присевшая отдохнуть на крыше дома, \nмогла бы стать причиной гибели цивилизации.",
            "Если когда-нибудь будет создан язык программирования, благодаря которому можно будет кодить на разговорном английском, \nокажется, что большинство программистов не знают языка.",
            "Если в очередном релизе Java будет реализована функция уборки программного мусора, \nбольшая часть Java-приложений будут удалять себя сразу после установки.",
            "Что вы знаете о трудном детстве? Какие там скользкие подоконники? Вот килобайтные игрушки – это и правда страшно.",
            "Никогда не пишите на пределе своих талантов! Помните: отладка – всегда в два раза сложнее написания кода. \nИ если вы напишете настолько умно, насколько способны, отладить его вы уже не сумеете.",
            "Что общего между программным кодом и церковью? Сначала мы их строим, потом начинаем на них молиться.",
            "Если в 9-00 вы видите программиста на рабочем месте, значит, он здесь ночевал.",
            "Скажите, JAVA-программист – это ориентация или все же диагноз?",
            "Задумайтесь, какое количество психических сил затрачено программистами на попытки понять \nфундаментальное различие между программой и алгоритмом!",
            "Если вы хотите получать пользу от программирования. никогда не программируйте!",
            "Основная цель любой разработки на Java– построить что-то такое, что простоит хотя бы до момента завершения стройки.",
            "Финальный релиз программного обеспечения не выйдет до тех пор, пока жив хотя бы один пользователь.",
            "Существует всего 2 способа писать код без багов. Но работает почему-то третий.",
            "Плохой код – совсем не плохой. Его просто не сумели правильно понять.",
            "Если никто не знает ответ на вопрос, пора начать читать документацию.",
            "Об операторе GoTo: даже в высшем обществе бывают ситуации, когда без фразы типа «иди на» обойтись невозможно.",
            "Не понимаю, почему индекс массива начинается с «0», а не с «1». \nСчитаю, что мое компромиссное решение – «0,5» — было отвергнуто без надлежащего изучения.",
            "Самое главное отличие C от C++: на Си вы можете делать ошибки, а в C++ — еще и наследовать их.",
            "Когда вы видите, что компьютер пишет: \"Вы не робот?\", подумайте - вдруг он просто хочет создать семью?",
            "Если программа работает не так, как написано в инструкции к ней - то ошибка может быть и там, и там.",
            "Согласно народной примете, в новый дом первым нужно впускать интернет-кабель. И где он ляжет - там ставьте \nкровать. И стол. И комп. И еду.",
            "На русификацию новой версии Windоws было потрачено 50 литров водки.",
            "Каждый раз когда я использую смайлик, я чувствую, как опускаюсь на ступеньку в развитии.",
            "Очень плохая идея была сказать соседу, что у него в шкафу интернет плохо ловит. Очень плохая.",
            "У америкосов есть софт обычный и пиратский, а у нас - обычный и лицензионный.",
            "Программисты много зарабатывают, это правда.\nМожно заработать остеохондроз, туннельный синдром, нервный срыв и всё это к 25!",
            "- Доктор, я себя чувствую, как C++.\n- Это как?\n- Меня никто не понимает, все боятся, и говорят, что я больше не нужен...",
            "Зачем к устройству с \"интуитивно-понятным интерфейсом\" инструкция на 100+ страниц?",
            "Жизнь слишком коротка, чтобы тратить её на безопасное извлечение флешки.",
            "Жил-был программист, и было у него два сына - Антон и Неантон.",
            "Самое главное при работе с компьютером - не дать ему понять, что ты торопишься!",
            "Самый трудолюбивый сотрудник в офисе: пашню засеял, город построил, воинов купил…",
            "- Девушка, да я таких, как Вы, гигами из Инета качаю!",
            "А помните голодные годы? Интернет по карточкам...",
            "Коль услышишь винта шелест - то Винда пошла на нерест.",
            "Два программиста:\n01: Ещё одно неловкое движение с твоей стороны... \n10: И???\n01: И... счётчик твоих неловких движений увеличится на единицу.",
            "Разговаривают два программиста:\n- А у меня духовка не включается...\n- Что пишет?",
            "Можно ли программу, написанную под Windоws, называть подоконником?",
            "Жена говорит программисту: \n- Сходи в магазин, купи батон колбасы. Да, если там есть яйца, возьми десяток. \nПрограммист приходит в магазин и спрашивает: \n- У вас есть яйца? \n- Есть. \n- Тогда дайте мне десяток батонов колбасы. ",
            "Забрали программиста в армию офицером. Построил он солдат и командует: \n- На нулевой-первый рассчитайся!",
            "Разговаривают два программиста: \n- Я слышал, что ты и в женщинах специалист? \n- Ну как специалист, обычный пользователь!",
            "Друг просит у программиста денег в долг: \n- Колян, одолжи в долг сто баксов до среды? \n- Держи Серёга, вот тебе сто двадцать восемь, для ровного счета!",
            "- В ваших программах нет ошибок, вот бы мне так писать! \n- говорит молодой кодер опытному программисту. \n- Не парься, просто мои программы работают при любом количестве ошибок в коде!",
            "- Нет, не вижу что у вас на роду написано! - говорит ясновидящая программисту. \n- А вы, в какой кодировке смотрите?",
            "Наш программист пришел на презентацию в красном галстуке под цвет своих глаз.",
            "- Чем отличается хороший программист от плохого? \n- У хорошего программиста после работы с кодом новых багов меньше чем старых!",
            "Если тебе что-то не нравится в твоей программе, то \nне спеши исправлять, если не помнишь, зачем это сделано.",
            "Если муху с монитора ты сгоняешь мышкой, значит тебе пора домой.",
            "Вчера играл с компьютером в преферанс. Он себе заработал уже на третий апгрейд."
    };

    //пошутить
    default String randomJoke() {
        int num = Util.random(JOKE_STORIES.length);
        return JOKE_STORIES[num];
    }

    //информация о шутнике
    default String infoJoke() {
        return String.format("%s", LABEL_JOKE);
    }

}




