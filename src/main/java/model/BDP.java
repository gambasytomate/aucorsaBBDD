package model;

public class BDP {
    private String register;      // Matrícula del autobús (FK a Bus)
    private int numdriver;        // ID del conductor (FK a Driver)
    private int idplace;          // ID del lugar/ruta (FK a Place)
    private String dayOfWeek;     // Día de la semana que opera

    // Constructor completo
    public BDP(String register, int numdriver, int idplace, String dayOfWeek) {
        this.register = register;
        this.numdriver = numdriver;
        this.idplace = idplace;
        this.dayOfWeek = dayOfWeek;
    }

    // Constructor sin día de la semana
    public BDP(String register, int numdriver, int idplace) {
        this.register = register;
        this.numdriver = numdriver;
        this.idplace = idplace;
        this.dayOfWeek = null;
    }

    // Getters y Setters
    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public int getNumdriver() {
        return numdriver;
    }

    public void setNumdriver(int numdriver) {
        this.numdriver = numdriver;
    }

    public int getIdplace() {
        return idplace;
    }

    public void setIdplace(int idplace) {
        this.idplace = idplace;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "BDP{" +
                "register='" + register + '\'' +
                ", numdriver=" + numdriver +
                ", idplace=" + idplace +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                '}';
    }
}

