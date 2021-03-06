package net.anatolich.subscriptions.currency.domain.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

/**
 * The source of exchange rates. It can be the system admin or some bank. The user can choose one provider to make
 * currency exchange.
 */
@Entity
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"name"})
public class ExchangeRateProvider {

    @Id
    @GeneratedValue(generator = "subscriptionSequence")
    @TableGenerator(name = "subscriptionSequence", table = "subscription_sequence")
    private Long id;
    @NaturalId
    @Column(length = 32, nullable = false, unique = true, updatable = false)
    private String name;
    @Column(length = 100, nullable = false, unique = true, updatable = false)
    private String title;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ExchangeRate> rates;

    public ExchangeRateProvider() {
        rates = new HashSet<>();
    }

    public void updateRates(Collection<ExchangeRate> newRates) {
        rates.removeAll(newRates);
        rates.addAll(newRates);
    }

    public Optional<ExchangeRate> rateForConversion(Currency from, Currency to) {
        return rates.stream()
            .filter(rate -> rate.supportsConversion(from, to))
            .findFirst();
    }

    public Set<ExchangeRate> rates() {
        return Collections.unmodifiableSet(rates);
    }
}
