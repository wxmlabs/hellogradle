package com.wxmlabs.hellogradle.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
        Locale locale = null;
        if (args.length == 1) {
            String lang = args[0];
            for (Locale l : Locale.getAvailableLocales()) {
                if (l.getLanguage().equals(new Locale(lang).getLanguage())) {
                    locale = l;
                }
            }
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        App app = new App();
        String greeting = app.getGreeting(locale);
        try {
            System.out.println(String.format("Greeting: %s", greeting));
            String result = app.echo(greeting);
            System.out.println(String.format("Result: %s", result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String echo(String input) throws IOException {
        BufferedReader reader = null;
        try {
            HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:8080").openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setUseCaches(false);
            http.setDoOutput(true);
            http.setDoInput(true);
            // request
            http.connect();
            OutputStream output = http.getOutputStream();
            String jsonCmd = "{";
            jsonCmd += "\"cmd\":\"echo\",";
            jsonCmd += "\"content\":\"" + input + "\"";
            jsonCmd += "}";
            output.write(jsonCmd.getBytes("UTF-8"));
            output.flush();
            output.close();
            // response
            int code = http.getResponseCode();
            if (200 == code) {
                reader = new BufferedReader(
                        new InputStreamReader(http.getInputStream()));
                String result = reader.readLine();
                return result;
            } else {
                String msg = String.format("request failed: %s(%d)", http.getResponseMessage(), code);
                throw new IOException(msg);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignore) {
                }
            }
        }
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
