package com.mycompany.app;
import static com.rollbar.notifier.config.ConfigBuilder.withAccessToken;

import java.util.HashMap;

import com.rollbar.api.payload.data.Server;
import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;

public class App {
    private Rollbar rollbar;

    public String getGreeting() {
        return "Hello World!";
    }
    public void setRollbar() {

        Config config = withAccessToken("yourtokenhere")
            .environment("production")
            .codeVersion("1.0.0")
            .build();
        this.rollbar = Rollbar.init(config);
    }

    public void sendError() throws Exception {
        throw new Exception("Something bad happened!");
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.getGreeting());
        app.setRollbar();
        try {
            app.sendError();
        } catch (Exception e) {
            HashMap<String,Object> map=new HashMap<String, Object>();
            map.put("Id","123");
            map.put("User Name","John Doe");
            map.put("Email","john@doe.com");
            app.rollbar.error(e, map);
        }
        try {
            app.rollbar.close(true);
        } catch (Exception e) {
            app.rollbar.error(e);
        }
      
    }
}
