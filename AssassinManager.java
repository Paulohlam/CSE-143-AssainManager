// Paul Lam
// Section AG, Neel jog
// A3 AssassinManager
// AssassinManager gives the ability to track and see the history of who is/was stalking
// who and who is/was killing who.
import java.io.*;
import java.util.*;

public class AssassinManager {
   private AssassinNode killRingNode;
   private AssassinNode graveYardNode;

   // Purpose: Takes a list of names and creates a stalking order of who is
   // stalking who in the order that appears on the list.
   // Pre: List must not be empty. If conditions are violated,
   // (throws an IllegalArgumentException).
   // Post: Creates a kill ring in the order that follows the order of the list.
   // Parameter: names = names of assassin participants.
   public AssassinManager(List < String > names) {
      if (names.size() == 0) {
         throw new IllegalArgumentException();
      }
      killRingNode = new AssassinNode(names.get(0));
      AssassinNode current = killRingNode;
      for (int i = 1; i < names.size(); i++) {
         current.next = new AssassinNode(names.get(i));
         current = current.next;
      }
   }

   // Purpose: Prints the stalking order of the kill ring.
   // Pre: No pre conditions
   // Post: Prints the stalking order of the kill ring. If only one person
   // remains in the kill ring, they will stalk themself.
   public void printKillRing() {
      if (killRingNode.next == null) {
         System.out.println("    " + killRingNode.name + " is stalking " + killRingNode.name);
      }
      AssassinNode current = killRingNode;
      while (current.next != null) {
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      if (current.next == null) {
         System.out.println("    " + current.name + " is stalking " + killRingNode.name);
      }
   }

   // Purpose: Prints out the names in the grave yard.
   // Pre: No pre conditions.
   // Post: Prints out the name in the grave yard and who they were killed by.
   // The most recent killed will be printed first.
   public void printGraveyard() {
      AssassinNode current = graveYardNode;
      while (current != null) {
         System.out.println("    " + current.name + " was killed by " + current.killer);
         current = current.next;
      }
   }

   // Purpose: Checks to see if the given name is in the kill ring.
   // Pre: No pre conditions.
   // Post: Checks to see if the given name is present in the kill ring and
   // returns true if it is and false if not.
   // Parameter: names = names of assassin participants.
   public boolean killRingContains(String name) {
      boolean checker = nameChecker(killRingNode, name);
      return checker;
   }

   // Purpose: Checks to see if the given name is in the grave yard.
   // Pre: No pre conditions.
   // Post: Checks to see if the given name is present in the grave yard and
   // returns true if it is and false if not.
   // Parameter: names = names of assassin participants.
   public boolean graveyardContains(String name) {
      boolean checker = nameChecker(graveYardNode, name);
      return checker;
   }

   // Purpose: Checks to see if the game is over.
   // Pre: No pre conditions.
   // Post: Checks to see if the game is over (when one person reamins).
   // If the game is over, it will return true.
   public boolean gameOver() {
      return (killRingNode.next == null);
   }

   // Purpose: Returns the name of the winner.
   // Pre: No pre conditions.
   // Post: Returns the winner if the game is over (one person remains). If the game is
   // not over it will return null.
   public String winner() {
      if (gameOver()) {
         return killRingNode.name;
      }
      return null;
   }

   // Purpose: Tracks the killing of the given name and transfers them to the
   // grave yard. Given name casing is ignored.
   // Pre: Given name must be part of the current kill ring. If condition is violated,
   // (throws an IllegalArgumentException).
   // Game must be active (can not be over). If violated,
   // (throws an IllegalStateException).
   // Post: Tracks and trasnfers the killed name from the kill ring to the grave yard.
   // Given name casings are ignored.
   // Parameter: names = names of assassin participants.
   public void kill(String name) {
      name = name.toLowerCase();
      if (gameOver()) {
         throw new IllegalStateException();
      }
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      AssassinNode current = killRingNode;
      AssassinNode previous = killRingNode;
      AssassinNode pathToGrave = killRingNode;
      while (current.next != null && !current.next.name.equalsIgnoreCase(name)) {
         previous = current;
         current = current.next;
      }
      if (current.next == null) {
         pathToGrave = killRingNode;
         killRingNode = pathToGrave.next;
         pathToGrave.next = null;
         pathToGrave.next = graveYardNode;
         graveYardNode = pathToGrave;
         graveYardNode.killer = current.name;
      } else {
         pathToGrave = current.next;
         current.next = current.next.next;
         pathToGrave.next = null;
         pathToGrave.next = graveYardNode;
         graveYardNode = pathToGrave;
         graveYardNode.killer = current.name;
      }
   }

   // Purpose: Checks to see if the name is present.
   // Pre: No pre conditions.
   // Post: Checks to see if the name is present and returns true if it is
   // and false if not.
   // Parameter: names = names of assassin participants.
   // checker = rotates between kill ring or grave yard
   private boolean nameChecker(AssassinNode checker, String name) {
      name = name.toLowerCase();
      AssassinNode current = checker;
      while (current != null) {
         if (current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
}