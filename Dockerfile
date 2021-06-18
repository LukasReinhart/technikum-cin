# sudo docker build -t personwebapp .

FROM openjdk:11-jre-slim
# only need Java Runtime Environment at version <= 11

LABEL company="technikum-wien.at" \
      author="Bernhard"

# WORKDIR /app
WORKDIR /usr/src/app

# COPY target/person.jar .
COPY ./target/person.jar .

CMD [ "java", "-jar", "person.jar" ]