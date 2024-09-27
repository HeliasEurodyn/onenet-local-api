# Makefile for building, pushing Docker images, and logging into Docker Hub

# Define the image name and tags
IMAGE_NAME = onenet-local-api
DOCKERHUB_REPO = eurodynrnd/onenet-local-api
TAG_LATEST = latest
DOCKER_USERNAME = eurodynrnd
DOCKER_PASSWORD = k12fAz2L1%fqcwx

# Use the TAG_VERSION variable passed as an argument or default to 2.0
TAG_VERSION ?= 7.0

# Define the target to build and push the Docker image
dockerhub-push: dockerhub-login
	sudo docker build -t $(IMAGE_NAME) .
	docker tag $(IMAGE_NAME) $(DOCKERHUB_REPO):$(TAG_LATEST)
	docker tag $(IMAGE_NAME) $(DOCKERHUB_REPO):$(TAG_VERSION)
	docker push $(DOCKERHUB_REPO):$(TAG_LATEST)
	docker push $(DOCKERHUB_REPO):$(TAG_VERSION)

# Define the target to login to Docker Hub
dockerhub-login:
	@echo "Logging into Docker Hub..."
	@docker login -u $(DOCKER_USERNAME) -p $(DOCKER_PASSWORD)

# Prevent echoing the password to the terminal
.SILENT: dockerhub-login
