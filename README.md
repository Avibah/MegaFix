# MegaFix
 Fixes Mega Boss spawning on Pixelmon in the simplest way possible!
 This sidemod is only required serverside.

 Note: This fix relies on enabling an event which could cause a Mega Boss to misreport itself as a Legendary while it selects a player to spawn on. If you or another mod you installed is using the 'LegendarySpawnEvent.ChoosePlayer' event, you may want to add a check in your implementation and/or ensure it is not handled in a way you did not intend. Successful spawns, however, are not impacted by this change.
