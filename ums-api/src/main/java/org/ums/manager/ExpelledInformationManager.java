package org.ums.manager;

import org.ums.domain.model.immutable.ExpelledInformation;
import org.ums.domain.model.mutable.MutableExpelledInformation;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
public interface ExpelledInformationManager extends
    ContentManager<ExpelledInformation, MutableExpelledInformation, Long> {

}
