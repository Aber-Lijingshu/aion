package org.aion.db.impl.mockdb;

import org.aion.base.db.IBytesKVDB;
import org.aion.db.impl.IDriver;
import org.aion.log.AionLoggerFactory;
import org.aion.log.LogEnum;
import org.slf4j.Logger;

import java.util.Properties;

/**
 * Mock implementation of a key value database using a ConcurrentHashMap as our
 * underlying implementation, mostly for testing, when the Driver API interface
 * is create, use this class as a first mock implementation
 *
 * @author yao
 */
public class MockDBDriver implements IDriver {

    private static final Logger LOG = AionLoggerFactory.getLogger(LogEnum.DB.name());

    private static final int MAJOR_VERSION = 1;
    private static final int MINOR_VERSION = 0;

    private static final String PROP_DB_TYPE = "db_type";
    private static final String PROP_DB_NAME = "db_name";

    /**
     * @inheritDoc
     */
    @Override
    public IBytesKVDB connect(Properties info) {

        String dbType = info.getProperty(PROP_DB_TYPE);
        String dbName = info.getProperty(PROP_DB_NAME);

        if (!dbType.equals(this.getClass().getName())) {
            LOG.error("Invalid dbType provided: {}", dbType);
            return null;
        }

        return new MockDB(dbName);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getMajorVersion() {
        return MAJOR_VERSION;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getMinorVersion() {
        return MINOR_VERSION;
    }
}
