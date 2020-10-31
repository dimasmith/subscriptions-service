package net.anatolich.subscriptions.support.dbrider;

import static net.anatolich.subscriptions.support.dbrider.DatabaseRiderTest.FLYWAY_META_TABLE;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate database-rider based test with this annotation to avoid issues with the flyway. Database-rider cleans data
 * from all the tables (if requested), including the flyway metadata table. That makes flyway to try to perform already
 * made migrations.
 *
 * That issue comes out only on test context restart.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DBRider
@DBUnit(caseSensitiveTableNames = true, mergeDataSets = true)
@DataSet(value = "reset.yml", skipCleaningFor = {FLYWAY_META_TABLE})
public @interface DatabaseRiderTest {

    String FLYWAY_META_TABLE = "flyway_schema_history";
}
