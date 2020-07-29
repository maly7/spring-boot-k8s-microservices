#  Getting Started with Spring Boot and Kubernetes
This readme should serve as a getting started guide for running a simple spring boot web app in kubernetes. It's not a comprehensive guide, but should serve as a starting point. At the time of writing it's focused on Spring Boot 2.3.X and Kubernetes 1.16.X

## Building a docker image
Before you can deploy anything to kubernetes, you'll first need a docker  image. A detailed guide can be found [here](https://spring.io/guides/gs/spring-boot-docker/). The tl;dr version is you can build a docker image using  the  following commands:

Maven:

`./mvnw spring-boot:build-image`

Gradle:

`./gradlew bootBuildImage`

## Running an Image in a Pod
The closest thing kubernetes has to running a single container is running a [Pod](https://kubernetes.io/docs/concepts/workloads/pods/) with a single container. To run your built image in a Pod do the following:

`kubectl run app-name --image=image_name --generator=run-pod/v1` where `app-name` will be the name of the Pod created and `image_name` should be the name of the image built by one of the previous commands. If you're working with a local version of kubernetes and not pushing your docker image, then add the flag `--image-pull-policy=Never`.

Run `kubectl get pods` to see the newly created pod. If you need to run and updated version, delete the pod using `kubectl delete pod app-name` then re-run it.

## Deployments
Running a single docker image in a pod is a great way of quickly debugging if your app has any issues running in kubernetes. However, having to delete and recreate the Pod every time isn't ideal and you miss out on other features in kubernetes. In order to run an app in a more sophisticated manner, use a [Deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/). 

Here's a quick template for a simple Spring Boot app:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: app-name-deployment
  labels:
    app: app-name
spec:
  replicas: 3
  selector:
    matchLabels:
      app: app-name
  template:
    metadata:
      labels:
        app: app-name
    spec:
      containers:
        - name: app-name
          image: image_name
          imagePullPolicy: Never # Only recommended for local k8s cluster
          ports:
            - containerPort: 8080
          livenessProbe:
            httpGet:
              port: 8080
              path: /actuator/health
            initialDelaySeconds: 5
            periodSeconds: 5
          readinessProbe:
            httpGet:
              port: 8080
              path: /actuator/health
            initialDelaySeconds: 5
            periodSeconds: 5
``` 

The template includes some nice features for Deployments. The readiness probe using the actuator will allow kubernetes to know if the app starts successfully. The liveness probe will allow kubernetes to restart the app if it crashes. The replicas allows for multiple instances of the app to run in order to handle load. 

To deploy the app this time  run:

`kubectl apply -f deployment.yml`

## Exposing Deployment with Service
You might need your  app to be exposed to other services within the cluster. In order to expose the app you can create a [Service](https://kubernetes.io/docs/concepts/services-networking/service/). Services provide features like DNS within the cluster.

Here's a sample service definition for the previous deployment:
```yaml
apiVersion: v1
kind: Service
metadata:
  name: app-name
spec:
  selector:
    app: app-name
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
```

To create this service run:
`kubectl apply -f service.yml`

The `spec.selector.app` should match the `metadata.labels.app` field from the deployment. This service definition allows other kubernetes resources with the  cluster to reach the app  using `http://app-name.default.svc.cluster.local` where `default` can be replaced with the namespace used for the service and deployment. 

## Ingress Routes
Occasionally an app might need to be accessible outside the cluster. This can be accomplished with an [ingress](https://kubernetes.io/docs/concepts/services-networking/ingress/). Before creating an ingress resource, first create an ingress controller such as [ingress-nginx](https://kubernetes.github.io/ingress-nginx/deploy/).

To expose the app using ingress, use this sample ingress definition:
```yaml
apiVersion: networking.k8s.io/v1beta1
kind: Ingress
metadata:
  name: test-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
    - host: localhost
      http:
        paths:
          - path: /
            backend:
              serviceName: app-name
              servicePort: 80
```

To create this resource run:
`kubectl apply -f ingress.yml`

This definition assumes the host `localhost` but can be changed to whatever hostname used for the cluster. Also note that `serviceName` and `servicePort` should match their respective values from the service definition. If running a local kubernetes cluster, your app could now be accessed from `http://localhost`. 

## Connecting to a Database
Setting up a database in kubernetes is pretty cumbersome, fortunately your app can use helm to take care of most of the difficult  work. To get mysql up and running in kubernetes, install  [helm](https://helm.sh/) (`brew install helm`), then run the command `helm install release-name -f values.yml stable/mysql` where `release-name` is the name given to most of the resources within kubernetes so use something you'll be  able to remember.  An example `values.yml` for this chart would look like:

```yaml
mysqlUser: app-name
mysqlDatabase: app-name
```

This will instruct the chart to create a database and non-root user for the application. 

To connect the application to mysql, setup the application properties:
```yaml
spring:
  application:
    name: app-name
  datasource:
    url: ${DATASOURCE_URL}
    username: app-name
    password: ${DATASOURCE_PASSWORD}
```

The deployment for the application can then be updated to load the two necessary environment variables:
```yaml
containers:
- name: app-name
  image: image_name
  imagePullPolicy: Never
  ports:
    - containerPort: 8080
  env:
    - name: DATASOURCE_URL
      value: "jdbc:mysql://mysql-release-name.default.svc.cluster.local:3306/dbName"
    - name: DATASOURCE_PASSWORD
      valueFrom:
        secretKeyRef:
          key: mysql-password
          name: mysql-release-name
```
This example shows two ways of providing environment variables to containers. The first is simply by name and value.  The second uses a secret. In this case `mysql-release-name` is a reference to the release name given to the helm chart installation. The secret and key were created when installing the chart. In the datasource url, `dbName` is whatever name was provided for the database in the `values.yml`. 