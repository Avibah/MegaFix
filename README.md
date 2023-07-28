# MegaFix
 Fixes Mega Boss spawning on Pixelmon 9.1.5!
 This sidemod is only required server-side, or client-side if you are playing Singleplayer. Mods that detect Mega Boss spawns, such as those that post messages to the chat, will continue to function with this mod installed.

 Note: This fix relies on enabling an event which could cause a Mega Boss to misreport itself as a Legendary while it selects a player to spawn on. If you or another mod you installed are using the 'LegendarySpawnEvent.ChoosePlayer' event, you may want to add a check in your implementation and/or ensure it is not handled in a way you did not intend. Successful spawns, however, are not impacted by this change.

 Other note: The event that this mod enables also makes the spawn chance of Mega Bosses the same as that of Legendaries. To combat this, some math is applied to simulate a higher or lower percent chance using the time between each spawn attempt. Due to the nature of chance, some Mega Bosses may spawn very close to each other or very far apart depending on how far apart your desired percentages are.
 
 A Config entry is provided in case you would like to disable adjusting the spawn time for spawn chances.
