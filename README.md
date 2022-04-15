# finance-otter-server

## Run the application in development mode

First, start a postgres instance. Ideally in Docker:
```shell
docker run -d \
  -p 5432:5432 \
  --name postgres-dev \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=finodev \
  postgres:latest
```
Then, start the application in `dev` mode:
```shell
mvn compile quarkus:dev
```
