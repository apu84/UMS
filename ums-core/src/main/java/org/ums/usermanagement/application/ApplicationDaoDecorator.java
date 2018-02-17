package org.ums.usermanagement.application;

import org.ums.decorator.ContentDaoDecorator;

public class ApplicationDaoDecorator extends
    ContentDaoDecorator<Application, MutableApplication, Long, ApplicationManager> implements ApplicationManager {

}
