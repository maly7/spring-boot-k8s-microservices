# spring-boot-k8s-microservices

## Prerequisites
* Install Helm `brew install helm`
* 

## Kubernetes cluster setup
1. install [nginx-ingress](https://kubernetes.github.io/ingress-nginx/deploy/)
1. build the docker images
    1. `./gradlew bootBuildImage`
    1. `./gradlew f:buildImage`
1. create api-gateway 
    1. create deployment `kubectl apply -f api-gateway/deployment.yml`
    1. create service `kubectl apply -f api-gateway/service.yml`
    1. expose the gateway with ingress `kubectl apply -f ingress/local-api-ingress.yml`
1. create auth-service
    1. install mysql helm chart: `helm install auth-mysql -f auth-service/mysql/values.yml stable/mysql`
    1. create deployment `kubectl apply -f auth-service/deployment.yml` 
    1. create service `kubectl apply -f auth-service/service.yml` 
1. create the frontend
    1. create the deployment `kubectl apply -f frontend/deployment.yml`
    1. create the service `kubectl apply -f frontend/service.yml`
    1. expose with ingress `kubect apply -f ingress/local-frontend-ingress.yml`
1. create chat-service
    1. install the mongodb helm chat `helm install chat -f chat-service/mongodb/values.yml bitnami/mongodb`
    1. create deployment `kubectl apply -f chat-service/deployment.yml` 
    1. create service `kubectl apply -f chat-service/service.yml` 
