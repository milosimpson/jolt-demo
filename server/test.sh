#!/bin/sh

curl http://localhost:8080/transform

echo ""

curl -XPOST -d@test.json http://localhost:8080/transform