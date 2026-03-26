# SQL1

# 1. запуск докера
docker-compose up -d

# 2. приложение (ВАЖНО с параметрами)
java -jar .\artifacts\app-deadline.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -Dspring.datasource.username=app -Dspring.datasource.password=pass

# 3. проверить руками
http://localhost:9999

# 4. тесты
./gradlew clean test
