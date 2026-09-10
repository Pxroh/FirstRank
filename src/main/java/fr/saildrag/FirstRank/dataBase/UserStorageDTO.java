package fr.saildrag.FirstRank.dataBase;

import fr.maxlego08.sarah.Column;

import java.util.UUID;

public record UserStorageDTO(
        @Column(value = "player_id", primary = true) UUID playerId,
        @Column(value = "unique_id", primary = true) String id,
        int value
) { }