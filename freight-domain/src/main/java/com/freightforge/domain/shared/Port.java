package  com.freightforge.domain.shared;

public record Port(String code, String name, String countryCode) {


    public Port{

        if(code == null || code.isBlank()){
            throw new IllegalArgumentException(" Port cannot be empty");
        }

        if(code.length != 5){
            throw new IllegalArgumentException("\"Port code must be exactly 5 characters (UN/LOCODE format). Got: \" + code");

        }
    code=code.toUpperCase();
    }

    public static Port of(String code) {
        return new Port(code, "", "");
    }

    // Human readable display
    public String display() {
        if (name != null && !name.isBlank()) {
            return name + " (" + code + ")";
        }
        return code;
    }
}