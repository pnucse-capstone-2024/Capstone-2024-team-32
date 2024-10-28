# 1. Java 21을 사용하기 위한 베이스 이미지 설정
FROM eclipse-temurin:21-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle 빌드 후 생성된 JAR 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 4. JAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
