#!/bin/bash

# Define variables
DOCKER_IMAGE="vps-spring"
VERSION_TAG="1.0.0"
LATEST_FLAG=true
REPOSITORY="wetagustin/vps-spring"

# Important!!! - I need this to push to dockerhub, because the terminal
# opened for executing this script is different that where i started it.
docker login

# Build the Docker image
docker build -t "${DOCKER_IMAGE}:${VERSION_TAG}" .

# Add the tags
docker tag "${DOCKER_IMAGE}:${VERSION_TAG}" "${REPOSITORY}:${VERSION_TAG}"
# Publish the images
docker push "${REPOSITORY}:${VERSION_TAG}"

# Add the latest tag
if [ "${LATEST_FLAG}" = true ]; then
    docker tag "${DOCKER_IMAGE}:${VERSION_TAG}" "${REPOSITORY}:latest"
    docker push "${REPOSITORY}:latest"
fi

# Remove the images
docker rmi "${DOCKER_IMAGE}:${VERSION_TAG}"
docker rmi "${REPOSITORY}:${VERSION_TAG}"

# Wait for user input
read -p "Image published, Press any key to exit..."