# Certificator

### About
Application to generate self igned certificates with for the purpose of development. No way usable for any production 
service. Application logs passwords and has options for command line injection.

### Todo
* upload cacerts store and view contents
* upload cacerts store + certificate and get back updated store

### System properties
* -Dcertificates.root - directory where certificates will be generated
* -DLOG_DIR - directory where applications will write its logs

### Baseimage (j20opensslbase) dockerfile
FROM amazoncorretto:20.0.1-alpine3.17

RUN apk update && apk add bash openssl