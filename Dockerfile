FROM adoptopenjdk/openjdk14 as builder
# JDK 1.8 버전을 베이스로 설정한 후 builder로 alias 처리합니다.

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
# Spring Boot 프로젝트 내의 gradle 설정 파일과 소스코드를 이미지로 가져옵니다.

RUN chmod +x ./gradlew
RUN ./gradlew bootjar
# gradlew 에 실행권한을 부여하고 프로젝트를 jar 형식의 파일로 빌드합니다.




FROM adoptopenjdk/openjdk14
# 위에서 빌드한 jar 파일을 실행해 주기 위해 다시 JDK 1.8 버전을 베이스로 설정합니다.

COPY --from=builder build/libs/*.jar spiritual-war.jar
VOLUME /tmp
EXPOSE 8080
# builder를 통해 생성된 jar 파일을 이미지로 가져옵니다.
# 8080 포트를 공개한다고 명시합니다.

ENTRYPOINT ["java", "-jar", "/spiritual-war.jar"]
# 가져온 jar 파일을 실행시킵니다.