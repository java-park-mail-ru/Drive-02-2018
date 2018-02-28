# Drive-02-2018
Java course 02.2018. Project: Drive

# API methods:


**/register** - регистрация нового пользователя

Передается POST запрос в формате:
{
    "mail":"example@yandex.ru", 
    "login":"MyLogin", 
    "password":"12345"
}
    
**/signin** - вход для пользователя

Передается POST запрос в формате:
{
    "mail":"example@mail.ru", 
    "password":"12345"
}

**/user** - вывод информации о пользователе в текущей сессии

**/edit** - редактирование пользователя в текущей сессии

**/logout** - выход из аккаунта

# Коды ответа:

"USER_ALREADY_EXISTS", "User already exists"
"WITHOUT_MAIL", "You need to write mail"
"WITHOUT_PASSWORD", "You need to write password"
"NO_SUCH_MAIL", "User with this mail doesn't exist"
"WRONG_PASSWORD", "Wrong password"
"NOT_LOGINED", "User isn't logined"
"NOTHING_TO_UPDATE", "You need to change something"


"SUCCESS_NEW_USER", "User was successfully added"
"SUCCESS_SIGNIN", "You have successfully logined"
"SUCCESS_GET_USER", "Your user is here"
"SUCCESS_LOGOUT", "Your have successfully logout"
"SUCCESS_UPDATE_PROFILE", "Your have successfully update your profile"
