NAME   := ums-jenkins
REGISTRY := harrenhall:5001/
IMG    := ${NAME}
LATEST := ${REGISTRY}${NAME}

build:
	@docker build -t ${IMG} .
	@docker tag ${IMG} ${LATEST}
push:
	@docker push ${LATEST}
