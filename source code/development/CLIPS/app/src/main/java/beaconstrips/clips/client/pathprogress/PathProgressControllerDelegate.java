package beaconstrips.clips.client.pathprogress;

import beaconstrips.clips.client.data.Proximity;
import beaconstrips.clips.client.data.Step;

/**
 * @file PathProgressControllerDelegate.java
 * @date 22/07/16
 * @version 1.0.0
 * @author Luca Soldera
 *
 *interfaccia che serve per notificare quando si nel raggio di un beacon del prossimo step oppure in quello di un proximity
 */

interface PathProgressControllerDelegate {
   void didReachStep(Step step);
   void didRangeProximity(Proximity proximity);
}