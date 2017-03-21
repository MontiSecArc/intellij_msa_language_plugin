package de.monticore.lang.montisecarc.informationflow;

public @interface SecurityClassification {

    MSASecurityClass securityClass()
            default MSASecurityClass.NON_CONFIDENTIAL;
}
