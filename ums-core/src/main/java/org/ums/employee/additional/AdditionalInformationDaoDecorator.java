package org.ums.employee.additional;

import org.ums.decorator.ContentDaoDecorator;

public class AdditionalInformationDaoDecorator extends
    ContentDaoDecorator<AdditionalInformation, MutableAdditionalInformation, String, AdditionalInformationManager>
    implements AdditionalInformationManager {
}
