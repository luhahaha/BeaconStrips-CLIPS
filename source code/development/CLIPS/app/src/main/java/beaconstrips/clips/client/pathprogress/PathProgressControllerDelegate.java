package beaconstrips.clips.client.pathprogress;

import java.io.Serializable;

import beaconstrips.clips.client.data.Proof;

/**
 * @file PathProgressControllerDelegate.java
 * @date 22/07/16
 * @version 1.0.0
 * @author Luca Soldera
 *
 *interfaccia che serve per notificare quando si nel raggio di un beacon del prossimo step oppure in quello di un proximity
 */

public interface PathProgressControllerDelegate extends Serializable{
   void didReachProof(Proof proof);
   void didRangeProximity(double percentage,String textToDisplay);
    void pathEnded(double totalScore);
}
