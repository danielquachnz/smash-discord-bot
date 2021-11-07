FROM gradle:5.6.2-jdk8
WORKDIR /app
COPY . /app
RUN ./gradlew build
EXPOSE 8000
CMD ["gradle", "bootRun", "-PskipDownload=true"]