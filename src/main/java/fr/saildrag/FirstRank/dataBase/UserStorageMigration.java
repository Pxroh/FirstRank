package fr.saildrag.FirstRank.dataBase;

import fr.maxlego08.sarah.database.Migration;
import fr.saildrag.library.storage.SQLStorage;

public class UserStorageMigration extends Migration {

    private final String table;

    public UserStorageMigration(String table) {
        this.table = table;
    }

    @Override
    public void up() {
        createOrAlter("%prefix%" + table, SQLStorage.createConsumerFromTemplate(UserStorageDTO.class,(Object)null));
    }
}
