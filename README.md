# 📋 Task Manager

**Веб-приложение для управления задачами в формате Kanban-доски.**

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-4169E1?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)
![Status](https://img.shields.io/badge/status-в%20разработке-orange)

> 🚧 **Проект находится в разработке.** Функциональность дорабатывается, поэтому что-то может меняться или работать не до конца.

Задачи раскладываются по трём колонкам: **«К выполнению»**, **«В процессе»** и **«Выполнено»**. Их можно создавать, редактировать, удалять, искать по названию, фильтровать и сортировать.

---

## 📋 Возможности

- 🗂 Kanban-доска с тремя колонками и счётчиками задач в каждой
- ➕ Создание, редактирование и удаление задач
- 🏷 У каждой задачи есть название, описание, статус, приоритет (низкий, средний, высокий) и срок выполнения
- 🔍 Поиск задач по названию (без учёта регистра)
- 🎛 Фильтрация по статусу и приоритету
- ↕️ Сортировка: сначала новые, сначала старые, ближайший дедлайн, дальний дедлайн
- ✅ Проверка данных в формах: название обязательно (до 255 символов), описание не длиннее 2000 символов
- 🌙 Тёмный интерфейс на Bootstrap 5

## 🚀 Технологии

| Область | Технологии |
| --- | --- |
| Язык и фреймворк | Java 21, Spring Boot 4.1.2 |
| Данные | Spring Data JPA, PostgreSQL 17 |
| Миграции | Flyway |
| Инфраструктура | Docker Compose (база данных) |
| Фронтенд | Thymeleaf, Bootstrap 5 |
| Прочее | Bean Validation, Lombok, Maven (встроен через `mvnw`) |

## 🛠 Требования

- Java 21 или новее
- Docker Desktop (для базы данных)

Maven устанавливать не нужно, он встроен в проект.

## ▶️ Запуск

**1. Склонируй репозиторий**

```bash
git clone https://github.com/isro456/TaskManager.git
cd TaskManager
```

**2. Запусти базу данных**

```bash
docker compose up -d
```

Это поднимет PostgreSQL в контейнере `taskmanager-postgres` на порту `5433`.

**3. Запусти приложение**

```bash
./mvnw spring-boot:run
```

На Windows используй `mvnw.cmd spring-boot:run`.

Таблица `tasks` создаётся автоматически при запуске через миграцию Flyway.

**4. Открой в браузере** http://localhost:8080/tasks

Чтобы остановить базу данных: `docker compose down`.

## 📖 Как пользоваться

| Действие | Как сделать |
| --- | --- |
| Создать задачу | Нажми **«+ Новая задача»** справа вверху. Новая задача попадает в колонку «К выполнению» |
| Изменить задачу | Нажми на карточку задачи. Здесь же можно поменять статус, и задача переедет в другую колонку |
| Удалить задачу | Открой задачу и нажми красную кнопку **«Удалить задачу»** |
| Найти задачу | Введи часть названия в поле поиска и нажми **«Применить»** |
| Отфильтровать | Выбери статус и (или) приоритет, затем **«Применить»** |
| Отсортировать | Выбери порядок в списке «Сортировка» и нажми **«Применить»** |
| Сбросить всё | Нажми **«Сбросить»** |

## 🌐 Страницы

| Метод | Путь | Описание |
| --- | --- | --- |
| `GET` | `/tasks` | Доска задач (параметры: `status`, `priority`, `title`, `sort`) |
| `GET` | `/tasks/new` | Форма создания задачи |
| `POST` | `/tasks` | Создание задачи |
| `GET` | `/tasks/{id}/edit` | Форма редактирования |
| `POST` | `/tasks/{id}/edit` | Сохранение изменений |
| `POST` | `/tasks/{id}/delete` | Удаление задачи |

Значения для `sort`: `newest`, `oldest`, `dueSoon`, `dueLate`.

## 🐘 База данных

Параметры подключения:

| Параметр | Значение |
| --- | --- |
| Host | `localhost` |
| Port | `5433` |
| Database | `taskmanager` |
| Username | `taskmanager` |
| Password | `local_dev_password` |

> ⚠️ Это учебные данные для локальной разработки. В реальном окружении их нужно заменить.

Посмотреть данные можно через [DBeaver](https://dbeaver.io), [pgAdmin](https://www.pgadmin.org) или прямо из терминала:

```bash
docker exec -it taskmanager-postgres psql -U taskmanager -d taskmanager
```

## 🗂 Структура проекта

```
TaskManager/
├── compose.yaml                    # PostgreSQL в Docker
├── pom.xml                         # Зависимости Maven
└── src/main/
    ├── java/com/taskmanager/
    │   ├── TaskManagerApplication.java
    │   ├── controller/
    │   │   └── TaskController.java     # Страницы и обработка форм
    │   ├── service/
    │   │   └── TaskService.java        # Логика работы с задачами
    │   ├── repository/
    │   │   └── TaskRepository.java     # Доступ к БД, поиск и фильтрация
    │   └── model/
    │       ├── Task.java               # Задача
    │       ├── TaskStatus.java         # TODO, IN_PROGRESS, DONE
    │       └── TaskPriority.java       # LOW, MEDIUM, HIGH
    └── resources/
        ├── application.properties      # Настройки приложения и БД
        ├── db/migration/               # Миграции Flyway
        └── templates/tasks/            # Страницы: доска, создание, редактирование
```

## 👤 Автор

[@isro456](https://github.com/isro456)
