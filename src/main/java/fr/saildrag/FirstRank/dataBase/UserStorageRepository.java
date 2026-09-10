package fr.saildrag.FirstRank.dataBase;

import fr.saildrag.library.storage.Repository;

public class UserStorageRepository implements Repository<UserStorage, UserStorageDTO> {
    @Override
    public UserStorage toEntity(UserStorageDTO userStorageDTO) {
        return new UserStorage(
                userStorageDTO.playerId(),
                userStorageDTO.id(),
                userStorageDTO.value()
        );
    }

    @Override
    public UserStorageDTO toDTO(UserStorage entity) {
        return new UserStorageDTO(
                entity.getPlayerId(),
                entity.getId(),
                entity.getValue()
        );
    }
}
