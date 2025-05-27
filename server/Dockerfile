FROM openjdk:17-jdk

# 1. 작업 디렉토리 설정
WORKDIR /app

# 2. Gradle wrapper와 빌드 설정 파일 복사
COPY gradlew .
COPY gradlew.bat .
COPY build.gradle .
COPY settings.gradle .

# 3. Gradle wrapper 폴더 복사
COPY gradle gradle
COPY gradle/wrapper gradle/wrapper

# 4. 소스 복사
COPY src src

# 5. 실행 권한 부여 + 빌드
RUN chmod +x ./gradlew
RUN ./gradlew build

# 6. 실행
CMD ["java", "-jar", "build/libs/watchout-0.0.1-SNAPSHOT.jar"]
