package me.ianhe.config;

import me.ianhe.exception.SystemException;
import me.ianhe.utils.Global;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CommonConfig {

    private CommonConfig() {
        throw new SystemException("工具类不允许实例化！");
    }

    private static final String MAIL_CONFIG_FILE = "mail_config.yml";
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonConfig.class);

    public static class MailConfigEntry {
        public String mailServer;
        public String mailPort;
        public String mailUser;
        public String mailPassword;
        public String mailFromAddress;
        public String mailFromName;
    }

    public static MailConfigEntry mailConfigEntry;

    private static String webappRoot;

    private static String contextPath;

    public static String getWebappRoot() {
        return webappRoot;
    }

    public static String getContextPath() {
        return contextPath;
    }

    public static MailConfigEntry getMailConfigEntry() {
        return mailConfigEntry;
    }

    public static void init(String rootPath, String contextName) {
        contextPath = contextName;
        webappRoot = rootPath;
        try {
            mailConfigEntry = loadConfig(MAIL_CONFIG_FILE, MailConfigEntry.class);
        } catch (RuntimeException e) {
            LOGGER.warn("mail not configured", e);
        }
    }

    public static <T> T loadConfig(String cfgFileName, Class<T> clazz) {
        return loadConfig(new File(Global.getClassPath(), cfgFileName), clazz);
    }

    public static <T> T loadConfig(InputStream inputStream, Class<T> clazz) {
        if (inputStream == null)
            throw new SystemException("InputStream is null.");
        try {
            return Yaml.loadType(inputStream, clazz);
        } catch (FileNotFoundException e) {
            throw new SystemException("Can not find " + inputStream.toString());
        }
    }

    public static <T> T loadConfig(File file, Class<T> clazz) {
        if (file == null)
            throw new SystemException("Loading file is null.");
        try {
            return Yaml.loadType(file, clazz);
        } catch (FileNotFoundException e) {
            throw new SystemException("Can not find " + file.getAbsolutePath());
        }
    }
}
