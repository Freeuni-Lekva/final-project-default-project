package user.bean;

import java.util.StringTokenizer;

public class ValidateUser {
    private User usr;

    public ValidateUser(User usr) {
        this.usr = usr;
    }

    public String Validate() {
        boolean u = ValidateUser();
        boolean p = ValidatePassword();
        boolean m = ValidateMail();
        return !u ? "Wrong Name" : !p ? "Wrong Password" : !m ?  "Wrong Mail" : "OK";
    }

    private boolean ValidateUser() {
        String name = usr.getUserName();
        if(name.length() < 3 || name.length() >= 20)
            return false;
        return true;
    }

    private boolean ValidatePassword() {
        String pass = usr.getPassword();
        if(pass.length() < 4 || pass.length() > 15)
            return false;
        return true;
    }

    private boolean ValidateMail() {
        String mail = usr.getEMail();
        StringTokenizer tok = new StringTokenizer(mail, ".@");
        if(tok.countTokens() >= 3)
            if(mail.contains("@") && mail.contains("."))
                return true;
        return false;
    }
}