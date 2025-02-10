#!/bin/bash

echo "Building project and image"
export JAVA_HOME="C:\Users\Youcode\.jdks\corretto-21.0.6"
export PATH=$JAVA_HOME/bin:$PATH
mvn clean package jib:dockerBuild -Dmaven.test.skip=true

ARTIFACT=$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

sed -e "s/\${artifactId}/$ARTIFACT/" -e "s/\${version}/$VERSION/" docker-compose-template.yml > docker-compose.yml

export DB_URL="jdbc:postgresql://db:5432/shopping-discount"
export DB_USER="postgres"
export DB_PASSWORD="1234"
export DB_SCHEMA_GENERATION="drop-and-create"

echo "Launching services ..."
docker-compose up
