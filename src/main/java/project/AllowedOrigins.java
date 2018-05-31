package project;

public class AllowedOrigins {

    public enum Origins {
        HEROKU_TEST("https://reallyawesomeapp.herokuapp.com/"),
        LOCALHOST_3000("http://localhost:3000"),
        HEROKU_DEPLOY("https://frontend-drive.herokuapp.com");

        private String origin;

        Origins(String s) {
            this.origin = s;
        }

        public static String[] toStringArray() {
            final String[] result = new String[Origins.values().length];
            int j = 0;
            for (Origins i : values()) {
                result[j++] = i.origin;
            }
            return result;
        }
    }
}
