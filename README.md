## Домашняя работа Aston #8

Создать docker-compose.yml, который развернет всю микросервисную систему, включая Kafka, PostgreSQL, API Gateway, Service Discovery, External Configuration и 2 микросервиса(user-service и notification-service, созданные ранее). Проверить, что сервисы корректно взаимодействуют друг с другом в контейнерной среде.

## Сервисы
- zookeeper — нужен для Kafka
- kafka — брокер сообщений
- postgres — база данных
- eureka-server — Service Discovery
- config-server — External Configuration
- api-gateway — единая точка входа
- user-service — управление пользователями
- notification-service — отправка email
