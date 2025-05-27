# TaskFlow
Расписание, с приоритетами и пользователями, по сути как calendar.google , но свой и лучше, ведь можно расширить, пока чисто back
Postgresql, Spring

ШАГИ ТЕСТИРОВАНИЯ(через swagger, postman)


Шаг#1:


POST

http://localhost:8080/api/auth/register

Body (JSON):

{
  "username": "sanya",
  "password": "123456"
}

вам выдадут auth токен


Шаг#2:

POST

http://localhost:8080/api/tasks

Authorization: Bearer <ТВОЙ_ТОКЕН_ИЗ_ЛОГИНА>

Content-Type: application/json

{
  "title": "Сдать диплом",
  "description": "Подготовить и отправить диплом до 30 июня",
  "deadline": "2025-06-30T23:59:00",
  "status": "TODO",
  "priority": "HIGH"
}


Шаг#3:

GET

http://localhost:8080/api/tasks

Authorization: Bearer <ТВОЙ_ТОКЕН_ИЗ_ЛОГИНА>
