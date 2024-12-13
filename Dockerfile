FROM openjdk:23-jdk-oracle AS builder

ARG COMPILE_DIR=/complieddir

WORKDIR ${COMPILE_DIR}

COPY mvnw . 
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN ./mvnw package -Dmaven.test.skip=true

FROM openjdk:23-jdk-oracle

ARG WORK_DIR=/app

WORKDIR ${WORK_DIR}

COPY --from=builder /complieddir/target/noticeboard-0.0.1-SNAPSHOT.jar noticeboard.jar

ENV PORT=8080

EXPOSE ${PORT}

HEALTHCHECK --interval=60s --timeout=5s --start-period=120s --retries=3 \
   CMD curl http://localhost:${PORT}/status || exit 1

ENTRYPOINT java -jar noticeboard.jar