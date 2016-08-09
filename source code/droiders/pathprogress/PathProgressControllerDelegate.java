package pathprogress;

interface PathProgressControllerDelegate {
   void didReachStep(Step step);
   void didRangeProximity(Proximity proximity);
}
