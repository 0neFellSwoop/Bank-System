package banking;

public class PassTimeCommandValidator {


    public boolean validate(String[] command) {
        if(command.length != 2){
            return false;
        } else {
            int months;
            try {
                months = Integer.parseInt(command[1]);
            } catch (NumberFormatException e) {
                return false;
            }
            return months > 0 && months <= 60;
        }
    }
}
