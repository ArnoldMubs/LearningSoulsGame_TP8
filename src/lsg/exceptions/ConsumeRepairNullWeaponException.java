package lsg.exceptions;

public class ConsumeRepairNullWeaponException extends Throwable {
    public ConsumeRepairNullWeaponException(){
        super("Trying to repair null weapon ! ");
    }
}
