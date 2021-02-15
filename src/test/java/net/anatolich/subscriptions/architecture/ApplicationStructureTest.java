package net.anatolich.subscriptions.architecture;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import net.anatolich.subscriptions.SubscriptionsServiceApplication;

@AnalyzeClasses(packagesOf = SubscriptionsServiceApplication.class, importOptions = DoNotIncludeTests.class)
class ApplicationStructureTest {

    @ArchTest
    static ArchRule onionArchitecture = Architectures.onionArchitecture()
        .domainModels("..domain.model..")
        .domainServices("..domain.service..")
        .applicationServices("..application..")
        .adapter("rest", "..infrastructure.rest..")
        .adapter("jpa", "..infrastructure.jpa..")
        .adapter("configuration", "..infrastructure.spring..");
}
