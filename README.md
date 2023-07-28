# MegaFix
 Fixes Mega Boss spawning on Pixelmon in the simplest way possible!
 This sidemod is only required server-side, or client-side if you are playing Singleplayer. Mods that detect Mega Boss spawns, such as those that post messages to the chat, will continue to function with this mod installed.

 Note: This fix relies on enabling an event which could cause a Mega Boss to misreport itself as a Legendary while it selects a player to spawn on. If you or another mod you installed are using the 'LegendarySpawnEvent.ChoosePlayer' event, you may want to add a check in your implementation and/or ensure it is not handled in a way you did not intend. Successful spawns, however, are not impacted by this change.

 Other note: The event that this mod enables also makes the spawn chance of Mega Bosses the same as the chance of Legendaries, which I am unable to change myself. Consider changing the ticks between spawns if you wish to simulate having a different percentage from Legendary spawning.
