#!/bin/sh

server=http://localhost:8080
#server=http://jolt-demo.appspot.com

curl ${server}/transform

echo ""

curl -XPOST -H "Content-Type: application/json" -d@test.json ${server}/transform
