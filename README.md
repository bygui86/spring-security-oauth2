# oauth2-id-ch

This a proof of concept which includes 3 important components for spring cloud security:

- Authentication/Authorization Server
- Resource Server (Product Service)
- API Gateway (Zuul and Reactive ones)

## Authentication/Authorization Server
This server is responsible for issuing access token using this endpoint:

- /oauth/token

Token issued by this server use the OAuth2 Password flow.

In order to check token's validity use this endpoint:
- /oauth/check_token

You can find the **Postman Collection** in the repo

## Resource Server (Product Service)

Simple OAuth2 Resource server, which exposes RESTful APIs which represent user's protected resources.

## API Gateway

In this PoC the scope of the API Gateway is just to forward calls either to the Auth/Authz Server or to the Resource Server.

Each service implements his own Authorization policies at the moment. We do not need Edge/Perimeter Authorization or Single Sign On here at the moment.

There are two different versions here of API Gateway:
- Zuul Based
- Reactive version using Spring Cloud Gateway
