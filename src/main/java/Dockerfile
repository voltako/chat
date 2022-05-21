FROM openjdk:17-alpine

WORKDIR /app


#RUN apk add --no-cache gcc musl-dev linux-headers


COPY . .

RUN javac Server/Server.java && javac client/Client.java
CMD ["java","Server/Server"]



