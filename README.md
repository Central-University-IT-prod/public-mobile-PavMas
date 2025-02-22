

# 📝 Устройство проекта

Проект представляет из себя многомодульное приложение, которое состоит из 4 модулей.

## Модуль Authentication

Представляет из себя полностью самостоятельный, ни от кого не зависящий, модуль для регистрации и авторизации пользователя, готов для выноса в отдельную библиотеку.

Продумано хранение паролей. Для безопасности пароли хранятся не в том виде, в каком их сообщил пользователь, а в качестве хэш-кода.
Для авторизации введенный пароль превращается в хэш-код и только потом сравнивается с эталонным.
Все данные о пользователе хранятся в базе данных (использована библиотека Room и Gson для оптимальной и быстрой конвертации массива строк в json).
Так же хранится пользователь, который авторизовался и не вышел из аккаунта. Хранение производится посредством DataStore

## Модуль Data

Этот модуль зависит от модуля Domain и App. Служит для получения информации с сервера и из паняти.
Здесь реализованы две базы данных(досуг и детали рекомендуемых мест).
Досуг хэшируется в базу данных, чтобы не занимать оперативную память пользователя. По завершении сессии детали из базы чистятся, за исключением тех, которые привязаны к мохраненным элементам досуга.
Обе базы данных работают с применением библиотеки Room.
HTTP-запросы осуществляются благодаря библиотеке Ktor и Kotlin Serialization(Позволяет быстро сериализовать запрос в объект)
Хранилище сессионного ключа доступа к Api хранится в DataStore

## Модуль Domain
Этот слой отвечает за бизнес-логику приложения, он не зависит ни от каких других слоев.
Позволяет Уменьшить зависимость между другими компонентами приложения.

## Модуль App
Здесь реализована работа с интерфейсом и получение локации.
Так же происходит распределение зависимостей с использованием DI (библиотека Hilt). Благодаря DI помогает управлять зависимостями (а в этом приложении их немало).
Проект становится легко масштабировать в будушем, делает проект более гибким, тестируемым и поддерживаемым

Отдельно про BaseRecyclerAdapter. Он спроектирован таким образом, чтобы в один RecyclerView можно было помещать разные элементы или использовать один и тот же адаптер для разных RecyclerView.
Для нового айтема нужно создать холдер, дата класс и добавить конкретную ей переменную в список других айтемов(ItemTypes)
Так же использована библиотека Picasso для более легкого, а самое галвнео быстрого помещения фотографий в айтемы RecyclerView и в фрагмент с деталями рекомендованных мест. 

Использованы Kotlin Flow и LiveData для управления потоком данных в архитектуре приложения. Эти способы комбинированы для более эффективного упревления данными в приложении. 

# Добавленные фичи

### Трекинг локации. Приложение обновляет данные каждые 700 метров, а показывает рекомендовынные места в радиусе 500 метров.
### Хранение авторизованного пользователя в приложении. Хранение большого количества данных о пользователе в базе данных. Надежное хранение паролей.
### Создание заметки о досуге с помощь удобного диалога.
### Шиммер со скелетом для WeatherView
### Прогресс индикатор для Recycler с айтемами рекомендованных мест и заметок досуга.

# Подробно архитектуру приложения можно рассмотреть на диаграмме (LifestyleHUB Диаграмма.drawio)