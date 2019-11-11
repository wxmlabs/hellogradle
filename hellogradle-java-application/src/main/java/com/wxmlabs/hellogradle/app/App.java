package com.wxmlabs.hellogradle.app;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public String getGreeting(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        ResourceBundle message = ResourceBundle.getBundle("message", locale, new EncodingResourceBundleControl("utf-8"));
        return message.getString("greeting");
    }

    public String getGreeting() {
        return getGreeting(Locale.getDefault());
    }

    public static void main(String[] args) {
        Locale locale;
        if (args.length == 1) {
            String lang = args[0];
            locale = Locale.forLanguageTag(lang);
        } else {
            locale = Locale.getDefault();
        }
        System.out.println(new App().getGreeting(locale));
    }

    private static final class EncodingResourceBundleControl
            extends ResourceBundle.Control {
        private final static Logger log = Logger.getLogger(EncodingResourceBundleControl.class.getName());
        private final String encoding;

        private EncodingResourceBundleControl(String encoding) {
            this.encoding = encoding;
        }

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale,
                                        String format, ClassLoader loader,
                                        boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            URL resourceURL = loader.getResource(resourceName);
            if (resourceURL != null) {
                try {
                    return new PropertyResourceBundle(new InputStreamReader(resourceURL.openStream(), encoding));
                } catch (Exception z) {
                    log.log(Level.FINE, "exception thrown during bundle initialization", z);
                }
            }

            return super.newBundle(baseName, locale, format, loader, reload);
        }
    }
}
