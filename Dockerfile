FROM openjdk:17.0.2

RUN mkdir -p /back_info
RUN touch /back_info/musspring_backend.log
RUN chmod 777 /back_info/musspring_backend.log

RUN mkdir -p /storage
RUN chmod 777 /storage

COPY "./build/libs/Tapki_Lapki-0.0.1-SNAPSHOT.jar" "tapki_lapki.jar"
ENTRYPOINT ["java", "-jar", "tapki_lapki.jar"]