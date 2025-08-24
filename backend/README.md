# DivKit Demo Backend

Backend для демо-проекта с использованием Java + Spring Boot + DivKit.

## Требования

- Java 17+
- Maven 3.6+

## Запуск

1. Перейдите в директорию backend:
```bash
cd backend
```

2. Запустите приложение:
```bash
./mvnw spring-boot:run
```

Приложение запустится на порту 8080.

## API Endpoints

### Получение UI схемы экрана
```
GET /api/ui/screen/{screenName}
```

Доступные экраны:
- `notes_list` - список заметок
- `note_create` - создание заметки
- `note_edit` - редактирование заметки
- `note_view` - просмотр заметки

### Выполнение действия
```
POST /api/ui/action
```

Тело запроса:
```json
{
  "type": "create_note",
  "data": {
    "title": "Заголовок",
    "content": "Содержание"
  },
  "screen": "note_create"
}
```

### Проверка здоровья
```
GET /api/ui/health
```

## База данных

Используется H2 in-memory база данных. При запуске автоматически создаются тестовые заметки.

H2 Console доступен по адресу: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Структура проекта

```
src/main/java/com/divkit/demo/
├── DivKitDemoApplication.java    # Главный класс
├── controller/                   # REST контроллеры
├── service/                      # Бизнес-логика
├── repository/                   # Доступ к данным
├── model/                        # JPA сущности
├── dto/                          # Data Transfer Objects
└── config/                       # Конфигурация
```

## Тестирование

Для тестирования API можно использовать:
- Postman
- curl
- H2 Console для просмотра данных
