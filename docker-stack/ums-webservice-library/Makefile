NAME   := ums-webservice-library
REGISTRY := harrenhall:5001/
TAG    := $(tag)
IMG    := ${NAME}
LATEST := ${REGISTRY}${NAME}:${TAG}

build:
	@docker build -t ${IMG} .
	@docker tag ${IMG} ${LATEST}
push:
	@docker push ${LATEST}
