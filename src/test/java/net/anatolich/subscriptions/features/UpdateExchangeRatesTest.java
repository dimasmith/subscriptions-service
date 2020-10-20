package net.anatolich.subscriptions.features;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import java.util.Currency;
import java.util.Set;
import net.anatolich.subscriptions.currency.application.ExchangeRatesManagementService;
import net.anatolich.subscriptions.currency.domain.ExchangeRate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@DBRider
@DBUnit(caseSensitiveTableNames = true)
@DisplayName("update exchange rates")
class UpdateExchangeRatesTest {

    @Autowired
    private ExchangeRatesManagementService exchangeRates;
    public static final Currency EUR = Currency.getInstance("EUR");
    public static final Currency USD = Currency.getInstance("USD");
    public static final Currency UAH = Currency.getInstance("UAH");

    @Test
    @DataSet(value = "currency/updateExchangeRates-setup.yml", cleanBefore = true)
//    @ExpectedDataSet(value = "currency/updateExchangeRates-expected.yml", compareOperation = CompareOperation.CONTAINS)
    @DisplayName("change existing rates")
    @WithMockUser(value = "admin", roles = {"USER", "ADMIN"})
    void changeExistingRates() {
        final ExchangeRate newUsdToUahRate = ExchangeRate.of("USD", "UAH", 50);
        final ExchangeRate newUahToUsdRate = ExchangeRate.of("UAH", "USD", 0.02);
        exchangeRates.updateAdminExchangeRates(Set.of(
            newUsdToUahRate, newUahToUsdRate
        ));

        var exchangeRates = this.exchangeRates.seeExchangeRates();

        Assertions.assertThat(exchangeRates)
            .hasSize(2);
        var usdToUahRate = newUsdToUahRate.getRate();
        Assertions.assertThat(exchangeRates)
            .as("conversion from %s to %s should have rate %s", USD, UAH, usdToUahRate)
            .filteredOn(rate -> rate.supportsConversion(USD, UAH))
            .extracting(ExchangeRate::getRate)
            .allSatisfy(rate -> Assertions.assertThat(rate).isEqualByComparingTo(usdToUahRate));

        var uahToUsdRate = newUahToUsdRate.getRate();
        Assertions.assertThat(exchangeRates)
            .as("conversion from %s to %s should have rate %s", UAH, USD, uahToUsdRate)
            .filteredOn(rate -> rate.supportsConversion(UAH, USD))
            .extracting(ExchangeRate::getRate)
            .allSatisfy(rate -> Assertions.assertThat(rate).isEqualByComparingTo(uahToUsdRate));

    }

    @Test
    @DataSet(value = "currency/updateExchangeRates-setup.yml", cleanBefore = true)
    @DisplayName("change existing rates and add new")
    @WithMockUser(value = "admin", roles = {"USER", "ADMIN"})
    void changeExistingRatesAndAddNew() {
        final ExchangeRate newUsdToUahRate = ExchangeRate.of("USD", "UAH", 50);
        final ExchangeRate newUahToUsdRate = ExchangeRate.of("UAH", "USD", 0.02);
        final ExchangeRate newEurToUahRate = ExchangeRate.of("EUR", "USD", 100);
        exchangeRates.updateAdminExchangeRates(Set.of(
            newUsdToUahRate, newUahToUsdRate, newEurToUahRate
        ));

        var exchangeRates = this.exchangeRates.seeExchangeRates();

        Assertions.assertThat(exchangeRates)
            .hasSize(3);
        var usdToUahRate = newUsdToUahRate.getRate();
        Assertions.assertThat(exchangeRates)
            .as("conversion from %s to %s should have rate %s", USD, UAH, usdToUahRate)
            .filteredOn(rate -> rate.supportsConversion(USD, UAH))
            .extracting(ExchangeRate::getRate)
            .allSatisfy(rate -> Assertions.assertThat(rate).isEqualByComparingTo(usdToUahRate));

        var uahToUsdRate = newUahToUsdRate.getRate();
        Assertions.assertThat(exchangeRates)
            .as("conversion from %s to %s should have rate %s", UAH, USD, uahToUsdRate)
            .filteredOn(rate -> rate.supportsConversion(UAH, USD))
            .extracting(ExchangeRate::getRate)
            .allSatisfy(rate -> Assertions.assertThat(rate).isEqualByComparingTo(uahToUsdRate));

        var eurToUahRate = newEurToUahRate.getRate();
        Assertions.assertThat(exchangeRates)
            .as("conversion from %s to %s should have rate %s", EUR, UAH, eurToUahRate)
            .filteredOn(rate -> rate.supportsConversion(EUR, UAH))
            .extracting(ExchangeRate::getRate)
            .allSatisfy(rate -> Assertions.assertThat(rate).isEqualByComparingTo(eurToUahRate));
    }
}
