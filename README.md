we have total 5 services 
1. MVC Service
2. API Gateway
3. Eureka
4. auth service
5. student service
6. college service

1. MVC service built for UI, request commint from browser
    - we have 4 html pages
       1. Login.html
       2. register.html
       3. studentList.html
       4. collegeList.html

2. API gateway is built for routing and filtering API

3. Eureka service, We use a Eureka service in microservices architectures for dynamic service discovery, allowing services to find and communicate with each other without hardcoding their network locations.
   It acts as a central registry where services register their location and health, enabling other services and API gateways to dynamically discover and connect to them, which supports scalability and resilience.

4. auth service created for user authentication based on JWT

5. student service built for student record

6. college service created for college record 
