# DivKit Demo Project

Демо-проект, демонстрирующий работу **Backend-driven UI** с использованием **DivKit**.

## Архитектура

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Java Backend  │    │   DivKit iOS    │    │   База данных   │
│   (Spring Boot) │◄──►│   (Swift)       │    │   (H2 in-memory)│
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## Компоненты

### 1. Backend (Java + Spring Boot)
- **Порт**: 8080
- **API**: REST endpoints для UI схем
- **База данных**: H2 in-memory
- **Функционал**: Управление заметками

### 2. iOS App (Swift + DivKit)
- **UI**: Полностью генерируется из Backend
- **Навигация**: Управляется Backend
- **Бизнес-логика**: Централизована на Backend

## Запуск проекта

### 1. Запуск Backend
```bash
cd backend
source "$HOME/.sdkman/bin/sdkman-init.sh"
mvn spring-boot:run
```

### 2. Запуск iOS App
- Откройте `ios/DivKitDemo.xcodeproj` в Xcode
- Запустите на симуляторе

## API Endpoints

### Получение UI схемы
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

## Тестирование

### Backend
- **Health check**: `http://localhost:8080/api/ui/health`
- **H2 Console**: `http://localhost:8080/h2-console`
- **API тестирование**: Postman, curl

### iOS App
- Приложение автоматически загружает UI схемы
- Все действия отправляются на Backend
- UI обновляется динамически

## Преимущества подхода

1. **Централизованное управление** UI
2. **Динамическое обновление** без обновления приложения
3. **A/B тестирование** интерфейсов
4. **Персонализация** под пользователей
5. **Быстрое прототипирование**

## Структура проекта

```
divkit-demo/
├── backend/                 # Java Backend
│   ├── src/main/java/      # Java код
│   ├── src/main/resources/ # Конфигурация
│   └── pom.xml            # Maven конфигурация
├── ios/                    # iOS приложение
│   └── DivKitDemo/        # Xcode проект
└── README.md              # Этот файл
```

## Технологии

- **Backend**: Java 17, Spring Boot 3.2, H2 Database
- **iOS**: Swift, DivKit
- **API**: REST, JSON
- **Архитектура**: Backend-driven UI
