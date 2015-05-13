package org.smarty.core.app;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;

/**
 * AbsWebApplication
 */
public abstract class AbsWebApplication extends Application {
    private ConnectionFactoryRegistry cfr;
    private SQLiteOpenHelper soh;
    private ConnectionRepository cr;

    @Override
    public void onCreate() {
        cfr = new ConnectionFactoryRegistry();
        cfr.addConnectionFactory(getConnectionFactory());
        soh = new SQLiteConnectionRepositoryHelper(this);
        cr = new SQLiteConnectionRepository(soh, cfr, getTextEncryptor());
    }

    protected abstract TextEncryptor getTextEncryptor();

    protected abstract ConnectionFactory getConnectionFactory();

    public ConnectionRepository getConnectionRepository() {
        return cr;
    }

    public ConnectionFactory getConnectionFactory(Class cls) {
        return cfr.getConnectionFactory(cls);
    }
}
