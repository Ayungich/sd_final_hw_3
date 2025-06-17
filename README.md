# 🛒 Контрольная работа № 3

Микросервисное приложение для обработки заказов, платежей и управления пользователями. Включает в себя:

- 📦 Сервис заказов (`orders-service`)
- 💰 Сервис платежей (`payments-service`)
- 🧩 Общая библиотека (`common`)
- 🌐 API-шлюз (`gateway`)
- 🐘 PostgreSQL, 🐳 Kafka, Docker Compose

---

## ⚙️ Технологии

- Java 17 + Spring Boot
- Spring Web / Spring Data JPA
- PostgreSQL
- Apache Kafka
- Docker + Docker Compose
- OpenAPI / Swagger UI

---

## 🚀 Как запустить

> **Требования**: Docker, Docker Compose, Java 17, Maven

1. **Склонировать проект:**

```bash
git clone https://github.com/Ayungich/sd_final_hw_3.git
cd shop-app
```

2. **Собрать все модули:**

```bash
mvn clean install
```

3. **Запустить все сервисы:**

```bash
docker compose up --build
```

> Убедись, что порты `8080`, `8081`, `8082`, `5432`, `29092` свободны.

---

## 🌍 Доступ к Swagger UI

- API Gateway: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Orders: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- Payments: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

---

## 🧪 Как тестировать

1. **Создать аккаунт:**

`POST /accounts`

```json
Headers:
X-User-Id: 11111111-1111-1111-1111-111111111111

Body:
{
  "initialBalance": 100.0
}
```

2. **Пополнить счёт:**

`POST /accounts/{id}/top-up`

```json
{
  "amount": 50.0
}
```

3. **Создать заказ:**

`POST /orders`

```json
Headers:
X-User-Id: 11111111-1111-1111-1111-111111111111

Body:
{
  "amount": 120.0,
  "description": "Летние шлёпанцы"
}
```

4. **Проверить заказ:**

`GET /orders/{id}`

5. **Проверить баланс:**

`GET /accounts/{id}`

---

## 🧱 Архитектура

- Каждый сервис использует отдельную базу данных (PostgreSQL)
- Взаимодействие между сервисами — через Kafka
- Exactly-once семантика не реализована (используется outbox/inbox для упрощённой idempotent обработки)

---

## 📁 Структура проекта

```
shop-app/
├── common/              # Общие классы и события (Kafka Events, DTO)
├── orders-service/      # Микросервис заказов
├── payments-service/    # Микросервис платёжной системы
├── gateway/             # API-шлюз
├── docker-compose.yml   # Контейнеризация
└── README.md
```

---

## ✅ Реализовано:

- [x] Микросервисная архитектура
- [x] Kafka-обмен событиями
- [x] Outbox/Inbox шаблоны
- [x] Валидация и вежные ошибки
- [x] Swagger UI
- [x] Docker Compose
- [x] Ручное и автоматическое тестирование через PowerShell/Swagger

---

## 🧠 Автор

> Разработано в рамках учебного проекта  
> - Автор: Лефанов Никита Юрьевич
> - [@ayungich](https://t.me/ayungich) — 2025
