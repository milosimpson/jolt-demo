#!/bin/sh

server=http://localhost:8080
#server=http://jolt-demo.appspot.com

curl ${server}/transform

echo ""

curl -XPOST -d@test.json ${server}/transform
