**Task Service (Spring Boot)**
REST-сервис для создания, получения, обновления и удаления задач.
Хранение в памяти (HashMap). ID генерируется автоматически (AtomicLong).

**Требования:**
Java 17+
Maven 3+
Spring Boot 3+

**Запуск**
mvn spring-boot:run

Сервис будет доступен по адресу:
http://localhost:8080

**API**
```base-url: /task```

**Модель Task**
```
{
  "id": 1,
  "creatorId": 12,
  "assignedUserId": 45,
  "status": "CREATED",
  "createDateTime": "2025-01-10",
  "deadlineDate": "2025-01-20",
  "priority": "HIGH"
}
```

```
**Ограничения:**
id — генерируется автоматически, передавать в body запрещено.
status — при создании должен быть null или CREATED. Сервис устанавливает CREATED автоматически.
deadlineDate >= createDateTime.
```

**Endpoints**
```
**1. GET /tasks/{id}**
Получить задачу по ID.
Response 200
JSON задачи.
```

```
Ошибки:
400 — неверный ID
404 — задача не найдена
```

```
**2. GET /tasks/all**
Получить все задачи.
Response 200
Массив задач.
```

```
**3. POST /tasks/create**
Создать новую задачу.
```

Request body
```
{
  "creatorId": 12,
  "assignedUserId": 45,
  "createDateTime": "2025-01-10",
  "deadlineDate": "2025-01-20",
  "priority": "HIGH"
}
```
Response 200
Созданная задача.
```
Ошибки
400 — передан id
400 — передан некорректный status
400 — deadline < createDateTime
```
```
**4. PUT /tasks/update/{id}**
Обновить задачу.
```
Request Body
```
{
  "creatorId": 12,
  "assignedUserId": 45,
  "status": "IN_PROGRESS",
  "createDateTime": "2025-01-10",
  "deadlineDate": "2025-01-22",
  "priority": "MEDIUM"
}
```
Response 200
Обновлённая задача.
```
Ошибки
404 — задача не найдена
400 — deadline < createDateTime
```
```
**5. DELETE /tasks/delete/{id}**
Удалить задачу.
```
Response 200
task with id: X deleted

```
Ошибки
404 — задача не найдена
```

```Ограничения и особенности:```
Данные не сохраняются между перезапусками (в памяти).
Конкурентный доступ поддерживается простейшим образом благодаря AtomicLong.
Ошибки возвращаются через ResponseStatusException.
