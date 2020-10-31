package net.anatolich.subscriptions.features;

import com.github.database.rider.core.api.dataset.DataSet;
import java.math.BigDecimal;
import java.util.Currency;
import net.anatolich.subscriptions.currency.application.ExchangeRatesManagementService;
import net.anatolich.subscriptions.currency.domain.ExchangeRate;
import net.anatolich.subscriptions.support.dbrider.DatabaseRiderTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DatabaseRiderTest
@DisplayName("load exchange rates")
class LoadExchangeRatesTest {

    @Autowired
    private ExchangeRatesManagementService exchangeRates;
    public static final Currency USD = Currency.getInstance("USD");
    public static final Currency UAH = Currency.getInstance("UAH");

    @Test
    @DataSet(value = "currency/seeExchangeRates-setup.yml", cleanBefore = true)
    @DisplayName("load exchange rates from default provider")
    void loadExchangeRatesFromDefaultProvider() {
        var exchangeRates = this.exchangeRates.seeExchangeRates();

        Assertions.assertThat(exchangeRates)
            .hasSize(2);
        var usdToUahRate = BigDecimal.valueOf(25);
        Assertions.assertThat(exchangeRates)
            .as("conversion from %s to %s should have rate %s", USD, UAH, usdToUahRate)
            .filteredOn(rate -> rate.supportsConversion(USD, UAH))
            .extracting(ExchangeRate::getRate)
            .allSatisfy(rate -> Assertions.assertThat(rate).isEqualByComparingTo(usdToUahRate));

        var uahToUsdRate = BigDecimal.valueOf(0.04);
        Assertions.assertThat(exchangeRates)
            .as("conversion from %s to %s should have rate %s", UAH, USD, uahToUsdRate)
            .filteredOn(rate -> rate.supportsConversion(UAH, USD))
            .extracting(ExchangeRate::getRate)
            .allSatisfy(rate -> Assertions.assertThat(rate).isEqualByComparingTo(uahToUsdRate));

    }
}
