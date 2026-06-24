# Changelog (last date I updated this date: 22/6/2026 :D)

All changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

Newer versions' changes are added above the older ones

Branch: Main branch

## [0.5a]

### Added:
- ~~Sided inventories to Thingamajig, as well as Energy and Fluid handling~~ Removed Course-specific content that doesn't fit in the mod
- Rainstone, a fluid you can stand on!
- Created an included library
- Added the Race Selection Screen! (not functional)
  - Added an open screen command to open said screen on demand

### Commits:
- 11/4/2026: Added sided inventories to Thingamajig
- 14/4/2026: Uploading broken code for hopefully some help from other people
- 25/4/2026: Changed the code, broken in other ways
- 28/4/2026: **FIXED ALL THE BUGS!!!**
- 28/4/2026: Fixed models a little. Now I can finally play with Create: Aeronautics! (more at [.github/info/minor_setback.md](../info/minor_setback.md))
- 28/4/2026: Some little bugfixes I missed
- 30/4/2026: Added the Rainstone Shard and allowed merging for [SolidFluidBlock](/src/main/java/net/MrGise/mmm/block/fluid/SolidFluidBlock.java)
- 1/5/2026: Started making the included library, looking for help
- 2/5/2026: Made the included library. It was easier than I expected!
- 5/5/2026: Added FE handling for [thingamajig](/src/main/java/net/MrGise/mmm/block/entity/ThingamajigBlockEntity.java), advancing the course
- 7/5/2026: Added the Race Selection Screen (WIP), it's utterly non-functional at the moment.
- 9/5/2026: Remade this mod's screen rendering to use a more modular system. Also, changed the GUI texture a bit.
- 9/5/2026: Created a helper method to make Contexts easier
- 9/5/2026: text y fix
- 14/5/2026: Added Confetti for my birthday, which is On the same day as Minecraft's!
- 15/5/2026: Made Party Popper non-stackable
- 15/5/2026: Finished splitting project
- 17/5/2026: Confettier!
- 17/5/2026: Confettierer!
- 17/5/2026: Removed Unnecessary stuff
- 21/5/2026: Fixed the race selection menu crashing when the onPlayerFirstJoin event is activated
- 30/5/2026: We're going bowling now!
- 22/6/2026: An incomplete bowl update
  - 22/6/2026: small fix
- 23/6/2026: Added rendering and fixed some things for the bowl. It doesn't render when re-joining a world.
- 24/6/2026: Fixed a rendering issue! (With ChatGPT's help... >~<)

## [0.4.1a] (skipping the -1.20.1 because it's not important)
For the changelog summary, visit [.github/logs](../logs/0.4.1a-1.20.1.md)
### Added:
- JEI support (advancing the course)
- Changelog will be updated from now on for new versions
- Added changelog for 0.4a-passover
- Changed, added and updated descriptions
- Summarized changelog

### Commits:
- 04/4/2026: Added Thingamajig JEI support and changed some directories
- 04/4/2026: Added Bowyery Table JEI support and required JEI in the
[mods.toml](/src/main/resources/META-INF/mods.toml) file
- 04/4/2026 (multiple): Updated README and changelog
- 04/4/2026: Added transfer handling for JEI compatibility and animated the arrow in the Thingamajig JEI screen
- 04/4/2026: Changed version because it's not that major + updated it in the gradle.properties file +
Reminder to check the changelog for more information
- 04/4/2026: Oop, sorry, forgot to update the changelog for the commit
- 05/4/2026: Added maxTransfer (filling with shift) support to the bowyery JEI support (buggy AF)
- 05/4/2026: Unbugged, for the cost of slight weirdness
- 06/4/2026: Very small changes
- 07/4/2026: Updated README.md
- 08/4/2026: Updated changelog
- 09/4/2026: FIXED EVERYTHING
- 09/4/2026: Description update!
- 10/4/2026: Updated changelog (+summary) and small bugfix
- 10/4/2026: Fixed ModTags.Items.ACTINOLITE link
