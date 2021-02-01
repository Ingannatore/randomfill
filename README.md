# RandomFill

RandomFill is a Bukkit/Spigot plugin that adds a new command to randomly replace air blocks in a parallelepiped-shaped area using a specified materials preset.

## Usage

RandomFill's syntax is similar to the `fill` command's one:

    /randomfill <from> <to> <preset>

Or using its alias:

    /rfill <from> <to> <preset>

Example:

    /randomfill ~ ~-1 ~4 ~3 ~-20 ~7 minepit

### Arguments

* `<from>`: area coordinates of the first corner
* `<to>`: area coordinates of the opposite corner
* `<preset>`: name of the preset (as defined in the "presets.json" file)

Coordinates can be specified as absolute (`123 64 354`) or relative with the "~" notation (`~ ~-1 ~4`); right now there is no support for the "^" notation.

### Permissions

Use the `ingannatore.randomFill` permission; by default only OPs can use this command.

### Command blocks

The command can also be used in a command block; in such case, relative coordinates will be calculated from the command block's position.

Public command block can be used by players even if they are not an OP.

## Presets

Each preset must have a unique name, with a list of weighted materials:

    {
        "name": "mypreset",
        "items": [
            {
                "material": "STONE",
                "weight": 0.5
            },
            {
                "material": "COAL_ORE",
                "weight": 0.3
            },
            {
                "material": "IRON_ORE",
                "weight": 0.2
            }
        ]
    }

Each item is defined by:

* `material`: Bukkit/Spigot material's ID
* `weight`: distribution percentage in decimal form (ie, 0.5 for 50%)

To create "holes" inside the filled area, set one `material` to `null`: 

    {
        "name": "mypreset",
        "items": [
            {
                "material": null,
                "weight": 0.2
            },
            {
                "material": "STONE",
                "weight": 0.6
            },
            {
                "material": "DIAMOND_ORE",
                "weight": 0.2
            }
        ]
    }

## Release notes

This plugin is still in a beta phase, so do expect errors, crashes and the likes.

In such cases, please take some time to open an issue and tell me the problem you encountered: your help is really appreciated.

## License

This project is licensed under the [GNU General Public License v3.0](https://choosealicense.com/licenses/gpl-3.0/).
