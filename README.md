# spring-boot-k8s-microservices

## Pre-requisites
* Install Helm `brew install helm`
* 

## Kubernetes cluster setup
1. install [nginx-ingress](https://kubernetes.github.io/ingress-nginx/deploy/)
1. create api-gateway `kubectl apply -f api-gateway/deployment.yml`
1. create api-gateway service `kubectl apply -f api-gateway/service.yml`
1. expose the api-gateway `kubectl apply -f ingress/local-ingress.yml`
1. create service-registry `kubectl apply -f service-registry/deployment.yml`
1. create service-registry service `kubectl apply -f service-registry/service.yml`
1. create auth-service
  1. install mysql helm chart: `helm install auth-mysql -f auth-service/mysql/values.yml stable/mysql`
  1. create deployment `kubectl apply -f auth-service/deployment.yml` 