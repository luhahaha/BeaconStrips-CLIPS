package beaconstrips.clips.client.viewcontroller.utility;

/**
 * Created by vivi on 20/08/16.
 */
public class risultatoProva {
    private String edificio;
    private String data;
    private String punteggio;
    private String durata;

    public String getData() {
        return data;
    }

    public String getDurata() {
        return durata;
    }

    public String getEdificio() {
        return edificio;
    }

    public String getPunteggio() {
        return punteggio;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public void setPunteggio(String punteggio) {
        this.punteggio = punteggio;
    }
}
