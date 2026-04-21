package connection;

public class Conf {
    public static class ConfigDB {
        private  final String USER = "root";
        private  final String PASS = "root";
        private  final String URL = "jdbc:mysql://localhost:3306/Mi_Aucorsa";


        public String getUrl(){
            return this.URL;
        }

        public String getUSER(){
            return this.USER;
        }

        public String getPass(){
            return this.PASS;
        }
    }
}
