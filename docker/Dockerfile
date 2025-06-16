FROM amazoncorretto:22 AS runtime
EXPOSE 8080
RUN mkdir /app
RUN mkdir /app/data
WORKDIR /app
# resources, etc...
#COPY --chown=1001:1001 ./out/production/resources /app/
COPY ./out/production/resources /app/
# artifact from menu Build->Build Artifacts...
#COPY --chown=1001:1001 ./out/artifacts/todo_server_jar/todo-server.jar /app
COPY ./out/artifacts/todo_server_jar/todo-server.jar /app
# todo 20250616 logback not work :(
#ENTRYPOINT ["java", "-Dio.ktor.development=true", "-Dfile.encoding=UTF-8","-cp","/app/todo-server.jar","ru.xsrv.todo.ApplicationKt"]
ENTRYPOINT ["java","-cp","/app/todo-server.jar","ru.xsrv.todo.ApplicationKt"]
