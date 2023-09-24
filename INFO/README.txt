TEST REST API
---------------

Отримання всіх даних
GET
http://localhost:8082/api/v1.0/contacts

Отримання даних за id
GET
http://localhost:8082/api/v1.0/contacts/2

Створення даних
POST
http://localhost:8082/api/v1.0/contacts

Налаштування в Postman: Body, raw, JSON.

{
    "id": 8,
    "name": "Klaus",
    "phone": "313 313-1313"
}

Оновлення даних за id
PUT
http://localhost:8082/api/v1.0/contacts/2

Налаштування в Postman: Body, raw, JSON.

{
    "phone": "777 777-7777"
}

Видалення даних за id
DELETE
http://localhost:8082/api/v1.0/contacts/3