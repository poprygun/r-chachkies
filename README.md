# RSocket playground

## [1.0.0 Release](https://github.com/poprygun/r-chachkies/releases/tag/1.0.0)

Client directly commuinicates to Server

## [2.0.0 Release](https://github.com/poprygun/r-chachkies/releases/tag/2.0.0)

Client communicates to Server via [Routing Broker](https://github.com/spencergibb/rsocket-routing-sample)

- Start Broker from broker/ directory

```bash
./mvnw spring-boot:run
```

- Start Server from server/ directory

```bash
./mvnw spring-boot:run
```

- Start Client from client/ directory

```bash
./mvnw spring-boot:run
```

Use Native Images

## [3.0.0 Release](https://github.com/poprygun/r-chachkies/releases/tag/3.0.0)

docker network create rsocket-net

docker run --network rsocket-net --name broker -p 61616:61616 -p 8002:8002 -p 7002:7002 broker:0.0.1-SNAPSHOT

docker run --network rsocket-net --name server -e 'IO_RSOCKET_ROUTING_CLIENT_BROKERS_0_TCP_HOST'='broker' -e 'IO_RSOCKET_ROUTING_CLIENT_BROKERS_0_TCP_PORT'='8002' server:0.0.1-SNAPSHOT

docker run broker:0.0.1-SNAPSHOT


docker build -t broker:0.0.1-SNAPSHOT
docker build -t client:0.0.1-SNAPSHOT
docker build -t server:0.0.1-SNAPSHOT

docker run -e 'IO_RSOCKET_ROUTING_CLIENT_BROKERS_0_TCP_HOST'='host.docker.internal' docker.io/library/server:0.0.1-SNAPSHOT
docker run -e 'IO_RSOCKET_ROUTING_CLIENT_BROKERS_0_TCP_HOST'='host.docker.internal' -e 'IO_RSOCKET_ROUTING_CLIENT_BROKERS_AUTOCONNECT'=false docker.io/library/server:0.0.1-SNAPSHOT
docker run -e 'IO_RSOCKET_ROUTING_CLIENT_BROKERS_0_TCP_HOST'='host.docker.internal' -e 'IO_RSOCKET_ROUTING_CLIENT_BROKERS_AUTOCONNECT'=false docker.io/library/client:0.0.1-SNAPSHOT
docker run -e io.rsocket.routing.client.brokers.tcp.host=host.docker.internal docker.io/library/client:0.0.1-SNAPSHOT