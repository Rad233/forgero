# Forgero Beta release 0.10.4

## ! Warning !

**This version is not compatible with earlier versions of Forgero: 0.9 and below, do not upgrade to this version unless
you have backed up your save.**

## Changelog 0.10.4.1

* Fixed patchouli guidebook recipe generator which caused a crash
* Fixed patchouli entries using old recipe format
* Added a gametest to the fabric compat module to check essential compatability
* Added a new recipe renderer for Forgero state recipes to correctly render cycled crafting output

## Changelog 0.10.4

* Fixed various tags which broke some recipes
* Fixed mod dependencies not applying properly causing compatability with other mods to break
* Added support for creating custom parts using resources
* Added tests for common recipes
* Optimised recipe creation process
* Improved performance when mining with forgero tools
* Implemented a lot of caching to help with expensive calculations being made too often
* Fixed crash when viewing tooltip with languages using commas instead of periods for decimals
* Fixed mining level not applying to modded blocks

## Changelog 0.10.3

* Fixed compatability with Roughly enough resources
* Changed data loading to never load disabled packages
* Fixed mining levels on tools
* Added drop to Assembly table
* Added assembly table to mineable tag
* Fixes various issues with duplicated mining speed application and calculations

## Changelog 0.10.2

* Fixed path mining pickaxe pattern mining description
* Implemented basic resource reloading data/assets
* Fixed patchouli advancement only generating when patchouli is loaded
* Improved assembly table model and voxel shape
* Assembly table drops inventory when closed
* Removed custom Forgero tool and weapon groups
* Removed generated oak variants of extended tools

## Changelog 0.10.1

* Removed uses of Fabric resource generators. Replaced with dynamic ARRP generators.
* Fixed mythicmetals integration, and optimised some materials
* Fixed materials count for reinforced handle
* Created a naming split element, to make it easier to negate spacing in names for some languages

## Changelog 0.10.0

* Completely revamped the core design and structure of Forgero
* Basically rewrote the system from scratch ( Yay..... )
* Added several new and unique schematics
* New module and pack systems to handle new content added via Forgero
* Packs added: vanilla, extended, structures and compatability
* New undying totem upgrade available
* New assembly station for taking apart tools
* New structures where the assembly station can be found
* Native support for Sodium(Indium is no longer required)
* Performance improvements when rendering item models
* Repair kits for all materials (craft base kit with leather + iron, then craft repair kit from base kit + desired
  material)
* New materials available (Polar bear pelt)


