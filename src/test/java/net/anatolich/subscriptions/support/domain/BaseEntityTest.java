package net.anatolich.subscriptions.support.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("base entity")
class BaseEntityTest {

    @Test
    @DisplayName("new entities are not equal")
    void newEntitiesAreNotEqual() {
        final var tom = new Cat();
        final var sam = new Cat();

        Assertions.assertThat(tom)
            .as("entities without identifiers are not equal")
            .isNotEqualTo(sam);
    }

    @Test
    @DisplayName("entities of different classes are not equal")
    void entitiesOfDifferentClassesAreNotEqual() {
        final var tom = new Cat();
        final var sam = new Dog();

        Assertions.assertThat(tom)
            .as("entities of different classes are not equal")
            .isNotEqualTo(sam);
    }

    @Test
    @DisplayName("no entity is equal to null")
    void noEntityIsEqualToNull() {
        final var tom = new Cat();
        final Cat sam = null;

        final var equals = tom.equals(sam);

        Assertions.assertThat(equals)
            .as("no entity is equal to null")
            .isFalse();
    }

    @Test
    @DisplayName("entities are equal to self")
    void entitiesAreEqualToSelf() {
        final var tom = new Cat();

        final var equals = tom.equals(tom);

        Assertions.assertThat(equals)
            .as("no entity is equal to null")
            .isTrue();
    }

    @Test
    @DisplayName("entities with same id are equal")
    void entitiesWithSameIdAreEqual() {
        final var tom = new Cat(1L);
        final var tomAgain = new Cat(1L);

        Assertions.assertThat(tom)
            .as("entities of same type and indetifier are equal")
            .isEqualTo(tomAgain);
    }

    @Test
    @DisplayName("identified entities of different class are not equal")
    void identifiedEntitiesOfDifferentClassAreNotEqual() {
        final var tom = new Cat(1L);
        final var buch = new Dog(1L);

        Assertions.assertThat(tom)
            .as("entities of same identifier but different type aren't equal")
            .isNotEqualTo(buch);
    }

    @Test
    @DisplayName("entities of same type and different identifiers are different")
    void entitiesOfSameTypeAndDifferentIdsAreDifferent() {
        final var tom = new Cat(1L);
        final var sam = new Cat(2L);

        Assertions.assertThat(tom)
            .as("entities of same identifier but different type aren't equal")
            .isNotEqualTo(sam);
    }

    @Test
    @DisplayName("hashes are constant")
    void hashesAreConstant() {
        final var sam = new Cat();
        final var tom = new Cat(1L);
        final var tim = new Cat(2L);

        Assertions.assertThat(tom.hashCode())
            .isEqualTo(BaseEntity.HASH);
        Assertions.assertThat(sam.hashCode())
            .isEqualTo(BaseEntity.HASH);
        Assertions.assertThat(tim.hashCode())
            .isEqualTo(BaseEntity.HASH);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    private static class Cat extends BaseEntity<Cat, Long> {

        private Long id;

        @Override
        public Long id() {
            return id;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    private static class Dog extends BaseEntity<Dog, Long> {

        private Long id;

        @Override
        public Long id() {
            return id;
        }
    }
}
