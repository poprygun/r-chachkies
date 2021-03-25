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

- Create native images for `client`, `server`, and `broker`

```bash
./mvnw spring-boot:build-image
```

- Create `broker` Kubernetes deployment/service

```bash
kubectl create -f k8s/broker-deployment.yaml
```

- Create `client` and `server` deployment

```bash
kubectl create -f k8s/client-server-deployment.yaml
```

- Watch `client` logs

```bash
kubectl logs <pod> -c client
```




