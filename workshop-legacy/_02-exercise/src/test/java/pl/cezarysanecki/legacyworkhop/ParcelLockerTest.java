package pl.cezarysanecki.legacyworkhop;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParcelLockerTest {

    User aClient = new User(UUID.randomUUID());

    ParcelLocker parcelLocker = ParcelLocker.empty();

    @Test
    void parcelLockerIsAssignedToUserWhenItIsLockedForHim() {
        //given
        Instant now = Instant.now();

        //when
        parcelLocker.lockFor(aClient, now);

        //then
        assertEquals(aClient, parcelLocker.getAssignedTo());
        assertEquals(now.plus(Period.ofDays(1)), parcelLocker.getLockUntil());
    }

    @Test
    void canReleaseParcelLockerIfItIsAssignedToUser() {
        //given
        Instant now = Instant.now();
        parcelLocker.lockFor(aClient, now);

        //when
        parcelLocker.open(aClient, now);

        //then
        assertNull(parcelLocker.getAssignedTo());
        assertNull(parcelLocker.getLockUntil());
    }

    @Test
    void canProlongLockIfUserIsInTimeWindowToBeAbleToDoThis() {
        //given
        Instant lockTime = Instant.now();
        parcelLocker.lockFor(aClient, lockTime);

        Instant prolongTime = lockTime.plus(Period.ofDays(1)).minus(Duration.ofMinutes(10));

        //when
        parcelLocker.prolong(prolongTime);

        //then
        assertEquals(lockTime.plus(Period.ofDays(2)), parcelLocker.getLockUntil());
    }

}