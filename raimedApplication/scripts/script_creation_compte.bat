@echo off
curl --location "http://localhost:8080/api/v1/auth/signup" --header "Content-Type: application/json" --data-raw "{\"username\": \"PottierP\", \"firstname\": \"Pierre\", \"lastname\": \"Pottier\", \"password\": \"pierre\", \"email\": \"pierre@raimed.fr\"}"
