package beaconstrips.clips.client.urlrequest;

/**
 * @file ServerError.java
 * @date 24/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene i dati relativi agli errori inviati dal server o riscontrati dal client, i parametri previsti, che possono non essere inizializzati, sono errorCode, il codice dell'errore, userMessage, il messaggio destinato all'utente dove viene spiegato che errore è stato riscontrato, e debugMessage, il messaggio destinato agli sviluppatori dove viene spiegato in modo più tecnico l'errore riscontrato
 */
public class ServerError {
   public int errorCode;
   public String debugMessage;
   public String userMessage;

   public ServerError(int errorCode, String debugMessage, String userMessage) {
      this.errorCode = errorCode;
      this.debugMessage = debugMessage;
      this.userMessage = userMessage;
   }
}
