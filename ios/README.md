# NotesDivKit iOS App

iOS приложение для демонстрации **Backend-driven UI** с использованием **DivKit**.

## Архитектура

Приложение полностью построено на принципах Backend-driven UI:
- **UI генерируется** из схем от Backend
- **Навигация управляется** Backend
- **Бизнес-логика** централизована на Backend

## Структура проекта

```
NotesDivKit/
├── Models/
│   └── APIModels.swift          # Модели данных для API
├── Services/
│   └── UISchemaService.swift    # Сервис для работы с Backend
├── ViewControllers/
│   └── MainViewController.swift # Основной экран с DivKit
├── AppDelegate.swift            # Точка входа приложения
├── SceneDelegate.swift          # Управление сценами
└── Info.plist                  # Конфигурация приложения
```

## Основные компоненты

### 1. APIModels.swift
Содержит все модели данных для работы с Backend API:
- `UISchemaResponse` - ответ с UI схемой
- `UISchema` - схема UI
- `UIItem` - элемент UI
- `ActionRequest/ActionResponse` - запросы/ответы действий

### 2. UISchemaService.swift
Сервис для взаимодействия с Backend:
- Загрузка UI схем экранов
- Выполнение действий
- Проверка здоровья Backend

### 3. MainViewController.swift
Основной экран приложения:
- Интеграция с DivKit
- Обработка действий от DivKit
- Управление состоянием UI
- Навигация между экранами

## Настройка

### 1. DivKit
Убедитесь, что DivKit добавлен в проект через Swift Package Manager.

### 2. Backend
Backend должен быть запущен на `localhost:8080`.

### 3. HTTP соединения
В Info.plist настроены разрешения для HTTP соединений с localhost.

## Запуск

1. **Запустите Backend** (см. основной README)
2. **Откройте проект** в Xcode
3. **Запустите** на симуляторе

## Работа приложения

1. **Запуск**: Приложение загружает экран `notes_list`
2. **Отображение**: DivKit отрисовывает UI на основе схемы от Backend
3. **Действия**: Пользователь взаимодействует с UI
4. **Обработка**: Действия отправляются на Backend
5. **Обновление**: UI обновляется на основе ответа от Backend

## API Endpoints

Приложение использует следующие endpoints:
- `GET /api/ui/screen/{screenName}` - получение UI схемы
- `POST /api/ui/action` - выполнение действия
- `GET /api/ui/health` - проверка здоровья Backend

## Отладка

### Логи
В консоли Xcode выводятся:
- Действия от DivKit
- Ошибки загрузки экранов
- Ошибки выполнения действий