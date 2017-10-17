package me.ianhe.db.plugin;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL Dialect
 *
 * @author iHelin
 * @since 2017/10/17 15:26
 */
public abstract class AbstractDialect {

    protected static final String DEF_SQL_LIMIT = " limit ";

    protected static final String DEF_SQL_LIMIT_CONNECTOR = ",";

    public enum Type {

        /**
         * MySQL
         */
        MYSQL,
        /**
         * Oracle
         */
        ORACLE;

        public static AbstractDialect getDialet(String dialectType) {
            if (StringUtils.isBlank(dialectType)) {
                throw new RuntimeException("The value of the dialect property of plugin in mybatis-config.xml " +
                        "is not defined!");
            }
            switch (valueOf(dialectType.trim().toUpperCase())) {
                case MYSQL:
                    return new MysqlDialect();
                case ORACLE:
                default:
                    throw new RuntimeException("The dialect is not supported!");
            }
        }
    }

    public boolean supportsLimit() {
        return false;
    }

    public boolean supportsLimitOffset() {
        return supportsLimit();
    }

    /**
     * getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit))
     *
     * @param sql
     * @param offset
     * @param limit
     * @return
     */
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
    }

    /**
     * select * from table limit offsetPlaceholder,limitPlaceholder
     *
     * @param sql
     * @param offset
     * @param offsetPlaceholder
     * @param limit
     * @param limitPlaceholder
     * @return
     */
    public abstract String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder);

}