# Lesson_5

## MainActivity — FourthActivity, SixthActivity
Объект-компаньон содержит метод start(context: Context) для запуска соответствующей Activity

## FifthActivity
Объект-компаньон содержит метод createStartIntent(context: Context): Intent, так как для контракта
необходим метод, возвращающий Intent.

При нажатии «Доставить результат»: уничтожает FifthActivity, а в ThirdActivity отображает Snackbar с
текстом из верхнего поля ввода. Если текст не был введён или содержит только пробельные символы, то
Snackbar содержит сообщение об этом.
При этом если на ThirdActivity пользователь возвращается посредством нажатия системной кнопки
«Назад», то возвращается resultCode=Activity.RESULT_CANCEL и Snackbar не отображается.

При нажатии «Сохранить»: если нижнее поле ввода пусто или содержит только пробельные символы, то
отображается сообщение об ошибке.

## FirstActivity, FourthActivity
launchMode установлены не в коде флагами, а в манифесте.

## SixthActivity (Доп. задание)
Не стал пробрасывать ClickListener во ViewHolder, так как для отображения Snackbar в этом нет необходимости.