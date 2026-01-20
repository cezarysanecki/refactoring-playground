package pl.cezarysanecki.legacyworkhop;

public interface ClaimRepository {

    Claim getOne(Long id);

    Claim save(Claim claim);
}
