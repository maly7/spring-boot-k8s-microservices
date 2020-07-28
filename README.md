# spring-boot-k8s-microservices

## Kubernetes cluster setup
1. install [nginx-ingress](https://kubernetes.github.io/ingress-nginx/deploy/)
1. create api-gateway `kubectl apply -f api-gateway/deployment.yml`
1. create api-gateway service `kubectl apply -f api-gateway/service.yml`
1. expose the api-gateway `kubectl apply -f ingress/local-ingress.yml`