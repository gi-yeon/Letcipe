#자바 버전
FROM openjdk:11-jdk

#라벨
LABEL maintainer="giyeon3145@gmail.com"

#포트번호 설정
EXPOSE 8888
ENV TZ=Asia/Seoul
#가져올 파일 jar또는 war
ARG JAR_FILE=./backend/build/libs/*.jar

#war파일 복사
COPY ${JAR_FILE} app.jar

#ENTRYPOINT 명령을 지정, app.jar 실행
ENTRYPOINT ["java","-jar","/app.jar"]

