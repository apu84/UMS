SUBDIRS := ums-microservice-cache-warmer ums-microservice-indexer ums-web ums-webservice-academic ums-webservice-library ums-webservice-bank ums-webservice-account ums-webservice-registrar

.PHONY : all $(SUBDIRS)
all : $(SUBDIRS)

$(SUBDIRS):
	$(MAKE) -C $@ build push
