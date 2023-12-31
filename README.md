# MegaFix
 Fixes Mega Boss (and now Wormhole) spawning on Pixelmon 9.1.5!
 This sidemod is only required server-side, or client-side if you are playing Singleplayer. Mods that detect Mega Boss spawns, such as those that post messages to the chat, will continue to function with this mod installed.

 Note: This fix relies on enabling an event which could cause a Mega Boss to misreport itself as a Legendary while it selects a player to spawn on. If you or another mod you installed are using the 'LegendarySpawnEvent.ChoosePlayer' event, you may want to add a check in your implementation and/or ensure it is not handled in a way you did not intend. Successful spawns, however, are not impacted by this change.

 Other note: The event that this mod enables also makes the spawn chance of Mega Bosses the same as that of Legendaries. To combat this, Mega Boss spawn times are adjusted for how much more or less common successful Legendary spawns are in relation to Mega Bosses, while keeping the percentage the same as Legendaries. Due to the nature of chance, some Mega Bosses may spawn very close to each other or very far apart depending on how far apart your desired percentages are.
 
 A Config entry is provided in case you would like to disable adjusting the spawn time for spawn chances.


--------------------

 As of version 1.1, Ultra Wormholes can now naturally spawn in the world. More config entries are added for you to be able to customize how they spawn and when. A Spawn Event is fired alongside the spawning of a Wormhole so broadcasts can be made using it, if implemented.

 Also, you are able to enable or disable each of the two fixes contained in the mod using their respective config entries, which are both set to enabled by default.
