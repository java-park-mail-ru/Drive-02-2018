# Drive-02-2018
Java course 02.2018. Project: Drive

# API methods:


**/register** - регистрация нового пользователя </br>

Передается POST запрос, содержащий json вида: <br />
{ "mail":"example@yandex.ru", <br />
  "login":"MyLogin", <br />
  "password":"12345"}<br />

Ответ в случае неудачи: <br />
{	"success":"false",<br />
	"status":"User isn't logined" }<br />
(Либо код другой ошибки)<br />

Если пользователь успешно зарегистрировался: <br />
{	"success":"true",<br />
	"status":"User was successfully added" }<br />
   
 Выполняется регистрация и вход. 
<br />

**/signin** - вход для пользователя

Передается POST запрос, содержащий json вида: <br />
{    "mail":"example@mail.ru", <br />
     "password":"12345" }<br />

**/userModel** - вывод информации о пользователе в текущей сессии<br />
 
 Вывод, в случае корректной куки: 
<br />
{"success":"true",<br />
 "status":"Your userModel is here",<br />
 "userModel":{<br />
	"id":3,<br />
	"mail":"andreyka@mail.ru",<br />
	"password":"213111",<br />
	"login":"LoginA"} <br />
}<br />

**/edit** - редактирование пользователя в текущей сессии <br />

Передается json с полями, которые надо изменить, например: <br />
{"mail": "andrey@mail.ru", <br />
"login": "TopPlayer" }<br />

**/logout** - выход из аккаунта


**/leaders/3/10** - лучшие игроки
Первый параметр - позиция игрока в рейтинге, <br />
второй - количество игроков, которых надо вывести. <br />
В примере выведется 10 человек, начиная с 3-его номера.<br />


# Коды ответа:

"USER_ALREADY_EXISTS", "User already exists"<br />
"WITHOUT_MAIL", "You need to write mail"<br />
"WITHOUT_PASSWORD", "You need to write password"<br />
"NO_SUCH_MAIL", "User with this mail doesn't exist"<br />
"WRONG_PASSWORD", "Wrong password"<br />
"NOT_LOGINED", "User isn't logined"<br />
"INCORRECT_SESSION", "Bad cookie" <br />
"NOTHING_TO_UPDATE", "You need to change something"<br />


"SUCCESS_NEW_USER", "User was successfully added"<br />
"SUCCESS_SIGNIN", "You have successfully logined"<br />
"SUCCESS_GET_USER", "Your userModel is here"<br />
"SUCCESS_LOGOUT", "Your have successfully logout"<br />
"SUCCESS_UPDATE_PROFILE", "Your have successfully update your profile"<br />
