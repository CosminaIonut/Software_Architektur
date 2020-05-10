package sample.controller;


public class NoPlaceException extends Throwable {
    public void mymessage(){
        System.out.println("Keine Platze fur diesen Kurs");
    }
}
